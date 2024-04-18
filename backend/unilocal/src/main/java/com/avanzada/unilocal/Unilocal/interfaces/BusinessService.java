package com.avanzada.unilocal.Unilocal.interfaces;

import com.avanzada.unilocal.Unilocal.dto.CommentDTO;
import com.avanzada.unilocal.Unilocal.dto.CreatePlaceDto;
import com.avanzada.unilocal.Unilocal.dto.RegisterRevisionDto;
import com.avanzada.unilocal.Unilocal.entity.Comment;
import com.avanzada.unilocal.Unilocal.entity.Person;
import com.avanzada.unilocal.Unilocal.entity.Place;
import com.avanzada.unilocal.Unilocal.enums.StateUnilocal;
import com.avanzada.unilocal.global.exceptions.AttributeException;
import com.avanzada.unilocal.global.exceptions.ResourceNotFoundException;

import java.util.List;

/**
 * @author Juanes Cardona
 */
public interface BusinessService {

    Place createBusiness(CreatePlaceDto createPlaceDto) throws AttributeException;
    Place updateBusiness(int id, CreatePlaceDto createPlaceDto) throws AttributeException, ResourceNotFoundException;

    Place deleteBusiness(int id) throws ResourceNotFoundException;

    Place findBusiness(int id) throws ResourceNotFoundException;

    List<Place> filterByState(StateUnilocal stateBusiness);

    List<Person> listOwnerBusiness() throws ResourceNotFoundException;

    void changeState(StateUnilocal newState, int id) throws ResourceNotFoundException;

    void registerRevision(RegisterRevisionDto registerRevisionDto, int id) throws ResourceNotFoundException;

    void addComment(int lugarId, CommentDTO comment);

}
