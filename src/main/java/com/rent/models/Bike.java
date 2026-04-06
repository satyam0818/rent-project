package com.rent.models;

public class Bike extends Vehicle {
    public Bike() {}

    @Override
    public String getVehicleDetails() {
        return name + " with speed: " + speed + " km/h, Rent: ₹" + rentPerHour + "/hour";
    }
}
