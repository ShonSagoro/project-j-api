package com.estancia.juventudes.controllers;

import com.estancia.juventudes.controllers.dtos.request.*;
import com.estancia.juventudes.controllers.dtos.response.BaseResponse;
import com.estancia.juventudes.controllers.dtos.response.GetCategoryResponse;
import com.estancia.juventudes.services.interfaces.ICategoryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("category")
public class CategoryController {

    private final ICategoryService service;

    public CategoryController(ICategoryService service) {
        this.service = service;
    }

    @Operation(summary = "Get a category by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category has been found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class)) }),
            @ApiResponse(responseCode = "401", description = "Category not found or you are not authorized",
                    content = @Content)
    })
    @GetMapping("{id}")
    public ResponseEntity<BaseResponse> get(@PathVariable Long id){
        BaseResponse response = service.get(id);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @Operation(summary = "Get all companies associated with a category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All companies found by category ID",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class)) }),
    })
    @GetMapping("{id}/companies")
    public ResponseEntity<BaseResponse> getCompanies(@PathVariable Long id){
        BaseResponse response = service.getAllCompanies(id);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }


    @Operation(summary = "Get all categories")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All categories found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class)) }),
    })
    @GetMapping
    public ResponseEntity<BaseResponse> getAll(){
        BaseResponse response = service.getAll();
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @Operation(summary = "Create a Category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The category has been created",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class)) }),
            @ApiResponse(responseCode = "401", description = "The category already exists or you are not authorized",
                    content = @Content)
    })
    @PostMapping
    public ResponseEntity<BaseResponse> create(@RequestBody CreateCategoryRequest request){
        BaseResponse response = service.create(request);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @Operation(summary = "Update a category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The category has been updated",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class)) }),
    })
    @PutMapping("{id}")
    public ResponseEntity<BaseResponse> update(@RequestBody UpdateCategoryRequest request,
                                               @PathVariable Long id){
        BaseResponse response = service.update(request, id);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @Operation(summary = "Delete a category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The category has been deleted")
    })
    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id){
        service.delete(id);
    }


    @Operation(summary = " Quick check of controller operation")
    @GetMapping("health")
    public String health() {
        return "Ok";
    }
}
