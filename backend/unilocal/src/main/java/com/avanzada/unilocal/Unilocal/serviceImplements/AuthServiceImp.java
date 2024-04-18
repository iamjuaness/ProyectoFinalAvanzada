package com.avanzada.unilocal.Unilocal.serviceImplements;

import com.avanzada.unilocal.Unilocal.dto.SesionUserDto;
import com.avanzada.unilocal.Unilocal.dto.TokenDto;
import com.avanzada.unilocal.Unilocal.entity.Person;
import com.avanzada.unilocal.Unilocal.interfaces.AuthService;
import com.avanzada.unilocal.Unilocal.repository.ClientRepository;
import com.avanzada.unilocal.Unilocal.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class AuthServiceImp implements AuthService {

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private JwtUtils jwtTokenService;
    @Autowired
    private PersonService personService;

    @Override
    public TokenDto loginClient(SesionUserDto sesionUserDto) throws Exception {
        // Buscar el usuario en la base de datos
        Optional<Person> user = personService.login(sesionUserDto);
        Person person = null;

        // Comprobar si el usuario existe y la contraseña es correcta
        if (user.isEmpty()) {
            throw new Exception("El correo no se encuentra asociado a un cliente.");
        } else {
            person = user.get();
        }

        Map<String, Object> authToken = new HashMap<>();
        authToken.put("role", "USER");
        authToken.put("nombre", person.getName());
        authToken.put("id", person.getId());

        return new TokenDto(jwtTokenService.generarToken(person.getEmail(), authToken));
    }

    @Override
    public TokenDto loginMod(SesionUserDto sesionUserDto) throws Exception {
        // Buscar el usuario en la base de datos
        Optional<Person> user = personService.login(sesionUserDto);
        Person person = null;

        // Comprobar si el usuario existe y la contraseña es correcta
        if (user.isEmpty()) {
            throw new Exception("El correo no se encuentra asociado a un moderador.");
        } else {
            person = user.get();
        }

        Map<String, Object> authToken = new HashMap<>();
        authToken.put("role", "MOD");
        authToken.put("nombre", person.getName());
        authToken.put("id", person.getId());

        return new TokenDto(jwtTokenService.generarToken(person.getEmail(), authToken));
    }
}
