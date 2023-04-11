package com.medical.ebnelhaythem.service;

import com.medical.ebnelhaythem.entity.Patient;
import com.medical.ebnelhaythem.repository.PatientRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class PatientServiceImpl implements PatientService{

    private PatientRepository patientRepository;

    //@Autowired
    //private MainMapper mainMapper;

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
    public void setPatientActive(Long patientId,Boolean active) {
        Optional<Patient> patient = patientRepository.findById(patientId);
       if(patient.isPresent()){
           patient.get().setActive(active);
       }
        patientRepository.save(patient.get());
    }

    @Override
    public Page<Patient> findAll(Pageable pageable) {
        return patientRepository.findAll(pageable);
    }

    @Override
    public void updateAllPatientStatus(Boolean active,Long cliniqueId) {
       List<Patient> patientList = patientRepository.findAllByUser_CliniqueId(cliniqueId);
        for (Patient aPatient : patientList
        ){
            aPatient.setActive(active);
            patientRepository.save(aPatient);
        }
    }

    @Override
    public Patient findByNumAffiliation(String numAffiliation) {
        return patientRepository.findByNumAffiliation(numAffiliation);
    }
}
