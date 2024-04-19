package com.avanzada.unilocal.Unilocal.repository;

import com.avanzada.unilocal.Unilocal.entity.Qualification;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QualificationRepository extends MongoRepository<Qualification, Integer> {
}
