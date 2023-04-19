package com.estancia.juventudes.services.interfaces;

import com.estancia.juventudes.controllers.dtos.request.CodeQRInfoRequest;
import com.estancia.juventudes.controllers.dtos.request.CodeQRRequest;
import com.estancia.juventudes.controllers.dtos.request.CreateUserRequest;
import com.estancia.juventudes.controllers.dtos.request.UpdateUserRequest;
import com.estancia.juventudes.controllers.dtos.response.BaseResponse;
import com.estancia.juventudes.entities.User;
import com.google.zxing.WriterException;

import java.awt.image.BufferedImage;

public interface IUserService {
    BaseResponse get(String email);
    BaseResponse create(CreateUserRequest request);

    BaseResponse update(UpdateUserRequest request, Long idUser);

    BaseResponse getAll();

    BaseResponse validityCodeQR(CodeQRInfoRequest request);

    BufferedImage getCodeQR(CodeQRRequest request) throws WriterException;

    void delete(long id);

    User getUser(String email);

    User getById(Long id);

    void verifyAge(User user);


}
