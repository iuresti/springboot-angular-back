package com.iuresti.learning.backendclients.services.impl;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iuresti.learning.backendclients.models.Client;
import com.iuresti.learning.backendclients.repository.IClientDAO;
import com.iuresti.learning.backendclients.services.IClientService;

@Service
public class ClientService implements IClientService {

    private static final Logger logger = LoggerFactory.getLogger(ClientService.class);

    @Autowired
    private IClientDAO clientDAO;

    @Override
    @Transactional(readOnly = true)
    public List<Client> findAll() {
        return clientDAO.findAllByOrderByIdAsc();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Client> findAll(Pageable pageable) {
        return clientDAO.findAll(pageable);
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

    @Override
    @Transactional
    public Client updateImage(long id, InputStream is, String originalFilename) {
        Client client = clientDAO.getOne(id);

        if (client == null) {
            throw new EntityNotFoundException("Id <" + id + "> not found");
        }

        String previousImage = client.getPathImage();
        Path uploads = prepareUploadsDirectory();
        String ext = originalFilename.substring(originalFilename.lastIndexOf('.'));

        try {
            Path newFile = uploads.resolve(UUID.randomUUID() + ext);
            Files.copy(is, newFile);
            client.setPathImage(newFile.getFileName().toString());
            clientDAO.save(client);
        } catch (IOException e) {
            logger.error("Can't save file", e);
            throw new RuntimeException("Can't save image");
        }

        if (previousImage != null) {
            removeImage(client, previousImage);
        }

        return clientDAO.save(client);
    }

    private void removeImage(Client client, String previousImage) {

        logger.info("Removing replaced image {} for client: {}", previousImage, client);


    }

    private Path prepareUploadsDirectory() {
        Path uploads = Paths.get("uploads");

        if (Files.notExists(uploads)) {
            try {
                Files.createDirectory(uploads);
            } catch (IOException e) {
                logger.error("Can't create directory: {}", uploads);
                throw new RuntimeException("Can't save image");
            }
        }

        return uploads;
    }
}
