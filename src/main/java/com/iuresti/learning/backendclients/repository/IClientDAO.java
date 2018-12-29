package com.iuresti.learning.backendclients.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iuresti.learning.backendclients.models.Client;

public interface IClientDAO extends JpaRepository<Client, Long> {

    List<Client> findAllByOrderByIdAsc();
}
