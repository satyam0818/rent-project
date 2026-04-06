package com.rent.models;

public class Car extends Vehicle {
    public Car() {}

    @Override
    public String getVehicleDetails() {
        return name + " with speed: " + speed + " km/h, Rent: ₹" + rentPerHour + "/hour";
    }
}
