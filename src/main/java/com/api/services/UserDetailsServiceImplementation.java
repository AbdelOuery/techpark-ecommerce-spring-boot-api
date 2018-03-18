package com.api.services;

import java.util.Collections;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.api.repositories.UserRepository;

@Service
public class UserDetailsServiceImplementation implements UserDetailsService {
	private final UserRepository userRepository;

    public UserDetailsServiceImplementation(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    	com.api.entities.User user = userRepository.findByEmail(email);
        
    	if (user == null) {
            throw new UsernameNotFoundException(email);
        }
    	
    	User userDetails = new User(user.getEmail(), user.getPassword(), Collections.EMPTY_LIST);
        return userDetails;
    }
}
