package com.wayup.Fola_Logistics.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@MappedSuperclass
public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private  String phoneNo;

    @Enumerated(EnumType.STRING)
    private Role role;

    public enum Role {
        CUSTOMER,
        RIDER
    }
}
