package com.avanzada.unilocal.Unilocal.controller;

import com.avanzada.unilocal.Unilocal.dto.*;
import com.avanzada.unilocal.Unilocal.entity.Person;
import com.avanzada.unilocal.Unilocal.entity.Place;
import com.avanzada.unilocal.Unilocal.serviceImplements.AuthServiceImp;
import com.avanzada.unilocal.Unilocal.serviceImplements.PersonService;
import com.avanzada.unilocal.global.dto.MessageDto;
import com.avanzada.unilocal.global.exceptions.AttributeException;
import com.avanzada.unilocal.global.exceptions.ResourceNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/user")
public class ClientController {

    @Autowired
    PersonService personService;
    @Autowired
    AuthServiceImp authServiceImp;


    @GetMapping("/get-all")
    public ResponseEntity<List<Person>> getAll() {
        return ResponseEntity.ok(personService.getAll());
    }


    @GetMapping("/get/{id}")
    public ResponseEntity<Person> getOne(@PathVariable("id") String id) throws ResourceNotFoundException {
        return ResponseEntity.ok(personService.getOne(id));
    }



    @PutMapping("/edit-profile/{id}")
    public ResponseEntity<MessageDto> update(@PathVariable("id") String id, @Valid @RequestBody UpdateUserDto updateUserDto) throws ResourceNotFoundException, AttributeException {
        Person person = personService.profileEdit(updateUserDto, id);
        String message = "user " + person.getName() + " have been updated";

        return ResponseEntity.ok(new MessageDto(HttpStatus.OK, message));
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<MessageDto> delete(@PathVariable("id") String id) throws ResourceNotFoundException {
        Person person = personService.delete(id);
        String message = "user " + person.getName() + " have been deleted";

        return ResponseEntity.ok(new MessageDto(HttpStatus.OK, message));
    }

    @PostMapping("/{usuarioId}/favoritos/{lugarId}")
    public ResponseEntity<String> agregarFavorito(@PathVariable String usuarioId, @PathVariable int lugarId) {
        try {
            personService.agregarFavorito(usuarioId, lugarId);
            return ResponseEntity.ok("Lugar agregado a favoritos.");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{usuarioId}/favoritos/{lugarId}")
    public ResponseEntity<String> eliminarFavorito(@PathVariable String usuarioId, @PathVariable int lugarId) {
        try {
            personService.eliminarFavorito(usuarioId, lugarId);
            return ResponseEntity.ok("Lugar eliminado de favoritos.");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/{usuarioId}/favoritos")
    public ResponseEntity<List<Place>> obtenerFavoritos(@PathVariable String usuarioId) throws ResourceNotFoundException {
        List<Place> favoritos = personService.obtenerFavoritos(usuarioId);
        return ResponseEntity.ok(favoritos);
    }

    @PostMapping("/{lugarId}/comments")
    public ResponseEntity<String> agregarComentario(@PathVariable int lugarId, @RequestBody CommentDTO comentario) throws ResourceNotFoundException {
        personService.addComment(lugarId, comentario);
        return ResponseEntity.ok("Comentario agregado exitosamente.");
    }

    @GetMapping("/usuario/{id}/lugares")
    public List<Place> obtenerLugaresUsuario(@PathVariable String id) throws ResourceNotFoundException {
        return personService.obtenerLugaresUsuario(id);
    }

    @PostMapping("/comentario/{id}/responder")
    public void responderComentario(@PathVariable int id, @RequestBody CommentDTO respuesta) throws ResourceNotFoundException {
        personService.responderComentario(id, respuesta);
    }

    @DeleteMapping("/comentario/{id}/delete/{idCliente}")
    public void eliminarComentario(@PathVariable int id, @PathVariable String idCliente) throws ResourceNotFoundException {
        personService.eliminarComentario(id, idCliente);
    }

    @PostMapping("/{lugarId}/qualifications")
    public ResponseEntity<String> agregarCalificacion(@PathVariable int lugarId, @RequestBody QualificationDTO qualificationDTO) throws ResourceNotFoundException {
        personService.addQualification(lugarId, qualificationDTO);
        return ResponseEntity.ok("Comentario agregado exitosamente.");
    }

}
