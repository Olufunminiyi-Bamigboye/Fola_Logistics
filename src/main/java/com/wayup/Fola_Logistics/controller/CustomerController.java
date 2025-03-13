package com.wayup.Fola_Logistics.controller;

import com.wayup.Fola_Logistics.dto.request.PackageRequestDTO;
import com.wayup.Fola_Logistics.dto.response.ApiResponse;
import com.wayup.Fola_Logistics.exception.InvalidRequestException;
import com.wayup.Fola_Logistics.exception.UserNotFoundException;
import com.wayup.Fola_Logistics.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/customers/")

public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @PostMapping("{customerId}/request-package")
    public ResponseEntity<ApiResponse> requestPackage(@PathVariable Long customerId, @RequestBody PackageRequestDTO packageRequestDTO) throws UserNotFoundException {
        return ResponseEntity.status(HttpStatus.CREATED).body(customerService.createPackageRequest(customerId, packageRequestDTO));
    }

    @PostMapping("{customerId}/cancel-request/{packageId}")
    public ResponseEntity<ApiResponse> cancelRequest(@PathVariable Long customerId, @PathVariable Long packageId) throws UserNotFoundException, InvalidRequestException {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(customerService.cancelPackageRequest(customerId, packageId));
    }
}
