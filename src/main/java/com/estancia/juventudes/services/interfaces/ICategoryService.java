package com.estancia.juventudes.services.interfaces;

import com.estancia.juventudes.controllers.dtos.request.CreateCategoryRequest;
import com.estancia.juventudes.controllers.dtos.request.UpdateCategoryRequest;
import com.estancia.juventudes.controllers.dtos.response.BaseResponse;
import com.estancia.juventudes.entities.Category;

public interface ICategoryService {
    BaseResponse get(Long id);
    BaseResponse create(CreateCategoryRequest request);

    BaseResponse update(UpdateCategoryRequest request, Long id);

    BaseResponse getAll();

    BaseResponse getAllCompanies(Long id);

    Category getById(Long id);

    void delete(long id);

}
