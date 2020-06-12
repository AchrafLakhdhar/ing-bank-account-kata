package com.ing.security;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.ing.entities.User;
import com.ing.services.UserService;

@SpringBootTest
@EnableTransactionManagement
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class JwtTest {

	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	UserService userService;
	
	@Autowired
	UserDetailsService userDetailsService;
	
	@Test
	public void testGenerateToken() {
		User user = userService.findOne("admin");
		String token = jwtTokenUtil.generateToken(user);
		assertNotNull(token);
	}
	
	@Test
	public void testValidateToken() {
		User user = userService.findOne("admin");
		String token = jwtTokenUtil.generateToken(user);
		assertNotNull(token);
		UserDetails userDetails = userDetailsService.loadUserByUsername("admin");
		boolean isValid = jwtTokenUtil.validateToken(token, userDetails);
		assertTrue(isValid, "The token must be valid");
	}
}
