package com.avanzada.unilocal.Unilocal.repository;

import com.avanzada.unilocal.Unilocal.entity.Revision;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RevisionRepository extends MongoRepository<Revision, Integer> {
}
