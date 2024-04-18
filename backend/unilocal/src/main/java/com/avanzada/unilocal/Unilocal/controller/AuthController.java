package com.avanzada.unilocal.Unilocal.controller;

import com.avanzada.unilocal.Unilocal.dto.RegisterUserDto;
import com.avanzada.unilocal.Unilocal.dto.SesionUserDto;
import com.avanzada.unilocal.Unilocal.dto.TokenDto;
import com.avanzada.unilocal.Unilocal.serviceImplements.AuthServiceImp;
import com.avanzada.unilocal.Unilocal.serviceImplements.PersonService;
import com.avanzada.unilocal.global.dto.MensajeAuthDto;
import com.avanzada.unilocal.global.dto.MessageDto;
import com.avanzada.unilocal.global.exceptions.AttributeException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthServiceImp authServiceImp;

    @Autowired
    PersonService personService;

    @PostMapping("/login-client")
    public ResponseEntity<MensajeAuthDto<TokenDto>> loginUser(@RequestBody SesionUserDto userDto) throws Exception {
        TokenDto tokenDto = authServiceImp.loginClient(userDto);
        return ResponseEntity.ok().body(new MensajeAuthDto<>(false, tokenDto));
    }

    @PostMapping("/register-client")
    public ResponseEntity<MessageDto> registerUser(@Valid @RequestBody RegisterUserDto registerUserDto) throws AttributeException {

        personService.signUp(registerUserDto);
        String message = "user " + registerUserDto.name() + " have been saved";

        return ResponseEntity.ok(new MessageDto(HttpStatus.OK, message));
    }

    @PostMapping("/login-mod")
    public ResponseEntity<MensajeAuthDto<TokenDto>> loginMod(@RequestBody SesionUserDto userDto) throws Exception {
        TokenDto tokenDto = authServiceImp.loginMod(userDto);
        return ResponseEntity.ok().body(new MensajeAuthDto<>(false, tokenDto));
    }
}
