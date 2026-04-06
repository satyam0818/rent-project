package com.rent.controllers;

import com.rent.models.Vehicle;
import com.rent.services.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/vehicles")
@CrossOrigin(origins = "*") // Allow frontend to fetch data if on a different port
public class VehicleController {

    @Autowired
    private RentalService rentalService;

    @GetMapping
    public List<Vehicle> getAllVehicles() {
        return rentalService.getAllVehicles();
    }

    @PostMapping
    public String addVehicle(@RequestBody Map<String, Object> payload) {
        String type = (String) payload.get("type");
        String name = (String) payload.get("name");
        int speed = Integer.parseInt(payload.get("speed").toString());
        double rentPerHour = Double.parseDouble(payload.get("rentPerHour").toString());
        String imageUrl = (String) payload.get("imageUrl");

        Vehicle v = com.rent.factory.VehicleFactory.getVehicle(type);
        if(v != null) {
            v.setType(type);
            v.setName(name);
            v.setSpeed(speed);
            v.setRentPerHour(rentPerHour);
            v.setImageUrl(imageUrl);
            rentalService.addVehicle(v);
            return "Vehicle added successfully.";
        }
        return "Invalid Vehicle Type.";
    }

    @PutMapping("/{id}")
    public String updateVehicleRent(@PathVariable int id, @RequestParam double newRent) {
        rentalService.updateVehicleRent(id, newRent);
        return "Vehicle rent updated successfully.";
    }

    @DeleteMapping("/{id}")
    public String deleteVehicle(@PathVariable int id) {
        rentalService.deleteVehicle(id);
        return "Vehicle deleted successfully.";
    }
}
