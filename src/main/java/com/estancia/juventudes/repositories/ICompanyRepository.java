package com.estancia.juventudes.repositories;

import com.estancia.juventudes.entities.Category;
import com.estancia.juventudes.entities.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ICompanyRepository extends JpaRepository<Company, Long> {
    @Query(value = "SELECT * FROM companies WHERE category_id=:categoryId", nativeQuery = true)
    List<Company> findByCategoryId(Long categoryId);
}
