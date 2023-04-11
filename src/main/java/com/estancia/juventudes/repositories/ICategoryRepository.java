package com.estancia.juventudes.repositories;

import com.estancia.juventudes.entities.Category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ICategoryRepository extends JpaRepository<Category, Long> {
    @Query(value = "SELECT * FROM categories WHERE name=:name", nativeQuery = true)
    Optional<Category> findByName(String name);
}
