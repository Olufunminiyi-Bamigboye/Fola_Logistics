package com.wayup.Fola_Logistics.service.impl;

import com.wayup.Fola_Logistics.dto.request.TransactionRequest;
import com.wayup.Fola_Logistics.dto.response.ApiResponse;
import com.wayup.Fola_Logistics.dto.response.TransactionResponse;
import com.wayup.Fola_Logistics.entity.Customer;
import com.wayup.Fola_Logistics.entity.PackageRequest;
import com.wayup.Fola_Logistics.entity.Transaction;
import com.wayup.Fola_Logistics.exception.InvalidAmountException;
import com.wayup.Fola_Logistics.exception.UserNotFoundException;
import com.wayup.Fola_Logistics.repository.PackageRequestRepository;
import com.wayup.Fola_Logistics.repository.TransactionRepository;
import com.wayup.Fola_Logistics.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private PackageRequestRepository packageRequestRepository;

    @Override
    public ApiResponse makePayment(Long requestId, TransactionRequest transactionRequest) throws InvalidAmountException {
         PackageRequest request = packageRequestRepository.findById(requestId).orElseThrow(() -> new RuntimeException("Request not found"));

        if (transactionRequest.getAmountPaid() == request.getPrice()) {
            Transaction transaction = new Transaction();
            transaction.setTransactionRef("R" + 1000 + (int) (Math.random() * 8999));
            transaction.setAmountPaid(transactionRequest.getAmountPaid());
            transaction.setAmountReceived(calculateDiscount(transactionRequest.getAmountPaid()));
            transaction.setTransactionDate(LocalDateTime.now());
            transaction.setPackageRequest(request.setPin(generatePin()));
            transaction.setPackageRequest(request.setStatus(PackageRequest.Status.REQUESTED));
            transaction.setCustomer(request.getCustomer());
            transaction.setPaymentStatus(Transaction.PaymentStatus.SUCCESS);

            request.setTransaction(transaction);
            packageRequestRepository.save(request);

            return new ApiResponse(false, "Transaction successful", convertTransactionToResponse(transaction));
        }
        throw new InvalidAmountException("invalid amount");
    }

    public TransactionResponse convertTransactionToResponse(Transaction transaction) {
        TransactionResponse transactionResponse = new TransactionResponse(
                transaction.getPackageRequest().getItemName(),
                transaction.getTransactionRef(),
                transaction.getAmountPaid(),
                transaction.getAmountReceived(),
                transaction.getTransactionDate()
        );
        return transactionResponse;
    }

    public static String generatePin(){
        Random random = new Random();
        String confirmationPin = String.format("%04d", random.nextInt(10000));
        return confirmationPin;
    }

    public static double calculateDiscount(double amount){
        double discount = 0.15 * amount;

        double amountReceived = amount - discount;
        String convertedAmount = String.format("%.2f", amountReceived);
        return Double.valueOf(convertedAmount);
    }
}
