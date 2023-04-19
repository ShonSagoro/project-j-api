package com.estancia.juventudes.controllers.dtos.request;


import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CodeQRInfoRequest {
    @Size(min=18, max=18, message = "La curp debe tener 18 caracteres")
    private String curp;
}
