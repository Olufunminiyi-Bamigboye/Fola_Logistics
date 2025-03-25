package com.wayup.Fola_Logistics.controller;


import com.wayup.Fola_Logistics.dto.request.TransactionRequest;
import com.wayup.Fola_Logistics.dto.response.ApiResponse;
import com.wayup.Fola_Logistics.exception.InvalidAmountException;
import com.wayup.Fola_Logistics.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/transactions/")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @PostMapping("{requestId}/make-payment")
    public ResponseEntity<ApiResponse> makePayment(@Validated @PathVariable Long requestId, @RequestBody TransactionRequest transactionRequest) throws InvalidAmountException {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(transactionService.makePayment(requestId, transactionRequest));
    }
}
