package com.estancia.juventudes.configuration.security.filters;

import com.estancia.juventudes.configuration.security.user.AuthCredentials;
import com.estancia.juventudes.configuration.security.user.UserDetailsImpl;
import com.estancia.juventudes.controllers.dtos.response.BaseResponse;
import com.estancia.juventudes.utilities.TokenUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.core.Authentication;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;


public class UserAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        AuthCredentials authCredentials = new AuthCredentials();
        try{
            authCredentials = new ObjectMapper().readValue(request.getReader(), AuthCredentials.class);
        }catch (Exception ignored){}
        UsernamePasswordAuthenticationToken userNamePAT = new UsernamePasswordAuthenticationToken(
                authCredentials.getEmail(),
                authCredentials.getPassword(),
                Collections.emptyList()
        );
        return getAuthenticationManager().authenticate(userNamePAT);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

        UserDetailsImpl userDetails = (UserDetailsImpl) authResult.getPrincipal();
        String token = TokenUtils.createToken(userDetails.getName(), userDetails.getUsername());

        response.addHeader("Authorization", "Bearer " + token);
        BaseResponse baseResponse=  BaseResponse.builder()
                .data("Bearer " + token)
                .message("Successfully authentication")
                .success(true)
                .httpStatus(HttpStatus.OK).build();

        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(toJson(baseResponse));
        out.flush();

        super.successfulAuthentication(request, response, chain, authResult);
    }

    private String toJson(BaseResponse baseResponse){
        Gson gson = new Gson();
        return gson.toJson(baseResponse);
    }
}
