package com.estancia.juventudes.entities.enums;

import lombok.Getter;

@Getter
public enum GenderType {
    MAN("hombre"),
    WOMEN("mujer"),
    NOT_SPECIFIED("no_especificado");


    private final String type;
    GenderType(String code){
        this.type=code;
    }
}
