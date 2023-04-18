package com.estancia.juventudes.services.interfaces;

import com.estancia.juventudes.controllers.dtos.request.CreateCompanyRequest;
import com.estancia.juventudes.controllers.dtos.request.UpdateCompanyRequest;
import com.estancia.juventudes.controllers.dtos.response.BaseResponse;
import com.estancia.juventudes.controllers.dtos.response.GetCompanyResponse;
import com.estancia.juventudes.entities.Category;
import com.estancia.juventudes.entities.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ICompanyService {

    BaseResponse get(Long id);

    BaseResponse create(CreateCompanyRequest request);

    BaseResponse update(UpdateCompanyRequest request, Long id);

    Page<GetCompanyResponse> getAll(Pageable pageable);

    void delete(Long id);

    List<GetCompanyResponse> GetCompaniesByCategoryId(Long id);

    BaseResponse getAllPromotion(Long id);

    Company getById(Long id);


}
