package com.fortune.batch;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
 
import com.fortune.model.User;
 
public class Processor implements ItemProcessor<User, User> {
 
    private static final Logger log = LoggerFactory.getLogger(Processor.class);
 
    @Override
    public User process(User user) throws Exception {
       
         
        final int active = user.getActive();
        final int id = user.getId();
        final String email = user.getEmail();
        final String lastName = user.getLastName();
        final String name = user.getName();
        final String phoneNumber = user.getPhoneNumber();
        final String password = user.getPassword();
        final String role =user.getRoles();      
 
        final User fixedUser = new  User(id,active , email,lastName, name,phoneNumber, password,role);
 
        log.info("Converting (" + user + ") into (" + fixedUser + ")");
 
        return fixedUser;
    }
}
