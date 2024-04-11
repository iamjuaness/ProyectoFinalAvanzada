package com.avanzada.unilocal.Unilocal.interfaces;

import com.avanzada.unilocal.Unilocal.dto.ChangePasswordDTO;
import com.avanzada.unilocal.Unilocal.dto.SesionUserDto;
import com.avanzada.unilocal.Unilocal.entity.Person;

import java.util.Optional;

public interface AccountService {
    Optional<Person> login(SesionUserDto sesionUserDto)throws Exception;
    Person delete(int idCuenta)throws Exception;
    void changePassword(ChangePasswordDTO changePasswordDTO)throws Exception;
}