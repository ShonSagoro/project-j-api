package com.estancia.juventudes.controllers.dtos.request;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter @Setter
public class CreateCategoryRequest {
    @NonNull
    private String name;

    @NonNull
    private String description;

    @NonNull
    private String color;

    @NonNull
    private String iconUrl;


}
