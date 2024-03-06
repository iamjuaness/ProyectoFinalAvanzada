package com.avanzada.unilocal.Unilocal.entity;

import com.avanzada.unilocal.Unilocal.resources.Qualification;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Setter
@Getter
@Document
@AllArgsConstructor
@NoArgsConstructor
public class Comment {

    @Id
    private int id;
    private String message;
    private Qualification qualification;
    private List<Comment> responses;
}
