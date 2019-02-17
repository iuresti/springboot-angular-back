package com.iuresti.learning.backendclients.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iuresti.learning.backendclients.models.Role;

@Repository
public interface IRoleDAO extends JpaRepository<Role, Long> {
    List<Role> findAllByNameIn(String... roleNames);
}
