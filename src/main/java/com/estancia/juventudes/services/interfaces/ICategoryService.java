package com.estancia.juventudes.services.interfaces;

import com.estancia.juventudes.controllers.dtos.request.CreateCategoryRequest;
import com.estancia.juventudes.controllers.dtos.request.UpdateCategoryRequest;
import com.estancia.juventudes.controllers.dtos.response.BaseResponse;

public interface ICategoryService {
    BaseResponse get(Long id);
    BaseResponse create(CreateCategoryRequest request);

    BaseResponse update(UpdateCategoryRequest request, Long idCategory);

    BaseResponse getAll();

    void delete(long id);
}
