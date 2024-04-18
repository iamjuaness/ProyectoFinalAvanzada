package com.avanzada.unilocal;

import com.avanzada.unilocal.Unilocal.dto.SesionUserDto;
import com.avanzada.unilocal.Unilocal.entity.Person;
import com.avanzada.unilocal.Unilocal.serviceImplements.PersonService;
import com.avanzada.unilocal.Unilocal.utils.JwtUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class AuthServiceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonService personService;

    @MockBean
    private JwtUtils jwtTokenService;

    @Test
    public void testLoginClient_Success() throws Exception {
        // Arrange
        SesionUserDto sesionUserDto = new SesionUserDto("juanes123@gmail.com", "juanes123");
        Person person = new Person(3, "Juanes Cardona");
        when(personService.login(sesionUserDto)).thenReturn(Optional.of(person));

        // Create a mock authentication token
        Map<String, Object> authToken = new HashMap<>();
        authToken.put("role", "USER");
        authToken.put("nombre", person.getName());
        authToken.put("id", person.getId());

        // Mock the behavior of generarToken to return a token based on the mock authentication token
        when(jwtTokenService.generarToken(person.getEmail(), authToken)).thenReturn("eyJhbGciOiJIUzUxMiJ9.eyJyb2xlIjoiVVNFUiIsImlkIjozLCJub21icmUiOiJKdWFuZXMgQ2FyZG9uYSIsInN1YiI6Imp1YW5lczEyM0BnbWFpbC5jb20iLCJpYXQiOjE3MTM0MTUxNDksImV4cCI6MTcxMzQxODc0OX0.csugRal2npeoOL4L7rT4EXxW2X-ibDI9iHSberzUJlZTQIGJvmziFHhdjQXpy91EP8RtRWhPtUX1_28s-zUy0Q");

        // Act & Assert
        mockMvc.perform(post("http://localhost:8080/api/user/login-client")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(sesionUserDto)))
                .andExpect(status().isOk());
    }

    @Test
    public void testLoginClient_UserNotFound() throws Exception {
        // Arrange
        SesionUserDto sesionUserDto = new SesionUserDto("pepe123@gmail.com", "123456");
        when(personService.login(sesionUserDto)).thenReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(post("http://localhost:8080/api/user/login-client")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(sesionUserDto)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testLoginMod_Success() throws Exception {
        // Arrange
        SesionUserDto sesionUserDto = new SesionUserDto("unilocal", "juanes123");
        Person person = new Person(2, "Moderador1");
        when(personService.login(sesionUserDto)).thenReturn(Optional.of(person));

        // Create a mock authentication token
        Map<String, Object> authToken = new HashMap<>();
        authToken.put("role", "MOD");
        authToken.put("nombre", person.getName());
        authToken.put("id", person.getId());

        // Mock the behavior of generarToken to return a token based on the mock authentication token
        when(jwtTokenService.generarToken(person.getEmail(), authToken)).thenReturn("");

        // Act & Assert
        mockMvc.perform(post("http://localhost:8080/api/user/login-mod")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(sesionUserDto)))
                .andExpect(status().isOk());
    }

//    @Test
//    public void testLoginMod_UserNotFound() throws Exception {
//        // Arrange
//        SesionUserDto sesionUserDto = new SesionUserDto(/* provide necessary values */);
//        when(personService.login(sesionUserDto)).thenReturn(Optional.empty());
//
//        // Act & Assert
//        mockMvc.perform(post("/loginMod")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(asJsonString(sesionUserDto)))
//                .andExpect(status().isNotFound());
//    }

    // Helper method to convert objects to JSON string
    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

