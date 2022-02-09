package com.medical.ebnelhaythem.model;

import com.medical.ebnelhaythem.entity.PriseEnCharge;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PatientModel extends BasicModel{

    private UserModel user;

    private String doit;

    private String affile;

    private String numAffiliation;

    private List<PriseEnCharge> priseEnCharges;

    private Date desactivationDate;

}
