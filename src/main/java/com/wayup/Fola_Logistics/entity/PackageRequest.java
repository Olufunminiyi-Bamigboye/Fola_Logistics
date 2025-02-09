package com.wayup.Fola_Logistics.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "package-request")
public class PackageRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "rider_id", nullable = true)
    private Rider rider;

    private String itemName;
    private double pickUpLatitude;
    private double pickUpLongitude;
    private double dropOffLatitude;
    private double dropOffLongitude;

    @Enumerated(EnumType.STRING)
    private Status status;

    public enum Status {
        REQUESTED, PICKED_UP, IN_TRANSIT, DELIVERED
    }
}
