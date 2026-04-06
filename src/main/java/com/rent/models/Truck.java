package com.rent.models;

public class Truck extends Vehicle {
    public Truck() {}

    @Override
    public String getVehicleDetails() {
        return name + " with speed: " + speed + " km/h, Rent: ₹" + rentPerHour + "/hour";
    }
}
