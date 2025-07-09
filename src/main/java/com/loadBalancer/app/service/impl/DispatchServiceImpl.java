package com.loadBalancer.app.service.impl;

import com.loadBalancer.app.dto.DispatchResponseDto;
import com.loadBalancer.app.dto.OrderDto;
import com.loadBalancer.app.dto.VehicleDto;
import com.loadBalancer.app.entity.Order;
import com.loadBalancer.app.entity.Vehicle;
import com.loadBalancer.app.exception.BadRequestException;
import com.loadBalancer.app.exception.ResourceNotFoundException;
import com.loadBalancer.app.repository.OrderRepository;
import com.loadBalancer.app.repository.VehicleRepository;
import com.loadBalancer.app.service.DispatchService;
import com.loadBalancer.app.util.DistanceUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service
public class DispatchServiceImpl implements DispatchService {

    private static final Logger logger = LoggerFactory.getLogger(DispatchServiceImpl.class);


    @Autowired
    public OrderRepository orderRepo;

    @Autowired
    public VehicleRepository vehicleRepo;

    @Autowired
    public ModelMapper mapper;

    @Override
    public String addOrders(List<OrderDto> orders) {
        logger.info("Received {} new orders to save.", orders != null ? orders.size() : 0);
        if (orders == null || orders.isEmpty()) {
            throw new BadRequestException("Order list cannot be null or empty");
        }
        List<Order> inputs = orders.stream().map(x -> mapper.map(x, Order.class)).collect(Collectors.toList());
        orderRepo.saveAll(inputs);
        logger.info("Successfully saved {} orders", inputs.size());
        return "Delivery orders accepted";
    }

    @Override
    public String addVehicles(List<VehicleDto> vehicles) {
        logger.info("Received {} new vehicles to save.", vehicles != null ? vehicles.size() : 0);
        if (vehicles == null || vehicles.isEmpty()) {
            throw new BadRequestException("Vehicle list cannot be null or empty");
        }

        List<Vehicle> inputs = vehicles.stream().map(x -> mapper.map(x, Vehicle.class)).collect(Collectors.toList());
        vehicleRepo.saveAll(inputs);
        logger.info("Successfully saved {} vehicles", inputs.size());
        return "Vehicles details accepted";
    }

    @Override
    public List<DispatchResponseDto> getDispatchResponse() {
        logger.info("Fetching dispatch plan...");
        List<Vehicle> vehicles = vehicleRepo.findAll();
        List<Order> orders = orderRepo.findAll();

        if (vehicles.isEmpty()) {
            logger.error("No vehicles found in the system.");
            throw new ResourceNotFoundException("No vehicles available in the system.");
        }

        if (orders.isEmpty()) {
            logger.error("No orders found in the system.");
            throw new ResourceNotFoundException("No delivery orders found.");
        }

        logger.info("Assigning {} orders to {} vehicles", orders.size(), vehicles.size());


        List<Order> unassigned = orders.stream().filter(x -> !x.isAssigned()).sorted(Comparator.comparing(Order::getPriority)).collect(Collectors.toList());
        Collections.reverse(unassigned);

        Map<String, List<Order>> assignments = new HashMap<>();
        for (Vehicle v : vehicles) {
            int capcityLeft = v.getCapacity();
            double totalDistance = 0.0;
            List<Order> assignedVehicle = new ArrayList<>();

            for (Order o : unassigned) {
                if (o.getPackageWeight() > capcityLeft) continue;

                double distance = DistanceUtil.calculateDistance(
                        v.getCurrentLatitude(), v.getCurrentLongitude(), o.getLatitude(), o.getLongitude()
                );

                o.setAssigned(true);
                assignedVehicle.add(o);
                capcityLeft -= o.getPackageWeight();
                totalDistance += distance;

            }

            if (!assignedVehicle.isEmpty()) {
                assignments.put(v.getVehicleId(), assignedVehicle);
                logger.info("Assigned {} orders to vehicle {}", assignedVehicle.size(), v.getVehicleId());
            }
        }

        orderRepo.saveAll(orders);
        logger.info("Marked assigned orders as saved.");

        List<DispatchResponseDto> dispatchplan = new ArrayList<>();

        for (Vehicle v : vehicles) {
            List<Order> assigned = assignments.getOrDefault(v.getVehicleId(), new ArrayList<>());
            int totalLoad = assigned.stream().mapToInt(Order::getPackageWeight).sum();

            double totalDistance = assigned.stream().mapToDouble(x -> DistanceUtil.calculateDistance(v.getCurrentLatitude(), v.getCurrentLongitude(), x.getLatitude(), x.getLongitude())).sum();

            List<OrderDto> assignedOrders = assigned.stream().map(x -> mapper.map(x, OrderDto.class)).collect(Collectors.toList());

            DispatchResponseDto response = new DispatchResponseDto();
            response.setVehicleId(v.getVehicleId());
            response.setTotalLoad(totalLoad);
            response.setTotalDistance(String.format("%.1f km", totalDistance));
            response.setAssignedOrders(assignedOrders);

            dispatchplan.add(response);

        }

        logger.info("Generated dispatch plan for {} vehicles", dispatchplan.size());
        return dispatchplan;
    }

}