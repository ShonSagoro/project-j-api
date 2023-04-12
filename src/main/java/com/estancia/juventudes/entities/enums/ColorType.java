package com.estancia.juventudes.entities.enums;

import lombok.Getter;

@Getter
public enum ColorType {
    VIOLET("D73A9B", "violet"),
    SKY_BKUE("2CA3DC", "sky_blue"),
    DEEP_RED("930303","deep_red"),
    YELLOW("FCBB16","yellow"),
    ORANGE("F94F30","orange"),
    LIGHT_GREEN("4FF96A","light_green"),
    PINK("FA055E","pink"),
    TURQUOISE("00C18F","turquoise"),
    BLACK("000000","black"),
    BLUE("2924FA","blue"),
    DEEP_GREEN("028316","deep_green"),
    LIGHT_PINK("EEADD5","light_pink"),
    LIGHT_YELLOW("FFE70D",""),
    RED("FE4444","red"),
    ;

    private final String colorCode;
    private final String colorName;


    ColorType(String code, String name) {
        this.colorCode=code;
        this.colorName=name;
    }
}
