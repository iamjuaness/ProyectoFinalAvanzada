package com.avanzada.unilocal.Unilocal.interfaces;

import com.avanzada.unilocal.Unilocal.dto.*;
import com.avanzada.unilocal.Unilocal.entity.Person;
import com.avanzada.unilocal.Unilocal.entity.Place;
import com.avanzada.unilocal.global.exceptions.ResourceNotFoundException;
import jakarta.mail.MessagingException;

import java.util.List;

public interface UserService extends AccountService {

    Person signUp(RegisterUserDto registerUserDto)throws Exception;

    Person profileEdit(UpdateUserDto updateUserDto, String id) throws ResourceNotFoundException;

    void sendLinkPassword(EmailDTO emailDTO) throws ResourceNotFoundException, MessagingException;

    void agregarFavorito(String usuarioId, int lugarId) throws ResourceNotFoundException;

    void eliminarFavorito(String usuarioId, int lugarId) throws ResourceNotFoundException;

    List<Place> obtenerFavoritos(String usuarioId) throws ResourceNotFoundException;

    void addComment(int lugarId, CommentDTO comment) throws ResourceNotFoundException;

    void eliminarComentario(int id, String idCliente);

    void addQualification(int lugarId, QualificationDTO qualificationDTO);

    List<Place> obtenerLugaresUsuario(String usuarioId) throws IllegalArgumentException, ResourceNotFoundException;

    void responderComentario(int comentarioId, CommentDTO respuesta) throws ResourceNotFoundException;
}
