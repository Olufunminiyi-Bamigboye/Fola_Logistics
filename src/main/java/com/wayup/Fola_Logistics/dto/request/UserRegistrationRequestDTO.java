package com.wayup.Fola_Logistics.dto.request;

import com.wayup.Fola_Logistics.entity.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRegistrationRequestDTO {
    private String name;
    private String email;
    private User.Role role;
    private double latitude;
    private double longitude;
}
