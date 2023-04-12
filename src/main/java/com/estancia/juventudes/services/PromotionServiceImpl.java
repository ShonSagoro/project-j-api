package com.estancia.juventudes.services;

import com.estancia.juventudes.controllers.advices.exceptions.NotFoundException;
import com.estancia.juventudes.controllers.dtos.request.CreatePromotionRequest;
import com.estancia.juventudes.controllers.dtos.request.UpdatePromotionRequest;
import com.estancia.juventudes.controllers.dtos.response.BaseResponse;
import com.estancia.juventudes.controllers.dtos.response.GetPromotionRequest;
import com.estancia.juventudes.entities.Promotion;
import com.estancia.juventudes.repositories.IPromotionRepository;
import com.estancia.juventudes.services.interfaces.IPromotionService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PromotionServiceImpl implements IPromotionService {

    private final IPromotionRepository repository;

    public PromotionServiceImpl(IPromotionRepository repository) {
        this.repository = repository;
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
        GetPromotionRequest response= from(promotion);

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
        List<GetPromotionRequest> responses = repository
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

    private GetPromotionRequest from(Promotion promotion){
        return GetPromotionRequest.builder()
                .id(promotion.getId())
                .name(promotion.getName())
                .description(promotion.getDescription())
                .build();
    }

    private Promotion from(CreatePromotionRequest request){
        Promotion promotion=new Promotion();
        promotion.setName(request.getName());
        promotion.setDescription(request.getDescription());
        return promotion;
    }

    private Promotion update(Promotion promotion, UpdatePromotionRequest update){
        promotion.setDescription(update.getDescription());
        promotion.setName(update.getName());
        return promotion;
    }
}
