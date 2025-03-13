package com.wayup.Fola_Logistics.service;

import com.wayup.Fola_Logistics.dto.request.PinRequest;
import com.wayup.Fola_Logistics.dto.request.UserRegistrationRequestDTO;
import com.wayup.Fola_Logistics.dto.response.ApiResponse;
import com.wayup.Fola_Logistics.exception.ExistingEmailException;
import com.wayup.Fola_Logistics.exception.InvalidRequestException;
import com.wayup.Fola_Logistics.exception.UserNotFoundException;

public interface RiderService {
    ApiResponse registerRider(UserRegistrationRequestDTO userRegistrationRequestDTO) throws InvalidRequestException, ExistingEmailException;
    ApiResponse getAvailableRequests(Long riderId) throws UserNotFoundException;
    ApiResponse acceptPackageRequest(Long riderId, Long requestId) throws UserNotFoundException;
    ApiResponse confirmPackageDelivery(Long riderId, Long requestId, PinRequest pinRequest) throws UserNotFoundException, InvalidRequestException;

}
