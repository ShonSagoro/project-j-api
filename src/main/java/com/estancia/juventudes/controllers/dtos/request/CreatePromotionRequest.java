package com.estancia.juventudes.controllers.dtos.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreatePromotionRequest {
    private String name;

    private String description;

    private  Long companyId;
}
