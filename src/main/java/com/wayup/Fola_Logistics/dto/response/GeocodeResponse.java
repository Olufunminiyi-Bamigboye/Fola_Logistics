package com.wayup.Fola_Logistics.dto.response;

import java.util.List;

public class GeocodeResponse {
    private double latitude;
    private double longitude;

    public GeocodeResponse(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
