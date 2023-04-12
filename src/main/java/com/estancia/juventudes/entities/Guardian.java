package com.estancia.juventudes.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter @Setter
@Table (name = "guardians")
public class Guardian {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String lastName;

    private String curp;

    private Integer phoneNumber;

    private String email;

    @OneToMany(mappedBy = "guardian")
    private List<User> users;
}
