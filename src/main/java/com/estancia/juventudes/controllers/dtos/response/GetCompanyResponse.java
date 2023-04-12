package com.estancia.juventudes.controllers.dtos.response;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class GetCompanyResponse {

    private Long id;

    private String name;

    private String address;

    private String location;

    private String logo;

    // area
    private Float latitude;
    private Float longitude;
}
