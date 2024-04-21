package com.avanzada.unilocal.Unilocal.serviceImplements;

import com.avanzada.unilocal.Unilocal.dto.*;
import com.avanzada.unilocal.Unilocal.entity.Comment;
import com.avanzada.unilocal.Unilocal.entity.Person;
import com.avanzada.unilocal.Unilocal.entity.Place;
import com.avanzada.unilocal.Unilocal.enums.StateUnilocal;
import com.avanzada.unilocal.Unilocal.interfaces.UserService;
import com.avanzada.unilocal.Unilocal.repository.ClientRepository;
import com.avanzada.unilocal.Unilocal.enums.Role;
import com.avanzada.unilocal.Unilocal.repository.CommentRepository;
import com.avanzada.unilocal.Unilocal.repository.PlaceRepository;
import com.avanzada.unilocal.global.exceptions.AttributeException;
import com.avanzada.unilocal.global.exceptions.ResourceNotFoundException;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author Juanes Cardona
 */
@Service
public class PersonService implements UserService {

    @Autowired
    ClientRepository clientRepository;
    @Autowired
    PlaceRepository placeRepository;
    @Autowired
    CommentServiceImp commentServiceImp;
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

        String cedula = registerUserDto.cedula();
        String pass = passwordEncoder.encode(registerUserDto.password());
        Person person = getPerson(registerUserDto, cedula, pass);
        return clientRepository.save(person);
    }


    private static Person getPerson(RegisterUserDto registerUserDto, String id, String password) {
        StateUnilocal register = StateUnilocal.Active;
        Person person = new Person();
        person.setCedula(id);
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
    public Person profileEdit(UpdateUserDto updateUserDto, String id) throws ResourceNotFoundException {
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
    public Person delete(String id) throws ResourceNotFoundException {
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

        person.setPassword(passwordEncoder.encode(changePasswordDTO.password()));
        clientRepository.save(person);
    }

    public void agregarFavorito(String usuarioId, int lugarId) throws ResourceNotFoundException {
        Optional<Person> usuario = Optional.ofNullable(clientRepository.findById(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado")));
        Optional<Place> lugar = Optional.ofNullable(placeRepository.findById(lugarId)
                .orElseThrow(() -> new ResourceNotFoundException("Lugar no encontrado")));

        usuario.get().getLugaresFavoritos().add(lugar.get().getId());
        clientRepository.save(usuario.get());
    }

    public void eliminarFavorito(String usuarioId, int lugarId) throws ResourceNotFoundException {
        Optional<Person> usuario = Optional.ofNullable(clientRepository.findById(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado")));
        Optional<Place> lugar = Optional.ofNullable(placeRepository.findById(lugarId)
                .orElseThrow(() -> new ResourceNotFoundException("Lugar no encontrado")));

        usuario.get().getLugaresFavoritos().remove(lugar);
        clientRepository.save(usuario.get());
    }

    public List<Place> obtenerFavoritos(String usuarioId) throws ResourceNotFoundException {
        List<Place> places = new ArrayList<>();
        Optional<Person> usuario = Optional.ofNullable(clientRepository.findById(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado")));
        for (int i : usuario.get().getLugaresFavoritos()){
            if (i != 0){
                for (Place place : placeRepository.findAll()){
                    if (place.getId() == i){
                        places.add(place);
                    }
                }
            }
        }
        return places;
    }

    @Override
    public void addComment(int lugarId, CommentDTO comment) throws ResourceNotFoundException {
        commentServiceImp.crearComentario(comment, lugarId);
    }

    @Override
    public void eliminarComentario(int id, String idCliente) {
        commentServiceImp.eliminarComentario(id, idCliente);
    }

    @Override
    public void addQualification(int lugarId, QualificationDTO qualificationDTO) {

    }


    public List<Place> obtenerLugaresUsuario(String usuarioId) {
        // Implementación para obtener la lista de lugares del usuario
        // Usar el lugarRepository para obtener los lugares asociados al usuario
        return placeRepository.findByOwner(usuarioId);
    }

    public void responderComentario(int comentarioId, CommentDTO respuesta) throws ResourceNotFoundException {
        commentServiceImp.responderComentario(comentarioId,respuesta);
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
    public Person getOne(String id) throws ResourceNotFoundException {
        return clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found"));
    }

    private Comment getComment(CommentDTO commentDTO){
        Comment comment = new Comment();
        comment.setMessage(commentDTO.message());
        return  comment;
    }
}
