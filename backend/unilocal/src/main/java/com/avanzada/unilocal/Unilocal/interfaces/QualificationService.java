package com.avanzada.unilocal.Unilocal.interfaces;

import com.avanzada.unilocal.Unilocal.dto.QualificationDTO;
import com.avanzada.unilocal.global.exceptions.ResourceNotFoundException;

public interface QualificationService {

    void agregarCalificacion(int lugarId, QualificationDTO qualificationDTO) throws ResourceNotFoundException;
    void actualizarCalificacion(int idCalificacion, QualificationDTO qualificationDTO) throws ResourceNotFoundException;

    void eliminarCalificacion(int idCalificacion, String idCliente) throws ResourceNotFoundException;

    double calcularPromedioCalificaciones(int lugarId) throws ResourceNotFoundException;
}
