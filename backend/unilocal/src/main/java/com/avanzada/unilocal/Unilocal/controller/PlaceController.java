package com.avanzada.unilocal.Unilocal.controller;

import com.avanzada.unilocal.Unilocal.dto.CommentDTO;
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

    @GetMapping("/get-place/{id}")
    public ResponseEntity<Place> getOne(@PathVariable("id") int id) throws ResourceNotFoundException {
        return ResponseEntity.ok(placeService.getOne(id));
    }

    @PostMapping("/create-place")
    public ResponseEntity<MessageDto> save(@Valid @RequestBody CreatePlaceDto createPlaceDto) throws AttributeException, ResourceNotFoundException {

        Place place = placeService.createBusiness(createPlaceDto);

        if (place != null){
            String message = "place " + createPlaceDto.name() + " have been saved";

            return ResponseEntity.ok(new MessageDto(HttpStatus.OK, message));
        } else {
            String message = "No se puede crear el lugar, necesita una cuenta activa para hacerlo";
            return ResponseEntity.ok(new MessageDto(HttpStatus.FORBIDDEN, message));
        }
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

        if (place != null){
            String message = "place " + place.getName() + " have been deleted";

            return ResponseEntity.ok(new MessageDto(HttpStatus.OK, message));
        } else {
            String message = "Accion incorrecta, el lugar ya ha sido eliminado previamente";
            return ResponseEntity.ok(new MessageDto(HttpStatus.FORBIDDEN, message));
        }
    }

//    @GetMapping("/search")
//    public ResponseEntity<List<Place>> buscarLugares(
//            @RequestParam(required = false) String name,
//            @RequestParam(required = false) String businessType,
//            @RequestParam(required = false) Double latitud,
//            @RequestParam(required = false) Double longitud,
//            @RequestParam(required = false, defaultValue = "1000") Double distanciaMaxima) {
//
//        List<Place> lugares = placeService.buscarLugares(nombre, tipo, latitud, longitud, distanciaMaxima);
//        return ResponseEntity.ok(lugares);
//    }

}
