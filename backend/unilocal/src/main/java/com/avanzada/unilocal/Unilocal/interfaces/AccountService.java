package com.avanzada.unilocal.Unilocal.interfaces;

import com.avanzada.unilocal.Unilocal.dto.ChangePasswordDTO;
import com.avanzada.unilocal.Unilocal.dto.SesionUserDto;
import com.avanzada.unilocal.Unilocal.entity.Person;

public interface AccountService {
    void login(SesionUserDto sesionUserDto)throws Exception;
    Person delete(int idCuenta)throws Exception;
    void sendLinkPassword(String email)throws Exception;
    void changePassword(ChangePasswordDTO changePasswordDTO)throws Exception;
}