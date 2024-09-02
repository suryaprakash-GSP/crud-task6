package com.posidex.crud_task6.jwt.filter;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.posidex.crud_task6.jwt.configuration.JwtTokenvalidator;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {

	private static final Logger log = LogManager.getFormatterLogger(JwtTokenFilter.class);

	private final JwtTokenvalidator jwtTokenvalidator;

	@Autowired
	private EntityManager entityManager;


	@Value("${service.name}")
	private String solutionName;

	public JwtTokenFilter(JwtTokenvalidator tokenProvider) {
		this.jwtTokenvalidator = tokenProvider;
	}

	@Transactional
	@Modifying
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String token = extractToken(request);
		// Code to validate the Authorization
		try {
			log.info("internal filter");
			String userName = jwtTokenvalidator.getUsernameFromToken(token);
			log.info("service name {}", solutionName);
			Object akid = entityManager.createNativeQuery(
					"SELECT akid FROM gateway_service_access_config WHERE username = ? AND service_name = ? AND active = 'Y'")
					.setParameter(1, userName).setParameter(2, solutionName).getSingleResult();
			log.info("generated akid {}", akid);
			if (akid != null && Integer.parseInt(akid.toString()) > 0
					&& (token != null && jwtTokenvalidator.validateToken(token))) {
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userName,
						null, null);
				SecurityContextHolder.getContext().setAuthentication(authentication);

			}
			filterChain.doFilter(request, response);
		} catch (Exception nre) {
			filterChain.doFilter(request, response);
		} 
	}

	private String extractToken(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7); // Remove "Bearer " prefix
		}
		return null;
	}
}
