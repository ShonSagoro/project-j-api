package com.estancia.juventudes.controllers.dtos.request;

import jakarta.validation.constraints.Size;
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
    @Size(min=18, max=18, message = "La curp debe tener 18 caracteres")
    private String curp;

    @NonNull
    private String gender;

    private String firstLastname;

    private  String secondLastname;

    private String dateOfBirth;

    private String address;

    private String numberPhone;

    private String rol;

    //for relationship
    private Long guardianId;

}
