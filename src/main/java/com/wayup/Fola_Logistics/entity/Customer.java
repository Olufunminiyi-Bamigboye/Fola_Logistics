package com.wayup.Fola_Logistics.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;


import java.util.Objects;


@Entity
@Table(name = "customers")
public class Customer extends User {
    private double latitude;
    private double longitude;

    public Customer(String name, String email, String phoneNo, Role role, double latitude, double longitude) {
        super(name, email, phoneNo, role);
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Customer() {
        super();
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Customer customer = (Customer) o;
        return Double.compare(latitude, customer.latitude) == 0 && Double.compare(longitude, customer.longitude) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), latitude, longitude);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
