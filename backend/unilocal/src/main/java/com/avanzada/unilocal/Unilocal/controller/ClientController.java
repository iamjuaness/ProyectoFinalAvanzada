package com.avanzada.unilocal.Unilocal.controller;

import com.avanzada.unilocal.Unilocal.dto.RegisterUserDto;
import com.avanzada.unilocal.Unilocal.dto.SesionUserDto;
import com.avanzada.unilocal.Unilocal.dto.TokenDto;
import com.avanzada.unilocal.Unilocal.dto.UpdateUserDto;
import com.avanzada.unilocal.Unilocal.entity.Person;
import com.avanzada.unilocal.Unilocal.serviceImplements.AuthServiceImp;
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
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/user")
public class ClientController {

    @Autowired
    PersonService personService;
    @Autowired
    AuthServiceImp authServiceImp;


    @GetMapping("/get-all")
    public ResponseEntity<List<Person>> getAll() {
        return ResponseEntity.ok(personService.getAll());
    }


    @GetMapping("/get/{id}")
    public ResponseEntity<Person> getOne(@PathVariable("id") int id) throws ResourceNotFoundException {
        return ResponseEntity.ok(personService.getOne(id));
    }



    @PutMapping("/edit-profile/{id}")
    public ResponseEntity<MessageDto> update(@PathVariable("id") int id, @Valid @RequestBody UpdateUserDto updateUserDto) throws ResourceNotFoundException, AttributeException {
        Person person = personService.profileEdit(updateUserDto, id);
        String message = "user " + person.getName() + " have been updated";

        return ResponseEntity.ok(new MessageDto(HttpStatus.OK, message));
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<MessageDto> delete(@PathVariable("id") int id) throws ResourceNotFoundException {
        Person person = personService.delete(id);
        String message = "user " + person.getName() + " have been deleted";

        return ResponseEntity.ok(new MessageDto(HttpStatus.OK, message));
    }

}
