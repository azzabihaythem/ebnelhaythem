package com.medical.ebnelhaythem.mapper;

import com.medical.ebnelhaythem.entity.Patient;
import com.medical.ebnelhaythem.dto.PatientDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MainMapper {

    MainMapper INSTANCE  = Mappers.getMapper(MainMapper.class);

    Patient convertToEntity(PatientDto patientDto);

    @Mapping(target = "user.password" ,source="user.password",qualifiedByName = "setEmptyString")//should not expose password even if it is encrypted
    PatientDto convertToDto(Patient patient);

    @Named("setEmptyString")
    public static String setNull(String o){
        return "";
    }

}
