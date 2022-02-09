package com.medical.ebnelhaythem.service;

import com.medical.ebnelhaythem.entity.Patient;
import com.medical.ebnelhaythem.mapper.MainMapper;
import com.medical.ebnelhaythem.model.PatientModel;
import com.medical.ebnelhaythem.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PatientServiceImpl implements PatientService{

    @Autowired
    private PatientRepository patientRepository;

    //@Autowired
    //private MainMapper mainMapper;

    @Override
    public Patient save(PatientModel patientModel) {

        return patientRepository.save(MainMapper.INSTANCE.toEntity(patientModel));

    }
}
