package com.estancia.juventudes.controllers.dtos.response;

import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class GetPromotionRequest {

    private Long id;

    private String name;

    private String description;
}
