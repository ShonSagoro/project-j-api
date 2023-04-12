package com.estancia.juventudes.controllers.dtos.request;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UpdateGuardianRequest {
    private String name;

    private String lastName;

    private String curp;

    private Integer phoneNumber;

    private String email;
}
