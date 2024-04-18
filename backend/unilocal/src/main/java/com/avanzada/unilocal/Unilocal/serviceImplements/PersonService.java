package com.avanzada.unilocal.Unilocal.serviceImplements;

import com.avanzada.unilocal.Unilocal.dto.*;
import com.avanzada.unilocal.Unilocal.entity.Person;
import com.avanzada.unilocal.Unilocal.enums.StateUnilocal;
import com.avanzada.unilocal.Unilocal.interfaces.UserService;
import com.avanzada.unilocal.Unilocal.repository.ClientRepository;
import com.avanzada.unilocal.Unilocal.enums.Role;
import com.avanzada.unilocal.global.exceptions.AttributeException;
import com.avanzada.unilocal.global.exceptions.ResourceNotFoundException;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * @author Juanes Cardona
 */
@Service
public class PersonService implements UserService {

    @Autowired
    ClientRepository clientRepository;
    @Autowired
    EmailService emailService;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public PersonService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Method to register a new user
     *
     * @param registerUserDto User to register
     * @return The registered user
     * @throws AttributeException Exception that will be executed if the nickname already exists
     */
    @Override
    public Person signUp(RegisterUserDto registerUserDto) throws AttributeException {
        if (clientRepository.existsByNickname(registerUserDto.nickname()))
            throw new AttributeException("nickname already in use");
        if (clientRepository.existsByEmail(registerUserDto.email()))
            throw new AttributeException("Email already in use");

        int id = autoIncrement();
        String pass = passwordEncoder.encode(registerUserDto.password());
        Person person = getPerson(registerUserDto, id, pass);
        return clientRepository.save(person);
    }

    private static Person getPerson(RegisterUserDto registerUserDto, int id, String password) {
        StateUnilocal register = StateUnilocal.Active;
        Person person = new Person();
        person.setId(id);
        person.setName(registerUserDto.name());
        person.setNickname(registerUserDto.nickname());
        person.setPassword(password);
        person.setEmail(registerUserDto.email());
        person.setResidenceCity(registerUserDto.residenceCity());
        person.setRole(Role.USER);
        person.setStateUnilocal(register);
        person.setPhoto(registerUserDto.photo());
        return person;
    }


    @Override
    public Optional<Person> login(SesionUserDto sesionUserDto) throws Exception {

        Optional<Person> person = clientRepository.findByEmail(sesionUserDto.email());


        if(person.isEmpty() || !passwordEncoder.matches(sesionUserDto.password(), person.get().getPassword())){
            return Optional.empty();
        }

        return person;
    }

    /**
     * Method to update user information
     *
     * @param updateUserDto User to be updated
     * @param id            User id
     * @return The updated user
     * @throws ResourceNotFoundException Exception that is executed if a user with that id is not found
     */
    @Override
    public Person profileEdit(UpdateUserDto updateUserDto, int id) throws ResourceNotFoundException {
        Person person = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found"));

        person.setName(updateUserDto.name());
        person.setEmail(updateUserDto.email());
        person.setNickname(updateUserDto.nickname());
        person.setPhoto(updateUserDto.photo());
        person.setResidenceCity(updateUserDto.residenceCity());

        return clientRepository.save(person);
    }

    /**
     * Method that deletes the user account
     *
     * @param id User id
     * @return User whose registration status was changed to Inactive
     * @throws ResourceNotFoundException Exception that is executed if a user with that id is not found
     */
    @Override
    public Person delete(int id) throws ResourceNotFoundException {
        Person person = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("El id no esta asociado a un usuario"));
        person.setStateUnilocal(StateUnilocal.Inactive);
        clientRepository.save(person);
        return person;
    }

    /**
     * This method generates a link and sends an email to a user to change the password
     *
     * @throws ResourceNotFoundException Exception that is executed if a user with that email is not found
     */
    @Override
    public void sendLinkPassword(EmailDTO emailDTO) throws ResourceNotFoundException, MessagingException {
        Person person = clientRepository.findByEmail(emailDTO.destinatario())
                .orElseThrow(() -> new ResourceNotFoundException("El email no esta asociado a un usuario"));

        emailService.sendEmail(emailDTO);
    }

    /**
     * This method changes the user's password
     *
     * @param changePasswordDTO DTO to change the password
     *
     * @throws ResourceNotFoundException Exception that is executed if a user with that id is not found
     */
    @Override
    public void changePassword(ChangePasswordDTO changePasswordDTO) throws ResourceNotFoundException {
        Person person = clientRepository.findById(changePasswordDTO.id())
                .orElseThrow(() -> new ResourceNotFoundException("El id no esta asociado a un usuario"));

        person.setPassword(changePasswordDTO.password());
        clientRepository.save(person);
    }


    //----------------------Private Methods--------------------------------------

    /**
     * Method that obtains all users from the database
     *
     * @return The database user list
     */
    public List<Person> getAll() {
        List<Person> personList = new ArrayList<>();
        for (Person person : clientRepository.findAll()){
            if (person.getRole().equals(Role.USER)){
                personList.add(person);
            }
        }
        return personList;
    }

    /**
     * Method that obtains a user by his/her ID from the database
     *
     * @param id User id
     * @return User
     * @throws ResourceNotFoundException Exception that is executed if a user with that id is not found
     */
    public Person getOne(int id) throws ResourceNotFoundException {
        return clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found"));
    }

    private int autoIncrement() {
        List<Person> people = clientRepository.findAll();
        return people.isEmpty() ? 1 :
                people.stream().max(Comparator.comparing(Person::getId)).get().getId() + 1;
    }
}
