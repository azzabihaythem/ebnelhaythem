package com.medical.ebnelhaythem.service;

import com.medical.ebnelhaythem.entity.Clinique;
import com.medical.ebnelhaythem.repository.CliniqueRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CliniqueServiceImpl implements  CliniqueService {

    CliniqueRepository cliniqueRepository ;

    public Clinique save(Clinique clinique) {
        return cliniqueRepository.save(clinique);
    }

}
