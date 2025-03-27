package com.wayup.Fola_Logistics.controller;


import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.wayup.Fola_Logistics.service.impl.StripePaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//@RestController
//@RequestMapping("/api/v1/")
public class PaymentController {

//    @Autowired
//    private StripePaymentService stripePaymentService;
//
//    @PostMapping("payment/{id}/make-payment")
//    public ResponseEntity<PaymentIntent> makePayment(@PathVariable Long id, @RequestParam double amount, @RequestParam String currency) throws StripeException {
//        return ResponseEntity.ok().body(stripePaymentService.createPayment(id, amount, currency));
//    }
}
