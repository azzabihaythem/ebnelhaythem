package com.medical.ebnelhaythem.service;

import com.medical.ebnelhaythem.entity.Patient;
import com.medical.ebnelhaythem.entity.Seance;

import java.util.List;
import java.util.Optional;

public interface SeanceService {

    public List<Seance> getAllSeance() ;
    public Seance save(Seance seance);
    public Optional<Seance> findById(Long id);

    public void  deleteById(Long id);


}
