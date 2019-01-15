package com.iuresti.learning.backendclients.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iuresti.learning.backendclients.models.Folio;
import com.iuresti.learning.backendclients.services.IClientService;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/api/folio")
public class FolioController {

    @Autowired
    private IClientService clientService;

    @RequestMapping("{id}")
    public ResponseEntity<Folio> getFolio(@PathVariable long id) {
        return new ResponseEntity<>(clientService.findFolioById(id), HttpStatus.OK);
    }
}
