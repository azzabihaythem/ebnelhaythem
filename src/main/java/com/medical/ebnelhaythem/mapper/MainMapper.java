package com.medical.ebnelhaythem.mapper;

import com.medical.ebnelhaythem.entity.Patient;
import com.medical.ebnelhaythem.dto.PatientDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MainMapper {

    MainMapper INSTANCE  = Mappers.getMapper(MainMapper.class);

    Patient convertToEntity(PatientDto patientDto);

    PatientDto convertToDto(Patient patient);

}
