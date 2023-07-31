package com.estancia.juventudes.controllers;

import com.estancia.juventudes.controllers.dtos.request.*;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private IUserService service;

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

    @Operation(summary = "Get state the account with the QR info")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "State of account",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class)) }),
            @ApiResponse(responseCode = "401", description = "User not found or you are not authorized",
                    content = @Content)
    })
    @PostMapping("verity")
    public ResponseEntity<BaseResponse> verityCodeQR(@RequestBody CodeQRInfoRequest request){
        return  service.validityCodeQR(request).apply();
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
        return  service.get(request.getEmail()).apply();
    }

    @Operation(summary = "Get all users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All users found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class)) }),
    })
    @GetMapping
    public ResponseEntity<BaseResponse> getAll(){
        return service.getAll().apply();
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
        return service.update(request, idUser).apply();
    }

    @Operation(summary = "Register a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The user has been created",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class)) }),
            @ApiResponse(responseCode = "401", description = "The user already exists or you are not authorized",
                    content = @Content)
    })
    @PostMapping("sing-up")
    public ResponseEntity<BaseResponse> create(@RequestBody CreateUserRequest request){
        return  service.create(request).apply();
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
    public ResponseEntity<String> health() {
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

}
