package com.avanzada.unilocal.Unilocal.controller;

import com.avanzada.unilocal.Unilocal.dto.*;
import com.avanzada.unilocal.Unilocal.entity.Place;
import com.avanzada.unilocal.Unilocal.serviceImplements.AuthServiceImp;
import com.avanzada.unilocal.Unilocal.serviceImplements.PersonService;
import com.avanzada.unilocal.Unilocal.serviceImplements.PlaceService;
import com.avanzada.unilocal.global.dto.MensajeAuthDto;
import com.avanzada.unilocal.global.dto.MessageDto;
import com.avanzada.unilocal.global.exceptions.AttributeException;
import com.avanzada.unilocal.global.exceptions.ResourceNotFoundException;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthServiceImp authServiceImp;

    @Autowired
    PersonService personService;

    @Autowired
    PlaceService placeService;

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

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody EmailDTO emailDTO) {
        try {
            personService.sendLinkPassword(emailDTO);
            return ResponseEntity.ok("Se ha enviado un correo electrónico con el enlace para restablecer la contraseña.");
        } catch (ResourceNotFoundException | MessagingException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody ChangePasswordDTO changePasswordDTO) {
        try {
            personService.changePassword(changePasswordDTO);
            return ResponseEntity.ok("Contraseña cambiada exitosamente.");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/login-mod")
    public ResponseEntity<MensajeAuthDto<TokenDto>> loginMod(@RequestBody SesionUserDto userDto) throws Exception {
        TokenDto tokenDto = authServiceImp.loginMod(userDto);
        return ResponseEntity.ok().body(new MensajeAuthDto<>(false, tokenDto));
    }

    @GetMapping("/get-places")
    public ResponseEntity<List<Place>> getAll(){
        return ResponseEntity.ok(placeService.getAll());
    }

}
