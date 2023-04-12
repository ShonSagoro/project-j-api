package com.estancia.juventudes.services;

import com.estancia.juventudes.controllers.dtos.request.CreateGuardianRequest;
import com.estancia.juventudes.controllers.dtos.request.UpdateGuardianRequest;
import com.estancia.juventudes.controllers.dtos.response.BaseResponse;
import com.estancia.juventudes.controllers.dtos.response.GetGuardianResponse;
import com.estancia.juventudes.entities.Guardian;
import com.estancia.juventudes.repositories.IGuardianRepository;
import com.estancia.juventudes.services.interfaces.IGuardianService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class GuardianServiceImpl  implements IGuardianService {

    private final IGuardianRepository repository;

    public GuardianServiceImpl(IGuardianRepository repository) {
        this.repository = repository;
    }

    @Override
    public BaseResponse get(Long id) {
        GetGuardianResponse response = from(id);
        return BaseResponse.builder()
                .data(response)
                .message("guardian by ID")
                .success(Boolean.TRUE)
                .httpStatus(HttpStatus.OK).build();
    }

    @Override
    public BaseResponse create(CreateGuardianRequest request) {
        GetGuardianResponse response = from(repository.save(from(request)));

        return BaseResponse.builder()
                .data(response)
                .message("guardian created")
                .success(Boolean.TRUE)
                .httpStatus(HttpStatus.OK).build();
    }

    @Override
    public BaseResponse update(Long id, UpdateGuardianRequest request) {
      Guardian guardian = repository.findById(id).orElseThrow(()->new RuntimeException("guardian do not exist"));
      guardian = update(guardian, request);
      GetGuardianResponse response = from(guardian);

        return BaseResponse.builder()
                .data(response)
                .message("guardian Updated")
                .success(Boolean.TRUE)
                .httpStatus(HttpStatus.OK).build();


    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Guardian getById(Long id) {
        return repository.findById(id).orElseThrow(RuntimeException::new);
    }

    private Guardian update(Guardian guardian, UpdateGuardianRequest request){
        guardian.setName(request.getName());
        guardian.setLastName(request.getLastName());
        guardian.setCurp(request.getCurp());
        guardian.setPhoneNumber(request.getPhoneNumber());
        guardian.setEmail(request.getEmail());
        return repository.save(guardian);
    }

    private GetGuardianResponse from(Long id){
        Guardian guardian = repository.findById(id).orElseThrow(()->new RuntimeException("Guardian do not exist"));
        return from(guardian);
    }

    private GetGuardianResponse from(Guardian guardian){
        GetGuardianResponse response = new GetGuardianResponse();
        response.setId(guardian.getId());
        response.setName(guardian.getName());
        response.setLastName(guardian.getLastName());
        response.setCurp(guardian.getCurp());
        response.setPhoneNumber(guardian.getPhoneNumber());
        response.setEmail(guardian.getEmail());
        return response;
    }

    private Guardian from(CreateGuardianRequest request){
        Guardian guardian = new Guardian();
        guardian.setName(request.getName());
        guardian.setLastName(request.getLastName());
        guardian.setCurp(request.getCurp());
        guardian.setPhoneNumber(request.getPhoneNumber());
        guardian.setEmail(request.getEmail());
        return  guardian;
    }
}
