package com.avanzada.unilocal.Unilocal.interfaces;

import com.avanzada.unilocal.Unilocal.dto.SesionUserDto;
import com.avanzada.unilocal.Unilocal.dto.TokenDto;

public interface AuthService {

    TokenDto loginClient(SesionUserDto sesionUserDto) throws Exception;
    TokenDto loginMod(SesionUserDto sesionUserDto) throws Exception;
}
