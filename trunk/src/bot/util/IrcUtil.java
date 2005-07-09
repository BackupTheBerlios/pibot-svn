package bot.util;

import java.util.ArrayList;
import java.util.List;


public class IrcUtil {

	public static List<User> TransformUsers(org.jibble.pircbot.User[] users) {
		List<User> userList = new ArrayList<User>();
		for (org.jibble.pircbot.User user : users)
			userList.add(new User(user.getNick()));
		return userList;
	}
}
