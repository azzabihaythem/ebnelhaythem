package com.medical.ebnelhaythem.service;

import com.medical.ebnelhaythem.entity.FactureLastNumber;

public interface FactureLastNumberService {
    FactureLastNumber findByCliniqueId(Long cliniqueId);
    FactureLastNumber save(FactureLastNumber factureLastNumber);
}
