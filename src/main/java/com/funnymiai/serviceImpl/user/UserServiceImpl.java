package com.funnymiai.serviceImpl.user;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.funnymiai.dao.user.UserDao;
import com.funnymiai.model.user.User;
import com.funnymiai.service.user.UserService;
 
 
@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserDao userDao;
 
	@Override
	public User getNameById(User user) {
		return userDao.getNameById(user);
	}
	
	
 
}
