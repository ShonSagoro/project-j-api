package com.estancia.juventudes.entities;

import com.estancia.juventudes.entities.enums.ColorType;
import jakarta.persistence.*;

@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;
  
    @Column(nullable = false)
    private ColorType color;
}