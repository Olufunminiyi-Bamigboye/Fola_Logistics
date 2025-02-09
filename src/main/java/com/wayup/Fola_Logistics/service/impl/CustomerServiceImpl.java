package com.wayup.Fola_Logistics.service.impl;

import com.wayup.Fola_Logistics.dto.request.PackageRequestDTO;
import com.wayup.Fola_Logistics.dto.request.UserRegistrationRequestDTO;
import com.wayup.Fola_Logistics.dto.response.ApiResponse;
import com.wayup.Fola_Logistics.entity.Customer;
import com.wayup.Fola_Logistics.entity.PackageRequest;
import com.wayup.Fola_Logistics.repository.CustomerRepository;
import com.wayup.Fola_Logistics.repository.PackageRequestRepository;
import com.wayup.Fola_Logistics.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final PackageRequestRepository packageRequestRepository;

    @Override
    public ApiResponse registerCustomer(UserRegistrationRequestDTO userRegistrationRequestDTO) {
        if(customerRepository.existsByEmail(userRegistrationRequestDTO.getEmail())) {
            throw new RuntimeException("Email already in use");
        }

        Customer customer = new Customer();
        customer.setName(userRegistrationRequestDTO.getName());
        customer.setEmail(userRegistrationRequestDTO.getEmail());
        customer.setRole(userRegistrationRequestDTO.getRole());
        customer.setLatitude(userRegistrationRequestDTO.getLatitude());
        customer.setLongitude(userRegistrationRequestDTO.getLongitude());
        Customer newlyRegisteredCustomer = customerRepository.save(customer);
        return ApiResponse.builder()
                .error(false)
                .message("Account created successfully")
                .data(newlyRegisteredCustomer)
                .build();
    }

    @Override
    public ApiResponse createPackageRequest(Long customerId, PackageRequestDTO packageRequestDTO) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new RuntimeException("Customer not found"));

        PackageRequest request = new PackageRequest();
        request.setCustomer(customer);
        request.setItemName(packageRequestDTO.getItemName());
        request.setPickUpLatitude(packageRequestDTO.getPickUpLatitude());
        request.setPickUpLongitude(packageRequestDTO.getPickUpLongitude());
        request.setDropOffLatitude(packageRequestDTO.getDropOffLatitude());
        request.setDropOffLongitude(packageRequestDTO.getDropOffLongitude());
        request.setStatus(PackageRequest.Status.REQUESTED);

        packageRequestRepository.save(request);

        return ApiResponse.builder()
                .error(false)
                .message("Package request successfully created! Waiting for a rider to pick")
                .build();
    }
}
