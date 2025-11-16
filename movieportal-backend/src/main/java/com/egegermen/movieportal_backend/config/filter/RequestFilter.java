package com.egegermen.movieportal_backend.config.filter;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Log4j2
@Component
public class RequestFilter extends OncePerRequestFilter {
	@Override
	protected void doFilterInternal(
			HttpServletRequest request, HttpServletResponse response,
			FilterChain filterChain)
			throws jakarta.servlet.ServletException, java.io.IOException {
		
		log.info("Request URI: {}", request.getRequestURI());
		log.info("Request Method: {}", request.getMethod());
		log.info("Request Headers: {}", request.getHeaderNames());
		filterChain.doFilter(request, response);
	}
}

