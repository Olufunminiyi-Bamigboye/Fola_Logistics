package com.wayup.Fola_Logistics.service.impl;

import com.wayup.Fola_Logistics.dto.request.UserRegistrationRequestDTO;
import com.wayup.Fola_Logistics.dto.response.ApiResponse;
import com.wayup.Fola_Logistics.entity.PackageRequest;
import com.wayup.Fola_Logistics.entity.Rider;
import com.wayup.Fola_Logistics.repository.PackageRequestRepository;
import com.wayup.Fola_Logistics.repository.RiderRepository;
import com.wayup.Fola_Logistics.service.LocationService;
import com.wayup.Fola_Logistics.service.NotificationService;
import com.wayup.Fola_Logistics.service.RiderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
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
        PackageRequest persistedRequest = packageRequestRepository.save(request);
        if (persistedRequest.getStatus() == PackageRequest.Status.PICKED_UP) {
            PackageRequest notifyRecipientAboutPackage = notifyRecipientAboutPackage();
            packageRequestRepository.save(notifyRecipientAboutPackage);
        }

        rider.setAvailable(false);
        riderRepository.save(rider);

        return new ApiResponse(false, "Package accepted successfully", null);
    }

    public ApiResponse confirmPackageDelivery(Long riderId, Long requestId, Long deliveryPin) {
        Rider rider = riderRepository.findById(riderId).orElseThrow(() -> new RuntimeException("Rider not found"));
        PackageRequest request = packageRequestRepository.findById(requestId).orElseThrow(() -> new RuntimeException("Request not found"));

        if (packageRequestRepository.existsByPin(deliveryPin)) {
            request.setStatus(PackageRequest.Status.DELIVERED);
            PackageRequest savedDeliveryRequest = packageRequestRepository.save(request);

            rider.setAvailable(true);
            rider.setLatitude(request.getDropOffLatitude());
            rider.setLongitude(request.getDropOffLongitude());
            riderRepository.save(rider);

            return new ApiResponse<>(false, "Package delivered successfully", null);
        }

        throw new RuntimeException("Package delivery pin not found");

    }


    @Scheduled(cron = "0 25 11 * * ?")
    public PackageRequest notifyRecipientAboutPackage(){
        List<PackageRequest> acceptedRequestList = packageRequestRepository.findAcceptedRequest();

        for (PackageRequest request : acceptedRequestList) {
            String requestDeliveryPin = generatePin();
            request.setPin(Long.valueOf((requestDeliveryPin)));
            packageRequestRepository.save(request);

            notificationService.sendNotification(
                    request.getRecipientEmail(),
                    "Delivery Notification",
                    "Your package from " + request.getCustomer().getName() +
                            " is about to be delivered by " + request.getRider().getName() + ".\n Your delivery confirmation PIN is: " +
                            requestDeliveryPin + ". Kindly provide this PIN to the driver upon arrival."
            );
            System.out.println("mail successfully sent out");
        }
        return null;
    }
    public static String generatePin(){
        Random random = new Random();
        String confirmationPin = String.format("%04d", random.nextInt(10000));
        return confirmationPin;
   }
}
