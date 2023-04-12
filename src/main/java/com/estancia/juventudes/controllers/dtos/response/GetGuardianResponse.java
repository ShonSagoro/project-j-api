package com.estancia.juventudes.controllers.dtos.response;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class GetGuardianResponse {
    private Long id;

    private String name;

    private String lastName;

    private String curp;

    private Integer phoneNumber;

    private String email;
}
