package com.estancia.juventudes.services;

import com.estancia.juventudes.controllers.dtos.request.CreateCompanyRequest;
import com.estancia.juventudes.controllers.dtos.request.UpdateCompanyRequest;
import com.estancia.juventudes.controllers.dtos.response.BaseResponse;
import com.estancia.juventudes.controllers.dtos.response.GetCategoryResponse;
import com.estancia.juventudes.controllers.dtos.response.GetCompanyResponse;
import com.estancia.juventudes.controllers.dtos.response.GetPromotionResponse;
import com.estancia.juventudes.entities.Category;
import com.estancia.juventudes.entities.Company;
import com.estancia.juventudes.repositories.ICompanyRepository;
import com.estancia.juventudes.services.interfaces.ICategoryService;
import com.estancia.juventudes.services.interfaces.ICompanyService;
import com.estancia.juventudes.services.interfaces.IPromotionService;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyServiceImpl implements ICompanyService {

    private final ICompanyRepository repository;

    private final IPromotionService promotionService;

    private final ICategoryService categoryService;

    public CompanyServiceImpl(ICompanyRepository repository, @Lazy IPromotionService promotionService, ICategoryService categoryService) {
        this.repository = repository;
        this.promotionService = promotionService;
        this.categoryService = categoryService;
    }

    @Override
    public BaseResponse get(Long id) {
        GetCompanyResponse response = from(id);
        return BaseResponse.builder()
                .data(response)
                .message("company by id")
                .success(Boolean.TRUE)
                .httpStatus(HttpStatus.OK).build();
    }

    @Override
    public BaseResponse create(CreateCompanyRequest request) {
        GetCompanyResponse response = from(repository.save(from(request)));

        return BaseResponse.builder()
                .data(response)
                .message("company created")
                .success(Boolean.TRUE)
                .httpStatus(HttpStatus.OK).build();
    }

    @Override
    public BaseResponse update(UpdateCompanyRequest request, Long id) {
        Company company = repository.findById(id).orElseThrow(RuntimeException::new);
        company = update(company, request);
        GetCompanyResponse response = from(company);
        return BaseResponse.builder()
                .data(response)
                .message("company info Updated")
                .success(Boolean.TRUE)
                .httpStatus(HttpStatus.OK).build();
    }

    @Override
    public Page<GetCompanyResponse> getAll(Pageable pageable) {
        return repository.findAll(pageable).map(this::from);
    }

    @Override
    public void delete(Long id) {
            repository.deleteById(id);
    }

    @Override
    public List<GetCompanyResponse> GetCompaniesByCategoryId(Long id) {
        return  repository.findByCategoryId(id)
                .stream()
                .map(this::from).collect(Collectors.toList());
    }

    @Override
    public BaseResponse getAllPromotion(Long id) {
        List<GetPromotionResponse> responses= promotionService.GetPromotionsByCompanyId(id);
        return BaseResponse.builder()
                .data(responses)
                .message("find all promotions")
                .success(true)
                .httpStatus(HttpStatus.FOUND).build();
    }

    @Override
    public Company getById(Long id) {
        return repository.findById(id).orElseThrow(RuntimeException::new);
    }


    private GetCompanyResponse from(Long id){
        Company company = repository.findById(id).orElseThrow(RuntimeException::new);
        return from(company);
    }

    private GetCompanyResponse from(Company company){
        GetCompanyResponse response = new GetCompanyResponse();
        response.setId(company.getId());
        response.setName(company.getName());
        response.setAddress(company.getAddress());
        response.setLocation(company.getLocation());
        response.setLogo(company.getLogo());
        response.setLatitude(company.getLatitude());
        response.setLongitude(company.getLongitude());
        response.setCategoryId(company.getCategory().getId());
        return response;
    }

    private Company from (CreateCompanyRequest request){
        Company company = new Company();
        company.setName(request.getName());
        company.setAddress(request.getAddress());
        company.setLocation(request.getLocation());
        company.setLogo(request.getLogo());
        company.setLatitude(request.getLatitude());
        company.setLongitude(request.getLongitude());
        company.setCategory(categoryService.getById(request.getCategoryId()));
        return company;
    }

    private Company update(Company company, UpdateCompanyRequest request){
        company.setName(request.getName());
        company.setAddress(request.getAddress());
        company.setLocation(request.getLocation());
        company.setLogo(request.getLogo());
        company.setLatitude(request.getLatitude());
        company.setLongitude(request.getLongitude());

        return repository.save(company);
    }
}
