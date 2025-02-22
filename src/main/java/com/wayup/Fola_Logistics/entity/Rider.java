package com.wayup.Fola_Logistics.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.Objects;

@Data
@Entity
@Table(name = "riders")
public class Rider extends User {
    private double latitude;
    private double longitude;
    private boolean isAvailable;

    public Rider(double latitude, double longitude, boolean isAvailable) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.isAvailable = isAvailable;
    }

    public Rider() {}

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

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Rider rider = (Rider) o;
        return Double.compare(latitude, rider.latitude) == 0 && Double.compare(longitude, rider.longitude) == 0 && isAvailable == rider.isAvailable;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), latitude, longitude, isAvailable);
    }

    @Override
    public String toString() {
        return "Rider{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                ", isAvailable=" + isAvailable +
                '}';
    }
}
