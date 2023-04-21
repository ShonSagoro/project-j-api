package com.estancia.juventudes.controllers;

import com.estancia.juventudes.controllers.dtos.request.CreateCompanyRequest;
import com.estancia.juventudes.controllers.dtos.request.UpdateCompanyRequest;
import com.estancia.juventudes.controllers.dtos.response.BaseResponse;
import com.estancia.juventudes.controllers.dtos.response.GetCategoryResponse;
import com.estancia.juventudes.controllers.dtos.response.GetCompanyResponse;
import com.estancia.juventudes.services.interfaces.ICompanyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("company")
public class CompanyController {
    @Autowired
    private ICompanyService service;


    @Operation(summary = "Get a company by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Company has been found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class)) }),
            @ApiResponse(responseCode = "401", description = "Company not found or you are not authorized",
                    content = @Content)
    })
    @GetMapping("{id}")
    public ResponseEntity<BaseResponse> get (@PathVariable Long id){
        BaseResponse baseResponse = service.get(id);
        return new ResponseEntity<>(baseResponse, baseResponse.getHttpStatus());
    }

    @Operation(summary = "Get all promotions associated with a company")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All promotions found by company ID",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class)) }),
    })
    @GetMapping("{id}/promotions")
    public ResponseEntity<BaseResponse> getPromotions (@PathVariable Long id){
        BaseResponse baseResponse = service.getAllPromotion(id);
        return new ResponseEntity<>(baseResponse, baseResponse.getHttpStatus());
    }

    @Operation(summary = "Get list companies by pages")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All companies within the requested page number",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Page.class)) }),
    })
    @GetMapping("page/{pageNumber}")
    public Page<GetCompanyResponse> page(@PathVariable Integer pageNumber){
        return service.getAll(PageRequest.of(pageNumber, 20));
    }


    @Operation(summary = "Create a company")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The company has been created",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class)) }),
            @ApiResponse(responseCode = "401", description = "The company already exists or you are not authorized",
                    content = @Content)
    })
    @PostMapping
    public ResponseEntity<BaseResponse> create(@RequestBody CreateCompanyRequest request){
        BaseResponse baseResponse = service.create(request);
        return new ResponseEntity<>(baseResponse, baseResponse.getHttpStatus());
    }

    @Operation(summary = "Update a company")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The company has been updated",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class)) }),
    })
    @PutMapping("{id}")
    public ResponseEntity<BaseResponse> update(@PathVariable Long id, @RequestBody UpdateCompanyRequest request){
        BaseResponse baseResponse = service.update(request, id);
        return new ResponseEntity<>(baseResponse, baseResponse.getHttpStatus());
    }


    @Operation(summary = "Delete a company")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The company has been deleted")
    })
    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id){
        service.delete(id);
    }
}
