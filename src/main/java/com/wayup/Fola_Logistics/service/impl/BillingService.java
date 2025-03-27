package com.wayup.Fola_Logistics.service.impl;


import com.wayup.Fola_Logistics.dto.response.ApiResponse;
import com.wayup.Fola_Logistics.dto.response.GeocodeResponse;
import com.wayup.Fola_Logistics.entity.PackageRequest;
import com.wayup.Fola_Logistics.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class BillingService {
    private static final double PRICE_PER_KM = 2.25;

    @Autowired
    private GeocodeService geocodeService;

    @Autowired
    private LocationService locationService;

    public Double calculateCharges(String origin, String destination) {
        GeocodeResponse originCoordinates = geocodeService.getGeocodeAddress(origin);
        GeocodeResponse destinationCoordinates = geocodeService.getGeocodeAddress(destination);

        double distanceInKm = locationService.calculateDistanceInKm(originCoordinates, destinationCoordinates);

        double calculatedPrice = distanceInKm * PRICE_PER_KM;
        String formattedPrice = String.format("%.2f", calculatedPrice);

        return Double.valueOf(formattedPrice);
    }
}
