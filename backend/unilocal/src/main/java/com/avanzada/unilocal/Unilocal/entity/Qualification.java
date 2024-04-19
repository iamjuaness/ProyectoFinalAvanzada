package com.avanzada.unilocal.Unilocal.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@Getter
@Document(collection = "qualifications")
@AllArgsConstructor
@NoArgsConstructor
public class Qualification {

    @Id
    private int id;
    private double qualification;
    private String idCliente;
    private int idLugar;

    public Qualification(double qualification, int idLugar, String idCliente) {
        this.qualification = qualification;
        this.idLugar = idLugar;
        this.idCliente = idCliente;
    }
}