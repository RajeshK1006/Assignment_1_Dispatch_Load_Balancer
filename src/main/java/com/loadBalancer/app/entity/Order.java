package com.loadBalancer.app.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    private String orderId;

    private double latitude;
    private double longitude;

    private String address;

    private int packageWeight;

    @Enumerated(EnumType.STRING)
    private Priority priority;

    private boolean assigned = false;

    // Default constructor
    public Order() {}

    // Parameterized constructor
    public Order(String orderId, double latitude, double longitude, String address, int packageWeight, Priority priority) {
        this.orderId = orderId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
        this.packageWeight = packageWeight;
        this.priority = priority;
        this.assigned = false;
    }

    // Getters
    public String getOrderId() {
        return orderId;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getAddress() {
        return address;
    }

    public int getPackageWeight() {
        return packageWeight;
    }

    public Priority getPriority() {
        return priority;
    }

    public boolean isAssigned() {
        return assigned;
    }

    // Setters
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPackageWeight(int packageWeight) {
        this.packageWeight = packageWeight;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public void setAssigned(boolean assigned) {
        this.assigned = assigned;
    }
}
