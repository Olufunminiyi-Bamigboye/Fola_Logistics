package com.wayup.Fola_Logistics.service;

import org.springframework.stereotype.Service;

@Service
public class LocationService {
    public double calculateDistance(double lat1, double lng1, double lat2, double lng2) {
        final int R = 3959; // Earth's radius in miles
        double latDistance = Math.toRadians(lat2 - lat1);
        double lngDistance = Math.toRadians(lng2 - lng1);

    //Haversine formula calculates the shortest distance between two points on a sphere using their latitudes and longitudes measured along the surface
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lngDistance / 2) * Math.sin(lngDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }

}
