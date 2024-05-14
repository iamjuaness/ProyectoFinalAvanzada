package com.avanzada.unilocal.Unilocal.serviceImplements;

import com.avanzada.unilocal.Unilocal.dto.RegisterUserDto;
import com.avanzada.unilocal.Unilocal.dto.SesionUserDto;
import com.avanzada.unilocal.Unilocal.dto.TokenDto;
import com.avanzada.unilocal.Unilocal.entity.Person;
import com.avanzada.unilocal.Unilocal.interfaces.AuthService;
import com.avanzada.unilocal.Unilocal.utils.JwtUtils;
import com.avanzada.unilocal.global.exceptions.AttributeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthServiceImp<T> implements AuthService {
    @Autowired
    private JwtUtils jwtTokenService;
    @Autowired
    private PersonService personService;

    @Override
    public TokenDto loginClient(SesionUserDto sesionUserDto) throws Exception {
        try {
            // Verificar si sesionUserDto es nulo
            if (sesionUserDto == null) {
                throw new IllegalArgumentException("Los datos de inicio de sesión son nulos");
            }

            // Buscar el usuario en la base de datos
            Optional<Person> user = personService.login(sesionUserDto);
            Person person = null;

            // Comprobar si el usuario existe y la contraseña es correcta
            if (user.isEmpty()) {
                throw new Exception("El correo no se encuentra asociado a un cliente.");
            } else {
                person = user.get();
            }

            // Crear los atributos del token de autenticación
            Map<String, Object> authToken = new HashMap<>();
            authToken.put("role", "USER");
            authToken.put("nombre", person.getName());
            authToken.put("id", person.getCedula());
            authToken.put("photo", person.getPhoto());

            // Generar el token JWT utilizando los datos del usuario autenticado
            String token = jwtTokenService.generarToken(person.getEmail(), authToken);

            // Crear un objeto TokenDto para devolver el token generado
            return new TokenDto(token);
        } catch (IllegalArgumentException ex) {
            // Manejar la excepción si los datos de inicio de sesión son nulos
            throw ex;
        } catch (Exception ex) {
            // Manejar cualquier otra excepción que pueda ocurrir durante la ejecución del método
            ex.printStackTrace(); // Manejo básico de excepciones
            throw new RuntimeException("Se produjo un error al iniciar sesión");
        }
    }

    @Override
    public TokenDto registerClient(RegisterUserDto registerUserDto) throws AttributeException {
        try {
            // Verificar si registerUserDto es nulo
            if (registerUserDto == null) {
                throw new IllegalArgumentException("Los datos de registro son nulos");
            }

            // Realizar el registro del usuario y obtener la persona registrada
            Person person = personService.signUp(registerUserDto);

            // Verificar si la persona obtenida es nula
            if (person == null) {
                throw new AttributeException("Error al registrar el usuario");
            }

            // Crear los atributos del token de autenticación
            Map<String, Object> authToken = new HashMap<>();
            authToken.put("role", "USER");
            authToken.put("nombre", person.getName());
            authToken.put("id", person.getCedula());
            authToken.put("photo", person.getPhoto());

            // Generar el token JWT utilizando los datos de la persona registrada
            String token = jwtTokenService.generarToken(person.getEmail(), authToken);

            // Crear un objeto TokenDto para devolver el token generado
            return new TokenDto(token);
        } catch (IllegalArgumentException ex) {
            // Manejar la excepción si los datos de registro son nulos
            throw ex;
        } catch (AttributeException ex) {
            // Manejar la excepción si ocurre un error al registrar el usuario
            throw ex;
        } catch (Exception ex) {
            // Manejar cualquier otra excepción que pueda ocurrir durante la ejecución del método
            ex.printStackTrace(); // Manejo básico de excepciones
            throw new RuntimeException("Se produjo un error al registrar el usuario");
        }
    }

    @Override
    public TokenDto loginMod(SesionUserDto sesionUserDto) throws Exception {
        try {
            // Verificar si sesionUserDto es nulo
            if (sesionUserDto == null) {
                throw new IllegalArgumentException("Los datos de inicio de sesión son nulos");
            }

            // Buscar el usuario en la base de datos
            Optional<Person> user = personService.login(sesionUserDto);
            Person person = null;

            // Comprobar si el usuario existe y la contraseña es correcta
            if (user.isEmpty()) {
                throw new Exception("El correo no se encuentra asociado a un moderador.");
            } else {
                person = user.get();
            }

            // Crear los atributos del token de autenticación
            Map<String, Object> authToken = new HashMap<>();
            authToken.put("role", "MOD");
            authToken.put("nombre", person.getName());
            authToken.put("id", person.getCedula());
            authToken.put("photo", person.getPhoto());

            // Generar el token JWT utilizando los datos del usuario autenticado
            String token = jwtTokenService.generarToken(person.getEmail(), authToken);

            // Crear un objeto TokenDto para devolver el token generado
            return new TokenDto(token);
        } catch (IllegalArgumentException ex) {
            // Manejar la excepción si los datos de inicio de sesión son nulos
            throw ex;
        } catch (Exception ex) {
            // Manejar cualquier otra excepción que pueda ocurrir durante la ejecución del método
            ex.printStackTrace(); // Manejo básico de excepciones
            throw new RuntimeException("Se produjo un error al iniciar sesión");
        }
    }

    public String actualizarToken(String token){
        return jwtTokenService.actualizarToken(token);
    }
}
