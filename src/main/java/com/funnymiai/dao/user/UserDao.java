package com.funnymiai.dao.user;

import org.apache.ibatis.annotations.Mapper;

import com.funnymiai.model.user.User;
@Mapper
public interface UserDao {
	
	public User getNameById(User user);
	
}