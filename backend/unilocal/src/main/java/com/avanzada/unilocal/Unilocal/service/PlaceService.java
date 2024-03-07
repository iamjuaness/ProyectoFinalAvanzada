package com.avanzada.unilocal.Unilocal.service;

import com.avanzada.unilocal.Unilocal.dto.CreatePlaceDto;
import com.avanzada.unilocal.Unilocal.entity.Place;
import com.avanzada.unilocal.Unilocal.repository.PlaceRepository;
import com.avanzada.unilocal.global.exceptions.AttributeException;
import com.avanzada.unilocal.global.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Service
public class PlaceService {

    @Autowired
    PlaceRepository placeRepository;

    public List<Place> getAll() {
        return placeRepository.findAll();
    }


    public Place getOne(int id) throws ResourceNotFoundException {
        return placeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found"));
    }

    public Place save(CreatePlaceDto createPlaceDto) throws AttributeException {

        if (placeRepository.existsByName(createPlaceDto.name()))
            throw new AttributeException("name already in use");

        int id = autoIncrement();
        Place place = new Place(id, createPlaceDto.description(), createPlaceDto.name(), createPlaceDto.schedules(), createPlaceDto.images(), createPlaceDto.businessType(), createPlaceDto.phones());

        return placeRepository.save(place);
    }

    public Place update(int id, CreatePlaceDto createPlaceDto) throws ResourceNotFoundException, AttributeException {

        Place place = placeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found"));

        if (placeRepository.existsByName(createPlaceDto.name()) && !Objects.equals(placeRepository.findByName(createPlaceDto.name()).get().getName(), createPlaceDto.name()))
            throw new AttributeException("id already in use");

        place.setName(createPlaceDto.name());
        place.setImages(createPlaceDto.images());
        place.setDescription(createPlaceDto.description());
        place.setSchedules(createPlaceDto.schedules());
        place.setBusinessType(createPlaceDto.businessType());
        place.setPhones(createPlaceDto.phones());

        return placeRepository.save(place);
    }

    public Place delete(int id) throws ResourceNotFoundException {
        Place place = placeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found"));
        placeRepository.delete(place);
        return place;
    }

    // private methods
    private int autoIncrement() {
        List<Place> places = placeRepository.findAll();
        return places.isEmpty() ? 1 :
                places.stream().max(Comparator.comparing(Place::getId)).get().getId() + 1;
    }
}
