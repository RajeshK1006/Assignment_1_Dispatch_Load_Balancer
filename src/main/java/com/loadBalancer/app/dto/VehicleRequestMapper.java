package com.loadBalancer.app.dto;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.List;



// this dto class  is used to get list of inputs (vehicle dtos) from the user or UI
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleRequestMapper {

    @Valid
    @NotEmpty(message = "Vehicles List cannot be empty")
    private List<VehicleDto> vehicles;

    public List<VehicleDto> getVehicles() {
        return vehicles;
    }
}
