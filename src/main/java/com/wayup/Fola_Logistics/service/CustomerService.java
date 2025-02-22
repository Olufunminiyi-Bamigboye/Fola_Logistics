package com.wayup.Fola_Logistics.service;

import com.wayup.Fola_Logistics.dto.request.PackageRequestDTO;
import com.wayup.Fola_Logistics.dto.request.UserRegistrationRequestDTO;
import com.wayup.Fola_Logistics.dto.response.ApiResponse;

public interface   CustomerService {
    ApiResponse registerCustomer(UserRegistrationRequestDTO userRegistrationRequestDTO);
    ApiResponse createPackageRequest(Long customerId, PackageRequestDTO packageRequestDTO);
}