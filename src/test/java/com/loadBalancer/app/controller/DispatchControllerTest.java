package com.loadBalancer.app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.loadBalancer.app.dto.*;
import com.loadBalancer.app.service.DispatchService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DispatchController.class)
public class DispatchControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DispatchService dispatchService;

    @Autowired
    private ObjectMapper objectMapper;

    private OrderDto orderDto;
    private VehicleDto vehicleDto;

    @BeforeEach
    void setUp() {
        orderDto = new OrderDto("ORD001", 12.97, 77.59, "Bangalore", 10, "HIGH");
        vehicleDto = new VehicleDto("VEH001", 100, 12.97, 77.64, "Indiranagar");
    }

    // test for adding orders
    @Test
    void testAddOrders_success() throws Exception {
        when(dispatchService.addOrders(anyList())).thenReturn("Delivery orders accepted");

        OrderRequestMapper orderRequest = new OrderRequestMapper();
        orderRequest.setOrders(List.of(orderDto));

        mockMvc.perform(post("/api/dispatch/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Delivery orders accepted"));
    }


    // test for adding vehicles
    @Test
    void testAddVehicles_success() throws Exception {
        when(dispatchService.addVehicles(anyList())).thenReturn("Vehicles details accepted");

        VehicleRequestMapper vehicleRequest = new VehicleRequestMapper();
        vehicleRequest.setVehicles(List.of(vehicleDto));

        mockMvc.perform(post("/api/dispatch/vehicles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(vehicleRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Vehicles details accepted"));
    }


    // test for getting dispatch plan
    @Test
    void testGetDispatchPlan_success() throws Exception {
        DispatchResponseDto response = new DispatchResponseDto();
        response.setVehicleId("VEH001");
        response.setTotalLoad(10);
        response.setTotalDistance("1.5 km");
        response.setAssignedOrders(List.of(orderDto));

        when(dispatchService.getDispatchResponse()).thenReturn(List.of(response));

        mockMvc.perform(get("/api/dispatch/plan"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].vehicleId").value("VEH001"))
                .andExpect(jsonPath("$[0].totalLoad").value(10));
    }

    // test for invalid order validation fails

    @Test
    void testAddOrders_invalidOrder_failsValidation() throws Exception {
        OrderDto invalidOrder = new OrderDto(); // No fields set

        OrderRequestMapper orderRequest = new OrderRequestMapper();
        orderRequest.setOrders(List.of(invalidOrder));

        mockMvc.perform(post("/api/dispatch/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderRequest)))
                .andExpect(status().isBadRequest());
    }
}
