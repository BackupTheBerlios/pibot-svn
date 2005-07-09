package bot;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import bot.listener.BotListener;

public class FeatureLoader {

	// directory settings
	private static final String BASE_DIR = "bin\\";
	private String featureDir;
 
	private Set<Class> knownListenerInterfaces = new HashSet<Class>();
	
	private Map<Class, BotListener> registeredFeatures = new HashMap<Class, BotListener>();
	private Map<Class, List<Class>> dependencies = new HashMap<Class, List<Class>>();
	
	private List<Class> uninitializedFeatures = new ArrayList<Class>();

	private EventNotificationBot bot;

	/**
	 * Initializes the FeatureLoader object with the given directory and the supplied
	 * bot. Adds all the interfaces to the known interfaces for which the bot provides
	 * according setter methods.
	 * @param bot the bot for which the features are loaded
	 * @param featureDir the directory where the features should be searched
	 */
	public FeatureLoader(EventNotificationBot bot, String featureDir) {
		this.featureDir = featureDir;
		this.bot = bot;

		addAcceptedInterfaces(bot.getClass());
	}
	
	/**
	 * retrieves the interfaces that are supported by the supplied bot.
	 * this is done by querying the bot for methods with the 
	 * @param cls the bot class for which the supplied interfaces should be retrieved
	 */
	private void addAcceptedInterfaces(Class<? extends EventNotificationBot> cls) {
		Method[] methods = cls.getMethods();
		for (Method method : methods) {
			String methodName = method.getName();
			if(methodName.startsWith("add") && methodName.endsWith("Listener")) {
				StringBuilder className = new StringBuilder(methodName.replaceAll("add", ""));
				className.insert(0, "bot.listener.");
				try {
					knownListenerInterfaces.add(Class.forName(className.toString()));
				}
				catch(Exception ex) {
					// should not happen, if the method is declared the interface
					// should be there in compiled form
					System.out.println("an error occcured while adding the accepted interfaces");
					// TODO: insert logging
				}
			}
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public List<String> getRegisteredFeatures() {
		List<String> features = new ArrayList<String>();
		for (Class cls : registeredFeatures.keySet()) {
			features.add(cls.getSimpleName());
		}
		return features;
	}

	/**
	 * loads all features from the featuredirectory and initializes them.
	 * first the feature directory is parsed for features.
	 * next those features are registeres with the FeatureLoader and register
	 * their dependencies.
	 * next all dependencies are tried to be resolved.
	 * then the features for which some dependencies could not be satisfied are being removed.
	 * and finally the features are added to the bot and initialized with the bot
	 */
	public void load() {
		List<String> featureNames = parseDirectory(featureDir);
		
		registerAllFeatures(featureNames);
		resolveDependencies();
		
		removeUninitalizedFeatures();
			
		initializeFeatures();
	}
	
	/**
	 * 
	 * @param path
	 * @return
	 */
	private List<String> parseDirectory(String path) {
		List<String> featureNames = new ArrayList<String>();

		File featureDir = new File(BASE_DIR + path);
		File[] files = featureDir.listFiles();
		if (files == null)
			return null;
		for (File file : files) {
			String fileName = file.getName();
			if (fileName.endsWith(".class")) {
				String className = fileName.replaceAll(".class", "");
				className = path + "." + className;
				featureNames.add(className);
			}
		}
		return featureNames;
	}
	
	/**
	 * 
	 * @param features
	 */
	private void registerAllFeatures(List<String> features) {
		for (String feature : features) {
			registerFeature(feature);
		}
	}
	
	/**
	 * 
	 * @param listenerName
	 */
	private void registerFeature(String listenerName) {
		try {
			Class cls = Class.forName(listenerName);
			BotListener listener = (BotListener) cls.newInstance();

			if (registeredFeatures.containsKey(cls))
				return;
			
			registeredFeatures.put(cls, listener);
			registerDependencies(listener);
			
		} catch (Exception e) {
			// only happens when class does not implement the bot
			// listener interface => no problem as nothing is done
			// with the class
		}
	}
	
	/**
	 * 
	 * @param listener
	 */
	private void registerDependencies(BotListener listener) {
		for (Class cls : listener.getDependencies()) {
			if(!dependencies.containsKey(cls)) {
				List<Class> depList = new ArrayList<Class>();
				depList.add(listener.getClass());
				dependencies.put(cls, depList);
			}
			else {
				dependencies.get(cls).add(listener.getClass());
			}		
		}
	}
	
	/**
	 * 
	 *
	 */
	private void resolveDependencies() {	
		for (Class<? extends BotListener> cls : dependencies.keySet())
			resolveDependenciesForClass(cls);	
	}
	
	/**
	 * 
	 * @param cls
	 */
	private void resolveDependenciesForClass(Class<? extends BotListener> cls) {
		BotListener curListener = registeredFeatures.get(cls);
		if(curListener != null) {
			for (Class curClass : dependencies.get(cls)) {
				BotListener listener = registeredFeatures.get(curClass);
				try {
					invokeMethod(listener, "set" + cls.getSimpleName(), cls, curListener);
				}
				catch(Exception e) {
					uninitializedFeatures.add(listener.getClass());
					// TODO: insert logging
					System.out.println("failed to initialize plugin: " + listener.getClass());
				}
			}
		}
		else {
			for (Class curClass : dependencies.get(cls)) {
				System.out.println("failed to initialize plugin: " + curClass.getSimpleName());
				// TODO: insert logging
				uninitializedFeatures.add(curClass);
			}
		}
	}
	
	/**
	 * 
	 *
	 */
	private void removeUninitalizedFeatures() {
		for (Class cls : uninitializedFeatures)
			registeredFeatures.remove(cls);
		uninitializedFeatures.clear();
	}

	/**
	 * 
	 *
	 */
	private void initializeFeatures() {
		for (BotListener listener : registeredFeatures.values()) {
			listener.setBot(bot);
			try {
				handleInterfaces(listener.getClass(), listener, "add");
			}
			catch(Exception ex) {
				// should not happen as all known interfaces have appropriate 
				// methods in the class; if it happens the bot class has to
				// be updated or the known interfaces collection is false
				System.out.println("an interface is missing");
				// TODO: insert logging
			}
		}
	}
	
	/**
	 * 
	 * @param cls
	 * @param listener
	 * @param methodName
	 * @throws NoSuchMethodException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	private void handleInterfaces(Class<? extends BotListener> cls, BotListener listener,
			String methodName) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		
		Class[] interfaces = cls.getInterfaces();
		for (Class curClass : interfaces) {
			if (knownListenerInterfaces.contains(curClass)) {
				StringBuffer name = new StringBuffer(curClass
						.getSimpleName());
				name.insert(0, methodName);
				invokeMethod(bot, name.toString(), curClass, listener);
			}
		}
	}
	
	/**
	 * 
	 * @param receiver
	 * @param methodName
	 * @param parameterType
	 * @param argument
	 * @throws NoSuchMethodException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	private void invokeMethod(Object receiver, String methodName, Class parameterType,
			Object argument) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		Class[] paramTypes = new Class[1];
		paramTypes[0] = parameterType;
		Method meth = receiver.getClass().getMethod(methodName, paramTypes);

		Object[] argList = new Object[1];
		argList[0] = parameterType.cast(argument);

		meth.invoke(receiver, argList);
	}

	/**
	 * 
	 *
	 */
	public void unload() {
		List<String> featureNames = parseDirectory(featureDir);
		for (String feature : featureNames) {
			unRegister(feature);
		}
	}

	/**
	 * 
	 * @param listenerName
	 */
	public void unRegister(String listenerName) {
		try {
			Class cls = Class.forName(listenerName);
			BotListener listener = (BotListener) cls.newInstance();

			registeredFeatures.remove(cls);

			handleInterfaces((Class<? extends BotListener>)cls, listener, "remove");

		} catch (Exception e) {
			System.out.println(e.toString());
			// TODO: handle exception
		}
	}




}
