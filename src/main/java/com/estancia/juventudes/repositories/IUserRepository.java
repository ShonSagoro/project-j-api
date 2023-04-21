package com.estancia.juventudes.repositories;

import com.estancia.juventudes.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    @Query(value = "SELECT * FROM users WHERE curp=:curp", nativeQuery = true)
    Optional<User> findByCurp(String curp);
}
