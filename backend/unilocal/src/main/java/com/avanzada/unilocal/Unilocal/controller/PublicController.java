package com.avanzada.unilocal.Unilocal.controller;

import com.avanzada.unilocal.Unilocal.dto.CrearCiudadDto;
import com.avanzada.unilocal.Unilocal.dto.CrearTipoDto;
import com.avanzada.unilocal.Unilocal.entity.Ciudad;
import com.avanzada.unilocal.Unilocal.entity.Tipo;
import com.avanzada.unilocal.Unilocal.serviceImplements.PublicService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/public")
public class PublicController {

    @Autowired
    private PublicService publicService;

    @GetMapping("/listar-ciudades")
    public List<Ciudad> getCiudades(){
        return publicService.getCiudades();
    }

    @GetMapping("/listar-tipos-negocios")
    public List<Tipo> getTipos(){
        return publicService.getTipos();
    }

    @PostMapping("/crear-ciudades")
    public void crearCiudad(@Valid @RequestBody CrearCiudadDto crearCiudadDto){
        publicService.crearCiudad(crearCiudadDto);
    }

    @PostMapping("/crear-tipos")
    public void crearTipo(@Valid @RequestBody CrearTipoDto crearTipoDto){
        publicService.crearTipos(crearTipoDto);
    }

}
