package com.avanzada.unilocal.Unilocal.repository;

import com.avanzada.unilocal.Unilocal.entity.Place;
import com.avanzada.unilocal.Unilocal.enums.BusinessType;
import com.avanzada.unilocal.Unilocal.enums.StateUnilocal;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface PlaceRepository extends MongoRepository<Place, Integer> {

    boolean existsByName(String name);
    Optional<Place> findByName(String name);
    Optional<Place> findById(int id);

    List<Place> findByOwner(String id);

    List<Place> findByNameContainingIgnoreCase(String name);

    List<Place> findByBusinessTypeIgnoreCase(BusinessType tipo);

    List<Place> findByStateBusinessIgnoreCase(StateUnilocal stateBusiness);


    List<Place> findByStateBusiness(StateUnilocal stateUnilocal);
}
