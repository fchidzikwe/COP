package com.fortune.service.impl;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.fortune.model.Facility;
import com.fortune.model.User;
import com.fortune.repository.FacilityRepository;
import com.fortune.repository.UserRepository;
import com.fortune.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private FacilityRepository facilityRepository;
	

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	public User findUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public void saveUser(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(1);
       // Role userRole = roleRepository.findByRole("USER");
      
		userRepository.save(user);
	}

	@Override
	public boolean isAdmin(String  email) {
		//User user = userRepository.findByEmail(email);
		//Set<Role> userRoles = user.getRoles();
		//search for role if is admin return true
		return true;
		
	}



	@Override
	public Facility getFacility(User user) {
		Facility facility = facilityRepository.findByUser(user);
		return facility;
	}

	@Override
	public User findUserByName(String name) {
		// TODO Auto-generated method stub
		return userRepository.findByName(name);
	}



}