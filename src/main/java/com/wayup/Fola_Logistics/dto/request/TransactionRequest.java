package com.wayup.Fola_Logistics.dto.request;

public class TransactionRequest {
    private double amountPaid;

    public TransactionRequest(double amountPaid) {
        this.amountPaid = amountPaid;
    }
    public double getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(double amountPaid) {
        this.amountPaid = amountPaid;
    }
}
