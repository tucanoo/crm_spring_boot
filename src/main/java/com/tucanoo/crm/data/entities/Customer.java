package com.tucanoo.crm.data.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "customer")
public class Customer {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column
    @NotBlank
    String firstName;

    @Column
    @NotBlank
    String lastName;

    @Column
    String emailAddress;

    @Column
    String address;

    @Column
    String city;

    @Column
    String country;

    @Column
    String phoneNumber;
}
