package com.wayup.Fola_Logistics.controller;

import com.wayup.Fola_Logistics.dto.response.ApiResponse;
import com.wayup.Fola_Logistics.service.RiderService;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<ApiResponse> getAvailableRequests(@PathVariable Long riderId) {
        return ResponseEntity.status(HttpStatus.OK).body(riderService.getAvailableRequests(riderId));
    }

    @PostMapping("{riderId}/accept-request/{requestId}")
    public ResponseEntity<ApiResponse> acceptRequest(@PathVariable Long riderId, @PathVariable Long requestId) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(riderService.acceptPackageRequest(riderId, requestId));
    }
}
