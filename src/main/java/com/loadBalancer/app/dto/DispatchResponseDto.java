package com.loadBalancer.app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


// this dto is used to get the response from the GET request - dispatch plan
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DispatchResponseDto {
    private String vehicleId;
    private int totalLoad;
    private String totalDistance;
    private List<OrderDto> assignedOrders;

    public String getVehicleId() {
        return vehicleId;
    }

    public int getTotalLoad() {
        return totalLoad;
    }

    public List<OrderDto> getAssignedOrders() {
        return assignedOrders;
    }

    public String getTotalDistance() {
        return totalDistance;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public void setAssignedOrders(List<OrderDto> assignedOrders) {
        this.assignedOrders = assignedOrders;
    }

    public void setTotalDistance(String totalDistance) {
        this.totalDistance = totalDistance;
    }

    public void setTotalLoad(int totalLoad) {
        this.totalLoad = totalLoad;
    }
}
