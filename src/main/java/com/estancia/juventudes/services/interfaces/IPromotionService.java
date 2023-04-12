package com.estancia.juventudes.services.interfaces;

import com.estancia.juventudes.controllers.dtos.request.CreatePromotionRequest;
import com.estancia.juventudes.controllers.dtos.request.UpdatePromotionRequest;
import com.estancia.juventudes.controllers.dtos.response.BaseResponse;

public interface IPromotionService {

    BaseResponse get(Long id);

    BaseResponse create(CreatePromotionRequest request);

    BaseResponse update(UpdatePromotionRequest request, Long id);

    BaseResponse getAll();

    void delete(long id);
}
