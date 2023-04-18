package com.estancia.juventudes.services.interfaces;

import com.estancia.juventudes.controllers.dtos.request.CreatePromotionRequest;
import com.estancia.juventudes.controllers.dtos.request.UpdatePromotionRequest;
import com.estancia.juventudes.controllers.dtos.response.BaseResponse;
import com.estancia.juventudes.controllers.dtos.response.GetCompanyResponse;
import com.estancia.juventudes.controllers.dtos.response.GetPromotionResponse;

import java.util.List;

public interface IPromotionService {

    BaseResponse get(Long id);

    BaseResponse create(CreatePromotionRequest request);

    BaseResponse update(UpdatePromotionRequest request, Long id);

    BaseResponse getAll();

    void delete(long id);

    List<GetPromotionResponse> GetPromotionsByCompanyId(Long id);
}
