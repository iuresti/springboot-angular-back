package com.iuresti.learning.backendclients.repository;

import org.springframework.data.repository.CrudRepository;

import com.iuresti.learning.backendclients.models.User;

public interface IUserDAO extends CrudRepository<User, Long> {

    User findByUsername(String username);
}
