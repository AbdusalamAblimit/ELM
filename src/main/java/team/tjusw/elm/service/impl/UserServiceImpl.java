package team.tjusw.elm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import team.tjusw.elm.mapper.UserMapper;
import team.tjusw.elm.po.User;
import team.tjusw.elm.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;
	
	@Override
	public User getUserByIdByPass(User user) {

		return userMapper.getUserByIdByPass(user);			
	}
	
	@Override
	public int getUserById(String userId) {
		return userMapper.getUserById(userId);
	}
	
	@Override
	public int saveUser(User user) {
		if(this.getUserById(user.getUserId())==1)
			return 0;
		else
			return userMapper.saveUser(user);
	}

	
	

	
}
