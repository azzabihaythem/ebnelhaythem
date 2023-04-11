package com.medical.ebnelhaythem.dto;

import com.medical.ebnelhaythem.entity.Patient;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class PatientAndAbscenceDto {

    Patient patient;

    List<Integer> abscence;

    String typeSeanceId;

    String borederauNumber;

    String factureNumber;

}
