package com.rent.controllers;

import com.rent.models.Booking;
import com.rent.services.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/bookings")
@CrossOrigin(origins = "*") // Allow frontend to fetch data
public class BookingController {

    @Autowired
    private RentalService rentalService;

    @PostMapping
    public Booking createBooking(@RequestBody Map<String, Object> payload) {
        String vehicleName = (String) payload.get("vehicleName");
        int hours = Integer.parseInt(payload.get("hours").toString());
        double rentPerHour = Double.parseDouble(payload.get("rentPerHour").toString());
        String paymentMethod = (String) payload.get("paymentMethod");

        return rentalService.bookVehicle(vehicleName, hours, rentPerHour, paymentMethod);
    }

    @GetMapping
    public List<Booking> getAllBookings() {
        return rentalService.getAllBookings();
    }

    @DeleteMapping("/{id}")
    public String deleteBooking(@PathVariable int id) {
        rentalService.cancelBooking(id);
        return "Booking cancelled successfully.";
    }
}
