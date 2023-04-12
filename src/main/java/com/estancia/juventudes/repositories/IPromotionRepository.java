package com.estancia.juventudes.repositories;

import com.estancia.juventudes.entities.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPromotionRepository extends JpaRepository<Promotion, Long> {
}
