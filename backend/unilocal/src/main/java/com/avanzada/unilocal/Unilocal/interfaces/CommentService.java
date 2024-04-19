package com.avanzada.unilocal.Unilocal.interfaces;

import com.avanzada.unilocal.Unilocal.dto.CommentDTO;
import com.avanzada.unilocal.Unilocal.entity.Comment;
import com.avanzada.unilocal.global.exceptions.ResourceNotFoundException;

import java.util.List;

public interface CommentService {

    void crearComentario(CommentDTO commentDTO, int lugarId) throws ResourceNotFoundException;


    void responderComentario(int comentarioId, CommentDTO respuesta) throws ResourceNotFoundException;

    List<Comment> listarComentariosNegocio(int lugarId);

    void eliminarComentario(int id, String idCliente);
}