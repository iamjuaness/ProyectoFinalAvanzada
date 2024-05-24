package com.avanzada.unilocal.Unilocal.serviceImplements;

import com.avanzada.unilocal.Unilocal.dto.*;
import com.avanzada.unilocal.Unilocal.entity.Comment;
import com.avanzada.unilocal.Unilocal.entity.Person;
import com.avanzada.unilocal.Unilocal.entity.Place;
import com.avanzada.unilocal.Unilocal.enums.StateUnilocal;
import com.avanzada.unilocal.Unilocal.interfaces.UserService;
import com.avanzada.unilocal.Unilocal.repository.ClientRepository;
import com.avanzada.unilocal.Unilocal.enums.Role;
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
        // Verificar si el DTO de registro no es nulo
        Objects.requireNonNull(registerUserDto, "El DTO de registro no puede ser nulo");

        // Verificar si el apodo (nickname) ya está en uso
        if (clientRepository.existsByNickname(registerUserDto.nickname())) {
            throw new AttributeException("El apodo ya está en uso");
        }

        // Verificar si el correo electrónico ya está en uso
        if (clientRepository.existsByEmail(registerUserDto.email())) {
            throw new AttributeException("El correo electrónico ya está en uso");
        }

        // Cifrar la contraseña antes de guardarla en la base de datos
        String passwordHash = passwordEncoder.encode(registerUserDto.password());

        // Crear una instancia de Person con los datos proporcionados en el DTO de registro
        Person person = getPerson(registerUserDto, registerUserDto.cedula(), passwordHash);

        // Guardar el nuevo usuario en la base de datos
        return clientRepository.save(person);
    }


    private static final StateUnilocal DEFAULT_STATE = StateUnilocal.Active;

    public static Person getPerson(RegisterUserDto registerUserDto, String id, String password) {
        // Verificar si el DTO de registro y el ID no son nulos ni vacíos
        Objects.requireNonNull(registerUserDto, "El DTO de registro no puede ser nulo");
        Objects.requireNonNull(id, "El ID no puede ser nulo");
        Objects.requireNonNull(password, "La contraseña no puede ser nula");

        // Verificar si la contraseña no está vacía
        if (password.isEmpty()) {
            throw new IllegalArgumentException("La contraseña no puede estar vacía");
        }

        // Crear una nueva instancia de Person
        Person person = new Person();

        // Asignar los datos proporcionados al objeto Person
        person.setCedula(id);
        person.setName(registerUserDto.name());
        person.setNickname(registerUserDto.nickname());
        person.setPassword(password);
        person.setEmail(registerUserDto.email());
        person.setResidenceCity(registerUserDto.residenceCity());
        person.setRole(Role.USER);
        person.setStateUnilocal(DEFAULT_STATE);
        person.setPhoto((String) registerUserDto.photo());

        return person;
    }


    @Override
    public Optional<Person> login(SesionUserDto sesionUserDto) throws Exception {
        // Verificar si el DTO de inicio de sesión y sus campos no son nulos ni vacíos
        if (sesionUserDto == null || sesionUserDto.email() == null || sesionUserDto.email().isEmpty() ||
                sesionUserDto.password() == null || sesionUserDto.password().isEmpty()) {
            throw new IllegalArgumentException("El DTO de inicio de sesión y sus campos no pueden ser nulos o vacíos");
        }

        // Buscar al usuario por su dirección de correo electrónico
        Optional<Person> person = clientRepository.findByEmail(sesionUserDto.email());

        // Verificar si se encontró un usuario y si la contraseña coincide
        if (person.isEmpty() || !passwordEncoder.matches(sesionUserDto.password(), person.get().getPassword())) {
            return Optional.empty(); // Si no se encuentra el usuario o la contraseña no coincide, devolver Optional.empty()
        }

        // Si el usuario y la contraseña son válidos, devolver el usuario
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
        // Verificar si el DTO de actualización de usuario y el ID no son nulos ni vacíos
        if (updateUserDto == null || id == null || id.isEmpty()) {
            throw new IllegalArgumentException("El DTO de actualización de usuario y el ID no pueden ser nulos o vacíos");
        }

        // Buscar al usuario por su ID
        Person person = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        // Actualizar el perfil del usuario con la información proporcionada en el DTO
        person.setName(updateUserDto.name());
        person.setEmail(updateUserDto.email());
        person.setNickname(updateUserDto.nickname());
        person.setPhoto((String) updateUserDto.photo());
        person.setResidenceCity(updateUserDto.residenceCity());

        // Guardar los cambios en el repositorio del cliente y devolver el usuario actualizado
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
        // Verificar si el ID no es nulo ni vacío
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("El ID no puede ser nulo o vacío");
        }

        // Buscar al usuario por su ID
        Person person = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("El ID no está asociado a un usuario"));

        // Cambiar el estado del usuario a "Inactivo"
        person.setStateUnilocal(StateUnilocal.Inactive);

        // Guardar los cambios en el repositorio del cliente
        Person updatedPerson = clientRepository.save(person);

        // Devolver el usuario actualizado
        return updatedPerson;
    }

    /**
     * This method generates a link and sends an email to a user to change the password
     *
     * @throws ResourceNotFoundException Exception that is executed if a user with that email is not found
     */
    @Override
    public String sendLinkPassword(EmailDTO emailDTO) throws ResourceNotFoundException, MessagingException {
        // Verificar si el DTO de correo electrónico es nulo o si el destinatario es nulo o vacío
        if (emailDTO == null || emailDTO.destinatario() == null || emailDTO.destinatario().isEmpty()) {
            throw new IllegalArgumentException("El DTO de correo electrónico y el destinatario no pueden ser nulos o vacíos");
        }

        // Buscar a la persona por su dirección de correo electrónico
        Person person = clientRepository.findByEmail(emailDTO.destinatario())
                .orElseThrow(() -> new ResourceNotFoundException("El correo electrónico no está asociado a un usuario"));

        // Enviar el correo electrónico con el servicio de correo electrónico
        emailService.sendEmail(emailDTO);
        return person.getCedula();
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
        // Verificar si el DTO es nulo o si el ID es nulo o vacío
        if (changePasswordDTO == null || changePasswordDTO.id() == null || changePasswordDTO.id().isEmpty()) {
            throw new IllegalArgumentException("El DTO de cambio de contraseña no puede ser nulo y debe contener un ID válido");
        }

        // Buscar al usuario por su ID
        Person person = clientRepository.findById(changePasswordDTO.id())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        // Codificar y establecer la nueva contraseña del usuario
        person.setPassword(passwordEncoder.encode(changePasswordDTO.password()));

        // Guardar los cambios en el repositorio del cliente
        clientRepository.save(person);
    }

    @Override
    public void agregarFavorito(String usuarioId, int lugarId) throws ResourceNotFoundException {
        // Verificar si el ID del usuario y del lugar son válidos
        if (usuarioId == null || usuarioId.isEmpty() || lugarId <= 0) {
            throw new IllegalArgumentException("El ID del usuario y del lugar deben ser válidos");
        }

        // Buscar al usuario y al lugar por sus respectivos IDs
        Person usuario = clientRepository.findById(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        Place lugar = placeRepository.findById(lugarId)
                .orElseThrow(() -> new ResourceNotFoundException("Lugar no encontrado"));

        // Agregar el ID del lugar a la lista de lugares favoritos del usuario
        usuario.getLugaresFavoritos().add(lugar.getId());

        // Guardar los cambios en el repositorio del cliente
        clientRepository.save(usuario);
    }

    @Override
    public void eliminarFavorito(String usuarioId, int lugarId) throws ResourceNotFoundException {
        // Buscar el usuario por su ID
        Person usuario = clientRepository.findById(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        // Buscar el lugar por su ID
        Place lugar = placeRepository.findById(lugarId)
                .orElseThrow(() -> new ResourceNotFoundException("Lugar no encontrado"));

        // Eliminar el lugar de la lista de lugares favoritos del usuario
        usuario.getLugaresFavoritos().removeIf(favorito -> favorito == lugarId);

        // Guardar los cambios en el repositorio del cliente
        clientRepository.save(usuario);
    }

    @Override
    public List<Place> obtenerFavoritos(String usuarioId) throws ResourceNotFoundException {
        // Validar que el ID del usuario no sea nulo ni vacío
        if (usuarioId == null || usuarioId.isEmpty()) {
            throw new IllegalArgumentException("El ID del usuario no puede ser nulo o vacío");
        }

        // Buscar el usuario por su ID
        Person usuario = clientRepository.findById(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        List<Place> places = new ArrayList<>();

        // Obtener los lugares favoritos del usuario y agregarlos a la lista
        for (int lugarId : usuario.getLugaresFavoritos()) {
            if (lugarId != 0) {
                Place place = placeRepository.findById(lugarId)
                        .orElse(null); // Si no se encuentra el lugar, place será null
                if (place != null) {
                    places.add(place);
                }
            }
        }

        return places;
    }

    @Override
    public void addComment(int lugarId, CommentDTO comment) throws ResourceNotFoundException {
        if (comment == null) {
            throw new IllegalArgumentException("El comentario no puede ser nulo");
        }

        commentServiceImp.crearComentario(comment, lugarId);
    }

    @Override
    public void eliminarComentario(int id, String idCliente) {
        if (idCliente == null || idCliente.isEmpty()) {
            throw new IllegalArgumentException("El ID del cliente no puede ser nulo o vacío");
        }

        commentServiceImp.eliminarComentario(id, idCliente);
    }
    @Override
    public void addQualification(int lugarId, QualificationDTO qualificationDTO) {

    }


    @Override
    public List<Place> obtenerLugaresUsuario(String usuarioId) throws IllegalArgumentException, ResourceNotFoundException {
        if (usuarioId == null || usuarioId.isEmpty()) {
            throw new IllegalArgumentException("El ID del usuario no puede ser nulo o vacío");
        }

        List<Place> lugares = placeRepository.findByOwner(usuarioId);

        // Verificar si la lista de lugares está vacía
        if (lugares.isEmpty()) {
            // Puedes lanzar una excepción, registrar un mensaje de advertencia o manejarlo de otra manera según tu lógica de negocio
            throw new ResourceNotFoundException("No se encontraron lugares asociados al usuario con ID: " + usuarioId);
        }

        return lugares;
    }

    @Override
    public void responderComentario(int comentarioId, CommentDTO respuesta) throws ResourceNotFoundException {
        try {
            commentServiceImp.responderComentario(comentarioId, respuesta);
        } catch (ResourceNotFoundException e) {
            // Manejar la excepción o lanzarla con más contexto
            throw new ResourceNotFoundException("No se pudo responder al comentario con ID: " + comentarioId);
        }
    }


    //----------------------Private Methods--------------------------------------


    /**
     * Method that obtains all users from the database
     *
     * @return The database user list
     */
    public List<Person> getAll() {
        List<Person> personList = new ArrayList<>();
        for (Person person : clientRepository.findAll()) {
            if (person.getRole() != null) {
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
        // Verificar si el ID es nulo o vacío
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("ID no puede ser nulo o vacío");
        }

        Optional<Person> personOptional = clientRepository.findById(id);

        // Verificar si la persona fue encontrada en el repositorio
        if (personOptional.isPresent()) {
            return personOptional.get();
        } else {
            // Si la persona no fue encontrada, lanzar una excepción ResourceNotFoundException
            throw new ResourceNotFoundException("Persona no encontrada con el ID: " + id);
        }
    }

    private Comment getComment(CommentDTO commentDTO) {
        if (commentDTO == null) {
            throw new IllegalArgumentException("El objeto CommentDTO no puede ser nulo");
        }

        // Verificar si el mensaje del comentario es nulo o vacío
        String message = commentDTO.message();
        if (message == null || message.isEmpty()) {
            throw new IllegalArgumentException("El mensaje del comentario no puede ser nulo o vacío");
        }

        Comment comment = new Comment();
        comment.setMessage(message);

        // Si hay más campos en el DTO que necesiten ser mapeados al objeto Comment, hazlo aquí

        return comment;
    }
}
