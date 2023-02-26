package com.ltp.gradesubmission.repository;

import java.lang.StackWalker.Option;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.ltp.gradesubmission.entity.User;

public interface UserRepository extends CrudRepository<User, Long> {
	Optional<User> findByUsername(String username);

}