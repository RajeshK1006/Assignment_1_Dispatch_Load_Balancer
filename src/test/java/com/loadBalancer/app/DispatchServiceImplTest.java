package com.loadBalancer.app;


import com.loadBalancer.app.dto.DispatchResponseDto;
import com.loadBalancer.app.dto.OrderDto;
import com.loadBalancer.app.dto.VehicleDto;
import com.loadBalancer.app.entity.Order;
import com.loadBalancer.app.entity.Priority;
import com.loadBalancer.app.entity.Vehicle;
import com.loadBalancer.app.exception.BadRequestException;
import com.loadBalancer.app.exception.ResourceNotFoundException;
import com.loadBalancer.app.repository.OrderRepository;
import com.loadBalancer.app.repository.VehicleRepository;
import com.loadBalancer.app.service.impl.DispatchServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class DispatchServiceImplTest {

    private DispatchServiceImpl dispatchService;
    private OrderRepository orderRepo;
    private VehicleRepository vehicleRepo;
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        orderRepo = mock(OrderRepository.class);
        vehicleRepo = mock(VehicleRepository.class);
        modelMapper = new ModelMapper();
        dispatchService = new DispatchServiceImpl();
        dispatchService.orderRepo = orderRepo;
        dispatchService.vehicleRepo = vehicleRepo;
        dispatchService.mapper = modelMapper;
    }

    @Test
    void testAddOrders_success() {
        OrderDto dto = new OrderDto("ORD001", 12.97, 77.59, "Bangalore", 10, "HIGH");
        String response = dispatchService.addOrders(List.of(dto));
        verify(orderRepo, times(1)).saveAll(anyList());
        assertEquals("Delivery orders accepted", response);
    }

    @Test
    void testAddVehicles_success() {
        VehicleDto dto = new VehicleDto("VEH001", 100, 12.97, 77.64, "Indiranagar");
        String response = dispatchService.addVehicles(List.of(dto));
        verify(vehicleRepo, times(1)).saveAll(anyList());
        assertEquals("Vehicles details accepted", response);
    }

    @Test
    void testGetDispatchResponse_successWithOneOrderAndOneVehicle() {
        Vehicle vehicle = new Vehicle("VEH001", 100, 12.97, 77.64, "Indiranagar");
        Order order = new Order("ORD001", 12.97, 77.59, "MG Road", 10, Priority.HIGH);

        when(vehicleRepo.findAll()).thenReturn(List.of(vehicle));
        when(orderRepo.findAll()).thenReturn(List.of(order));

        List<DispatchResponseDto> result = dispatchService.getDispatchResponse();

        assertEquals(1, result.size());
        DispatchResponseDto response = result.get(0);
        assertEquals("VEH001", response.getVehicleId());
        assertEquals(10, response.getTotalLoad());
        assertEquals(1, response.getAssignedOrders().size());
        assertEquals("ORD001", response.getAssignedOrders().get(0).getOrderId());

        verify(orderRepo, times(1)).saveAll(anyList());
    }



    @Test
    void testAddOrders_emptyList_shouldThrowException() {
        BadRequestException exception = assertThrows(BadRequestException.class,
                () -> dispatchService.addOrders(List.of()));

        assertEquals("Order list cannot be null or empty", exception.getMessage());
        verify(orderRepo, never()).saveAll(anyList());
    }



    @Test
    void testGetDispatchResponse_noVehicles_shouldThrowException() {
        when(vehicleRepo.findAll()).thenReturn(List.of());
        when(orderRepo.findAll()).thenReturn(List.of(new Order("ORD001", 12.97, 77.59, "MG Road", 10, Priority.HIGH)));

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> dispatchService.getDispatchResponse());

        assertEquals("No vehicles available in the system.", exception.getMessage());
    }


}

