package com.wayup.Fola_Logistics.service.impl;

import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCancelParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

//@Service
public class StripePaymentService {

//    @Value("${stripe.publishable.key}")
//    private String stripePublishableKey;
//
//    public PaymentIntent createPayment(Long requestId, double amount, String currency) throws StripeException {
//        PaymentIntent payment = new PaymentIntent();
//        payment.setId(String.valueOf(requestId));
//        payment.setAmount((long) amount);
//        payment.setCurrency(currency);
//
//        return PaymentIntent.create((Map<String, Object>) payment);
//    }
}
