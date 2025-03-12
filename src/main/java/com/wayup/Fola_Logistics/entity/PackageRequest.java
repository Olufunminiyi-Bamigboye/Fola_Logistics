package com.wayup.Fola_Logistics.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Objects;


@Entity
@Table(name = "package-request")
public class PackageRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = true)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "rider_id", nullable = true)
    private Rider rider;

    private String itemName;
    private double pickUpLatitude;
    private double pickUpLongitude;
    private double dropOffLatitude;
    private double dropOffLongitude;
    private String recipient;
    private String recipientEmail;
    private String pin;

    @Enumerated(EnumType.STRING)
    private Status status;

    public PackageRequest(Customer customer, Rider rider, String itemName, double pickUpLatitude, double pickUpLongitude, double dropOffLatitude, double dropOffLongitude, Status status) {
        this.customer = customer;
        this.rider = rider;
        this.itemName = itemName;
        this.pickUpLatitude = pickUpLatitude;
        this.pickUpLongitude = pickUpLongitude;
        this.dropOffLatitude = dropOffLatitude;
        this.dropOffLongitude = dropOffLongitude;
        this.pin = pin;
        this.status = status;

    }

    public PackageRequest() {

    }

    public PackageRequest(String pin) {
        this.pin = pin;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Rider getRider() {
        return rider;
    }

    public void setRider(Rider rider) {
        this.rider = rider;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getPickUpLatitude() {
        return pickUpLatitude;
    }

    public void setPickUpLatitude(double pickUpLatitude) {
        this.pickUpLatitude = pickUpLatitude;
    }

    public double getPickUpLongitude() {
        return pickUpLongitude;
    }

    public void setPickUpLongitude(double pickUpLongitude) {
        this.pickUpLongitude = pickUpLongitude;
    }

    public double getDropOffLatitude() {
        return dropOffLatitude;
    }

    public void setDropOffLatitude(double dropOffLatitude) {
        this.dropOffLatitude = dropOffLatitude;
    }

    public double getDropOffLongitude() {
        return dropOffLongitude;
    }

    public void setDropOffLongitude(double dropOffLongitude) {
        this.dropOffLongitude = dropOffLongitude;
    }

    public Status getStatus() {
        return status;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getRecipientEmail() {
        return recipientEmail;
    }

    public void setRecipientEmail(String recipientEmail) {
        this.recipientEmail = recipientEmail;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PackageRequest request = (PackageRequest) o;
        return Double.compare(pickUpLatitude, request.pickUpLatitude) == 0 && Double.compare(pickUpLongitude, request.pickUpLongitude) == 0 && Double.compare(dropOffLatitude, request.dropOffLatitude) == 0 && Double.compare(dropOffLongitude, request.dropOffLongitude) == 0 && Objects.equals(id, request.id) && Objects.equals(customer, request.customer) && Objects.equals(rider, request.rider) && Objects.equals(itemName, request.itemName) && Objects.equals(recipient, request.recipient) && Objects.equals(recipientEmail, request.recipientEmail) && Objects.equals(pin, request.pin) && status == request.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, customer, rider, itemName, pickUpLatitude, pickUpLongitude, dropOffLatitude, dropOffLongitude, recipient, recipientEmail, pin, status);
    }

    @Override
    public String toString() {
        return "PackageRequest{" +
                "id=" + id +
                ", customer=" + customer +
                ", rider=" + rider +
                ", itemName='" + itemName + '\'' +
                ", pickUpLatitude=" + pickUpLatitude +
                ", pickUpLongitude=" + pickUpLongitude +
                ", dropOffLatitude=" + dropOffLatitude +
                ", dropOffLongitude=" + dropOffLongitude +
                ", recipient='" + recipient + '\'' +
                ", recipientEmail='" + recipientEmail + '\'' +
                ", pin=" + pin +
                ", status=" + status +
                '}';
    }

    public enum Status {
        REQUESTED, PICKED_UP, DELIVERED, CANCELLED
    }
}
