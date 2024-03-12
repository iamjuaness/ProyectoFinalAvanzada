package com.avanzada.unilocal.Unilocal.interfaces;

import com.avanzada.unilocal.Unilocal.dto.CreatePlaceDto;
import com.avanzada.unilocal.Unilocal.dto.RegisterRevisionDto;
import com.avanzada.unilocal.Unilocal.entity.Place;
import com.avanzada.unilocal.Unilocal.enums.StateUnilocal;
import com.avanzada.unilocal.global.exceptions.AttributeException;
import com.avanzada.unilocal.global.exceptions.ResourceNotFoundException;

/**
 * @author Juanes Cardona
 */
public interface BusinessService {

    Place createBusiness(CreatePlaceDto createPlaceDto) throws AttributeException;
    Place updateBusiness(int id, CreatePlaceDto createPlaceDto) throws AttributeException, ResourceNotFoundException;

    Place deleteBusiness(int id) throws ResourceNotFoundException;

    void findBusiness(int id);

    void filterByState(StateUnilocal stateBusiness);

    void listOwnerBusiness();

    void changeState(StateUnilocal newState);

    void registerRevision(RegisterRevisionDto registerRevisionDto);

}
