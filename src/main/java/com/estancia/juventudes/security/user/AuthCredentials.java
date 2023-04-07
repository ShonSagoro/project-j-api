package com.estancia.juventudes.security.user;

import lombok.Data;

@Data
public class AuthCredentials {
    private String email;
    private String password;
}
