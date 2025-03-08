package com.wayup.Fola_Logistics.service;

import com.wayup.Fola_Logistics.dto.request.PinRequest;
import com.wayup.Fola_Logistics.dto.request.UserRegistrationRequestDTO;
import com.wayup.Fola_Logistics.dto.response.ApiResponse;
import com.wayup.Fola_Logistics.entity.PackageRequest;

public interface RiderService {
    ApiResponse registerRider(UserRegistrationRequestDTO userRegistrationRequestDTO);
    ApiResponse getAvailableRequests(Long riderId);
    ApiResponse acceptPackageRequest(Long riderId, Long requestId);
    ApiResponse confirmPackageDelivery(Long riderId, Long requestId, PinRequest pinRequest);

}
