package com.estancia.juventudes.controllers.dtos.request;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UpdateCategoryRequest {

    private String name;

    private String description;

    private String color;

    private String iconUrl;
}
