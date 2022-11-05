package com.medical.ebnelhaythem.service;

import com.medical.ebnelhaythem.entity.Seance;
import com.medical.ebnelhaythem.entity.SeanceType;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

public interface SeanceTypeService {

    public List<SeanceType> getAllSeanceType() ;
    public SeanceType save(SeanceType seanceType);
    public Optional<SeanceType> findById(Long id);

    public void  deleteById(Long id);


}
