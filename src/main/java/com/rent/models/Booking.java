package com.rent.models;

public class Booking {
    private int bookingId;
    private String vehicleName;
    private int hours;
    private double totalAmount;
    private String paymentMethod;

    public Booking() {}

    public Booking(String vehicleName, int hours, double totalAmount, String paymentMethod) {
        this.vehicleName = vehicleName;
        this.hours = hours;
        this.totalAmount = totalAmount;
        this.paymentMethod = paymentMethod;
    }

    // Getters and Setters
    public int getBookingId() { return bookingId; }
    public void setBookingId(int bookingId) { this.bookingId = bookingId; }

    public String getVehicleName() { return vehicleName; }
    public void setVehicleName(String vehicleName) { this.vehicleName = vehicleName; }

    public int getHours() { return hours; }
    public void setHours(int hours) { this.hours = hours; }

    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
}
