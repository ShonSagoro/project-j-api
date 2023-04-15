package com.estancia.juventudes.controllers;

import com.estancia.juventudes.controllers.dtos.request.*;
import com.estancia.juventudes.controllers.dtos.response.BaseResponse;
import com.estancia.juventudes.services.interfaces.ICategoryService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("category")
public class CategoryController {

    private final ICategoryService service;

    public CategoryController(ICategoryService service) {
        this.service = service;
    }

    @GetMapping("{id}")
    public ResponseEntity<BaseResponse> get(@PathVariable Long id){
        BaseResponse response = service.get(id);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @GetMapping("{id}/companies")
    public ResponseEntity<BaseResponse> getCompanies(@PathVariable Long id){
        BaseResponse response = service.getAllCompanies(id);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }



    @GetMapping
    public ResponseEntity<BaseResponse> getAll(){
        BaseResponse response = service.getAll();
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @PostMapping
    public ResponseEntity<BaseResponse> create(@RequestBody CreateCategoryRequest request){
        BaseResponse response = service.create(request);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @PutMapping("{id}")
    public ResponseEntity<BaseResponse> update(@RequestBody UpdateCategoryRequest request,
                                               @PathVariable Long id){
        BaseResponse response = service.update(request, id);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id){
        service.delete(id);
    }

    @GetMapping("health")
    public String health() {
        return "Ok";
    }
}
