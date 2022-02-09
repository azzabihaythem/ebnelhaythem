package com.medical.ebnelhaythem.mapper;

import com.medical.ebnelhaythem.entity.Patient;
import com.medical.ebnelhaythem.model.PatientModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MainMapper {

    MainMapper INSTANCE  = Mappers.getMapper(MainMapper.class);

    Patient toEntity(PatientModel patientModel);

    PatientModel toModel(Patient patient);

}
