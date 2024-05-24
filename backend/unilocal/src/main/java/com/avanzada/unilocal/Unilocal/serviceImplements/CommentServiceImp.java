package com.avanzada.unilocal.Unilocal.serviceImplements;

import com.avanzada.unilocal.Unilocal.dto.CommentDTO;
import com.avanzada.unilocal.Unilocal.entity.Comment;
import com.avanzada.unilocal.Unilocal.entity.Place;
import com.avanzada.unilocal.Unilocal.entity.Qualification;
import com.avanzada.unilocal.Unilocal.interfaces.CommentService;
import com.avanzada.unilocal.Unilocal.repository.CommentRepository;
import com.avanzada.unilocal.Unilocal.repository.PlaceRepository;
import com.avanzada.unilocal.global.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImp implements CommentService {

    @Autowired
    CommentRepository commentRepository;
    @Autowired
    PlaceRepository placeRepository;

    @Override
    public void crearComentario(CommentDTO commentDTO, int lugarId) throws ResourceNotFoundException {
        Optional<Place> place = Optional.ofNullable(placeRepository.findById(lugarId)
                .orElseThrow(() -> new ResourceNotFoundException("lugar no encontrado")));

        int id = autoIncrement();
        Comment comment = new Comment(id, commentDTO.message(), commentDTO.idCliente());
        commentRepository.save(comment);
        place.get().getComments().add(String.valueOf(id));
        placeRepository.save(place.get());
    }

    @Override
    public void responderComentario(int comentarioId, CommentDTO respuesta) throws ResourceNotFoundException {
        // Implementación para responder a un comentario
        // Usar el comentarioRepository para obtener y actualizar el comentario con la respuesta
        Optional<Comment> comentario = Optional.ofNullable(commentRepository.findById(comentarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Comentario no encontrado")));
        int id = autoIncrement();
        Comment comment = new Comment(id, respuesta.message(), respuesta.idCliente());
        comentario.get().getResponses().add(String.valueOf(id));
        commentRepository.save(comment);
        commentRepository.save(comentario.get());
    }

    @Override
    public List<Comment> listarComentariosNegocio(int lugarId) {
        List<Comment> comments = new ArrayList<>();
        for (Place place : placeRepository.findAll()){
            if (place.getComments() != null && !place.getComments().isEmpty()){
                for (String comment : place.getComments()){
                    if (comment.equals(String.valueOf(lugarId))){
                        Optional<Comment> comment1 = commentRepository.findById(Integer.parseInt(comment));
                        comments.add(comment1.get());
                    }
                }
            }
        }
        return comments;
    }


    @Override
    public void eliminarComentario(int id, String idCliente) {
        for (Place place : placeRepository.findAll()){
            if (place != null && place.getOwner().equals(idCliente)){
                Optional<Comment> comment = commentRepository.findById(id);
                commentRepository.delete(comment.get());
            }
        }
    }

    private int autoIncrement() {
        List<Comment> comments = commentRepository.findAll();
        return comments.isEmpty() ? 1 :
                comments.stream().max(Comparator.comparing(Comment::getId)).get().getId() + 1;
    }

}
