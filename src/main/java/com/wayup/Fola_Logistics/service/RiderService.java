package com.wayup.Fola_Logistics.service;

import com.wayup.Fola_Logistics.dto.request.UserRegistrationRequestDTO;
import com.wayup.Fola_Logistics.dto.response.ApiResponse;
import com.wayup.Fola_Logistics.entity.PackageRequest;

import java.util.List;

public interface RiderService {
    ApiResponse registerRider(UserRegistrationRequestDTO userRegistrationRequestDTO);
    List<PackageRequest> getAvailableRequests(Long riderId);
}
