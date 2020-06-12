package com.ing.security;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.ing.entities.User;
import com.ing.entities.UserDto;
import com.ing.services.UserService;

@SpringBootTest
@EnableTransactionManagement
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class UserSecurityTest {

	private static final String FIRST_NAME = "Walter";
	private static final String LAST_NAME = "White";
	private static final String USER_NAME = "WWhite";
	private static final String PASSWORD = "1234";

	@Autowired
	UserService userService;
	

	@Test
	public void testSuperUserCreation() {
		User user = userService.findOne("admin");
		assertNotNull(user, "User must not be null");
	}

	@Test
	public void testUserCreation() {
		UserDto userDto = prepareUser();
		User user = userService.save(userDto);
		user = userService.findOne(USER_NAME);
		assertNotNull(user, "User must not be null");
		assertNotNull(user.getPassword());
		assertNotEquals(PASSWORD, user.getPassword(), "Password must be hashed");
	}
	
	@Test
	public void testTokenCreation() {
		
	}

	private UserDto prepareUser() {
		UserDto userDto = new UserDto();
		userDto.setFirstName(FIRST_NAME);
		userDto.setLastName(LAST_NAME);
		userDto.setUserName(USER_NAME);
		userDto.setPassword(PASSWORD);
		return userDto;
	}

}
