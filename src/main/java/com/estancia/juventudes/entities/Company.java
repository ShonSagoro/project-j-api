package com.estancia.juventudes.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@Table(name = "companies")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private String logo;

    // area
    @Column(nullable = false)
    private Float latitude;

    @Column(nullable = false)
    private Float longitude;
}
