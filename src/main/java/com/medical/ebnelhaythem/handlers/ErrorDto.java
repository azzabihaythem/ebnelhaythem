package com.medical.ebnelhaythem.handlers;

import com.medical.ebnelhaythem.exception.ErrorCodes;
import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorDto {

    private Integer httpCode;
    private ErrorCodes code;
    private String message;

}
