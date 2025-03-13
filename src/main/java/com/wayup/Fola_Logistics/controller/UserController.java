package com.wayup.Fola_Logistics.controller;

import com.wayup.Fola_Logistics.dto.request.UserRegistrationRequestDTO;
import com.wayup.Fola_Logistics.dto.response.ApiResponse;
import com.wayup.Fola_Logistics.exception.ExistingEmailException;
import com.wayup.Fola_Logistics.exception.InvalidRequestException;
import com.wayup.Fola_Logistics.exception.UserNotFoundException;
import com.wayup.Fola_Logistics.service.CustomerService;
import com.wayup.Fola_Logistics.service.RiderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/users/")

public class UserController {
    @Autowired
    private CustomerService customerService;

    @Autowired
    private RiderService riderService;

    @PostMapping("register")
    public ResponseEntity<ApiResponse> registerUser(@RequestBody UserRegistrationRequestDTO userRegistrationRequestDTO) throws InvalidRequestException, ExistingEmailException {
        if (userRegistrationRequestDTO.getRole() == null) {
            throw new InvalidRequestException("Role is required");
        }
        return switch (userRegistrationRequestDTO.getRole()) {
            case CUSTOMER -> ResponseEntity.status(HttpStatus.CREATED).body(customerService.registerCustomer(userRegistrationRequestDTO));
            case RIDER -> ResponseEntity.status(HttpStatus.CREATED).body(riderService.registerRider(userRegistrationRequestDTO));
        };
    }
}
