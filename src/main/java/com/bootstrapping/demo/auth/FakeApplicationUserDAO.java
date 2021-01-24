package com.bootstrapping.demo.auth;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.bootstrapping.demo.security.ApplicationUserRole;
import com.google.common.collect.Lists;

@Repository("fake")
public class FakeApplicationUserDAO implements ApplicationUserDAO {

	private final PasswordEncoder passEncoder;

	@Autowired
	public FakeApplicationUserDAO(PasswordEncoder passEncoder) {
		super();
		this.passEncoder = passEncoder;
	}

	@Override
	public Optional<ApplicationUser> selectApplicationUserByUsername(String username) {
		return getApplicationUsers().stream().filter(appUser -> appUser.getUsername().equalsIgnoreCase(username))
				.findFirst();
	}

	private List<ApplicationUser> getApplicationUsers() {
		List<ApplicationUser> lstAppUsrs = Lists.newArrayList(
				new ApplicationUser(ApplicationUserRole.STUDENT.getGrantedAuthorities(), true, true, true,true,
						passEncoder.encode("anna"), "anna"),
				new ApplicationUser(ApplicationUserRole.ADMIN.getGrantedAuthorities(), true, true, true,true,
						passEncoder.encode("linda"), "linda"),
				new ApplicationUser(ApplicationUserRole.ADMINTRAINEE.getGrantedAuthorities(), true, true, true,true,
						passEncoder.encode("tom"), "tom"));
		return lstAppUsrs;

	}

}
