package com.rent.models;

public abstract class Vehicle {
    protected int id;
    protected String type;
    protected String name;
    protected int speed;
    protected double rentPerHour;
    protected String imageUrl;

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getSpeed() { return speed; }
    public void setSpeed(int speed) { this.speed = speed; }

    public double getRentPerHour() { return rentPerHour; }
    public void setRentPerHour(double rentPerHour) { this.rentPerHour = rentPerHour; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public abstract String getVehicleDetails();
}
