package com.wayup.Fola_Logistics.util;

import com.wayup.Fola_Logistics.dto.response.GeocodeResponse;

public class DistanceCalculator {
    public double calculateDistanceInKm(GeocodeResponse point1, GeocodeResponse point2) {
        final int R = 6371; // Radius of the Earth in kilometers
        double lat1 = Math.toRadians(point1.getLatitude());
        double lon1 = Math.toRadians(point1.getLongitude());
        double lat2 = Math.toRadians(point2.getLatitude());
        double lon2 = Math.toRadians(point2.getLongitude());

        double dlat = lat2 - lat1;
        double dlon = lon2 - lon1;

        double a = Math.sin(dlat / 2) * Math.sin(dlat / 2) +
                Math.cos(lat1) * Math.cos(lat2) *
                        Math.sin(dlon / 2) * Math.sin(dlon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return R * c;
    }
}
