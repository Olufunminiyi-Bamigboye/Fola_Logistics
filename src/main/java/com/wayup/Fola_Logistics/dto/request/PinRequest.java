package com.wayup.Fola_Logistics.dto.request;

public class PinRequest {
    private String deliveryPin;

    public PinRequest(String deliveryPin) {
        this.deliveryPin = deliveryPin;
    }

    public String getDeliveryPin() {
        return deliveryPin;
    }

    public void setDeliveryPin(String deliveryPin) {
        this.deliveryPin = deliveryPin;
    }
}
