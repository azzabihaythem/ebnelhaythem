package com.medical.ebnelhaythem.exception;

public enum ErrorCodes {

    PATIENT_NOT_FOUND(1000),
    PATIENT_NOT_VALID(1001),
    PRISE_EN_CHARGE_NOT_FOUND(2000),
    USER_NOT_FOUND(3000);

    private int code;

    ErrorCodes(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
