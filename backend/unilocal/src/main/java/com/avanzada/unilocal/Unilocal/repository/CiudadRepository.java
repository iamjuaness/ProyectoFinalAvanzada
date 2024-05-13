package com.avanzada.unilocal.Unilocal.repository;

import com.avanzada.unilocal.Unilocal.entity.Ciudad;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CiudadRepository extends MongoRepository<Ciudad, Integer> {
}
