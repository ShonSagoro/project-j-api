package com.estancia.juventudes.controllers;

import com.estancia.juventudes.controllers.dtos.request.CreatePromotionRequest;
import com.estancia.juventudes.controllers.dtos.request.UpdatePromotionRequest;
import com.estancia.juventudes.controllers.dtos.response.BaseResponse;
import com.estancia.juventudes.services.interfaces.IPromotionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("promotion")
public class PromotionController {

    private final IPromotionService service;

    public PromotionController(IPromotionService service) {
        this.service = service;
    }


    @Operation(summary = "Get a promotion by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Promotion has been found",
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

    @Operation(summary = "Get all promotion")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All promotions found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class)) }),
    })
    @GetMapping
    public ResponseEntity<BaseResponse> getAll(){
        BaseResponse response = service.getAll();
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @Operation(summary = "Create a Promotion")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The promotion has been created",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class)) }),
            @ApiResponse(responseCode = "401", description = "The promotion already exists or you are not authorized",
                    content = @Content)
    })
    @PostMapping
    public ResponseEntity<BaseResponse> create(@RequestBody CreatePromotionRequest request){
        BaseResponse response = service.create(request);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @Operation(summary = "Update a promotion")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The promotion has been updated",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class)) }),
    })
    @PutMapping("{id}")
    public ResponseEntity<BaseResponse> update(@RequestBody UpdatePromotionRequest request,
                                               @PathVariable Long id){
        BaseResponse response = service.update(request, id);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @Operation(summary = "Delete a promotion")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The promotion has been deleted")
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
