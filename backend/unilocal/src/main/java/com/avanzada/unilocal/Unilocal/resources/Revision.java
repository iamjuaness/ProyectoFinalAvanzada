package com.avanzada.unilocal.Unilocal.resources;

import com.avanzada.unilocal.Unilocal.entity.Person;
import com.avanzada.unilocal.Unilocal.enums.StateUnilocal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Revision {

    private StateUnilocal stateUnilocal;
    private Person person;
    private String description;
}
