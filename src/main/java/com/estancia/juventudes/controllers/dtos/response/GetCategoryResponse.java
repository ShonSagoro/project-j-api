package com.estancia.juventudes.controllers.dtos.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetCategoryResponse {
    private Long id;

    private String name;

    private String description;

    private String colorName;

    private String colorCode;

    private String iconUrl;
}
