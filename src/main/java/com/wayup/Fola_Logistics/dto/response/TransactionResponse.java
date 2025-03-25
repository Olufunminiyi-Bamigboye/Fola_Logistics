package com.wayup.Fola_Logistics.dto.response;

import java.time.LocalDateTime;

public class TransactionResponse {
    private String itemName;
    private String transactionRef;
    private double amountPaid;
    private double amountReceived;
    private LocalDateTime transactionDate;

    public TransactionResponse(String itemName, String transactionRef, double amountPaid, double amountReceived, LocalDateTime transactionDate) {
        this.itemName = itemName;
        this.transactionRef = transactionRef;
        this.amountPaid = amountPaid;
        this.amountReceived = amountReceived;
        this.transactionDate = transactionDate;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

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
}
