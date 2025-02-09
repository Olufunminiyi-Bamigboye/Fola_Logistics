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
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RiderServiceImpl implements RiderService {
    private final RiderRepository riderRepository;
    private final PackageRequestRepository packageRequestRepository;
    private final LocationService locationService;

    @Override
    public ApiResponse registerRider(UserRegistrationRequestDTO userRegistrationRequestDTO) {
        if(riderRepository.existsByEmail(userRegistrationRequestDTO.getEmail())) {
            throw new RuntimeException("Email already in use");
        }

        Rider rider = new Rider();
        rider.setName(userRegistrationRequestDTO.getName());
        rider.setEmail(userRegistrationRequestDTO.getEmail());
        rider.setRole(userRegistrationRequestDTO.getRole());
        rider.setLatitude(userRegistrationRequestDTO.getLatitude());
        rider.setLongitude(userRegistrationRequestDTO.getLongitude());
        Rider newlyRegisteredRider = riderRepository.save(rider);
        return ApiResponse.builder()
                .error(false)
                .message("Account created successfully")
                .data(newlyRegisteredRider)
                .build();
    }

    @Override
    public List<PackageRequest> getAvailableRequests(Long riderId) {
        Rider rider = riderRepository.findById(riderId).orElseThrow(() -> new RuntimeException("Rider not found"));

        return packageRequestRepository.findByStatus(PackageRequest.Status.REQUESTED).stream()
                .filter(request -> locationService.calculateDistance(
                        rider.getLatitude(),
                        rider.getLongitude(),
                        request.getPickUpLatitude(),
                        request.getPickUpLongitude()) <= 2 )
                .collect(Collectors.toList());

    }
}
