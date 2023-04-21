package com.estancia.juventudes.services;

import com.estancia.juventudes.controllers.advices.exceptions.NotFoundException;
import com.estancia.juventudes.controllers.dtos.request.CreateCategoryRequest;
import com.estancia.juventudes.controllers.dtos.request.UpdateCategoryRequest;
import com.estancia.juventudes.controllers.dtos.response.BaseResponse;
import com.estancia.juventudes.controllers.dtos.response.GetCategoryResponse;
import com.estancia.juventudes.controllers.dtos.response.GetCompanyResponse;
import com.estancia.juventudes.entities.Category;
import com.estancia.juventudes.entities.Company;
import com.estancia.juventudes.entities.enums.converters.ColorTypeConverter;
import com.estancia.juventudes.repositories.ICategoryRepository;
import com.estancia.juventudes.services.interfaces.ICategoryService;
import com.estancia.juventudes.services.interfaces.ICompanyService;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements ICategoryService {

    private final ICategoryRepository repository;

    private final ICompanyService companyService;

    private final ColorTypeConverter converter;



    public CategoryServiceImpl(ICategoryRepository repository, @Lazy ICompanyService companyService, ColorTypeConverter converter) {
        this.repository = repository;
        this.companyService = companyService;
        this.converter = converter;
    }

    @Override
    public BaseResponse get(Long id) {
        GetCategoryResponse response = from(id);
        return BaseResponse.builder()
                .data(response)
                .message("Category has been found")
                .success(true)
                .httpStatus(HttpStatus.FOUND).build();
    }

    @Override
    public BaseResponse create(CreateCategoryRequest request) {
        findCopy(request);

        Category category = repository.save(from(request));
        GetCategoryResponse response= from(category);

        return BaseResponse.builder()
                .data(response)
                .message("The category has been created")
                .success(true)
                .httpStatus(HttpStatus.CREATED).build();
    }
    @Override
    public BaseResponse update(UpdateCategoryRequest request, Long id) {
        Category category = repository.findById(id).orElseThrow(RuntimeException::new);
        Category response = repository.save(update(category, request));
        return BaseResponse.builder()
                .data(from(response))
                .message("The category has been updated")
                .success(true)
                .httpStatus(HttpStatus.ACCEPTED).build();
    }

    @Override
    public BaseResponse getAll() {
        List<GetCategoryResponse> responses = repository
                .findAll()
                .stream()
                .map(this::from).collect(Collectors.toList());
        return BaseResponse.builder()
                .data(responses)
                .message("All categories found")
                .success(true)
                .httpStatus(HttpStatus.FOUND).build();
    }

    @Override
    public BaseResponse getAllCompanies(Long id) {

        List<GetCompanyResponse> responses= companyService.GetCompaniesByCategoryId(id);
        return BaseResponse.builder()
                .data(responses)
                .message("All companies found by category ID")
                .success(true)
                .httpStatus(HttpStatus.FOUND).build();
    }

    @Override
    public Category getById(Long id) {
        return repository.findById(id)
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public void delete(long id) {
        repository.deleteById(id);
    }

    private void findCopy(CreateCategoryRequest request){
        Optional<Category> possibleCopy = repository.findByName(request.getName());

        if(possibleCopy.isPresent()){
            throw new RuntimeException("the category exist"); // (RegisterException)
        }
    }

    private GetCategoryResponse from(Long id){
        Category category = repository.findById(id).orElseThrow(RuntimeException::new);
        return from(category);
    }

    private GetCategoryResponse from(Category category){
        return  GetCategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .colorCode(category.getColor().getColorCode())
                .colorName(category.getColor().getColorName())
                .iconUrl(category.getIconUrl())
                .build();
    }

    private Category from(CreateCategoryRequest request){
        Category category= new Category();
        category.setName(request.getName());
        category.setColor(converter.convertToEntityAttribute(request.getColor()));
        category.setDescription(request.getDescription());
        category.setIconUrl(request.getIconUrl());
        return  category;
    }

    private Category update(Category category, UpdateCategoryRequest request){
        category.setName(request.getName());
        category.setDescription(request.getDescription());
        category.setColor(converter.convertToEntityAttribute(request.getColor()));
        category.setIconUrl(request.getIconUrl());
        return category;
    }

}
