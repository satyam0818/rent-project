package com.rent.factory;

import com.rent.models.Bike;
import com.rent.models.Car;
import com.rent.models.Truck;
import com.rent.models.Vehicle;

public class VehicleFactory {

    public static Vehicle getVehicle(String type) {
        if (type == null) {
            return null;
        }
        if (type.equalsIgnoreCase("Car")) {
            return new Car();
        } else if (type.equalsIgnoreCase("Bike")) {
            return new Bike();
        } else if (type.equalsIgnoreCase("Truck")) {
            return new Truck();
        }
        return null;
    }
}
