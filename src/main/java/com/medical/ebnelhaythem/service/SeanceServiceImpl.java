package com.medical.ebnelhaythem.service;

import com.medical.ebnelhaythem.entity.Patient;
import com.medical.ebnelhaythem.entity.Seance;
import com.medical.ebnelhaythem.repository.PatientRepository;
import com.medical.ebnelhaythem.repository.SeanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class SeanceServiceImpl implements SeanceService {
    @Autowired
    private SeanceRepository seanceRepository;

    @Override
    public List<Seance> getAllSeance() {
        return (List<Seance>) seanceRepository.findAll() ;
    }

    @Override
    public Seance save(Seance seance) {
        return seanceRepository.save(seance) ;
    }

    @Override
    public Optional<Seance> findById(Long id) {
        return  seanceRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
       seanceRepository.deleteById(id);
    }
}
