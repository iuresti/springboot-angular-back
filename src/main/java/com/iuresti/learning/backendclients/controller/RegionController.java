package com.iuresti.learning.backendclients.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iuresti.learning.backendclients.models.Region;
import com.iuresti.learning.backendclients.services.IRegionService;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api/region")
public class RegionController {

    private IRegionService iRegionService;

    public RegionController(IRegionService iRegionService) {
        this.iRegionService = iRegionService;
    }

    @GetMapping
    public List<Region> findAll() {
        return iRegionService.findAll();
    }
}
