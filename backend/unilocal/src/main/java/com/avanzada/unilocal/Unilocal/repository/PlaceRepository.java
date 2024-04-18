package com.avanzada.unilocal.Unilocal.repository;

import com.avanzada.unilocal.Unilocal.entity.Place;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface PlaceRepository extends MongoRepository<Place, Integer> {

    boolean existsByName(String name);
    Optional<Place> findByName(String name);
    Optional<Place> findById(int id);

    List<Place> findByNombreContainingIgnoreCase(String nombre);

    List<Place> findByTipoIgnoreCase(String tipo);

//    @Query("SELECT l FROM Lugar l WHERE " +
//            "ACOS(SIN(RADIANS(:latitud)) * SIN(RADIANS(l.latitud)) + " + "COS(RADIANS(:latitud)) * COS(RADIANS(l.latitud)) * COS(RADIANS(l.longitud) - RADIANS(:longitud))) * 6371 <= :distanciaMaxima")
//    List<Place> buscarPorUbicacion(@Param("latitud") Double latitud,
//                                   @Param("longitud") Double longitud,
//                                   @Param("distanciaMaxima") Double distanciaMaxima);
//
//    @Query("SELECT l FROM Lugar l WHERE " +
//            "l.nombre LIKE %:nombre% AND " +
//            "l.tipo LIKE %:tipo% AND " +
//            "ACOS(SIN(RADIANS(:latitud)) * SIN(RADIANS(l.latitud)) + " +
//            "COS(RADIANS(:latitud)) * COS(RADIANS(l.latitud)) * COS(RADIANS(l.longitud) - RADIANS(:longitud))) * 6371 <= :distanciaMaxima")
//    List<Place> buscarPorNombreTipoYDistancia(@Param("nombre") String nombre,
//                                              @Param("tipo") String tipo,
//                                              @Param("latitud") Double latitud,
//                                              @Param("longitud") Double longitud,
//                                              @Param("distanciaMaxima") Double distanciaMaxima);

    List<Place> findByUsuarioId(int usuarioId);
}
