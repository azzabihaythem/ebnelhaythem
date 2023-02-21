package com.medical.ebnelhaythem.service;

import com.medical.ebnelhaythem.entity.FactureLastNumber;
import com.medical.ebnelhaythem.repository.FactureLastNumberRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class FactureLastNumberServiceImpl implements FactureLastNumberService{

    private FactureLastNumberRepository factureLastNumberRepository;

    @Override
    public FactureLastNumber findByCliniqueId(Long cliniqueId) {
        return factureLastNumberRepository.findByCliniqueId(cliniqueId);
    }
}
