package com.estancia.juventudes.controllers.dtos.request;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UpdateCompanyRequest {

    private String name;

    private String address;

    private String location;

    private String logo;

    // area
    private Float latitude;
    private Float longitude;
}
