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

// test 3 ///



}


//package com.medical.ebnelhaythem.mapper;
//
//import com.medical.ebnelhaythem.dto.UserDto;
//import com.medical.ebnelhaythem.entity.Clinique;
//import com.medical.ebnelhaythem.entity.Patient;
//import com.medical.ebnelhaythem.dto.PatientDto;
//import com.medical.ebnelhaythem.entity.User;
//import org.mapstruct.Mapper;
//import org.mapstruct.Mapping;
//import org.mapstruct.Named;
//import org.mapstruct.factory.Mappers;
//
//@Mapper
//public interface MainMapper {
//
//    MainMapper INSTANCE  = Mappers.getMapper(MainMapper.class);
//
//    @Mapping(target = "user.password" ,source="user.password",qualifiedByName = "setEmptyString")//should not expose password even if it is
//    @Mapping(target = "clinique" ,source="cliniqueId",qualifiedByName = "setClinique")//should not expose password even if it is
//    Patient convertToEntity(PatientDto patientDto);
//
//    @Mapping(target = "user.password" ,source="user.password",qualifiedByName = "setEmptyString")//should not expose password even if it is encrypted
//    @Mapping(target = "cliniqueId" ,source="clinique.id")
//    PatientDto convertToDto(Patient patient);
//
//
//    @Mapping(target = "clinique" ,source="cliniqueId",qualifiedByName = "setClinique")//should not expose password even if it is
//    User convertToEntity(UserDto userDto);
//
//    @Mapping(target = "cliniqueId" ,source="clinique.id")
//    UserDto convertToDto(User user);
//
//    @Named("setEmptyString")
//    public static String setNull(String o){
//        return "";
//    }
//
//    @Named("setClinique")
//    public static Clinique setClinique(String o){
//
//        Clinique clinique =  new Clinique();
//        clinique.setId(Long.getLong(o));
//
//        return clinique;
//    }
//
//}
