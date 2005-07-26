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
import java.util.logging.Logger;

import bot.listener.BotListener;
import bot.logging.Logging;

/**
 * Dynamically loads and registerse features, that implement at least one of the supported 
 * interfaces of the bot that is supplied in the constructor.
 * Those features are loaded from the specified feature directory and afterwards registered
 * with the bot to which the feature loader is associated.
 * The FeatureLoader also resolves dependencies between various features, each feature is
 * able to specify on which other features it depends and which therefore have to exist as
 * well in order for the specific feature to work properly.
 * @author Ulrich Krömer
 *
 */
public class FeatureLoader {

	// directory settings
	private static final String BASE_DIR = "bin\\";

	private Logger logger = Logging.getBotLogger();
	private String featureDir;
 
	private Set<Class> knownListenerInterfaces = new HashSet<Class>();
	
	private Map<Class<? extends BotListener> , BotListener> registeredFeatures = new HashMap<Class<? extends BotListener>, BotListener>();
	private Map<Class<? extends BotListener>, List<Class<? extends BotListener>>> dependencies = new HashMap<Class<? extends BotListener>, List<Class<? extends BotListener>>>();
	
	private List<Class<? extends BotListener>> uninitializedFeatures = new ArrayList<Class<? extends BotListener>>();

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
					logger.severe("an error occcured while adding the accepted interfaces");
				}
			}
		}
	}
	
	/**
	 * returns a list of names of all the features that are currently registered
	 * with the bot. those list is needed for being able to determine at runtime
	 * the number and type of the features that are already loaded.
	 * @return a list with the names of all already registered features
	 */
	public List<String> getRegisteredFeatures() {
		List<String> features = new ArrayList<String>();
		for (Class<? extends BotListener> cls : registeredFeatures.keySet()) {
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
		logger.info("successfully loaded features");
	}
	
	/**
	 * parses a given directory for all .class files. (ie tests for files
	 * ending with .class). returns a list of the fully qualified class names
	 * ready to be instantiated by a class loader.
	 * @param path the directory that should be parsed.
	 * @return a list of fully qualified class names that are in the specified directory.
	 */
	private List<String> parseDirectory(String path) {
		List<String> classNames = new ArrayList<String>();

		File featureDir = new File(BASE_DIR + path);
		File[] files = featureDir.listFiles();
		if (files == null)
			return classNames;
		for (File file : files) {
			String fileName = file.getName();
			if (fileName.endsWith(".class")) {
				String className = fileName.replaceAll(".class", "");
				className = path + "." + className;
				classNames.add(className);
			}
		}
		return classNames;
	}
	
	/**
	 * registers all features of the feature list by iterating
	 * over all features and calling registerFeature on each
	 * feature. the entries in the feature list are the names
	 * of the features
	 * @param features the list of feature names.
	 */
	private void registerAllFeatures(List<String> features) {
		for (String feature : features) {
			registerFeature(feature);
		}
	}
	
	/**
	 * registers the listener with the given name. this means that
	 * each feature first gets instantiated, afterwards they are inserted
	 * into the map that contains all currently registered features. if 
	 * the feature is already registered and loaded it gets reloaded, to 
	 * enable deploying a new version of a feature without stopping the bot.
	 * finally the dependencies for each feature are determined by calling 
	 * the registerDependencies method.
	 * if an exception is thrown nothing is done because that only means that
	 * the class does not implement the interface and therefore nothing is done
	 * afterwards with the class anyway.
	 * @param listenerName the name of the listener that should be registered
	 */
	private void registerFeature(String listenerName) {
		try {
			Class cls = Class.forName(listenerName);
			BotListener listener = (BotListener) cls.newInstance();
			
			registeredFeatures.put((Class<? extends BotListener>)cls, listener);
			registerDependencies(listener);
			
		} catch (Exception e) {
			// only happens when class does not implement the bot
			// listener interface => no problem as nothing is done
			// with the class
		}
	}
	
	/**
	 * registers the dependencies for a supplied listener.
	 * each listener specifies on which other features it dependencies
	 * by returning a list of dependencies as the return value of the 
	 * getDependencies method.
	 * @param listener the listener for which the dependencies need to be resolved
	 */
	private void registerDependencies(BotListener listener) {
		for (Class<? extends BotListener> cls : listener.getDependencies()) {
			if(!dependencies.containsKey(cls)) {
				List<Class<? extends BotListener>> depList = new ArrayList<Class<? extends BotListener>>();
				depList.add(listener.getClass());
				dependencies.put(cls, depList);
			}
			else {
				dependencies.get(cls).add(listener.getClass());
			}		
		}
	}
	
	/**
	 * resolves the dependencies for each listener by calling the
	 * resolveDependenciesForClass method on each feature.
	 */
	private void resolveDependencies() {	
		for (Class<? extends BotListener> cls : dependencies.keySet())
			resolveDependenciesForClass(cls);	
	}
	
	/**
	 * resolves the dependencies for a given class. this means that first the 
	 * class is looked up and then for each dependent class the object of the current
	 * class is provided as parameter for a setter method that has to exist in order
	 * to being able to resolve the dependency (by using dependency injection each 
	 * feature does not have to know at instantiation time which feature object really
	 * gets associated with the class.
	 * if the current feature does not exist at all, all classed that depend on this feature
	 * are added to the list of not properly initialized features which are then cleaned up 
	 * after initialization of the rest of the features succeeded. if a feature in the list
	 * of dependent classes does not provide the necessary setter method it is also added
	 * to that list and cleaned up later (which means that the feature wont work)
	 * @param cls the class for which the dependencies should be resolved.
	 */
	private void resolveDependenciesForClass(Class<? extends BotListener> cls) {
		BotListener curListener = registeredFeatures.get(cls);
		if(curListener != null) {
			for (Class<? extends BotListener> curClass : dependencies.get(cls)) {
				BotListener listener = registeredFeatures.get(curClass);
				try {
					invokeMethod(listener, "set" + cls.getSimpleName(), cls, curListener);
				}
				catch(Exception e) {
					uninitializedFeatures.add(listener.getClass());
					logger.warning("failed to initialize plugin: " + listener.getClass());
				}
			}
		}
		else {
			for (Class<? extends BotListener> curClass : dependencies.get(cls)) {
				logger.warning("failed to initialize plugin: " + curClass.getSimpleName());
				uninitializedFeatures.add(curClass);
			}
		}
	}
	
	/**
	 * removes all features that could not be initialized properly from the list of features.
	 * this means that those features are not availible however if all features are reloaded
	 * and the missing dependency exists afterwards the feature is instantiated again and 
	 * gets initialized. 
	 */
	private void removeUninitalizedFeatures() {
		for (Class<? extends BotListener> cls : uninitializedFeatures)
			registeredFeatures.remove(cls);
		uninitializedFeatures.clear();
	}

	/**
	 * initializes the list of features that remain after features that were not initialized
	 * properly have been removed from the feature list. initializing means that for each
	 * listener the setBot method is called with the current bot object and that the listeners
	 * are registered with the bot by determining all interfaces that the listener implements.
	 * this is done by calling the handleInterfaces method on each listener.
	 * if an exception occurs inside the handleInterfaces method this means that the bot does
	 * not provide the necessary setter method that it promised to provide when determining
	 * the supported interfaces.
	 * after initializing the features this method calls the onLoad method on each listener
	 * to enable each feature to do custom initialization that has to be done after all dependencies
	 * have been resolved (and which therefore can not be done inside the constructor)
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
				// be updated
				logger.severe("an interface is not supported by the bot");
			}
		}
		for(BotListener listener : registeredFeatures.values()) {
			listener.onLoad();
		}
	}
	
	/**
	 * handles all interfaces that the supplied class supports and calls the associated
	 * method on the bot. this means it registers the provided listener object with the bot
	 * object.
	 * @param cls the class for which all supported interfaces should be determined and 
	 * associated with the bot
	 * @param listener the listener which has to be registered with the bot and therefore
	 * needs to be provided to the invokeMethod method
	 * @param methodPrefix the prefix of the methods that should be called
	 * @throws NoSuchMethodException if the method does not exist
	 * @throws IllegalAccessException if the method does not provide the necessary (public) access rights
	 * @throws InvocationTargetException if the target of the invocation does not exist
	 */
	private void handleInterfaces(Class<? extends BotListener> cls, BotListener listener,
			String methodPrefix) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		
		Class[] interfaces = cls.getInterfaces();
		for (Class curClass : interfaces) {
			if (knownListenerInterfaces.contains(curClass)) {
				StringBuffer name = new StringBuffer(curClass
						.getSimpleName());
				name.insert(0, methodPrefix);
				invokeMethod(bot, name.toString(), curClass, listener);
			}
		}
	}
	
	/**
	 * inovkes a method one parameter on a specified object. 
	 * the method is supplied as argument "methodName", the formal parameter type is
	 * supported as "parameteType" and the the actual parameter objects as "argument"
	 * @param receiver the object on which the method should be called
	 * @param methodName the name of the method that should be called
	 * @param parameterType the type of the formal parameter of the method
	 * @param argument the acutal parameter to the method
	 * @throws NoSuchMethodException if the method does not exist
	 * @throws IllegalAccessException if the access rights of the method are to strong to be called
	 * @throws InvocationTargetException if the target of the invocation does not exist
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
	 * unloads all features.
	 * before removing all features from the bot this method calls the onUnload method
	 * for each feature to provide each feature with the possibility to perform necessary
	 * clean up routines.
	 */
	public void unload() {
		for(BotListener listener : registeredFeatures.values()) {
			listener.onUnload();
		}
		try {
			for(Class<? extends BotListener> cls : registeredFeatures.keySet()) {
				handleInterfaces(cls, registeredFeatures.get(cls), "remove");
			}
		}
		catch(Exception ex) {
			// should not happen as all known interfaces have appropriate 
			// methods in the class; if it happens the bot class has to
			// be updated
			logger.severe("an interface is not supported by the bot");
		}
		registeredFeatures.clear();
		logger.info("successfully unloaded all features");
	}
}
