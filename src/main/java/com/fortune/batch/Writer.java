package com.fortune.batch;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import com.fortune.repository.UserRepository;
import com.fortune.model.User;
 
public class Writer implements ItemWriter<User> {
	
 
	@Autowired
    private final UserRepository userRepository;
     
    public Writer(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
 
    @Override
    public void write(List<? extends User> users) throws Exception {
        userRepository.save(users);
    }
}