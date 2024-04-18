package com.avanzada.unilocal.Unilocal.serviceImplements;

import com.avanzada.unilocal.Unilocal.dto.CommentDTO;
import com.avanzada.unilocal.Unilocal.dto.CreatePlaceDto;
import com.avanzada.unilocal.Unilocal.dto.RegisterRevisionDto;
import com.avanzada.unilocal.Unilocal.entity.Person;
import com.avanzada.unilocal.Unilocal.entity.Place;
import com.avanzada.unilocal.Unilocal.enums.StateUnilocal;
import com.avanzada.unilocal.Unilocal.interfaces.BusinessService;
import com.avanzada.unilocal.Unilocal.repository.PlaceRepository;
import com.avanzada.unilocal.Unilocal.resources.Revision;
import com.avanzada.unilocal.global.exceptions.AttributeException;
import com.avanzada.unilocal.global.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * @author Juanes Cardona
 */
@Service
public class PlaceService implements BusinessService {

    @Autowired
    PlaceRepository placeRepository;

    @Override
    public Place createBusiness(CreatePlaceDto createPlaceDto) throws AttributeException {
        if (placeRepository.existsByName(createPlaceDto.name()))
            throw new AttributeException("name already in use");

        int id = autoIncrement();
        StateUnilocal stateBusiness = StateUnilocal.Revision;
        Place place = getPlace(createPlaceDto, id, stateBusiness);

        return placeRepository.save(place);
    }

    private static Place getPlace(CreatePlaceDto createPlaceDto, int id, StateUnilocal stateUnilocal){
        Place place = new Place();
        place.setId(id);
        place.setName(createPlaceDto.name());
        place.setDescription(createPlaceDto.description());
        place.setOwner(createPlaceDto.owner());
        place.setPhones(createPlaceDto.phones());
        place.setImages(createPlaceDto.images());
        place.setLocation(createPlaceDto.location());
        place.setStateBusiness(stateUnilocal);
        place.setSchedules(createPlaceDto.schedules());
        place.setBusinessType(createPlaceDto.businessType());
        return place;
    }

    @Override
    public Place updateBusiness(int id, CreatePlaceDto createPlaceDto) throws AttributeException, ResourceNotFoundException {
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

    @Override
    public Place deleteBusiness(int id) throws ResourceNotFoundException {
        Place place = placeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("El id no esta asociado a un lugar"));
        place.setStateBusiness(StateUnilocal.Inactive);
        placeRepository.save(place);
        return place;
    }

    @Override
    public Place findBusiness(int id) throws ResourceNotFoundException {
        Place place = placeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("El id no esta asociado a un lugar"));

        return place;
    }

    @Override
    public List<Place> filterByState(StateUnilocal stateBusiness) {

        List<Place> places = new ArrayList<>();

        for(Place place: placeRepository.findAll()){
            if (place.getStateBusiness().equals(stateBusiness)){
                places.add(place);
            }
        }

        return places;
    }

    @Override
    public List<Person> listOwnerBusiness() throws ResourceNotFoundException {
        List<Person> people = new ArrayList<>();

        for(Place place: placeRepository.findAll()){
            if (place != null && place.getOwner() != null) throw new ResourceNotFoundException("No hay lugares creados");{

            }
        }

        return people;

    }

    @Override
    public void changeState(StateUnilocal newState, int id) throws ResourceNotFoundException {

        for (Place place : placeRepository.findAll()){
            if (place.getId() == id) throw new ResourceNotFoundException("El id no esta asociado a un lugar");
            {
                place.setStateBusiness(newState);
            }
        }
    }

    @Override
    public void registerRevision(RegisterRevisionDto registerRevisionDto, int id) throws ResourceNotFoundException {
        for (Place place : placeRepository.findAll()){
            if (place.getId() == id) throw new ResourceNotFoundException("El id no esta asociado a un lugar"); {
                place.getRevisions().add(new Revision(registerRevisionDto.stateBusiness(), registerRevisionDto.mod(), registerRevisionDto.description()));
            }
        }
    }

    @Override
    public void addComment(int lugarId, CommentDTO comment) {

    }

    public List<Place> buscarLugares(String nombre, String tipo, Double latitud, Double longitud, Double distanciaMaxima) {
        // Implementar la lógica de búsqueda de lugares según los parámetros proporcionados
        // Puedes utilizar métodos de consulta de Spring Data JPA o consultas personalizadas según sea necesario
        // Por ejemplo:
        if (nombre != null && tipo != null && latitud != null && longitud != null) {
//            return placeRepository.buscarPorNombreTipoYDistancia(nombre, tipo, latitud, longitud, distanciaMaxima);
        } else if (nombre != null) {
            return placeRepository.findByNombreContainingIgnoreCase(nombre);
        } else if (tipo != null) {
            return placeRepository.findByTipoIgnoreCase(tipo);
        } else if (latitud != null && longitud != null) {
//            return placeRepository.buscarPorUbicacion(latitud, longitud, distanciaMaxima);
        } else {
            return placeRepository.findAll();
        }
        return null;
    }

    //-----------------------------Private Methods----------------------------------------

    public List<Place> getAll() {
        List<Place> places = new ArrayList<>();
        for (Place place : placeRepository.findAll()){
            if (place.getStateBusiness().equals(StateUnilocal.Active)){
                places.add(place);
            }
        }
        return places;
    }


    public Place getOne(int id) throws ResourceNotFoundException {
        return placeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found"));
    }

    // private methods
    private int autoIncrement() {
        List<Place> places = placeRepository.findAll();
        return places.isEmpty() ? 1 :
                places.stream().max(Comparator.comparing(Place::getId)).get().getId() + 1;
    }
}
