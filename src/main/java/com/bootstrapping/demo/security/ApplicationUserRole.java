package com.bootstrapping.demo.security;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.google.common.collect.Sets;
public enum ApplicationUserRole {
STUDENT(Sets.newHashSet()),
ADMIN(Sets.newHashSet(ApplicationUserPermission.COURSE_READ,ApplicationUserPermission.COURSE_WRITE,ApplicationUserPermission.STUDENT_WRITE,ApplicationUserPermission.STUDENT_READ)),
ADMINTRAINEE(Sets.newHashSet(ApplicationUserPermission.COURSE_READ,ApplicationUserPermission.STUDENT_READ));

private Set<ApplicationUserPermission> perms;

ApplicationUserRole(HashSet<ApplicationUserPermission> newHashSet) {
this.perms=newHashSet;
}

public Set<ApplicationUserPermission> getPerms() {
	return perms;
}

public Set<SimpleGrantedAuthority> getGrantedAuthorities()
{
Set<SimpleGrantedAuthority> setAuth= perms.stream().map(permission->new SimpleGrantedAuthority(permission.getPermission())).collect(Collectors.toSet());
setAuth.add(new SimpleGrantedAuthority("ROLE_"+this.name()));
return setAuth;
}
	
}
