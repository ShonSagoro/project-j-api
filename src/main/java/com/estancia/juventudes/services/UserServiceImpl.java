package com.estancia.juventudes.services;

import com.estancia.juventudes.controllers.advices.exceptions.NotFoundException;
import com.estancia.juventudes.controllers.dtos.request.CreateUserRequest;
import com.estancia.juventudes.controllers.dtos.request.UpdateUserRequest;
import com.estancia.juventudes.controllers.dtos.response.BaseResponse;
import com.estancia.juventudes.controllers.dtos.response.GetUserResponse;
import com.estancia.juventudes.entities.Guardian;
import com.estancia.juventudes.entities.User;
import com.estancia.juventudes.entities.enums.converters.GenderTypeConverter;
import com.estancia.juventudes.repositories.IUserRepository;
import com.estancia.juventudes.services.interfaces.IGuardianService;
import com.estancia.juventudes.services.interfaces.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements IUserService {

    private final IGuardianService guardianService;
    private final IUserRepository repository;

    private final GenderTypeConverter converter;

    public UserServiceImpl( IGuardianService guardianService, IUserRepository repository, GenderTypeConverter converter) {
        this.guardianService = guardianService;
        this.repository = repository;
        this.converter = converter;
    }

    @Override
    public BaseResponse get(String email) {
        User user = repository.findByEmail(email)
                .orElseThrow(NotFoundException::new);
        return BaseResponse.builder()
                .data(from(user))
                .message("user for: " + email)
                .success(true)
                .httpStatus(HttpStatus.FOUND).build();
    }

    @Override
    public BaseResponse create(CreateUserRequest request) {
        Optional<User> possibleCopy = repository.findByEmail(request.getEmail());

        if(possibleCopy.isPresent()){
            throw new RuntimeException("the user exist"); // (RegisterException)
        }
        else if (verifyCurp(request.getCurp()) ){
            User user = repository.save(from(request));
            GetUserResponse response= from(user);

            return BaseResponse.builder()
                    .data(response)
                    .message("The user has been created")
                    .success(true)
                    .httpStatus(HttpStatus.CREATED).build();
        }
        CreateUserRequest user = request;
        return BaseResponse.builder()
                .data(user)
                .message("The user was not created by an unexpected value ")
                .success(false)
                .httpStatus(HttpStatus.CONFLICT).build();
    }

    @Override
    public BaseResponse update(UpdateUserRequest request,  Long idUser) {
        User user = repository.findById(idUser).orElseThrow(RuntimeException::new);
        User response = repository.save(update(user, request));
        return BaseResponse.builder()
                .data(from(response))
                .message("user updated")
                .success(true)
                .httpStatus(HttpStatus.ACCEPTED).build();
    }

    @Override
    public BaseResponse getAll() {
        List<GetUserResponse> responses = repository
                .findAll()
                .stream()
                .map(this::from).collect(Collectors.toList());
        return BaseResponse.builder()
                .data(responses)
                .message("find all users")
                .success(true)
                .httpStatus(HttpStatus.FOUND).build();
    }


    @Override
    public void delete(long id) {
        repository.deleteById(id);
    }

    @Override
    public User getUser(String email) {
        return repository.findByEmail(email)
                .orElseThrow(RuntimeException::new);
    }

    @Override
    public User getById(Long id) {
        return repository.findById(id).orElseThrow(RuntimeException::new);
    }

    @Override
    public void verifyAge(User user) {
        Long age = getAge(user.getId());
        Boolean status = isValid(age);
        user.setActive(status);
        repository.save(user);
    }

    public Boolean isValid(Long age){
        return age <= 29;
    }

    public Long getAge(Long id){
        User user = repository.findById(id).orElseThrow(RuntimeException::new);
        LocalDate dateOfBirth = LocalDate.parse(user.getDateOfBirth());
        LocalDate todayDate = LocalDate.now();
        return ChronoUnit.YEARS.between(dateOfBirth, todayDate);
    }

    private GetUserResponse from(User user){
        Guardian guardian = user.getGuardian();
        return GetUserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .curp(user.getCurp())
                .gender(converter.convertToDatabaseColumn(user.getGender()))
                .firstLastname(user.getFirstLastname())
                .secondLastname(user.getSecondLastname())
                .dateOfBirth(user.getDateOfBirth())
                .address(user.getAddress())
                .numberPhone(user.getNumberPhone())
                .rol(user.getRol())
                .guardianId(guardian!=null?guardian.getId():0)
                .build();
    }

    private User from(CreateUserRequest request){
        User user = new User();
        user.setEmail(request.getEmail());
        user.setName(request.getName());
        user.setPassword(new BCryptPasswordEncoder().encode(request.getPassword()));
        user.setCurp(request.getCurp());
        user.setGender(converter.convertToEntityAttribute(request.getGender()));
        user.setFirstLastname(request.getFirstLastname());
        user.setSecondLastname(request.getSecondLastname());
        user.setDateOfBirth(request.getDateOfBirth());
        user.setAddress(request.getAddress());
        user.setNumberPhone(request.getNumberPhone());
        user.setRol(request.getRol());
        user.setActive(true);
        if(request.getGuardianId()!=0){
       //     user.setGuardian(guardian);
        }
        return user;
    }



    private User update(User user, UpdateUserRequest update){

        user.setName(update.getName());
        user.setEmail(update.getEmail());
        user.setPassword(new BCryptPasswordEncoder().encode(update.getPassword()));
        user.setGender(converter.convertToEntityAttribute(update.getGender()));
        user.setFirstLastname(update.getFirstLastname());
        user.setSecondLastname(update.getSecondLastname());
        user.setDateOfBirth(update.getDateOfBirth());
        user.setAddress(update.getAddress());
        user.setNumberPhone(update.getNumberPhone());
        user.setRol(update.getRol());
        if(update.getGuardianId()!=0){
            Guardian guardian= guardianService.getById(update.getGuardianId());
            user.setGuardian(guardian);
        }else{
            user.setGuardian(null);
        }
        return user;
    }

    public boolean verifyCurp (String curpUser) {
        String curp = curpUser;

        String seachr = "^[A-Z]{4}\\d{6}[H|M][A-Z]{5}[A-Z0-9]{2}$";
        Pattern patron = Pattern.compile(seachr);
        Matcher verify = patron.matcher(curp);
        if(verify.matches()){

            return true;
        }return false;


    }

}
