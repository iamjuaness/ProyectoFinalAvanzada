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
        Optional<Place> lugar = Optional.ofNullable(placeRepository.findById(lugarId)
                .orElseThrow(() -> new ResourceNotFoundException("Lugar no encontrado con ID: " + lugarId)));
        lugar.get().setStateBusiness(StateUnilocal.Active);
        Optional<Person> person = Optional.ofNullable(clientRepository.findById(lugar.get().getOwner())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + lugar.get().getOwner())));
        Revision revision = new Revision(revisionServiceImp.autoIncrement(), StateUnilocal.Active, registerRevisionDto.mod(), registerRevisionDto.description());
        revisionRepository.save(revision);
        emailService.sendEmail(new EmailDTO("Su negocio fue autorizado", "Nos agradece informarle que su negocio cumple con las normas y fue autorizado", person.get().getEmail()));
        lugar.get().getRevisions().add(revision.getId());
        placeRepository.save(lugar.get());
    }

    public void rechazarLugar(int lugarId) throws ResourceNotFoundException, MessagingException {
        Optional<Place> lugar = Optional.ofNullable(placeRepository.findById(lugarId)
                .orElseThrow(() -> new ResourceNotFoundException("Lugar no encontrado con ID: " + lugarId)));
        lugar.get().setStateBusiness(StateUnilocal.Refused);
        Optional<Person> person = Optional.ofNullable(clientRepository.findById(lugar.get().getOwner())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + lugar.get().getOwner())));
        emailService.sendEmail(new EmailDTO("Su negocio fue rechazado", "Lamentamos informarle que su negocio no cumple con las normas de UniLocal y fue rechazado", person.get().getEmail()));
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
