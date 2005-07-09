package bot.util;

import java.util.List;
import java.util.ArrayList;



public class UserManager {

	private static final UserManager INSTANCE = new UserManager();
	
	private List<User> masters;
	private List<User> ops;
	
	public static UserManager getInstance() {
		return INSTANCE;
	}
	
	private UserManager() {
		masters = new ArrayList<User>();
		ops = new ArrayList<User>();	
	}
	
	public void addMaster(User user) {
		masters.add(user);
	}
	
	public boolean isMaster(User user) {
		return masters.contains(user);
	}
	
	public boolean isMaster(String nickName) {
		User user = new User(nickName);
		return masters.contains(user);
	}
	
	public void addOp(User user) {
		ops.add(user);
	}
	
	public boolean isOp(User user) {
		return ops.contains(user);
	}
	
	public boolean isOp(String nickName) {
		User user = new User(nickName);
		return ops.contains(user);
	}
}
