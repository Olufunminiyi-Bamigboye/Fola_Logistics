package com.wayup.Fola_Logistics.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "customers")
public class Customer extends User {
    private double latitude;
    private double longitude;
}
