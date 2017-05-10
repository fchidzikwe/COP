package com.fortune.service;

import com.fortune.model.Facility;
import com.fortune.model.User;

public interface UserService {
	public User findUserByEmail(String email);
	
	public User findUserByName(String name);
	
	public Facility getFacility(User user);

	public void saveUser(User user);
	
	public boolean isAdmin(String email);
}
