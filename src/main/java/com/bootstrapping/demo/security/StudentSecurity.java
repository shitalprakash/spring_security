package com.bootstrapping.demo.security;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.bootstrapping.demo.auth.ApplicationUserService;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class StudentSecurity extends WebSecurityConfigurerAdapter {
	
	
	private final PasswordEncoder passwordEncoder;
	private final ApplicationUserService appservice;
	@Autowired
	public StudentSecurity(PasswordEncoder passwordEncoder,ApplicationUserService appservice) {
		this.passwordEncoder = passwordEncoder;
		this.appservice = appservice;
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
	.formLogin()
	.loginPage("/login").permitAll()
	.defaultSuccessUrl("/Courses",true)
	.usernameParameter("username")
	.passwordParameter("password")
	.and()
	.rememberMe().alwaysRemember(true).rememberMeParameter("rememberme").tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(21)) 
	.key("somethingverysecure")// defaults to two weeks
	.and()
	.logout()
		.logoutUrl("/logout")
		.deleteCookies("JSESSIONID","remember-me")
		.invalidateHttpSession(true)
		.clearAuthentication(true)
		.logoutSuccessUrl("/login")
	;
	}

	/*
	 * @Override
	 * 
	 * @Bean protected UserDetailsService userDetailsService() {
	 * 
	 * 
	 * UserDetails shitalUser= User.builder() .username("shitalprakash")
	 * .password(passwordEncoder.encode("password"))
	 * //.roles(ApplicationUserRole.STUDENT.name())
	 * .authorities(ApplicationUserRole.STUDENT.getGrantedAuthorities()) .build();
	 * 
	 * UserDetails lindaUser=User.builder().username("linda").
	 * password(passwordEncoder.encode("password")).authorities(ApplicationUserRole.
	 * ADMIN.getGrantedAuthorities()).build();
	 * 
	 * UserDetails
	 * tomUser=User.builder().username("tom").password(passwordEncoder.encode(
	 * "password")).authorities(ApplicationUserRole.ADMINTRAINEE.
	 * getGrantedAuthorities()).build(); return new
	 * InMemoryUserDetailsManager(shitalUser,lindaUser,tomUser); }
	 */
	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider() {
		DaoAuthenticationProvider daoProvider=new DaoAuthenticationProvider();
		daoProvider.setPasswordEncoder(passwordEncoder);
		daoProvider.setUserDetailsService(appservice);
		return daoProvider;
		
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(daoAuthenticationProvider());
	}
	
}
