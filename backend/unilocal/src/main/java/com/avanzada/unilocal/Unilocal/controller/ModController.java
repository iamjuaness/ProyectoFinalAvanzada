package com.avanzada.unilocal.Unilocal.controller;

import com.avanzada.unilocal.Unilocal.dto.EmailDTO;
import com.avanzada.unilocal.Unilocal.dto.RegisterRevisionDto;
import com.avanzada.unilocal.Unilocal.dto.SesionUserDto;
import com.avanzada.unilocal.Unilocal.dto.TokenDto;
import com.avanzada.unilocal.Unilocal.entity.Person;
import com.avanzada.unilocal.Unilocal.entity.Place;
import com.avanzada.unilocal.Unilocal.entity.Revision;
import com.avanzada.unilocal.Unilocal.serviceImplements.ModeradorService;
import com.avanzada.unilocal.Unilocal.serviceImplements.PersonService;
import com.avanzada.unilocal.global.exceptions.ResourceNotFoundException;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/mods")
public class ModController {

    @Autowired
    ModeradorService moderadorService;
    @Autowired
    PersonService personService;


    @PostMapping("/autorizar/{lugarId}")
    public ResponseEntity<String> autorizarLugar(@PathVariable int lugarId, @Valid @RequestBody RegisterRevisionDto registerRevisionDto) throws ResourceNotFoundException, MessagingException {
        try {
            moderadorService.autorizarLugar(lugarId, registerRevisionDto);
            return ResponseEntity.ok("Se ha enviado un correo electronico al usuario avisando que su negocio fue autorizado.");
        } catch (ResourceNotFoundException | MessagingException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/rechazar/{lugarId}")
    public ResponseEntity<String> rechazarLugar(@PathVariable int lugarId) throws ResourceNotFoundException, MessagingException {
        try {
            moderadorService.rechazarLugar(lugarId);
            return ResponseEntity.ok("Se ha enviado un correo electronico al usuario avisando que su negocio fue rechazado.");
        } catch (ResourceNotFoundException | MessagingException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/lugares/pendientes")
    public List<Place> getLugaresPendientes() {
        return moderadorService.getLugaresPendientes();
    }

    @GetMapping("/lugares/autorizados")
    public List<Place> getLugaresAutorizados() {
        return moderadorService.getLugaresAutorizados();
    }

    @GetMapping("/lugares/rechazados")
    public List<Place> getLugaresRechazados() {
        return moderadorService.getLugaresRechazados();
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Person> getOne(@PathVariable("id") String id) throws ResourceNotFoundException {
        return ResponseEntity.ok(personService.getOne(id));
    }

    @GetMapping("/revision-history")
    public List<Revision> getRevisionsHistory(){
        return moderadorService.revisionHistory();
    }
}
