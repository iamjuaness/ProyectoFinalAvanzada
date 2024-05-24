package com.avanzada.unilocal.Unilocal.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Document(collection = "comments")
@AllArgsConstructor
@NoArgsConstructor
public class Comment {

    @Id
    private int id;
    private String idClient;
    private String message;
    private List<String> responses = new ArrayList<>();

    public Comment(String message) {
        this.message = message;
    }

    public Comment(int id, String message, String idClient) {
        this.id = id;
        this.message = message;
        this.idClient = idClient;
    }
}
