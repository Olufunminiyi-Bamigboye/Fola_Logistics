package com.wayup.Fola_Logistics.dto.request;

import lombok.Data;

@Data
public class PackageRequestDTO {
    private String itemName;
    private double pickUpLatitude;
    private double pickUpLongitude;
    private double dropOffLatitude;
    private double dropOffLongitude;

}
