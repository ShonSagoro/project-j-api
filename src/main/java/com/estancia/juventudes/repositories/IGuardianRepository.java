package com.estancia.juventudes.repositories;

import com.estancia.juventudes.entities.Guardian;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IGuardianRepository extends JpaRepository<Guardian, Long> {
}
