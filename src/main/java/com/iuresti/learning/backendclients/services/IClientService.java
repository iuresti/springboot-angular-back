package com.iuresti.learning.backendclients.services;

import java.util.List;

import com.iuresti.learning.backendclients.models.Client;

public interface IClientService {

    List<Client> findAll();

    Client findById(Long id);

    Client save(Client client);

    void delete(Long id);

}
