package com.niit.dao;

import com.niit.entity.User;

public interface UserDao
{
	void registration(User user);
	boolean isEmailUnique(String email);
	User login(User user);

void updateUser(User user);
User getUser(String email);

}
