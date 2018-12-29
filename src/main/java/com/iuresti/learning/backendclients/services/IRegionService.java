package com.iuresti.learning.backendclients.services;

import java.util.List;

import com.iuresti.learning.backendclients.models.Region;

public interface IRegionService {
    List<Region> findAll();
}
