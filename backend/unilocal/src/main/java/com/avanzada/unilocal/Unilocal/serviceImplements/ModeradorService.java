package com.avanzada.unilocal.Unilocal.serviceImplements;

import com.avanzada.unilocal.Unilocal.dto.EmailDTO;
import com.avanzada.unilocal.Unilocal.dto.RegisterRevisionDto;
import com.avanzada.unilocal.Unilocal.entity.Person;
import com.avanzada.unilocal.Unilocal.entity.Place;
import com.avanzada.unilocal.Unilocal.entity.Revision;
import com.avanzada.unilocal.Unilocal.enums.StateUnilocal;
import com.avanzada.unilocal.Unilocal.repository.ClientRepository;
import com.avanzada.unilocal.Unilocal.repository.PlaceRepository;
import com.avanzada.unilocal.Unilocal.repository.RevisionRepository;
import com.avanzada.unilocal.global.exceptions.NoDataFoundException;
import com.avanzada.unilocal.global.exceptions.ResourceNotFoundException;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ModeradorService {
    @Autowired
    PlaceRepository placeRepository;
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    RevisionRepository revisionRepository;
    @Autowired
    EmailService emailService;
    @Autowired
    RevisionServiceImp revisionServiceImp;


    public void autorizarLugar(int lugarId, RegisterRevisionDto registerRevisionDto) throws ResourceNotFoundException, MessagingException {
        // Verificar si el ID del lugar es válido
        if (lugarId <= 0) {
            throw new IllegalArgumentException("ID de lugar inválido: " + lugarId);
        }

        // Obtener el lugar por ID
        Optional<Place> lugarOptional = placeRepository.findById(lugarId);
        if (lugarOptional.isEmpty()) {
            throw new ResourceNotFoundException("Lugar no encontrado con ID: " + lugarId);
        }

        Place lugar = lugarOptional.get();
        // Cambiar el estado del negocio a "Activo"
        lugar.setStateBusiness(StateUnilocal.Active);

        // Guardar la revisión
        Revision revision = new Revision(revisionServiceImp.autoIncrement(), StateUnilocal.Active, registerRevisionDto.mod(), registerRevisionDto.description());
        revisionRepository.save(revision);

        // Enviar correo electrónico al propietario
        Optional<Person> personOptional = clientRepository.findById(lugar.getOwner());
        if (personOptional.isEmpty()) {
            throw new ResourceNotFoundException("Usuario no encontrado con ID: " + lugar.getOwner());
        }
        Person person = personOptional.get();
        emailService.sendEmail(new EmailDTO("Su negocio fue autorizado", "Nos alegra informarle que su negocio cumple con las normas y fue autorizado.", person.getEmail()));

        // Agregar la revisión al lugar y guardar los cambios
        lugar.getRevisions().add(revision.getId());
        placeRepository.save(lugar);
    }

    public void rechazarLugar(int lugarId) throws ResourceNotFoundException, MessagingException {
        // Verificar si el ID del lugar es válido
        if (lugarId <= 0) {
            throw new IllegalArgumentException("ID de lugar inválido: " + lugarId);
        }

        // Obtener el lugar por ID
        Optional<Place> lugarOptional = placeRepository.findById(lugarId);
        if (lugarOptional.isEmpty()) {
            throw new ResourceNotFoundException("Lugar no encontrado con ID: " + lugarId);
        }

        Place lugar = lugarOptional.get();
        // Cambiar el estado del negocio a "Rechazado"
        lugar.setStateBusiness(StateUnilocal.Refused);

        // Enviar correo electrónico al propietario
        Optional<Person> personOptional = clientRepository.findById(lugar.getOwner());
        if (personOptional.isEmpty()) {
            throw new ResourceNotFoundException("Usuario no encontrado con ID: " + lugar.getOwner());
        }
        Person person = personOptional.get();
        emailService.sendEmail(new EmailDTO("Su negocio fue rechazado", "Lamentamos informarle que su negocio no cumple con las normas de UniLocal y fue rechazado", person.getEmail()));

        // Guardar los cambios en el lugar
        placeRepository.save(lugar);
    }


    public List<Place> getLugaresPendientes() {
        List<Place> places = new ArrayList<>();
        if (placeRepository != null) {
            for (Place place : placeRepository.findAll()) {
                if (place != null && place.getStateBusiness() != null && place.getStateBusiness().equals(StateUnilocal.Revision)) {
                    places.add(place);
                }
            }
        }
        return places;
    }

    public List<Place> getLugaresAutorizados() {
        List<Place> places = new ArrayList<>();
        if (placeRepository != null) {
            for (Place place : placeRepository.findAll()) {
                if (place != null && place.getStateBusiness() != null && place.getStateBusiness().equals(StateUnilocal.Active)) {
                    places.add(place);
                }
            }
        }
        return places;
    }

    public List<Place> getLugaresRechazados() {
        List<Place> places = new ArrayList<>();
        if (placeRepository != null) {
            for (Place place : placeRepository.findAll()) {
                if (place != null && place.getStateBusiness() != null && place.getStateBusiness().equals(StateUnilocal.Refused)) {
                    places.add(place);
                }
            }
        }
        return places;
    }

    public Person getOne(String id) throws ResourceNotFoundException {
        return clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found"));
    }

    public List<Revision> revisionHistory() throws NoDataFoundException {
        // Verificar si el repositorio de revisiones es nulo
        if (revisionRepository == null) {
            throw new IllegalStateException("El repositorio de revisiones no está inicializado correctamente.");
        }

        // Obtener todas las revisiones del repositorio
        List<Revision> revisions = revisionRepository.findAll();

        // Verificar si no se encontraron revisiones
        if (revisions.isEmpty()) {
            throw new NoDataFoundException("No se encontraron revisiones en la base de datos.");
        }

        return revisions;
    }


}
