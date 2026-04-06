package com.rent.services;

import com.rent.dao.BookingDao;
import com.rent.dao.VehicleDao;
import com.rent.models.Booking;
import com.rent.models.Vehicle;
import com.rent.strategy.CardPayment;
import com.rent.strategy.CashPayment;
import com.rent.strategy.PaymentStrategy;
import com.rent.strategy.UpiPayment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RentalService {

    @Autowired
    private VehicleDao vehicleDao;

    @Autowired
    private BookingDao bookingDao;

    public List<Vehicle> getAllVehicles() {
        return vehicleDao.getAllVehicles();
    }

    public void addVehicle(Vehicle vehicle) {
        vehicleDao.addVehicle(vehicle);
    }

    public void updateVehicleRent(int id, double newRent) {
        vehicleDao.updateVehicleRent(id, newRent);
    }

    public void deleteVehicle(int id) {
        vehicleDao.deleteVehicle(id);
    }

    public double calculateTotalAmount(int hours, double rentPerHour) {
        return hours * rentPerHour;
    }

    public boolean processPayment(String paymentMethod, double amount) {
        PaymentStrategy strategy = null;
        if (paymentMethod.equalsIgnoreCase("Cash")) {
            strategy = new CashPayment();
        } else if (paymentMethod.equalsIgnoreCase("UPI")) {
            strategy = new UpiPayment();
        } else if (paymentMethod.equalsIgnoreCase("Card")) {
            strategy = new CardPayment();
        }

        if (strategy != null) {
            return strategy.pay(amount);
        }
        return false;
    }

    public Booking bookVehicle(String vehicleName, int hours, double rentPerHour, String paymentMethod) {
        double totalAmount = calculateTotalAmount(hours, rentPerHour);
        boolean paymentSuccess = processPayment(paymentMethod, totalAmount);

        if (paymentSuccess) {
            Booking booking = new Booking(vehicleName, hours, totalAmount, paymentMethod);
            bookingDao.createBooking(booking);
            return bookingDao.getLatestBooking();
        }
        return null; // Payment failed
    }

    public List<Booking> getAllBookings() {
        return bookingDao.getAllBookings();
    }

    public void cancelBooking(int id) {
        bookingDao.deleteBooking(id);
    }
}
