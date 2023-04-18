package com.estancia.juventudes.controllers;

import com.estancia.juventudes.controllers.dtos.request.CreateCompanyRequest;
import com.estancia.juventudes.controllers.dtos.request.UpdateCompanyRequest;
import com.estancia.juventudes.controllers.dtos.response.BaseResponse;
import com.estancia.juventudes.controllers.dtos.response.GetCompanyResponse;
import com.estancia.juventudes.services.interfaces.ICompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("company")
public class CompanyController {
    @Autowired
    private ICompanyService service;

    @GetMapping("/id/{id}")
    public ResponseEntity<BaseResponse> get (@PathVariable Long id){
        BaseResponse baseResponse = service.get(id);
        return new ResponseEntity<>(baseResponse, baseResponse.getHttpStatus());
    }

    @GetMapping("/{id}/promotions")
    public ResponseEntity<BaseResponse> getPromotions (@PathVariable Long id){
        BaseResponse baseResponse = service.getAllPromotion(id);
        return new ResponseEntity<>(baseResponse, baseResponse.getHttpStatus());
    }

    @GetMapping("/page/{pageNumber}")
    public Page<GetCompanyResponse> page(@PathVariable Integer pageNumber){
        return service.getAll(PageRequest.of(pageNumber, 20));
    }

    @PostMapping
    public ResponseEntity<BaseResponse> create(@RequestBody CreateCompanyRequest request){
        BaseResponse baseResponse = service.create(request);
        return new ResponseEntity<>(baseResponse, baseResponse.getHttpStatus());
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse> update(@PathVariable Long id, @RequestBody UpdateCompanyRequest request){
        BaseResponse baseResponse = service.update(request, id);
        return new ResponseEntity<>(baseResponse, baseResponse.getHttpStatus());
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        service.delete(id);
    }
}
