package com.ing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.ing.entities.User;
import com.ing.entities.UserDto;
import com.ing.services.UserService;

@Component
public class ServerInitializer implements ApplicationRunner {

	@Value("${security.admin.username:admin}")
	private String userName;

	@Value("${security.admin.password:admin}")
	private String password;

	@Autowired
	private UserService userService;

	@Override
	public void run(ApplicationArguments applicationArguments) throws Exception {

		User superUser = userService.findOne(userName);
		if (superUser == null) {
			UserDto superUserDto = new UserDto();
			superUserDto.setUserName(userName);
			superUserDto.setPassword(password);
			superUserDto.setFirstName(userName);
			superUserDto.setLastName(userName);
			userService.save(superUserDto);

		}
	}
}