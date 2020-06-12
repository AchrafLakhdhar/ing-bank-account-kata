package com.ing.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ing.entities.User;
import com.ing.entities.UserDto;
import com.ing.services.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping(method = RequestMethod.POST)
	public User saveUser(@RequestBody UserDto user) {
		return userService.save(user);
	}

	@RequestMapping(method = RequestMethod.GET)
	public List<User> listUser() {
		return userService.findAll();
	}

	@RequestMapping(value = "/{userName}", method = RequestMethod.GET)
	public User getOne(@PathVariable String userName) {
		return userService.findOne(userName);
	}

	@RequestMapping(method = RequestMethod.PUT)
	public UserDto update(@RequestBody UserDto userDto) {
		return userService.update(userDto);
	}

	@RequestMapping(value = "/{userName}", method = RequestMethod.DELETE)
	public void delete(@PathVariable String userName) {
		userService.delete(userName);

	}

}
