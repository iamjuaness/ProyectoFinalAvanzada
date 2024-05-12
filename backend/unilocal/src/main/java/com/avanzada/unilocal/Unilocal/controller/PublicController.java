package com.avanzada.unilocal.Unilocal.controller;

import com.avanzada.unilocal.Unilocal.serviceImplements.PublicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/public")
public class PublicController {

    @Autowired
    private PublicService publicService;

    @GetMapping("/listar-ciudades")
    public List<String> getCiudades(){
        return publicService.getCiudades();
    }

    @GetMapping("/listar-tipos-negocios")
    public List<String> getTipos(){
        return publicService.getCiudades();
    }

}
