package com.medical.ebnelhaythem.validator;

import com.medical.ebnelhaythem.dto.PatientDto;
import com.medical.ebnelhaythem.utils.Constants;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

//can be replaced by spring validation
public class PatientValidator {

    public static List<String> validate(PatientDto patientDto){

        List<String> errors = new ArrayList<>();

        if(patientDto == null){
            errors.add(Constants.PATIENT_NULL_ERROR_MESSAGE);
            return  errors;
        }

        if(!StringUtils.hasLength(patientDto.getAffile())){
           errors.add(Constants.AFFILE_EMPTY_ERROR_MESSAGE);
        }

        return errors;
    }
}
