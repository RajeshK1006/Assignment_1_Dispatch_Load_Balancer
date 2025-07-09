package com.loadBalancer.app.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "vehicles")
public class Vehicle {

    @Id
    private String vehicleId;

    private int capacity;

    private double currentLatitude;
    private double currentLongitude;

    private String currentAddress;

    // Default constructor (required by JPA)
    public Vehicle() {}

    // Parameterized constructor
    public Vehicle(String vehicleId, int capacity, double currentLatitude, double currentLongitude, String currentAddress) {
        this.vehicleId = vehicleId;
        this.capacity = capacity;
        this.currentLatitude = currentLatitude;
        this.currentLongitude = currentLongitude;
        this.currentAddress = currentAddress;
    }

    // Getters
    public String getVehicleId() {
        return vehicleId;
    }

    public int getCapacity() {
        return capacity;
    }

    public double getCurrentLatitude() {
        return currentLatitude;
    }

    public double getCurrentLongitude() {
        return currentLongitude;
    }

    public String getCurrentAddress() {
        return currentAddress;
    }

    // Setters
    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setCurrentLatitude(double currentLatitude) {
        this.currentLatitude = currentLatitude;
    }

    public void setCurrentLongitude(double currentLongitude) {
        this.currentLongitude = currentLongitude;
    }

    public void setCurrentAddress(String currentAddress) {
        this.currentAddress = currentAddress;
    }
}
