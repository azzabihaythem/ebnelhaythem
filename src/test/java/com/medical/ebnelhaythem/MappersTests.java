package com.medical.ebnelhaythem;

import com.medical.ebnelhaythem.entity.Patient;
import com.medical.ebnelhaythem.mapper.MainMapper;
import com.medical.ebnelhaythem.model.PatientModel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MappersTests {

    @Test
    public void PatientTest() {
        //given
        PatientModel patientModel = new PatientModel();
        patientModel.setAffile("dfqfds");
        patientModel.setDoit("qsdqs");

        //when
        Patient patient = MainMapper.INSTANCE.toEntity( patientModel );

        //then
        assertNotEquals( patientModel , null);


    }

}
