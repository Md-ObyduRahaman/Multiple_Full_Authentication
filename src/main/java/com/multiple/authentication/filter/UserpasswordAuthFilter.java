package com.multiple.authentication.filter;

import java.io.IOException;
import java.util.Random;
import java.util.UUID;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.multiple.authentication.auth.SecretKeyAuthToken;
import com.multiple.authentication.auth.UserPasswordAuthToken;
import com.multiple.authentication.model.UserSecurityKey;
import com.multiple.authentication.repo.ReceiptManager;
import com.multiple.authentication.repo.UserSecretKeyRepo;;

@Component
public class UserpasswordAuthFilter extends OncePerRequestFilter {

	@Autowired
	AuthenticationManager manager;
	@Autowired
	UserSecretKeyRepo userSecretKeyRepo;
	@Autowired
	ReceiptManager receiptManager;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String uname = request.getHeader("uname");
		String password = request.getHeader("password");
		String key = request.getHeader("secret_key");

		if (key == null) {
			// uid and password
			UserPasswordAuthToken userPasswordAuthToken = new UserPasswordAuthToken(uname, password);
			Authentication authenticate = manager.authenticate(userPasswordAuthToken);

			// save generated key into db
			UserSecurityKey securityKey = new UserSecurityKey();
			securityKey.setKey((new Random().nextInt(999) * 1000) + "");
			securityKey.setUsername(uname);

			userSecretKeyRepo.save(securityKey);

		} else {
			// through the key
			Authentication authenticate = manager.authenticate(new SecretKeyAuthToken(uname, key));

			// store inside db
			 String string = UUID.randomUUID().toString();
			 receiptManager.add(string);

			// generate a token
			response.setHeader("Authorization",string);
		}

	}

	// this method enable request in runtime
	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		return !request.getServletPath().equals("/login");
	}

}
