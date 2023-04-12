package com.estancia.juventudes.services;

import com.estancia.juventudes.controllers.dtos.request.CreateCompanyRequest;
import com.estancia.juventudes.controllers.dtos.request.UpdateCompanyRequest;
import com.estancia.juventudes.controllers.dtos.response.BaseResponse;
import com.estancia.juventudes.controllers.dtos.response.GetCompanyResponse;
import com.estancia.juventudes.entities.Company;
import com.estancia.juventudes.repositories.ICompanyRepository;
import com.estancia.juventudes.services.interfaces.ICompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class CompanyServiceImpl implements ICompanyService {

    @Autowired
    private ICompanyRepository repository;

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
        Company company = repository.findById(id).orElseThrow();
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


    private GetCompanyResponse from(Long id){
        Company company = repository.findById(id).orElseThrow();
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
