package com.avanzada.unilocal.Unilocal.entity;

import com.avanzada.unilocal.Unilocal.entity.Person;
import com.avanzada.unilocal.Unilocal.enums.StateUnilocal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@Getter
@Document(collection = "revisions")
@AllArgsConstructor
@NoArgsConstructor
public class Revision {

    @Id
    private int id;
    private StateUnilocal stateUnilocal;
    private String mod;
    private String description;
}
