package com.estancia.juventudes.services.interfaces;
import com.estancia.juventudes.controllers.dtos.response.BaseResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;
import java.security.GeneralSecurityException;

public interface IGoogleDriveService {
    BaseResponse listEverything() throws GeneralSecurityException, IOException;

    String uploadFile(MultipartFile file, String filePath);

    BaseResponse downloadFile(String id, OutputStream outputStream) throws GeneralSecurityException, IOException;

}
