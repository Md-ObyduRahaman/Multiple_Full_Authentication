package com.multiple.authentication.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.multiple.authentication.auth.ReceiptAuthentication;

public class ReceiptAuthFilter extends OncePerRequestFilter{
	@Autowired
	private AuthenticationManager authenticationManager;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
			String receiptNo = request.getHeader("Authorization");
			ReceiptAuthentication authentication = new ReceiptAuthentication(receiptNo, null);
			Authentication authenticate = authenticationManager.authenticate(authentication);
			SecurityContextHolder.getContext().setAuthentication(authenticate);
			filterChain.doFilter(request, response);
	}
	// this method enable request in runtime
		@Override
		protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
			return request.getServletPath().equals("/login");
		}

}
