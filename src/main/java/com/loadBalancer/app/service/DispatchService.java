package com.loadBalancer.app.service;

import com.loadBalancer.app.dto.DispatchResponseDto;
import com.loadBalancer.app.dto.OrderDto;
import com.loadBalancer.app.dto.VehicleDto;

import java.util.List;

public interface DispatchService {

    String addOrders(List<OrderDto> orders);
    String addVehicles(List<VehicleDto> vehicles);

    List<DispatchResponseDto> getDispatchResponse();
}
