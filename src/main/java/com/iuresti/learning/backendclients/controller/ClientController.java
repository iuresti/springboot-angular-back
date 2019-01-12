package com.iuresti.learning.backendclients.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.iuresti.learning.backendclients.models.Client;
import com.iuresti.learning.backendclients.services.IClientService;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api/client")
public class ClientController {

    @Autowired
    private IClientService clientService;

    @GetMapping("/page/{page}")
    public Page<Client> getAll(@PathVariable int page) {
        return clientService.findAll(PageRequest.of(page, 4));
    }

    @GetMapping
    public List<Client> getAll() {
        return clientService.findAll();
    }

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping("{id}")
    public ResponseEntity<Client> findById(@PathVariable Long id) {

        return new ResponseEntity<>(clientService.findById(id), HttpStatus.OK);
    }

    @PostMapping
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity<?> save(@Valid @RequestBody Client client, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return handleBadRequest(bindingResult);
        }

        client.setId(null);
        return new ResponseEntity<>(clientService.save(client), HttpStatus.CREATED);
    }

    private ResponseEntity<?> handleBadRequest(BindingResult bindingResult) {
        Map<String, Object> response = new HashMap<>();

        response.put("errors", bindingResult.getFieldErrors().stream().map(error -> error.getField() + ": " + error.getDefaultMessage()).collect(Collectors.toList()));
        response.put("message", "Bad request");

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @Secured({"ROLE_ADMIN"})
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public Client update(@RequestBody Client client) {
        return clientService.save(client);
    }

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @PutMapping("/{id}/image")
    @ResponseStatus(HttpStatus.OK)
    public Client updateImage(@RequestParam MultipartFile file, @PathVariable long id) throws IOException {
        try (InputStream is = file.getInputStream()) {
            return clientService.updateImage(id, is, file.getOriginalFilename());
        }
    }

    @GetMapping("/image/{image:.+}")
    public ResponseEntity<Resource> updateImage(@PathVariable String image) throws IOException {
        Path path = Paths.get("uploads").resolve(image).toAbsolutePath();
        Resource resource = new UrlResource(path.toUri());
        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"");

        return new ResponseEntity<>(resource, httpHeaders, HttpStatus.OK);
    }

    @Secured({"ROLE_ADMIN"})
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        clientService.delete(id);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> clientNotFoundHandler(Exception ex) {
        Map<String, Object> response = new HashMap<>();

        response.put("message", ex.getMessage());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> commonExceptionHandler(Exception ex) {
        Map<String, Object> response = new HashMap<>();

        response.put("message", ex.getMessage());

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
