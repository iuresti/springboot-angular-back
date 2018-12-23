package com.iuresti.learning.backendclients.services.impl;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iuresti.learning.backendclients.models.Client;
import com.iuresti.learning.backendclients.repository.IClientDAO;
import com.iuresti.learning.backendclients.services.IClientService;

@Service
public class ClientService implements IClientService {

    @Autowired
    private IClientDAO clientDAO;

    @Override
    @Transactional(readOnly = true)
    public List<Client> findAll() {
        return clientDAO.findAllByOrderByIdAsc();
    }

    @Override
    @Transactional(readOnly = true)
    public Client findById(Long id) {
        return clientDAO.findById(id).orElseThrow(() -> new EntityNotFoundException("Id <" + id + "> not found"));
    }

    @Override
    @Transactional
    public Client save(Client client) {

        if (client.getId() == null) {
            client.setCreatedAt(new Date(LocalDate.now().toEpochDay()));
        }

        return clientDAO.save(client);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        clientDAO.deleteById(id);
    }
}
