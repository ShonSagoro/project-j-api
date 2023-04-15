package com.estancia.juventudes.controllers;

import com.estancia.juventudes.controllers.dtos.request.CreateUserRequest;
import com.estancia.juventudes.controllers.dtos.request.LoginRequest;
import com.estancia.juventudes.controllers.dtos.request.UpdateUserRequest;
import com.estancia.juventudes.controllers.dtos.response.BaseResponse;
import com.estancia.juventudes.services.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private final IUserService service;

    public UserController(IUserService service){
        this.service = service;
    }

    @PostMapping("email")
    public ResponseEntity<BaseResponse> get(@RequestBody LoginRequest request){
        BaseResponse response = service.get(request.getEmail());
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @GetMapping
    public ResponseEntity<BaseResponse> getAll(){
        BaseResponse response = service.getAll();
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @PostMapping("reg")
    public ResponseEntity<BaseResponse> create(@RequestBody CreateUserRequest request){
        BaseResponse response = service.create(request);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @PutMapping("{idUser}")
    public ResponseEntity<BaseResponse> update(@RequestBody UpdateUserRequest request,
                                               @PathVariable Long idUser){
        BaseResponse response = service.update(request, idUser);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @DeleteMapping("{idUser}")
    public void delete(@PathVariable Long idUser){
        service.delete(idUser);
    }

    @GetMapping("health")
    public String health() {
        return "Ok";
    }

}
