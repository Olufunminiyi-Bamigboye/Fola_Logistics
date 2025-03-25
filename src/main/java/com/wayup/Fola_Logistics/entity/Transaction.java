package com.wayup.Fola_Logistics.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;
import java.util.Objects;

@Data
@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String transactionRef;
    private double amountPaid;
    private double amountReceived;
    private LocalDateTime transactionDate;

    @OneToOne
    @JoinColumn(name = "package_request_id", nullable = false)
    private PackageRequest packageRequest;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Customer customer;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    public Transaction(String transactionRef, double amountPaid, double amountReceived, LocalDateTime transactionDate, PackageRequest packageRequest, Customer customer, PaymentStatus paymentStatus) {
        this.transactionRef = transactionRef;
        this.amountPaid = amountPaid;
        this.amountReceived = amountReceived;
        this.transactionDate = transactionDate;
        this.packageRequest = packageRequest;
        this.customer = customer;
        this.paymentStatus = paymentStatus;
    }

    public Transaction() {}

    public String getTransactionRef() {
        return transactionRef;
    }

    public void setTransactionRef(String transactionRef) {
        this.transactionRef = transactionRef;
    }

    public double getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(double amountPaid) {
        this.amountPaid = amountPaid;
    }

    public double getAmountReceived() {
        return amountReceived;
    }

    public void setAmountReceived(double amountReceived) {
        this.amountReceived = amountReceived;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }

    public PackageRequest getPackageRequest() {
        return packageRequest;
    }

    public void setPackageRequest(PackageRequest packageRequest) {
        this.packageRequest = packageRequest;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Double.compare(amountPaid, that.amountPaid) == 0 && Double.compare(amountReceived, that.amountReceived) == 0 && Objects.equals(id, that.id) && Objects.equals(transactionRef, that.transactionRef) && Objects.equals(transactionDate, that.transactionDate) && Objects.equals(packageRequest, that.packageRequest) && Objects.equals(customer, that.customer) && paymentStatus == that.paymentStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, transactionRef, amountPaid, amountReceived, transactionDate, packageRequest, customer, paymentStatus);
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", transactionRef='" + transactionRef + '\'' +
                ", amountPaid=" + amountPaid +
                ", amountReceived=" + amountReceived +
                ", transactionDate=" + transactionDate +
                ", packageRequest=" + packageRequest +
                ", customer=" + customer +
                ", paymentStatus=" + paymentStatus +
                '}';
    }

    public enum PaymentStatus {
        SUCCESS, FAILED, PENDING, IN_PROGRESS;
    }
}
