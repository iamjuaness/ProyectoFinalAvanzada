package com.avanzada.unilocal.Unilocal.serviceImplements;

import com.avanzada.unilocal.Unilocal.entity.Comment;
import com.avanzada.unilocal.Unilocal.entity.Revision;
import com.avanzada.unilocal.Unilocal.repository.RevisionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class RevisionServiceImp {

    @Autowired
    RevisionRepository revisionRepository;

    private int autoIncrement() {
        List<Revision> revisions = revisionRepository.findAll();
        return revisions.isEmpty() ? 1 :
                revisions.stream().max(Comparator.comparing(Revision::getId)).get().getId() + 1;
    }
}
