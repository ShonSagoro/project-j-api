package com.estancia.juventudes.controllers.dtos.request;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UpdateUserRequest {

    private String name;

    private String email;

    private String password;

    private String gender;

    private String firstLastname;

    private String secondLastname;

    private String dateOfBirth;

    private String address;

    private Short age;

    private String numberPhone;

    private  String rol;

    //for relationship
    private Long guardianId;
}
