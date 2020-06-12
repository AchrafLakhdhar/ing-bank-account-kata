package com.ing.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ing.entities.LoginUser;
import com.ing.entities.User;
import com.ing.security.AuthToken;
import com.ing.security.JwtTokenUtil;
import com.ing.services.UserService;

@RestController
@RequestMapping(value = "/security")
public class AuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/token", method = RequestMethod.POST)
	public AuthToken getToken(@RequestBody LoginUser loginUser) throws AuthenticationException {

		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginUser.getUserName(), loginUser.getPassword()));
		final User user = userService.findOne(loginUser.getUserName());
		final String token = jwtTokenUtil.generateToken(user);
		return new AuthToken(token, user.getUserName());
	}
}
