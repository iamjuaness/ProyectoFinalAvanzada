package com.avanzada.unilocal.Unilocal.service;

import com.avanzada.unilocal.Unilocal.dto.RegisterUserDto;
import com.avanzada.unilocal.Unilocal.dto.UpdateUserDto;
import com.avanzada.unilocal.Unilocal.entity.Person;
import com.avanzada.unilocal.Unilocal.repository.PersonRepository;
import com.avanzada.unilocal.Unilocal.enums.Role;
import com.avanzada.unilocal.global.exceptions.AttributeException;
import com.avanzada.unilocal.global.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class PersonService {

    @Autowired
    PersonRepository personRepository;

    public List<Person> getAll() {
        return personRepository.findAll();
    }


    public Person getOne(int id) throws ResourceNotFoundException {
        return personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found"));
    }

    public Person save(RegisterUserDto registerUserDto) throws AttributeException {

        if (personRepository.existsByNickname(registerUserDto.nickname()))
            throw new AttributeException("nickname already in use");

        int id = autoIncrement();
        Person person = new Person(id, registerUserDto.name(), registerUserDto.photo(), registerUserDto.nickname(), registerUserDto.email(), registerUserDto.password(), registerUserDto.residenceCity(), Role.USER);

        return personRepository.save(person);
    }

    public Person update(int id, UpdateUserDto updateUserDto) throws ResourceNotFoundException, AttributeException {

        Person person = personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found"));

        person.setName(updateUserDto.name());
        person.setEmail(updateUserDto.email());
        person.setNickname(updateUserDto.nickname());
        person.setPhoto(updateUserDto.photo());
        person.setResidenceCity(updateUserDto.residenceCity());

        return personRepository.save(person);
    }

    public Person delete(int id) throws ResourceNotFoundException {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found"));
        personRepository.delete(person);
        return person;
    }

    // private methods
    private int autoIncrement() {
        List<Person> people = personRepository.findAll();
        return people.isEmpty() ? 1 :
                people.stream().max(Comparator.comparing(Person::getId)).get().getId() + 1;
    }
}
