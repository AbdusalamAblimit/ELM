package team.tjusw.elm.service;

import team.tjusw.elm.po.User;

public interface UserService {
	public User getUserByIdByPass(User user);

	public int saveUser(User user);
	
	public int getUserById(String userId);
}
