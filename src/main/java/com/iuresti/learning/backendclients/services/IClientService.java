package com.iuresti.learning.backendclients.services;

import java.io.InputStream;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.iuresti.learning.backendclients.models.Client;
import com.iuresti.learning.backendclients.models.Folio;

public interface IClientService {

    List<Client> findAll();

    Page<Client> findAll(Pageable pageable);

    Client findById(Long id);

    Client save(Client client);

    void delete(Long id);

    Client updateImage(long id, InputStream is, String originalFilename);

    Folio findFolioById(Long id);

    void deleteFolioById(Long id);

    Folio saveFolio(Folio folio);
}
