package com.wayup.Fola_Logistics.controller;

import com.wayup.Fola_Logistics.dto.request.PinRequest;
import com.wayup.Fola_Logistics.dto.response.ApiResponse;
import com.wayup.Fola_Logistics.exception.InvalidRequestException;
import com.wayup.Fola_Logistics.exception.UserNotFoundException;
import com.wayup.Fola_Logistics.service.RiderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/riders/")

public class RiderController {
    @Autowired
    private RiderService riderService;

    @GetMapping("{riderId}/available-requests")
    public ResponseEntity<ApiResponse> getAvailableRequests(@PathVariable Long riderId) throws UserNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(riderService.getAvailableRequests(riderId));
    }

    @PostMapping("{riderId}/accept-request/{requestId}")
    public ResponseEntity<ApiResponse> acceptRequest(@PathVariable Long riderId, @PathVariable Long requestId) throws UserNotFoundException {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(riderService.acceptPackageRequest(riderId, requestId));
    }

    @PostMapping("{riderId}/delivery/{requestId}/confirm-pin")
    public ResponseEntity<ApiResponse> confirmDelivery(@PathVariable Long riderId, @PathVariable Long requestId, @RequestBody PinRequest pinRequest) throws UserNotFoundException, InvalidRequestException {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(riderService.confirmPackageDelivery(riderId, requestId, pinRequest));
    }
}
