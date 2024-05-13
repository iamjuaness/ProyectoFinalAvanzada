package com.avanzada.unilocal.Unilocal.entity;

import com.avanzada.unilocal.Unilocal.enums.BusinessType;
import com.avanzada.unilocal.Unilocal.enums.StateUnilocal;
import com.avanzada.unilocal.Unilocal.resources.Horario;
import com.avanzada.unilocal.Unilocal.resources.Location;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Document(collection = "places")
@AllArgsConstructor
@NoArgsConstructor
public class Place implements Serializable {


    @Id
    private int id;
    private String description;
    private String name;

    private List<Horario> schedules;

    private List<String> images;
    private List<String> comments = new ArrayList<>();
    private String businessType;
    private List<String> phones;
    private StateUnilocal stateBusiness;
    private String owner;
    private Location location;

    private  List<Integer> qualifications = new ArrayList<>();

    private List<Integer> revisions = new ArrayList<>();

    public Place(int id, String description, String name, List<Horario> schedules, List<String> images, String businessType, List<String> phones, StateUnilocal stateBusiness, String owner, Location location) {
        this.id = id;
        this.description = description;
        this.name = name;
        this.schedules = schedules;
        this.images = images;
        this.businessType = businessType;
        this.phones = phones;
        this.stateBusiness = stateBusiness;
        this.owner = owner;
        this.location = location;
    }
}
