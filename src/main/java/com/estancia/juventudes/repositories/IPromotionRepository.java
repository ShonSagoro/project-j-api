package com.estancia.juventudes.repositories;

import com.estancia.juventudes.entities.Company;
import com.estancia.juventudes.entities.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IPromotionRepository extends JpaRepository<Promotion, Long> {
    @Query(value = "SELECT * FROM promotions WHERE company_id=:companyId", nativeQuery = true)
    List<Promotion> findByCompanyId(Long companyId);
}

