package com.medical.ebnelhaythem.service;

import com.medical.ebnelhaythem.entity.Patient;
import com.medical.ebnelhaythem.repository.PatientRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
@Slf4j
public class PatientServiceImpl implements PatientService{

    private PatientRepository patientRepository;

    private EntityManager entityManager;

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
    public List<Patient> findAll() {
        return patientRepository.findAll();
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
    @Transactional
    public void deleteAll() {
        try {
            log.warn("Deleting all patients...");
            patientRepository.deleteAll();
            resetPatientIdSequence();
            log.info("✅ All patients deleted successfully");
        } catch (Exception e) {
            log.error("Error deleting all patients: ", e);
            throw new RuntimeException("Failed to delete all patients", e);
        }
    }
    @Transactional
    private void resetPatientIdSequence() {
        // Solution Hibernate native
        Session session = entityManager.unwrap(Session.class);

        // Pour PostgreSQL
        //session.createNativeQuery("ALTER SEQUENCE patient_id_seq RESTART WITH 1").executeUpdate();

        // Pour MySQL
         session.createNativeQuery("ALTER TABLE patient AUTO_INCREMENT = 1").executeUpdate();

        // Pour H2
        // session.createNativeQuery("ALTER TABLE patient ALTER COLUMN id RESTART WITH 1").executeUpdate();
    }

    @Override
    public Patient findByNumAffiliation(String numAffiliation) {
        return patientRepository.findByNumAffiliation(numAffiliation);
    }
}
