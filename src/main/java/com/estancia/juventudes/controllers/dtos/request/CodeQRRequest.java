package com.estancia.juventudes.controllers.dtos.request;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CodeQRRequest {

    private String curp;
}
