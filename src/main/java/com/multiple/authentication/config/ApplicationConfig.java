package com.multiple.authentication.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.multiple.authentication.filter.ReceiptAuthFilter;
import com.multiple.authentication.filter.UserpasswordAuthFilter;
import com.multiple.authentication.provider.ReceiptAuthProvider;
import com.multiple.authentication.provider.SecretKeyAuthProvider;
import com.multiple.authentication.provider.UserPasswordAuthprovider;

@Configuration
public class ApplicationConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	UserpasswordAuthFilter filter;
	@Autowired
	UserPasswordAuthprovider userPasswordAuthProvider;
	@Autowired
	SecretKeyAuthProvider otpAuthProvider;
	// @Autowired
	//ReceiptAuthFilter receiptFilter;
	@Autowired
	ReceiptAuthProvider receiptAuthProvider;

	
	  @Bean	  
	  @Override public AuthenticationManager authenticationManagerBean() throws
	  Exception { 
	 return super.authenticationManagerBean(); }
	 
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(userPasswordAuthProvider)
		.authenticationProvider(otpAuthProvider)
				.authenticationProvider(receiptAuthProvider);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.addFilterAt(filter, BasicAuthenticationFilter.class)
		.addFilterBefore(receiptFilter(),
				BasicAuthenticationFilter.class);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}

	@Bean
	public ReceiptAuthFilter receiptFilter() {
		return new ReceiptAuthFilter();
	}
}
