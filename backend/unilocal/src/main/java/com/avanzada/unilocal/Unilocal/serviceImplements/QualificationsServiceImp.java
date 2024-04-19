package com.avanzada.unilocal.Unilocal.serviceImplements;

import com.avanzada.unilocal.Unilocal.dto.QualificationDTO;
import com.avanzada.unilocal.Unilocal.entity.Place;
import com.avanzada.unilocal.Unilocal.entity.Qualification;
import com.avanzada.unilocal.Unilocal.interfaces.QualificationService;
import com.avanzada.unilocal.Unilocal.repository.ClientRepository;
import com.avanzada.unilocal.Unilocal.repository.PlaceRepository;
import com.avanzada.unilocal.Unilocal.repository.QualificationRepository;
import com.avanzada.unilocal.global.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class QualificationsServiceImp implements QualificationService {

    @Autowired
    PlaceRepository placeRepository;
    @Autowired
    QualificationRepository qualificationRepository;
    @Autowired
    ClientRepository clientRepository;

    @Override
    public void agregarCalificacion(int lugarId, QualificationDTO qualificationDTO) throws ResourceNotFoundException {
        Optional<Place> place = Optional.ofNullable(placeRepository.findById(lugarId)
                .orElseThrow(() -> new ResourceNotFoundException("Lugar no encontrado")));

        Qualification qualification = new Qualification(qualificationDTO.qualification(), lugarId, qualificationDTO.idCliente());
        place.get().getQualifications().add(qualification.getId());
    }

    @Override
    public void actualizarCalificacion(int idCalificacion, QualificationDTO qualificationDTO) throws ResourceNotFoundException {
        Optional<Qualification> qualification = Optional.ofNullable(qualificationRepository.findById(idCalificacion)
                .orElseThrow(() -> new ResourceNotFoundException("Calificacion no encontrado")));

        qualification.get().setQualification(qualificationDTO.qualification());
        qualification.get().setIdCliente(qualificationDTO.idCliente());
        qualificationRepository.save(qualification.get());
    }

    @Override
    public void eliminarCalificacion(int idCalificacion, String idCliente) throws ResourceNotFoundException {
        Optional<Qualification> qualification = Optional.ofNullable(qualificationRepository.findById(idCalificacion)
                .orElseThrow(() -> new ResourceNotFoundException("Calificacion no encontrado")));

        if (qualification.get().getIdCliente().equals(idCliente)){
            qualificationRepository.delete(qualification.get());
        }
    }

    @Override
    public double calcularPromedioCalificaciones(int lugarId) throws ResourceNotFoundException {
        double suma = 0.0;
        Optional<Place> place = Optional.ofNullable(placeRepository.findById(lugarId)
                .orElseThrow(() -> new ResourceNotFoundException("Lugar no encontrado")));

        for (int i : place.get().getQualifications()){
            if (i != 0 ){
                suma += qualificationRepository.findById(i).get().getQualification();
            }
        }
        return  suma/place.get().getQualifications().size();
    }
}
