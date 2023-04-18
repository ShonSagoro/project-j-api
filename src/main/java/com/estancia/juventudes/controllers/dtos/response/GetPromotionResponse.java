package com.estancia.juventudes.controllers.dtos.response;

import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class GetPromotionResponse {

    private Long id;

    private String name;

    private String description;

    private Long companyId;
}
