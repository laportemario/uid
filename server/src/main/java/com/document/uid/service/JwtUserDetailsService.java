package com.document.uid.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
	private Environment env;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		String login = env.getProperty("jwt.secret");
				
		if (login.equals(username)) {
			return new User("mario", "$2y$12$rB8es0ELVQz.yp4GaLeKme83ElIHqIc3CrZKsbtG5aCwpj6ERPGC2",
					new ArrayList<>());
		} else {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
	}

}