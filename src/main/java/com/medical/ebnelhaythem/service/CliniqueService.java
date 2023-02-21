package com.medical.ebnelhaythem.service;

import com.medical.ebnelhaythem.entity.Clinique;

public interface CliniqueService {

    public Clinique save(Clinique clinique);
    public Clinique findByCliniqueId(long clinique);


}
