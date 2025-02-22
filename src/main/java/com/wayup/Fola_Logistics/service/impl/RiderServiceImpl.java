package com.wayup.Fola_Logistics.service.impl;

import com.wayup.Fola_Logistics.dto.request.UserRegistrationRequestDTO;
import com.wayup.Fola_Logistics.dto.response.ApiResponse;
import com.wayup.Fola_Logistics.entity.Customer;
import com.wayup.Fola_Logistics.entity.PackageRequest;
import com.wayup.Fola_Logistics.entity.Rider;
import com.wayup.Fola_Logistics.repository.CustomerRepository;
import com.wayup.Fola_Logistics.repository.PackageRequestRepository;
import com.wayup.Fola_Logistics.repository.RiderRepository;
import com.wayup.Fola_Logistics.service.CustomerService;
import com.wayup.Fola_Logistics.service.LocationService;
import com.wayup.Fola_Logistics.service.RiderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service

public class RiderServiceImpl implements RiderService {
    @Autowired
    private RiderRepository riderRepository;
    @Autowired
    private PackageRequestRepository packageRequestRepository;
    @Autowired
    private LocationService locationService;

    @Override
    public ApiResponse registerRider(UserRegistrationRequestDTO userRegistrationRequestDTO) {
        if(riderRepository.existsByEmail(userRegistrationRequestDTO.getEmail())) {
            throw new RuntimeException("Email already in use");
        }

        Rider rider = new Rider();
        rider.setName(userRegistrationRequestDTO.getName());
        rider.setEmail(userRegistrationRequestDTO.getEmail());
        rider.setPhoneNo(userRegistrationRequestDTO.getPhoneNo());
        rider.setRole(userRegistrationRequestDTO.getRole());
        rider.setLatitude(userRegistrationRequestDTO.getLatitude());
        rider.setLongitude(userRegistrationRequestDTO.getLongitude());
        Rider newlyRegisteredRider = riderRepository.save(rider);
        return new ApiResponse(false, "Account created successfully", newlyRegisteredRider);
    }

    @Override
    public ApiResponse getAvailableRequests(Long riderId) {
        Rider rider = riderRepository.findById(riderId).orElseThrow(() -> new RuntimeException("Rider not found"));

        List<PackageRequest> packageRequests = packageRequestRepository.findByStatus(PackageRequest.Status.REQUESTED).stream()
                .filter(request -> locationService.calculateDistance(
                        rider.getLatitude(),
                        rider.getLongitude(),
                        request.getPickUpLatitude(),
                        request.getPickUpLongitude()) <= 2 )
                .collect(Collectors.toList());

        return new ApiResponse(false, "Available package request nearby", packageRequests);
    }

    @Override
    public ApiResponse acceptPackageRequest(Long riderId, Long requestId) {
        Rider rider = riderRepository.findById(riderId).orElseThrow(() -> new RuntimeException("Rider not found"));
        PackageRequest request = packageRequestRepository.findById(requestId).orElseThrow(() -> new RuntimeException("Request not found"));

        if (request.getStatus() != PackageRequest.Status.REQUESTED) {
            return new ApiResponse(false, "Already accepted by another driver", null);
        }

        request.setRider(rider);
        request.setStatus(PackageRequest.Status.PICKED_UP);
        packageRequestRepository.save(request);

        rider.setAvailable(false);
        riderRepository.save(rider);

        return new ApiResponse(false, "Package accepted successfully", null);
    }
}
