package com.bootstrapping.demo.auth;

import java.util.Optional;

public interface ApplicationUserDAO {
	public Optional<ApplicationUser> selectApplicationUserByUsername(String username);
}
