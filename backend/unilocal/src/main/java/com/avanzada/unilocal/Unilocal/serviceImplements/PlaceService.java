package com.avanzada.unilocal.Unilocal.serviceImplements;

import com.avanzada.unilocal.Unilocal.dto.CreatePlaceDto;
import com.avanzada.unilocal.Unilocal.dto.RegisterRevisionDto;
import com.avanzada.unilocal.Unilocal.entity.Person;
import com.avanzada.unilocal.Unilocal.entity.Place;
import com.avanzada.unilocal.Unilocal.enums.BusinessType;
import com.avanzada.unilocal.Unilocal.enums.StateUnilocal;
import com.avanzada.unilocal.Unilocal.interfaces.BusinessService;
import com.avanzada.unilocal.Unilocal.repository.ClientRepository;
import com.avanzada.unilocal.Unilocal.repository.PlaceRepository;
import com.avanzada.unilocal.Unilocal.entity.Revision;
import com.avanzada.unilocal.Unilocal.resources.Location;
import com.avanzada.unilocal.global.exceptions.AttributeException;
import com.avanzada.unilocal.global.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author Juanes Cardona
 */
@Service
public class PlaceService implements BusinessService {

    @Autowired
    PlaceRepository placeRepository;
    @Autowired
    ClientRepository clientRepository;

    @Override
    public Place createBusiness(CreatePlaceDto createPlaceDto) throws AttributeException, ResourceNotFoundException {
        // Verificar si el DTO de creación de lugar no es nulo
        Objects.requireNonNull(createPlaceDto, "El DTO de creación de lugar no puede ser nulo");

        // Verificar si el nombre del lugar ya está en uso
        if (placeRepository.existsByName(createPlaceDto.name())) {
            throw new AttributeException("El nombre del lugar ya está en uso");
        }

        // Buscar al propietario por su ID
        Optional<Person> personOptional = clientRepository.findById(createPlaceDto.owner());
        Person person = personOptional.orElseThrow(() -> new ResourceNotFoundException("El usuario no se ha encontrado"));

        // Verificar si el propietario está en un estado adecuado para crear un nuevo lugar
        if (!person.getStateUnilocal().equals(StateUnilocal.Active)) {
            throw new IllegalStateException("El propietario no está en un estado adecuado para crear un nuevo lugar");
        }

        // Obtener un nuevo ID para el lugar
        int id = autoIncrement();

        // Establecer el estado del negocio como "Revisión"
        StateUnilocal stateBusiness = StateUnilocal.Revision;

        // Crear una instancia de Place con los datos proporcionados en el DTO de creación de lugar
        Place place = getPlace(createPlaceDto, id, stateBusiness);

        // Agregar el ID del nuevo lugar a la lista de lugares del propietario
        person.getMyPlaces().add(place.getId());

        // Guardar los cambios en el propietario y en el lugar en la base de datos
        clientRepository.save(person);
        return placeRepository.save(place);
    }

    private static Place getPlace(CreatePlaceDto createPlaceDto, int id, StateUnilocal stateUnilocal) {
        // Verificar si el DTO de creación de lugar no es nulo
        Objects.requireNonNull(createPlaceDto, "El DTO de creación de lugar no puede ser nulo");

        // Verificar si el estado del negocio no es nulo
        Objects.requireNonNull(stateUnilocal, "El estado del negocio no puede ser nulo");

        // Verificar si el ID es válido
        if (id <= 0) {
            throw new IllegalArgumentException("El ID debe ser un número positivo");
        }

        // Crear una nueva instancia de Place con los datos proporcionados en el DTO de creación de lugar
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
        // Verificar si el DTO de creación de lugar no es nulo
        Objects.requireNonNull(createPlaceDto, "El DTO de creación de lugar no puede ser nulo");

        // Buscar el lugar por su ID
        Place place = placeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lugar no encontrado"));

        // Verificar si el nombre del lugar ya está en uso por otro lugar
        if (placeRepository.existsByName(createPlaceDto.name()) &&
                !Objects.equals(placeRepository.findByName(createPlaceDto.name()).get().getId(), id)) {
            throw new AttributeException("El nombre del lugar ya está en uso");
        }

        // Actualizar los datos del lugar con los proporcionados en el DTO de creación de lugar
        place.setName(createPlaceDto.name());
        place.setImages(createPlaceDto.images());
        place.setDescription(createPlaceDto.description());
        place.setSchedules(createPlaceDto.schedules());
        place.setBusinessType(createPlaceDto.businessType());
        place.setPhones(createPlaceDto.phones());
        place.setLocation(createPlaceDto.location());

        // Guardar los cambios y devolver el lugar actualizado
        return placeRepository.save(place);
    }

    @Override
    public Place deleteBusiness(int id) throws ResourceNotFoundException {
        // Buscar el lugar por su ID
        Place place = placeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("El ID no está asociado a un lugar"));

        // Verificar si el estado del lugar no es "Inactivo"
        if (!place.getStateBusiness().equals(StateUnilocal.Inactive)) {
            // Cambiar el estado del lugar a "Inactivo"
            place.setStateBusiness(StateUnilocal.Inactive);

            // Recorrer todos los usuarios para eliminar el lugar de sus listas de lugares favoritos si es necesario
            for (Person person : clientRepository.findAll()) {
                if (person != null && !person.getLugaresFavoritos().isEmpty()) {
                    if (person.getLugaresFavoritos().contains(place.getId())) {
                        person.getLugaresFavoritos().remove(Integer.valueOf(place.getId()));
                        clientRepository.save(person);
                    }
                }
            }

            // Guardar los cambios en la base de datos y devolver el lugar eliminado
            return placeRepository.save(place);
        } else {
            // Devolver null o lanzar una excepción si el lugar ya estaba inactivo
            throw new IllegalStateException("El lugar ya está inactivo");
        }
    }

    @Override
    public Place findBusiness(int id) throws ResourceNotFoundException {
        // Buscar el lugar por su ID
        return placeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("El ID no está asociado a un lugar"));
    }

    @Override
    public List<Place> filterByState(StateUnilocal stateBusiness) {
        // Verificar si el estado proporcionado no es nulo
        Objects.requireNonNull(stateBusiness, "El estado del negocio no puede ser nulo");

        // Filtrar los lugares por estado en la base de datos
        return placeRepository.findByStateBusinessIgnoreCase(stateBusiness);
    }

    @Override
    public List<Person> listOwnerBusiness() throws ResourceNotFoundException {
        List<Person> people = new ArrayList<>();

        // Obtener todos los lugares
        List<Place> places = placeRepository.findAll();

        // Verificar si no hay lugares creados en el sistema
        if (places.isEmpty()) {
            throw new ResourceNotFoundException("No hay lugares creados");
        }

        // Recorrer todos los lugares y agregar los propietarios válidos a la lista
        for (Place place : places) {
            if (place.getOwner() != null) {
                Optional<Person> ownerOptional = clientRepository.findById(place.getOwner());
                ownerOptional.ifPresent(people::add);
            }
        }

        return people;
    }

    @Override
    public void changeState(StateUnilocal newState, int id) throws ResourceNotFoundException {
        // Buscar el lugar por su ID
        Optional<Place> placeOptional = placeRepository.findById(id);
        Place place = placeOptional.orElseThrow(() -> new ResourceNotFoundException("El ID no está asociado a un lugar"));

        // Cambiar el estado del lugar
        place.setStateBusiness(newState);

        // Guardar el cambio en la base de datos
        placeRepository.save(place);
    }


    public List<Place> buscarLugares(String nombre, BusinessType tipo, Double latitud, Double longitud, Double distanciaMaxima) {
        // Implementar la lógica de búsqueda de lugares según los parámetros proporcionados
        // Puedes utilizar métodos de consulta de Spring Data JPA o consultas personalizadas según sea necesario
        if (nombre != null && tipo != null && latitud != null && longitud != null) {
//            return placeRepository.buscarPorNombreTipoYDistancia(nombre, tipo, latitud, longitud, distanciaMaxima);
        } else if (nombre != null) {
            return placeRepository.findByNameContainingIgnoreCase(nombre);
        } else if (tipo != null) {
            return placeRepository.findByBusinessTypeIgnoreCase(tipo);
        } else if (latitud != null && longitud != null) {
//            return placeRepository.buscarPorUbicacion(latitud, longitud, distanciaMaxima);
        } else {
            return placeRepository.findAll();
        }
        return null;
    }

    //-----------------------------Private Methods----------------------------------------

    public List<Place> getAll() {
        List<Place> activePlaces = new ArrayList<>();
        try {
            // Obtener todos los lugares de la base de datos
            List<Place> allPlaces = placeRepository.findAll();

            // Verificar si la lista de lugares no está vacía
            if (allPlaces.isEmpty()) {
                // Manejar el caso en el que no hay lugares en la base de datos
                throw new ResourceNotFoundException("No se encontraron lugares en la base de datos");
            }

            // Filtrar los lugares en estado activo y agregarlos a la lista de lugares activos
            for (Place place : allPlaces) {
                if (place.getStateBusiness().equals(StateUnilocal.Active)) {
                    activePlaces.add(place);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return activePlaces;
    }


    public Place getOne(int id) throws ResourceNotFoundException {
        try {
            // Buscar el lugar por su ID en la base de datos
            Optional<Place> placeOptional = placeRepository.findById(id);

            // Verificar si el lugar existe en la base de datos
            if (placeOptional.isPresent()) {
                // Devolver el lugar si se encuentra
                return placeOptional.get();
            } else {
                // Lanzar una excepción si el lugar no se encuentra
                throw new ResourceNotFoundException("Lugar no encontrado para el ID: " + id);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ResourceNotFoundException("Error al buscar el lugar para el ID: " + id);
        }
    }

    // private methods
    private int autoIncrement() {
        List<Place> places = placeRepository.findAll();
        return places.isEmpty() ? 1 :
                places.stream().max(Comparator.comparing(Place::getId)).get().getId() + 1;
    }
}
