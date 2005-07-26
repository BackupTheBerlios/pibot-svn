package feature.util.common;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Logger;

import bot.logging.Logging;

public class ByteStreamSerializer implements Serializer {

	private final Logger logger = Logging.getFeatureLogger();
	
	public boolean serialize(Object obj, String path) {
		try {
			FileOutputStream fileStream = new FileOutputStream(path);
			ObjectOutputStream objectStream = new ObjectOutputStream(fileStream);
			objectStream.writeObject(obj);
			objectStream.close();
			fileStream.close();
			return true;
		}
		catch(IOException ex) {
			logger.severe("error while writing to file " + path);
			return false;
		}
	}
	
	public Object deserialize(String path) {
		try {
			FileInputStream fileStream = new FileInputStream(path);
			ObjectInputStream objectStream = new ObjectInputStream(fileStream);
			Object obj = objectStream.readObject();
			objectStream.close();
			fileStream.close();
			return obj;
		}
		catch(Exception ex) {
			logger.severe("Error while reading from file: " + path);
			return null;
		}
	}
	
}
