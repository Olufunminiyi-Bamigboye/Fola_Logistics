package com.wayup.Fola_Logistics.service.impl;

import com.wayup.Fola_Logistics.dto.request.PackageRequestDTO;
import com.wayup.Fola_Logistics.dto.request.UserRegistrationRequestDTO;
import com.wayup.Fola_Logistics.dto.response.ApiResponse;
import com.wayup.Fola_Logistics.entity.Customer;
import com.wayup.Fola_Logistics.entity.PackageRequest;
import com.wayup.Fola_Logistics.repository.CustomerRepository;
import com.wayup.Fola_Logistics.repository.PackageRequestRepository;
import com.wayup.Fola_Logistics.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PackageRequestRepository packageRequestRepository;

    @Override
    public ApiResponse registerCustomer(UserRegistrationRequestDTO userRegistrationRequestDTO) {
        if(customerRepository.existsByEmail(userRegistrationRequestDTO.getEmail())) {
            throw new RuntimeException("Email already in use");
        }

        Customer customer = new Customer();
        customer.setName(userRegistrationRequestDTO.getName());
        customer.setEmail(userRegistrationRequestDTO.getEmail());
        customer.setPhoneNo(userRegistrationRequestDTO.getPhoneNo());
        customer.setRole(userRegistrationRequestDTO.getRole());
        customer.setLatitude(userRegistrationRequestDTO.getLatitude());
        customer.setLongitude(userRegistrationRequestDTO.getLongitude());
        Customer newlyRegisteredCustomer = customerRepository.save(customer);
        return new ApiResponse(false, "Account created successfully", newlyRegisteredCustomer);
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
        request.setRecipient(packageRequestDTO.getRecipient());
        request.setRecipientEmail(packageRequestDTO.getRecipientEmail());
        request.setStatus(PackageRequest.Status.REQUESTED);

        packageRequestRepository.save(request);

        return new ApiResponse(false, "Package request successfully created! Waiting for a rider to pick", request);
    }
}
