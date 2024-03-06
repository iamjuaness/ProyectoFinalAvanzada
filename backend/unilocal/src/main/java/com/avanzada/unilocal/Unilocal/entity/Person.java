package com.avanzada.unilocal.Unilocal.entity;


import com.avanzada.unilocal.Unilocal.enums.Role;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Setter
@Getter
@Document(collection = "people")
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

    public Person(int id, String name, String photo, String nickname, String email, String password, String residenceCity, Role role) {
    }
}
