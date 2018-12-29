package com.iuresti.learning.backendclients.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iuresti.learning.backendclients.models.Region;

@Repository
public interface IRegionDAO extends JpaRepository<Region, Long> {
}
