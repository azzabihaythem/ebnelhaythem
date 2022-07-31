package com.medical.ebnelhaythem.service;

import com.medical.ebnelhaythem.entity.Clinique;
import com.medical.ebnelhaythem.repository.CliniqueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CliniqueServiceImpl implements  CliniqueService {

    @Autowired
    CliniqueRepository cliniqueRepository ;
    public Clinique save(Clinique clinique) {
        return cliniqueRepository.save(clinique);
    }

}
