package com.avanzada.unilocal.Unilocal.entity;

import com.avanzada.unilocal.Unilocal.enums.BusinessType;
import com.avanzada.unilocal.Unilocal.enums.StateUnilocal;
import com.avanzada.unilocal.Unilocal.resources.Location;
import com.avanzada.unilocal.Unilocal.resources.Revision;
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

    private List<String> schedules;

    private List<String> images;
    private List<Comment> comments;
    private BusinessType businessType;
    private List<String> phones;
    private StateUnilocal stateBusiness;
    private String owner;
    private Location location;

    List<Revision> revisions = new ArrayList<>();

    public Place(int id, String description, String name, List<String> schedules, List<String> images, BusinessType businessType, List<String> phones, StateUnilocal stateBusiness, String owner, Location location) {
    }
}
