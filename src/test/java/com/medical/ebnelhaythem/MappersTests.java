package com.medical.ebnelhaythem;

import com.fasterxml.jackson.annotation.JsonIgnoreType;
import com.medical.ebnelhaythem.dto.PatientDto;
import com.medical.ebnelhaythem.entity.Patient;
import com.medical.ebnelhaythem.mapper.MainMapper;
import net.bytebuddy.implementation.bind.annotation.IgnoreForBinding;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MappersTests {

    @Test
    public void PatientTest() {
        //given
        PatientDto patientModel = new PatientDto();
        patientModel.setAffile("dfqfds");
        patientModel.setDoit("qsdqs");

        //when
        Patient patient = MainMapper.INSTANCE.convertToEntity( patientModel );

        //then
        assertNotEquals( patientModel , null);


    }

}
