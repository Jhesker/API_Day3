package com.tts.UsersAPIFinal.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tts.UsersAPIFinal.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
	public List<User> findByState(String state);
}
