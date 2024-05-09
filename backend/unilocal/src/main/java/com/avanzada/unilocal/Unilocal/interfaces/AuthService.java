package com.avanzada.unilocal.Unilocal.interfaces;

import com.avanzada.unilocal.Unilocal.dto.RegisterUserDto;
import com.avanzada.unilocal.Unilocal.dto.SesionUserDto;
import com.avanzada.unilocal.Unilocal.dto.TokenDto;
import com.avanzada.unilocal.global.exceptions.AttributeException;

public interface AuthService<T> {

    TokenDto loginClient(SesionUserDto sesionUserDto) throws Exception;

    TokenDto registerClient(RegisterUserDto<T> registerUserDto) throws AttributeException;

    TokenDto loginMod(SesionUserDto sesionUserDto) throws Exception;
}
