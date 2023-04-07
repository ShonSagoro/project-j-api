package com.estancia.juventudes.controllers.dtos.request;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter @Setter
public class CreateUserRequest {

    @NonNull
    private String email;

    @NonNull
    private String name;

    @NonNull
    private String password;

    @NonNull
    private String curp;

    @NonNull
    private String gender;

    private String firstLastname;

    private  String secondLastname;

    private String birthday;

    private String address;

    private Short age;

    private String numberPhone;

}
