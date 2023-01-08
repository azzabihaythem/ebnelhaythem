package com.medical.ebnelhaythem.service;

import com.medical.ebnelhaythem.entity.Seance;
import com.medical.ebnelhaythem.repository.SeanceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class SeanceServiceImpl implements SeanceService{

    private SeanceRepository seanceRepository;

    @Override
    public Seance save(Seance seance) {
        return seanceRepository.save(seance);
    }
}
