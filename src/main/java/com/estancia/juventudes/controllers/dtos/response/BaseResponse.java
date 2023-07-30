package com.estancia.juventudes.controllers.dtos.response;

import org.springframework.http.HttpStatus;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

@Builder @Getter
public class BaseResponse {

    private Object data;

    private String message; 

    private Boolean success;

    private HttpStatus httpStatus;

    public ResponseEntity<BaseResponse> apply() {
        return new ResponseEntity<>(this, httpStatus);
    }

}
