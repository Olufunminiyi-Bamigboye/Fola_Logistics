package com.wayup.Fola_Logistics.service;

import com.wayup.Fola_Logistics.dto.request.TransactionRequest;
import com.wayup.Fola_Logistics.dto.response.ApiResponse;
import com.wayup.Fola_Logistics.exception.InvalidAmountException;

public interface TransactionService {
    ApiResponse makePayment(Long requestId, TransactionRequest transactionRequest) throws InvalidAmountException;
}
