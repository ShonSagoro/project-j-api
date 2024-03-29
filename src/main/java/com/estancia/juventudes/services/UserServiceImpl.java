package com.estancia.juventudes.services;

import com.estancia.juventudes.controllers.advices.exceptions.NotFoundException;
import com.estancia.juventudes.controllers.dtos.request.CodeQRInfoRequest;
import com.estancia.juventudes.controllers.dtos.request.CodeQRRequest;
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
import com.estancia.juventudes.utilities.CodeQRUtils;
import com.google.zxing.WriterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.awt.image.BufferedImage;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private IGuardianService guardianService;

    @Autowired
    private IUserRepository repository;

    @Autowired
    private GenderTypeConverter converter;

    @Autowired
    private PasswordEncoder passwordEncoder;


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
        checkPossibleCopies(request);
        if (!existCurp(request.getCurp()) ){
            throw new RuntimeException("The user was not created by an unexpected value");
        }

        User user = repository.save(from(request));
        GetUserResponse response= from(user);

        return BaseResponse.builder()
                .data(response)
                .message("The user has been created")
                .success(true)
                .httpStatus(HttpStatus.CREATED).build();
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
    public BaseResponse validityCodeQR(CodeQRInfoRequest request) {
        User user=from(request.getCurp());
        verifyAge(user);
        return BaseResponse.builder()
                .data(user.getActive())
                .message("The state of user")
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
        Long age = getAge(user);
        Boolean status = isValid(age);
        user.setActive(status);
        repository.save(user);
    }

    @Override
    public BufferedImage getCodeQR(CodeQRRequest request) throws WriterException {
        User user=from(request.getCurp());
        String info=getInfoQR(user);
        return CodeQRUtils.generateQRCode(info);
    }

    public Boolean isValid(Long age){
        return age <= 29;
    }

    public Long getAge(User user){
        LocalDate dateOfBirth = LocalDate.parse(user.getDateOfBirth());
        LocalDate todayDate = LocalDate.now();
        return ChronoUnit.YEARS.between(dateOfBirth, todayDate);
    }

    private void checkPossibleCopies(CreateUserRequest request){
        copyByCurp(request.getCurp());
        copyByEmail(request.getEmail());
    }

    private void copyByCurp(String curp){
        Optional<User> possibleCopy=repository.findByCurp(curp);
        if(possibleCopy.isPresent()){
            throw new RuntimeException("There is already a user with that curp");
        }
    }

    private void copyByEmail(String email){
        Optional<User> possibleCopy = repository.findByEmail(email);
        if(possibleCopy.isPresent()){
            throw new RuntimeException("There is already a user with that Email");
        }
    }

    private User from(String curp) {
        return repository.findByCurp(curp)
                .orElseThrow(RuntimeException::new);
    }

    private String getInfoQR(User user){
        return "Curp: " + user.getCurp();
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
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setCurp(request.getCurp());
        user.setGender(converter.convertToEntityAttribute(request.getGender()));
        user.setFirstLastname(request.getFirstLastname());
        user.setSecondLastname(request.getSecondLastname());
        user.setDateOfBirth(request.getDateOfBirth());
        user.setAddress(request.getAddress());
        user.setNumberPhone(request.getNumberPhone());
        user.setRol(request.getRol());
        user.setActive(true);
        Guardian guardian = request.getGuardianId()!= null? guardianService.getById(request.getGuardianId()):null;
        user.setGuardian(guardian);
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

    private boolean existCurp (String curp) {
        String search = "^[A-Z]{4}\\d{6}[H|M][A-Z]{5}[A-Z0-9]{2}$";
        Pattern patron = Pattern.compile(search);
        Matcher verify = patron.matcher(curp);
        return verify.matches();
    }

}
