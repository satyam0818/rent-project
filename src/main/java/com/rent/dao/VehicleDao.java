package com.rent.dao;

import com.rent.factory.VehicleFactory;
import com.rent.models.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class VehicleDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Vehicle> getAllVehicles() {
        String sql = "SELECT * FROM vehicles";
        return jdbcTemplate.query(sql, new RowMapper<Vehicle>() {
            @Override
            public Vehicle mapRow(ResultSet rs, int rowNum) throws SQLException {
                String name = rs.getString("name");
                String type = rs.getString("type");
                // Using Factory Pattern to instantiate the proper Vehicle object
                Vehicle vehicle = VehicleFactory.getVehicle(type);
                if (vehicle != null) {
                    vehicle.setId(rs.getInt("id"));
                    vehicle.setType(type);
                    vehicle.setName(name);
                    vehicle.setSpeed(rs.getInt("speed"));
                    vehicle.setRentPerHour(rs.getDouble("rent_per_hour"));
                    vehicle.setImageUrl(rs.getString("image_url"));
                }
                return vehicle;
            }
        });
    }

    public void addVehicle(Vehicle vehicle) {
        String sql = "INSERT INTO vehicles (type, name, speed, rent_per_hour, image_url) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, vehicle.getType(), vehicle.getName(), vehicle.getSpeed(), vehicle.getRentPerHour(), vehicle.getImageUrl());
    }

    public void updateVehicleRent(int id, double newRent) {
        String sql = "UPDATE vehicles SET rent_per_hour = ? WHERE id = ?";
        jdbcTemplate.update(sql, newRent, id);
    }

    public void deleteVehicle(int id) {
        String sql = "DELETE FROM vehicles WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
