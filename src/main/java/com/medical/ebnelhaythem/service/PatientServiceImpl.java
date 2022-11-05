package com.medical.ebnelhaythem.service;

import com.medical.ebnelhaythem.entity.Patient;
import com.medical.ebnelhaythem.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class PatientServiceImpl implements PatientService{

    @Autowired
    private PatientRepository patientRepository;

    //@Autowired
    //private MainMapper mainMapper;

    @Override
    public List<Patient> getAllPatient() {
        // TODO Auto-generated method stub
        return (List<Patient>) patientRepository.findAll() ;
    }


    @Override
    public Patient save(Patient patient) {

       // Patient  patient =  patientRepository.save(MainMapper.INSTANCE.convertToEntity(patientDto));

        return patientRepository.save(patient);

    }


    @Override
    public Optional<Patient> findById(Long id) {
        return patientRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        patientRepository.deleteById(id);
    }

    @Override
    public Patient desactivatePatient(Long id) {
        Optional<Patient> patient = patientRepository.findById(id);
       if(patient.isPresent()){
           patient.get().setActive(false);

       }
        patientRepository.save(patient.get());
        return null;
    }
}
