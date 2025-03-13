package com.wayup.Fola_Logistics.service;

import com.wayup.Fola_Logistics.dto.request.PackageRequestDTO;
import com.wayup.Fola_Logistics.dto.request.UserRegistrationRequestDTO;
import com.wayup.Fola_Logistics.dto.response.ApiResponse;
import com.wayup.Fola_Logistics.exception.ExistingEmailException;
import com.wayup.Fola_Logistics.exception.InvalidRequestException;
import com.wayup.Fola_Logistics.exception.UserNotFoundException;

public interface   CustomerService {
    ApiResponse registerCustomer(UserRegistrationRequestDTO userRegistrationRequestDTO) throws InvalidRequestException, ExistingEmailException;
    ApiResponse createPackageRequest(Long customerId, PackageRequestDTO packageRequestDTO) throws UserNotFoundException;
    ApiResponse cancelPackageRequest(Long customerId, Long packageId) throws UserNotFoundException, InvalidRequestException;
}