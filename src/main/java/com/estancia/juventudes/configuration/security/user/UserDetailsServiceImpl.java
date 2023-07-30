package com.estancia.juventudes.configuration.security.user;

import com.estancia.juventudes.entities.User;
import com.estancia.juventudes.services.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private IUserService service;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = service.getUser(username);
        service.verifyAge(user);
        if (user.getActive()){
            return new UserDetailsImpl(user);
        }else {
            return null;
        }

    }
}
