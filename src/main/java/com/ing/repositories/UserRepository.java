package com.ing.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ing.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

	public User findByUserName(String userName);
	public void deleteByUserName(String userName);
}
