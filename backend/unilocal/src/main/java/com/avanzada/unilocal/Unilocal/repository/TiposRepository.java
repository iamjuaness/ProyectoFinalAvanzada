package com.avanzada.unilocal.Unilocal.repository;

import com.avanzada.unilocal.Unilocal.entity.Tipo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TiposRepository extends MongoRepository<Tipo, Integer> {
}
