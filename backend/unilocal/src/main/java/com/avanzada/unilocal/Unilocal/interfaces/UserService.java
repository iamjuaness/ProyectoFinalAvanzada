package com.avanzada.unilocal.Unilocal.interfaces;

import com.avanzada.unilocal.Unilocal.dto.RegisterUserDto;
import com.avanzada.unilocal.Unilocal.dto.UpdateUserDto;
import com.avanzada.unilocal.Unilocal.entity.Person;

public interface UserService extends AccountService {

    Person signUp(RegisterUserDto registerUserDto)throws Exception;

    Person profileEdit(UpdateUserDto updateUserDto, int id)throws Exception;

}
