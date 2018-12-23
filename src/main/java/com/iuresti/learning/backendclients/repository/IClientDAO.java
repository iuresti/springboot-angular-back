package com.iuresti.learning.backendclients.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.iuresti.learning.backendclients.models.Client;

public interface IClientDAO extends CrudRepository<Client, Long> {

    List<Client> findAllByOrderByIdAsc();
}
