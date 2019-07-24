package org.bioauth.typeauth.config;

import org.bioauth.typeauth.domain.Client;
import org.bioauth.typeauth.domain.User;
import org.bioauth.typeauth.service.ClientServiceDb;
import org.bioauth.typeauth.service.UserServiceDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserSecurityUtil {

	private UserServiceDb userServiceDb;

	@Autowired @Lazy
	public UserSecurityUtil(UserServiceDb userServiceDb) {
		this.userServiceDb = userServiceDb;
	}

	public User getAuthenticatedUser()
	{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!auth.isAuthenticated())
			return null;
		Optional<User> opUser = userServiceDb.findUserByUsername(auth.getName());
		if (opUser.isPresent())
			return opUser.get();
		else
			return null;
	}
}
