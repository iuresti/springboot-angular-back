package com.iuresti.learning.backendclients.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.iuresti.learning.backendclients.models.Folio;

@Repository
public interface IFolioDAO extends CrudRepository<Folio, Long> {
}
