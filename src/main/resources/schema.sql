DROP TABLE IF EXISTS vehicles;
CREATE TABLE vehicles (
    id INT AUTO_INCREMENT PRIMARY KEY,
    type VARCHAR(50) NOT NULL,
    name VARCHAR(255) NOT NULL,
    speed INT NOT NULL,
    rent_per_hour DOUBLE NOT NULL,
    image_url TEXT NOT NULL
);

DROP TABLE IF EXISTS bookings;
CREATE TABLE bookings (
    booking_id INT AUTO_INCREMENT PRIMARY KEY,
    vehicle_name VARCHAR(255) NOT NULL,
    hours INT NOT NULL,
    total_amount DOUBLE NOT NULL,
    payment_method VARCHAR(50) NOT NULL
);
