package com.estancia.juventudes.entities;

import com.estancia.juventudes.entities.enums.GenderType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter @Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String curp;

    @Column(nullable = false)
    private GenderType gender;

    private String firstLastname;

    private String secondLastname;

    private String dateOfBirth;

    private String address;

    private Short age;

    private String numberPhone;

    private String rol;

    @ManyToOne
    private Guardian guardian;

}
