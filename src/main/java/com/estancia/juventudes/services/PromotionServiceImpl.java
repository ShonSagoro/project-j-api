package com.estancia.juventudes.services;

import com.estancia.juventudes.controllers.advices.exceptions.NotFoundException;
import com.estancia.juventudes.controllers.dtos.request.CreatePromotionRequest;
import com.estancia.juventudes.controllers.dtos.request.UpdatePromotionRequest;
import com.estancia.juventudes.controllers.dtos.response.BaseResponse;
import com.estancia.juventudes.controllers.dtos.response.GetCompanyResponse;
import com.estancia.juventudes.controllers.dtos.response.GetPromotionResponse;
import com.estancia.juventudes.entities.Company;
import com.estancia.juventudes.entities.Promotion;
import com.estancia.juventudes.repositories.IPromotionRepository;
import com.estancia.juventudes.services.interfaces.ICompanyService;
import com.estancia.juventudes.services.interfaces.IPromotionService;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PromotionServiceImpl implements IPromotionService {

    private final IPromotionRepository repository;
    private final ICompanyService companyService;

    public PromotionServiceImpl(IPromotionRepository repository,  ICompanyService companyService) {
        this.repository = repository;
        this.companyService = companyService;
    }

    @Override
    public BaseResponse get(Long id) {
        Promotion promotion = repository.findById(id)
                .orElseThrow(NotFoundException::new);
        return BaseResponse.builder()
                .data(from(promotion))
                .message("Promotion has been found")
                .success(true)
                .httpStatus(HttpStatus.FOUND).build();
    }

    @Override
    public BaseResponse create(CreatePromotionRequest request) {
        Promotion promotion = repository.save(from(request));
        GetPromotionResponse response= from(promotion);

        return BaseResponse.builder()
                .data(response)
                .message("The promotion has been created")
                .success(true)
                .httpStatus(HttpStatus.CREATED).build();
    }

    @Override
    public BaseResponse update(UpdatePromotionRequest request, Long id) {
        Promotion promotion = repository.findById(id).orElseThrow(RuntimeException::new);
        Promotion response = repository.save(update(promotion, request));
        return BaseResponse.builder()
                .data(from(response))
                .message("promotion updated")
                .success(true)
                .httpStatus(HttpStatus.ACCEPTED).build();
    }

    @Override
    public BaseResponse getAll() {
        List<GetPromotionResponse> responses = repository
                .findAll()
                .stream()
                .map(this::from).collect(Collectors.toList());
        return BaseResponse.builder()
                .data(responses)
                .message("find all promotions")
                .success(true)
                .httpStatus(HttpStatus.FOUND).build();
    }

    @Override
    public void delete(long id) {
        repository.deleteById(id);
    }

    @Override
    public List<GetPromotionResponse> GetPromotionsByCompanyId(Long id) {
        return repository
                .findByCompanyId(id)
                .stream()
                .map(this::from).collect(Collectors.toList());
    }

    private GetPromotionResponse from(Promotion promotion){
        return GetPromotionResponse.builder()
                .id(promotion.getId())
                .name(promotion.getName())
                .description(promotion.getDescription())
                .companyId(promotion.getCompany().getId())
                .build();
    }


    private Promotion from(CreatePromotionRequest request){
        Promotion promotion=new Promotion();
        promotion.setName(request.getName());
        promotion.setDescription(request.getDescription());
        promotion.setCompany(companyService.getById(request.getCompanyId()));
        return promotion;
    }

    private Promotion update(Promotion promotion, UpdatePromotionRequest update){
        promotion.setDescription(update.getDescription());
        promotion.setName(update.getName());
        return promotion;
    }
}
