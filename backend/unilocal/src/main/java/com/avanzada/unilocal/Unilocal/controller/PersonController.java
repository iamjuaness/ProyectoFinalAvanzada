package com.avanzada.unilocal.Unilocal.controller;

import com.avanzada.unilocal.Unilocal.dto.RegisterUserDto;
import com.avanzada.unilocal.Unilocal.dto.SesionUserDto;
import com.avanzada.unilocal.Unilocal.dto.TokenDto;
import com.avanzada.unilocal.Unilocal.dto.UpdateUserDto;
import com.avanzada.unilocal.Unilocal.entity.Person;
import com.avanzada.unilocal.Unilocal.serviceImplements.JwtTokenService;
import com.avanzada.unilocal.Unilocal.serviceImplements.PersonService;
import com.avanzada.unilocal.global.dto.MessageDto;
import com.avanzada.unilocal.global.exceptions.AttributeException;
import com.avanzada.unilocal.global.exceptions.ResourceNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class PersonController {

    @Autowired
    PersonService personService;

    @Autowired
    private JwtTokenService jwtTokenService;


    @GetMapping("/users")
    public ResponseEntity<List<Person>> getAll() {
        return ResponseEntity.ok(personService.getAll());
    }


    @GetMapping("/{id}")
    public ResponseEntity<Person> getOne(@PathVariable("id") int id) throws ResourceNotFoundException {
        return ResponseEntity.ok(personService.getOne(id));
    }

    @PostMapping("/register")
    public ResponseEntity<MessageDto> registerUser(@Valid @RequestBody RegisterUserDto registerUserDto) throws AttributeException {

        personService.signUp(registerUserDto);
        String message = "user " + registerUserDto.name() + " have been saved";

        return ResponseEntity.ok(new MessageDto(HttpStatus.OK, message));
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody SesionUserDto userDto) throws Exception {
        // Buscar el usuario en la base de datos
        Optional<Person> user = personService.login(userDto);
        Person person = null;

        // Comprobar si el usuario existe y la contraseña es correcta
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
        } else {
            person = user.get();
        }

        Map<String, Object> authToken = new HashMap<>();
        authToken.put("role", person.getRole());
        authToken.put("nombre", person.getName());
        authToken.put("id", person.getId());

        TokenDto tokenDto = new TokenDto(jwtTokenService.generarToken(person.getEmail(), authToken));

        return ResponseEntity.ok(tokenDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MessageDto> update(@PathVariable("id") int id, @Valid @RequestBody UpdateUserDto updateUserDto) throws ResourceNotFoundException, AttributeException {
        Person person = personService.profileEdit(updateUserDto, id);
        String message = "user " + person.getName() + " have been updated";

        return ResponseEntity.ok(new MessageDto(HttpStatus.OK, message));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<MessageDto> delete(@PathVariable("id") int id) throws ResourceNotFoundException {
        Person person = personService.delete(id);
        String message = "user " + person.getName() + " have been deleted";

        return ResponseEntity.ok(new MessageDto(HttpStatus.OK, message));
    }

}
