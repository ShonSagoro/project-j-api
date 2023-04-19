package com.estancia.juventudes.controllers;

import com.estancia.juventudes.controllers.dtos.request.CodeQRRequest;
import com.estancia.juventudes.controllers.dtos.request.CreateUserRequest;
import com.estancia.juventudes.controllers.dtos.request.LoginRequest;
import com.estancia.juventudes.controllers.dtos.request.UpdateUserRequest;
import com.estancia.juventudes.controllers.dtos.response.BaseResponse;
import com.estancia.juventudes.services.interfaces.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private final IUserService service;

    public UserController(IUserService service){
        this.service = service;
    }


    @Operation(summary = "Get the code qr by user curp")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The code has been generated"),
            @ApiResponse(responseCode = "401", description = "The code is not generated or you are not authorized")
    })
    @PostMapping("/code")
    public void generateQRCode(HttpServletResponse response,@RequestBody CodeQRRequest request) throws Exception {
        BufferedImage image = service.getCodeQR(request);
        ServletOutputStream outputStream = response.getOutputStream();
        ImageIO.write(image, "png", outputStream);
        outputStream.flush();
        outputStream.close();
    }

    @Operation(summary = "Get a promotion by email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User has been found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class)) }),
            @ApiResponse(responseCode = "401", description = "User not found or you are not authorized",
                    content = @Content)
    })
    @PostMapping("email")
    public ResponseEntity<BaseResponse> get(@RequestBody LoginRequest request){
        BaseResponse response = service.get(request.getEmail());
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @Operation(summary = "Get all users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All users found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class)) }),
    })
    @GetMapping
    public ResponseEntity<BaseResponse> getAll(){
        BaseResponse response = service.getAll();
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @Operation(summary = "Register a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The user has been created",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class)) }),
            @ApiResponse(responseCode = "401", description = "The user already exists or you are not authorized",
                    content = @Content)
    })
    @PostMapping("reg")
    public ResponseEntity<BaseResponse> create(@RequestBody CreateUserRequest request){
        BaseResponse response = service.create(request);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @Operation(summary = "Update a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The user has been updated",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class)) }),
    })
    @PutMapping("{idUser}")
    public ResponseEntity<BaseResponse> update(@RequestBody UpdateUserRequest request,
                                               @PathVariable Long idUser){
        BaseResponse response = service.update(request, idUser);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @Operation(summary = "Delete a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The user has been deleted")
    })
    @DeleteMapping("{idUser}")
    public void delete(@PathVariable Long idUser){
        service.delete(idUser);
    }

    @Operation(summary = " Quick check of controller operation")
    @GetMapping("health")
    public String health() {
        return "Ok";
    }

}
