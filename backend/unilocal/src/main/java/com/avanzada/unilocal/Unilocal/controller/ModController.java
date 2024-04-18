package com.avanzada.unilocal.Unilocal.controller;

import com.avanzada.unilocal.Unilocal.dto.SesionUserDto;
import com.avanzada.unilocal.Unilocal.dto.TokenDto;
import com.avanzada.unilocal.Unilocal.entity.Person;
import com.avanzada.unilocal.Unilocal.entity.Place;
import com.avanzada.unilocal.Unilocal.serviceImplements.ModeradorService;
import com.avanzada.unilocal.Unilocal.serviceImplements.PersonService;
import com.avanzada.unilocal.global.exceptions.ResourceNotFoundException;
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


    @PostMapping("/autorizar/{lugarId}")
    public void autorizarLugar(@PathVariable int lugarId) throws ResourceNotFoundException {
        moderadorService.autorizarLugar(lugarId);
    }

    @PostMapping("/rechazar/{lugarId}")
    public void rechazarLugar(@PathVariable int lugarId) throws ResourceNotFoundException {
        moderadorService.rechazarLugar(lugarId);
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
}
