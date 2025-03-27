package com.wayup.Fola_Logistics.service.impl;

import com.wayup.Fola_Logistics.dto.request.PinRequest;
import com.wayup.Fola_Logistics.dto.request.UserRegistrationRequestDTO;
import com.wayup.Fola_Logistics.dto.response.ApiResponse;
import com.wayup.Fola_Logistics.entity.PackageRequest;
import com.wayup.Fola_Logistics.entity.Rider;
import com.wayup.Fola_Logistics.exception.ExistingEmailException;
import com.wayup.Fola_Logistics.exception.InvalidRequestException;
import com.wayup.Fola_Logistics.exception.UserNotFoundException;
import com.wayup.Fola_Logistics.repository.PackageRequestRepository;
import com.wayup.Fola_Logistics.repository.RiderRepository;
import com.wayup.Fola_Logistics.service.LocationService;
import com.wayup.Fola_Logistics.service.NotificationService;
import com.wayup.Fola_Logistics.service.RiderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service

public class RiderServiceImpl implements RiderService {
    @Autowired
    private RiderRepository riderRepository;
    @Autowired
    private PackageRequestRepository packageRequestRepository;
    @Autowired
    private LocationService locationService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private GeocodeService geocodeService;


    @Override
    public ApiResponse registerRider(UserRegistrationRequestDTO userRegistrationRequestDTO) throws ExistingEmailException {
        if (riderRepository.existsByEmail(userRegistrationRequestDTO.getEmail())) {
            throw new ExistingEmailException("Email already in use");
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
    public ApiResponse getAvailableRequests(Long riderId) throws UserNotFoundException {
        Rider rider = riderRepository.findById(riderId).orElseThrow(() -> new UserNotFoundException("Rider not found"));
        List<PackageRequest> packageRequests = packageRequestRepository.findByStatus(PackageRequest.Status.REQUESTED);


        List<PackageRequest> PackageRequests = packageRequestRepository.findByStatus(PackageRequest.Status.REQUESTED).stream()
                .filter(request -> locationService.calculateDistance(
                        rider.getLatitude(),
                        rider.getLongitude(),
                        request.getPickUpLatitude(),
                        request.getPickUpLongitude()) <= 2 )
                .collect(Collectors.toList());

        return new ApiResponse(false, "Available package request nearby", packageRequests);
    }


    @Override
    public ApiResponse acceptPackageRequest(Long riderId, Long requestId) throws UserNotFoundException {
        Rider rider = riderRepository.findById(riderId).orElseThrow(() -> new UserNotFoundException("Rider not found"));
        PackageRequest request = packageRequestRepository.findById(requestId).orElseThrow(() -> new RuntimeException("Request not found"));

        if (request.getStatus() != PackageRequest.Status.REQUESTED) {
            return new ApiResponse(false, "Already accepted by another driver", null);
        }

        request.setRider(rider);
        request.setStatus(PackageRequest.Status.PICKED_UP);
        PackageRequest persistedRequest = packageRequestRepository.save(request);

        notificationService.sendNotification(
                request.getRecipientEmail(),
                "Delivery Notification",
                "Your package from " + request.getCustomer().getName() +
                        " is about to be delivered by " + request.getRider().getName() + ".\n Your delivery confirmation PIN is: " +
                        request.getPin() + ". Kindly provide this PIN to the driver upon arrival."
        );

        rider.setAvailable(false);
        riderRepository.save(rider);

        return new ApiResponse(false, "Package accepted successfully", null);
    }

    public ApiResponse confirmPackageDelivery(Long riderId, Long requestId, PinRequest pinRequest) throws UserNotFoundException, InvalidRequestException {
        Rider rider = riderRepository.findById(riderId).orElseThrow(() -> new UserNotFoundException("Rider not found"));
        Optional<PackageRequest> request = packageRequestRepository.findById(requestId);
        if (request.isPresent()) {
            PackageRequest packageRequest = request.get();
            if (pinRequest.getDeliveryPin().equals(packageRequest.getPin())) {
                packageRequest.setStatus(PackageRequest.Status.DELIVERED);
                packageRequestRepository.save(packageRequest);
            }
            rider.setAvailable(true);
            rider.setLatitude(packageRequest.getDropOffLatitude());
            rider.setLongitude(packageRequest.getDropOffLongitude());
            riderRepository.save(rider);

            return new ApiResponse<>(false, "Package delivered successfully", null);
        }
        throw new InvalidRequestException("Package delivery pin provided does not match ");
    }
}

