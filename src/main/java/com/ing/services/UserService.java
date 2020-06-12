package com.ing.services;

import java.util.List;

import com.ing.entities.User;
import com.ing.entities.UserDto;

public interface UserService {

	User save(UserDto user);

	List<User> findAll();

	void delete(String userName);

	User findOne(String userName);

	UserDto update(UserDto userDto);
}
