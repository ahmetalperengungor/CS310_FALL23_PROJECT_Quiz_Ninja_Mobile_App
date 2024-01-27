package com.app.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.app.models.User;
@Repository
public interface UserRepository extends MongoRepository<User, String>{
	public User findByUsernameAndPassword(String username, String password);
	public User findByUsername(String username);
	
	
	
	
}
