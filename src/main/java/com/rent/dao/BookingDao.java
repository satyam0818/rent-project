package com.rent.dao;

import com.rent.models.Booking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class BookingDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void createBooking(Booking booking) {
        String sql = "INSERT INTO bookings (vehicle_name, hours, total_amount, payment_method) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, booking.getVehicleName(), booking.getHours(), booking.getTotalAmount(), booking.getPaymentMethod());
    }

    public List<Booking> getAllBookings() {
        String sql = "SELECT * FROM bookings";
        return jdbcTemplate.query(sql, new RowMapper<Booking>() {
            @Override
            public Booking mapRow(ResultSet rs, int rowNum) throws SQLException {
                Booking b = new Booking();
                b.setBookingId(rs.getInt("booking_id"));
                b.setVehicleName(rs.getString("vehicle_name"));
                b.setHours(rs.getInt("hours"));
                b.setTotalAmount(rs.getDouble("total_amount"));
                b.setPaymentMethod(rs.getString("payment_method"));
                return b;
            }
        });
    }

    public void deleteBooking(int id) {
        String sql = "DELETE FROM bookings WHERE booking_id = ?";
        jdbcTemplate.update(sql, id);
    }
    
    // Additional method to get latest booking
    public Booking getLatestBooking() {
        String sql = "SELECT * FROM bookings ORDER BY booking_id DESC LIMIT 1";
        List<Booking> bookings = jdbcTemplate.query(sql, new RowMapper<Booking>() {
            @Override
            public Booking mapRow(ResultSet rs, int rowNum) throws SQLException {
                Booking b = new Booking();
                b.setBookingId(rs.getInt("booking_id"));
                b.setVehicleName(rs.getString("vehicle_name"));
                b.setHours(rs.getInt("hours"));
                b.setTotalAmount(rs.getDouble("total_amount"));
                b.setPaymentMethod(rs.getString("payment_method"));
                return b;
            }
        });
        return bookings.isEmpty() ? null : bookings.get(0);
    }
}
