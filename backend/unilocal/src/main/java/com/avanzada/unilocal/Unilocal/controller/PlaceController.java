package com.avanzada.unilocal.Unilocal.controller;

import com.avanzada.unilocal.Unilocal.dto.CreatePlaceDto;
import com.avanzada.unilocal.Unilocal.entity.Place;
import com.avanzada.unilocal.Unilocal.serviceImplements.PlaceService;
import com.avanzada.unilocal.global.dto.MessageDto;
import com.avanzada.unilocal.global.exceptions.AttributeException;
import com.avanzada.unilocal.global.exceptions.ResourceNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/place")
public class PlaceController {

    @Autowired
    PlaceService placeService;


    @GetMapping("/get-places")
    public ResponseEntity<List<Place>> getAll(){
        return ResponseEntity.ok(placeService.getAll());
    }



    @GetMapping("/get/{id}")
    public ResponseEntity<Place> getOne(@PathVariable("id") int id) throws ResourceNotFoundException {
        return ResponseEntity.ok(placeService.getOne(id));
    }

    @PostMapping("/create-place")
    public ResponseEntity<MessageDto> save(@Valid @RequestBody CreatePlaceDto createPlaceDto) throws AttributeException {

        placeService.createBusiness(createPlaceDto);
        String message = "place " + createPlaceDto.name() + " have been saved";

        return ResponseEntity.ok(new MessageDto(HttpStatus.OK, message));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<MessageDto> update(@PathVariable("id") int id, @Valid @RequestBody CreatePlaceDto createPlaceDto) throws ResourceNotFoundException, AttributeException {
        Place place = placeService.updateBusiness(id, createPlaceDto);
        String message = "place " + place.getName() + " have been updated";

        return ResponseEntity.ok(new MessageDto(HttpStatus.OK, message));
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<MessageDto> delete(@PathVariable("id") int id) throws ResourceNotFoundException {
        Place place = placeService.deleteBusiness(id);
        String message = "user " + place.getName() + " have been deleted";

        return ResponseEntity.ok(new MessageDto(HttpStatus.OK, message));
    }

}
