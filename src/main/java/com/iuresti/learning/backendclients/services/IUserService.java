package com.iuresti.learning.backendclients.services;

import com.iuresti.learning.backendclients.models.User;

public interface IUserService {
    User findByUsername(String username);
}
