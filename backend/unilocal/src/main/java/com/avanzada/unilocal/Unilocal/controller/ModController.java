package com.avanzada.unilocal.Unilocal.controller;

import com.avanzada.unilocal.Unilocal.dto.SesionUserDto;
import com.avanzada.unilocal.Unilocal.dto.TokenDto;
import com.avanzada.unilocal.Unilocal.entity.Person;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/mods")
public class ModController {




//    @PostMapping("/login-mod")
//    public ResponseEntity<?> loginMod(@RequestBody SesionUserDto userDto) throws Exception {
//        // Buscar el usuario en la base de datos
//        Optional<Person> user = personService.login(userDto);
//        Person person = null;
//
//        // Comprobar si el usuario existe y la contraseña es correcta
//        if (user.isEmpty()) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
//        } else {
//            person = user.get();
//        }
//
//        Map<String, Object> authToken = new HashMap<>();
//        authToken.put("role", person.getRole());
//        authToken.put("nombre", person.getName());
//        authToken.put("id", person.getId());
//
//        TokenDto tokenDto = new TokenDto(jwtTokenService.generarToken(person.getEmail(), authToken));
//
//        return ResponseEntity.ok(tokenDto);
//    }
}
