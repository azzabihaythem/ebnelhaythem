package com.medical.ebnelhaythem.dto;

import com.medical.ebnelhaythem.entity.PriseEnCharge;
import lombok.*;

import java.util.Date;
import java.util.List;

@Data
public class PatientDto extends BasicDto {

    private UserDto user;

    private String doit;

    private String affile;

    private String numAffiliation;

    private List<PriseEnCharge> priseEnCharges;

    private Date desactivationDate;

}
