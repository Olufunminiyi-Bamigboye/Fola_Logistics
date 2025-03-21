package com.wayup.Fola_Logistics.service;


import com.wayup.Fola_Logistics.dto.response.GeocodeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class BillingService {
    @Autowired
    private GeocodeService geocodeService;

    @Autowired
    private LocationService locationService;

    public Double calculateCharges(String origin, String destination) {
        GeocodeResponse originCoordinates = geocodeService.getGeocodeAddress(origin);
        GeocodeResponse destinationCoordinates = geocodeService.getGeocodeAddress(destination);

        double distanceInKm = locationService.calculateDistanceInKm(originCoordinates, destinationCoordinates);

        double calculatedPrice = distanceInKm * 1.25;
        String formattedPrice = String.format("%.2f", calculatedPrice);

        return Double.valueOf(formattedPrice);
    }
}
