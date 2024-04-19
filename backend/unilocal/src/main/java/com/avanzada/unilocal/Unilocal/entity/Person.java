package com.avanzada.unilocal.Unilocal.entity;


import com.avanzada.unilocal.Unilocal.enums.Role;
import com.avanzada.unilocal.Unilocal.enums.StateUnilocal;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@Document(collection = "users")
@NoArgsConstructor
@ToString
public class Person implements Serializable {

    @Id
    private String cedula;
    private String name;
    private String photo;
    private String nickname;
    private String email;
    private String password;
    private String residenceCity;
    private List<Integer> myPlaces = new ArrayList<>();
    private Role role;
    private StateUnilocal stateUnilocal;
    private List<Integer> lugaresFavoritos = new ArrayList<>();


    public Person(String cedula, String name, String photo, String nickname, String email, String password, String residenceCity, Role role, StateUnilocal stateUnilocal) {
        this.cedula = cedula;
        this.name = name;
        this.photo = photo;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.residenceCity = residenceCity;
        this.role = role;
        this.stateUnilocal = stateUnilocal;
    }

    public Person(String cedula, String name){
        this.cedula = cedula;
        this.name = name;
    }

    public Person(String cedula, String name, String photo, String nickname, String email, String residenceCity) {
        this.cedula = cedula;
        this.name = name;
        this.photo = photo;
        this.nickname = nickname;
        this.email = email;
        this.residenceCity = residenceCity;
    }
}
