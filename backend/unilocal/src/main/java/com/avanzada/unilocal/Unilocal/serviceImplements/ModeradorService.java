package com.avanzada.unilocal.Unilocal.serviceImplements;

import com.avanzada.unilocal.Unilocal.entity.Place;
import com.avanzada.unilocal.Unilocal.enums.StateUnilocal;
import com.avanzada.unilocal.Unilocal.repository.PlaceRepository;
import com.avanzada.unilocal.global.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ModeradorService {
    @Autowired
    PlaceRepository placeRepository;


    public void autorizarLugar(int lugarId) throws ResourceNotFoundException {
        Optional<Place> lugar = Optional.ofNullable(placeRepository.findById(lugarId)
                .orElseThrow(() -> new ResourceNotFoundException("Lugar no encontrado con ID: " + lugarId)));
        lugar.get().setStateBusiness(StateUnilocal.Active);
        placeRepository.save(lugar.get());
    }

    public void rechazarLugar(int lugarId) throws ResourceNotFoundException {
        Optional<Place> lugar = Optional.ofNullable(placeRepository.findById(lugarId)
                .orElseThrow(() -> new ResourceNotFoundException("Lugar no encontrado con ID: " + lugarId)));
        lugar.get().setStateBusiness(StateUnilocal.Refused);
        placeRepository.save(lugar.get());
    }

    public List<Place> getLugaresPendientes() {
        List<Place> places = new ArrayList<>();
        for (Place place : placeRepository.findAll()){
            if (place.getStateBusiness().equals(StateUnilocal.Revision)){
                places.add(place);
            }
        }
        return places;
    }

    public List<Place> getLugaresAutorizados() {
        List<Place> places = new ArrayList<>();
        for (Place place : placeRepository.findAll()){
            if (place.getStateBusiness().equals(StateUnilocal.Active)){
                places.add(place);
            }
        }
        return places;
    }

    public List<Place> getLugaresRechazados() {
        List<Place> places = new ArrayList<>();
        for (Place place : placeRepository.findAll()){
            if (place.getStateBusiness().equals(StateUnilocal.Refused)){
                places.add(place);
            }
        }
        return places;
    }
}
