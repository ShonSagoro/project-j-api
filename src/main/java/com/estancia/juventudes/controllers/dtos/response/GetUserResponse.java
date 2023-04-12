package com.estancia.juventudes.controllers.dtos.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetUserResponse {

    private Long id;

    private String email;

    private String name;

    private String curp;

    private String gender;

    private String firstLastname;

    private  String secondLastname;

    private String dateOfBirth;

    private String address;

    private Short age;

    private String numberPhone;

    private String rol;

    //for relationship
    private Long guardianId;
}


