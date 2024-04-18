package com.avanzada.unilocal.Unilocal.entity;


import com.avanzada.unilocal.Unilocal.enums.Role;
import com.avanzada.unilocal.Unilocal.enums.StateUnilocal;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Document(collection = "users")
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Person implements Serializable {

    @Id
    private int id;
    private String name;
    private String photo;
    private String nickname;
    private String email;
    private String password;
    private String residenceCity;
    private Place[] myPlaces;
    private Role role;
    private StateUnilocal stateUnilocal;
    private Set<Place> lugaresFavoritos = new HashSet<>();


    public Person(int id, String name, String photo, String nickname, String email, String password, String s, Role role, StateUnilocal register) {
    }

    public Person(int id, String name){
        this.id = id;
        this.name = name;
    }
}
