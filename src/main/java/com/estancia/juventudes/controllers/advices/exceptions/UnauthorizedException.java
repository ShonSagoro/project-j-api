package com.estancia.juventudes.controllers.advices.exceptions;

public class UnauthorizedException extends RuntimeException{
    public UnauthorizedException(){
        super("Unauthorized");
    }
}
