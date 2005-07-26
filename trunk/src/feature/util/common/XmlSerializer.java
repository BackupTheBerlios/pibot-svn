package feature.util.common;

import java.lang.reflect.Field;

public class XmlSerializer implements Serializer {

	public boolean serialize(Object obj, String path) {
		Class cls = obj.getClass();
		Field[] fields = cls.getFields();
		
		
		return false;
	}

	public Object deserialize(String path) {
		
		return null;
	}

}
