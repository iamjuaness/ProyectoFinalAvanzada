package com.avanzada.unilocal.Unilocal.serviceImplements;

import com.avanzada.unilocal.Unilocal.dto.CrearCiudadDto;
import com.avanzada.unilocal.Unilocal.dto.CrearTipoDto;
import com.avanzada.unilocal.Unilocal.entity.Ciudad;
import com.avanzada.unilocal.Unilocal.entity.Place;
import com.avanzada.unilocal.Unilocal.entity.Tipo;
import com.avanzada.unilocal.Unilocal.enums.StateUnilocal;
import com.avanzada.unilocal.Unilocal.repository.CiudadRepository;
import com.avanzada.unilocal.Unilocal.repository.PlaceRepository;
import com.avanzada.unilocal.Unilocal.repository.TiposRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;

@Service
public class PublicService {

    @Autowired
    private CiudadRepository ciudadRepository;

    @Autowired
    private TiposRepository tiposRepository;

    @Autowired
    private PlaceRepository placeRepository;


    public List<Ciudad> getCiudades(){
        return ciudadRepository.findAll();
    }

    public List<Tipo> getTipos(){
        return tiposRepository.findAll();
    }

    public void crearCiudad(CrearCiudadDto crearCiudadDto){
        Ciudad ciudad = new Ciudad();

        ciudad.setNombre(crearCiudadDto.nombre());
        ciudad.setId(autoIncrementCiudad());
        ciudadRepository.save(ciudad);

    }

    public void crearTipos(CrearTipoDto crearTipoDto){
        Tipo tipo = new Tipo();
        tipo.setId(autoIncrementTipo());
        tipo.setTipo(crearTipoDto.tipo());

        tiposRepository.save(tipo);
    }

    public void cambiarEstadoNegociosRechazados() {
        // Obtener la fecha actual
        LocalDate fechaActual = LocalDate.now();

        // Obtener la lista de negocios rechazados
        List<Place> negociosRechazados = placeRepository.findByStateBusiness(StateUnilocal.Refused);

        // Iterar sobre los negocios rechazados
        for (Place negocio : negociosRechazados) {
            // Calcular la diferencia en días entre la fecha actual y la fecha de rechazo del negocio
//            long diasDesdeRechazo = ChronoUnit.DAYS.between(negocio.getFechaRechazo(), fechaActual);

            // Si han pasado más de 5 días desde el rechazo, cambiar el estado del negocio a "inactivo"
//            if (diasDesdeRechazo > 5) {
//                negocio.setStateBusiness(StateUnilocal.Inactive);
//                // Guardar el cambio en la base de datos
//                placeRepository.save(negocio);
//            }
        }
    }

    // private methods
    private int autoIncrementCiudad() {
        List<Ciudad> ciudad = ciudadRepository.findAll();
        return ciudad.isEmpty() ? 1 :
                ciudad.stream().max(Comparator.comparing(Ciudad::getId)).get().getId() + 1;
    }

    private int autoIncrementTipo() {
        List<Tipo> tipo = tiposRepository.findAll();
        return tipo.isEmpty() ? 1 :
                tipo.stream().max(Comparator.comparing(Tipo::getId)).get().getId() + 1;
    }
}
