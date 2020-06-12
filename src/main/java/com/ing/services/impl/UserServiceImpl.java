package com.ing.services.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ing.entities.User;
import com.ing.entities.UserDto;
import com.ing.repositories.UserRepository;
import com.ing.services.UserService;

@Service
public class UserServiceImpl implements UserDetailsService, UserService {
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder bcryptEncoder;

	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		User user = userRepository.findByUserName(userName);
		if(user == null){
			throw new UsernameNotFoundException("Invalid userName or password.");
		}
		return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(), getAuthority());
	}

	private List<SimpleGrantedAuthority> getAuthority() {
		return Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
	}

	public List<User> findAll() {
		List<User> list = new ArrayList<>();
		userRepository.findAll().iterator().forEachRemaining(list::add);
		return list;
	}

	@Override
	public void delete(String userName) {
		userRepository.deleteById(userName);
	}

	@Override
	public User findOne(String userName) {
		return userRepository.findByUserName(userName);
	}


    @Override
    public UserDto update(UserDto userDto) {
        User user = findOne(userDto.getUserName());
        if(user != null) {
            BeanUtils.copyProperties(userDto, user, "password");
            userRepository.save(user);
        }
        return userDto;
    }

    @Override
    public User save(UserDto user) {
	    User newUser = new User();
	    newUser.setUsername(user.getUserName());
	    newUser.setFirstName(user.getFirstName());
	    newUser.setLastName(user.getLastName());
	    newUser.setPassword(bcryptEncoder.encode(user.getPassword()));

        return userRepository.save(newUser);
    }

}
