package com.wayup.Fola_Logistics.service.impl;

import com.wayup.Fola_Logistics.dto.request.PackageRequestDTO;
import com.wayup.Fola_Logistics.dto.request.UserRegistrationRequestDTO;
import com.wayup.Fola_Logistics.dto.response.ApiResponse;
import com.wayup.Fola_Logistics.dto.response.GeocodeResponse;
import com.wayup.Fola_Logistics.entity.Customer;
import com.wayup.Fola_Logistics.entity.PackageRequest;
import com.wayup.Fola_Logistics.exception.ExistingEmailException;
import com.wayup.Fola_Logistics.exception.InvalidRequestException;
import com.wayup.Fola_Logistics.exception.UserNotFoundException;
import com.wayup.Fola_Logistics.repository.CustomerRepository;
import com.wayup.Fola_Logistics.repository.PackageRequestRepository;
import com.wayup.Fola_Logistics.service.BillingService;
import com.wayup.Fola_Logistics.service.CustomerService;
import com.wayup.Fola_Logistics.service.GeocodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;


@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PackageRequestRepository packageRequestRepository;

    @Autowired
    private GeocodeService geocodeService;

    @Autowired
    private BillingService billingService;

    @Override
    public ApiResponse registerCustomer(UserRegistrationRequestDTO userRegistrationRequestDTO) throws ExistingEmailException {
        if(customerRepository.existsByEmail(userRegistrationRequestDTO.getEmail())) {
            throw new ExistingEmailException("Email already in use");
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
    public ApiResponse createPackageRequest(Long customerId, PackageRequestDTO packageRequestDTO) throws UserNotFoundException {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new UserNotFoundException("Customer not found"));

        PackageRequest request = new PackageRequest();
        request.setCustomer(customer);
        request.setItemName(packageRequestDTO.getItemName());
        request.setPickUpLatitude(geocodeService.getGeocodeAddress(packageRequestDTO.getPickUpAddress()).getLatitude());
        request.setPickUpLongitude(geocodeService.getGeocodeAddress(packageRequestDTO.getPickUpAddress()).getLongitude());
        request.setDropOffLatitude(geocodeService.getGeocodeAddress(packageRequestDTO.getDropOffAddress()).getLatitude());
        request.setDropOffLatitude(geocodeService.getGeocodeAddress(packageRequestDTO.getDropOffAddress()).getLongitude());
        request.setRecipient(packageRequestDTO.getRecipient());
        request.setRecipientEmail(packageRequestDTO.getRecipientEmail());
        request.setPrice(billingService.calculateCharges(packageRequestDTO.getPickUpAddress(), packageRequestDTO.getDropOffAddress()));

        packageRequestRepository.save(request);


        return new ApiResponse(false, "Package request successfully created! Waiting for a rider to pick", request);
    }

    @Override
    public ApiResponse cancelPackageRequest(Long customerId, Long packageId) throws UserNotFoundException, InvalidRequestException {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new UserNotFoundException("Customer not found"));
        Optional<PackageRequest> request = packageRequestRepository.findById(packageId);
        if (request.isPresent()) {
            PackageRequest packageRequest = request.get();
            if (packageRequest.getStatus() == PackageRequest.Status.PICKED_UP) {
                throw new RuntimeException("This package has been picked up already, kindly apply for a return when it gets delivered");
            } else if (packageRequest.getStatus() == PackageRequest.Status.DELIVERED) {
                throw new RuntimeException("This package is already delivered, kindly apply for return");
            } else if (packageRequest.getStatus() == PackageRequest.Status.CANCELLED) {
                throw new RuntimeException("This package has been cancelled, kindly apply for return");
            } else {
                packageRequest.setStatus(PackageRequest.Status.CANCELLED);
                packageRequestRepository.save(packageRequest);

                return new ApiResponse<>(false, "Package request cancelled", packageRequest);
            }
        }
        throw new InvalidRequestException("Package request not found");
    }



}
