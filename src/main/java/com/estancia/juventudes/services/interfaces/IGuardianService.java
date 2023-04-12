package com.estancia.juventudes.services.interfaces;

import com.estancia.juventudes.controllers.dtos.request.CreateGuardianRequest;
import com.estancia.juventudes.controllers.dtos.request.UpdateGuardianRequest;
import com.estancia.juventudes.controllers.dtos.request.UpdateUserRequest;
import com.estancia.juventudes.controllers.dtos.response.BaseResponse;
import com.estancia.juventudes.entities.Guardian;

public interface IGuardianService {

    BaseResponse get(Long id);

    BaseResponse create(CreateGuardianRequest request);

    BaseResponse update(Long id, UpdateGuardianRequest request);

    void delete (Long id);

    Guardian getById(Long id);


}
