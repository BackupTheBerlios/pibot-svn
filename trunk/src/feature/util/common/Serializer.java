package feature.util.common;

public interface Serializer {
	
	public boolean serialize(Object obj, String path);
	
	
	public Object deserialize(String path);
}
