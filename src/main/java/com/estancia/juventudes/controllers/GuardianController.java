package com.estancia.juventudes.controllers;

import com.estancia.juventudes.controllers.dtos.request.CreateGuardianRequest;
import com.estancia.juventudes.controllers.dtos.request.UpdateGuardianRequest;
import com.estancia.juventudes.controllers.dtos.response.BaseResponse;
import com.estancia.juventudes.services.interfaces.IGuardianService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("guardian")
public class GuardianController {

    @Autowired
    private IGuardianService service;


    @Operation(summary = "Get a guardian by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Guardian has been found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class)) }),
            @ApiResponse(responseCode = "401", description = "Guardian not found or you are not authorized",
                    content = @Content)
    })
    @GetMapping("{id}")
    public ResponseEntity<BaseResponse> get(@PathVariable Long id){
        return service.get(id).apply();
    }

    @Operation(summary = "Create a Guardian")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The guardian has been created",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class)) })
    })
    @PostMapping
    public ResponseEntity<BaseResponse> create(@RequestBody CreateGuardianRequest request){
        return service.create(request).apply();
    }

    @Operation(summary = "Update a guardian")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The guardian has been updated",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class)) }),
    })
    @PutMapping("{id}")
    public ResponseEntity<BaseResponse> update(@PathVariable Long id, @RequestBody UpdateGuardianRequest request){
        return service.update(id, request).apply();
    }

    @Operation(summary = "Delete a guardian")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The guardian has been deleted")
    })
    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id){
        service.delete(id);
    }

    @Operation(summary = " Quick check of controller operation")
    @GetMapping("health")
    public ResponseEntity<String> health() {
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

}
