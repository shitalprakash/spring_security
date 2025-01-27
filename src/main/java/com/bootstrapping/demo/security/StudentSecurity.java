package com.bootstrapping.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class StudentSecurity extends WebSecurityConfigurerAdapter {
	
	
	private final PasswordEncoder passwordEncoder;
	
	@Autowired
	public StudentSecurity(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
http
//.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).and()
.csrf().disable()
	.authorizeRequests()
	.antMatchers("/","/index","/css/*","/js/*")	.permitAll()
	.antMatchers("/api/**").hasRole(ApplicationUserRole.STUDENT.name())
		/*
		 * .antMatchers(HttpMethod.DELETE,"/management/api/**").hasAuthority(
		 * ApplicationUserPermission.COURSE_WRITE.getPermission())
		 * .antMatchers(HttpMethod.PUT,"/management/api/**").hasAuthority(
		 * ApplicationUserPermission.COURSE_WRITE.getPermission())
		 * .antMatchers(HttpMethod.POST,"/management/api/**").hasAuthority(
		 * ApplicationUserPermission.COURSE_WRITE.getPermission())
		 * .antMatchers(HttpMethod.GET,"/management/api/**").hasAnyRole(
		 * ApplicationUserRole.ADMIN.name(),ApplicationUserRole.ADMINTRAINEE.name())
		 */.anyRequest()
	.authenticated()
	.and()
	.httpBasic();
	}

	@Override
	@Bean
	protected UserDetailsService userDetailsService() {
	UserDetails shitalUser=	User.builder()
		.username("shitalprakash")
		.password(passwordEncoder.encode("password"))
		//.roles(ApplicationUserRole.STUDENT.name())
		.authorities(ApplicationUserRole.STUDENT.getGrantedAuthorities())
		.build();
	
	UserDetails lindaUser=User.builder().username("linda").
			password(passwordEncoder.encode("password")).authorities(ApplicationUserRole.ADMIN.getGrantedAuthorities()).build();
	
	UserDetails tomUser=User.builder().username("tom").password(passwordEncoder.encode("password")).authorities(ApplicationUserRole.ADMINTRAINEE.getGrantedAuthorities()).build();
	return new InMemoryUserDetailsManager(shitalUser,lindaUser,tomUser);
	}

	
}
