package com.medical.ebnelhaythem.service;

import com.medical.ebnelhaythem.entity.Seance;
import com.medical.ebnelhaythem.entity.SeanceType;
import com.medical.ebnelhaythem.repository.SeanceRepository;
import com.medical.ebnelhaythem.repository.SeanceTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
@Component
public class SeanceTypeServiceImpl implements SeanceTypeService{
    @Autowired
    SeanceTypeRepository seanceTypeRepository ;


    @Override
    public List<SeanceType> getAllSeanceType() {
        return (List<SeanceType>) seanceTypeRepository.findAll() ;
    }

    @Override
    public SeanceType save(SeanceType seanceType) {
        return seanceTypeRepository.save(seanceType);
    }

    @Override
    public Optional<SeanceType> findById(Long id) {
        return seanceTypeRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        seanceTypeRepository.deleteById(id);

    }
}
