package org.baeldung.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.baeldung.security.SecurityRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * User Details Service - hard coded to two users for the example.
 */
@Service
public class MyUserDetailsService implements UserDetailsService {
	private final Log logger = LogFactory.getLog(this.getClass());

	private Map<String, User> availableUsers = new HashMap<String, User>();

	public MyUserDetailsService() {
		populateDemoUsers();

	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		logger.info("Load user by username " + username);

		UserDetails user = availableUsers.get(username);
		if (user == null) 
			throw new UsernameNotFoundException("Username not found");
		else 
			return availableUsers.get(username);
	}


	private void populateDemoUsers() {
		logger.info("Populate demo users");

		availableUsers.put("user", createUser("user", "password", Arrays.asList(SecurityRole.ROLE_USER)));
		availableUsers.put("admin", createUser("admin", "password", Arrays.asList(SecurityRole.ROLE_ADMIN)));
		availableUsers.put("test", createUser("test", "password", Arrays.asList(SecurityRole.ROLE_USER)));
		availableUsers.put("john", createUser("john", "password", Arrays.asList(SecurityRole.ROLE_USER)));
		availableUsers.put("test", createUser("savani", "password", Arrays.asList(SecurityRole.ROLE_USER)));
	}


	private User createUser(String username, String password, List<SecurityRole> roles) {
		logger.info("Create user " + username);

		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		for (SecurityRole role : roles) {
			authorities.add(new SimpleGrantedAuthority(role.toString()));
		}
		return new User(username, password, true, true, true, true, authorities);
	}
}
