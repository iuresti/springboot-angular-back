package com.iuresti.learning.backendclients.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.iuresti.learning.backendclients.models.Region;
import com.iuresti.learning.backendclients.repository.IRegionDAO;
import com.iuresti.learning.backendclients.services.IRegionService;

@Service
public class RegionService implements IRegionService {

    private IRegionDAO regionDAO;

    public RegionService(IRegionDAO regionDAO) {
        this.regionDAO = regionDAO;
    }

    @Override
    public List<Region> findAll() {
        return regionDAO.findAll();
    }
}
