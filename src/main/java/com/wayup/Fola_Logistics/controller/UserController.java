package com.wayup.Fola_Logistics.controller;

import com.wayup.Fola_Logistics.dto.request.UserRegistrationRequestDTO;
import com.wayup.Fola_Logistics.dto.response.ApiResponse;
import com.wayup.Fola_Logistics.entity.Customer;
import com.wayup.Fola_Logistics.service.CustomerService;
import com.wayup.Fola_Logistics.service.RiderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/")
@RequiredArgsConstructor
public class UserController {
    private final CustomerService customerService;
    private final RiderService riderService;

    @PostMapping("users")
    public ResponseEntity<ApiResponse> registerUser(@RequestBody UserRegistrationRequestDTO userRegistrationRequestDTO) {
        if (userRegistrationRequestDTO.getRole() == null) {
            throw new RuntimeException("Role is required");
        }
        return switch (userRegistrationRequestDTO.getRole()) {
            case CUSTOMER -> ResponseEntity.status(HttpStatus.CREATED).body(customerService.registerCustomer(userRegistrationRequestDTO));
            case RIDER -> ResponseEntity.status(HttpStatus.CREATED).body(riderService.registerRider(userRegistrationRequestDTO));
        };
    }
}
