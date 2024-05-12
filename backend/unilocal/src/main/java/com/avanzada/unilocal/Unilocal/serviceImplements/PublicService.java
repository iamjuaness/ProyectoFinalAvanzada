package com.avanzada.unilocal.Unilocal.serviceImplements;

import com.avanzada.unilocal.Unilocal.repository.CiudadRepository;
import com.avanzada.unilocal.Unilocal.repository.TiposRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PublicService {

    @Autowired
    private CiudadRepository ciudadRepository;

    @Autowired
    private TiposRepository tiposRepository;


    public List<String> getCiudades(){
        return ciudadRepository.findAll();
    }

    public List<String> getTipos(){
        return tiposRepository.findAll();
    }
}
