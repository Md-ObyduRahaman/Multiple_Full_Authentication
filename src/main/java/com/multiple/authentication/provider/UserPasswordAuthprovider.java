package com.multiple.authentication.provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.multiple.authentication.auth.UserPasswordAuthToken;
import com.multiple.authentication.service.MyUserDetailsService;

@Component
public class UserPasswordAuthprovider implements AuthenticationProvider {

	@Autowired
	MyUserDetailsService userDetailsService;
	@Autowired
	PasswordEncoder encoder;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		UserDetails user = userDetailsService.loadUserByUsername(authentication.getName());
		if (encoder.matches(authentication.getCredentials() + "", user.getPassword())) {
			return new UserPasswordAuthToken(user.getUsername(), user.getPassword(), user.getAuthorities());
		}
		throw new BadCredentialsException("Failed Authentication !!");
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return UserPasswordAuthToken.class.equals(authentication);
	}

}
