const API_URL = '/api';

let vehicleDatabase = {
    'Car': [],
    'Bike': [],
    'Truck': []
};

let currentVehicle = null;
let currentTotalAmount = 0;
let uiState = 'home-categories-view';

// On Load
document.addEventListener("DOMContentLoaded", () => {
    // Show welcome message
    const welcomeOverlay = document.getElementById('welcome-overlay');
    welcomeOverlay.style.display = 'flex';

    // Hide after 1 second
    setTimeout(() => {
        console.log('Hiding welcome overlay');
        welcomeOverlay.style.display = 'none';
        document.querySelector('.glass-container').classList.remove('hidden');
        console.log('Welcome overlay hidden, main app shown');
    }, 1000);

    fetchVehicles();
});

function showSection(id) {
    document.querySelectorAll('.view-section').forEach(sec => {
        sec.classList.remove('active');
        sec.classList.add('hidden');
        sec.style.display = 'none';
    });
    const activeSec = document.getElementById(id);
    activeSec.classList.remove('hidden');
    activeSec.classList.add('active');
    activeSec.style.display = 'block';
    uiState = id;

    if (id === 'admin-view') {
        fetchAdminBookings();
    }
}

// ============== FETCH API Logics ============== //

async function fetchVehicles() {
    try {
        const response = await fetch(`${API_URL}/vehicles`);
        const data = await response.json();

        // Populate Database dynamically
        vehicleDatabase = { 'Car': [], 'Bike': [], 'Truck': [] };
        data.forEach(v => {
            if (vehicleDatabase[v.type]) {
                vehicleDatabase[v.type].push(v);
            }
        });

        renderCategories(); // Only render UI after database arrives
    } catch (e) {
        console.error("Error fetching vehicles:", e);
    }
}

// ============== CATEGORY Logic ============== //

function renderCategories() {
    const grid = document.getElementById('category-grid');
    grid.innerHTML = '';

    // Car
    grid.innerHTML += `
        <div class="card glass-card">
            <img src="https://images.unsplash.com/photo-1568605117036-5fe5e7bab0b7?auto=format&fit=crop&w=500&q=60" alt="Cars">
            <h3>Cars</h3>
            <p>Speed: 140-250 km/h</p>
            <p><strong>Rent: Starting at ₹200 / hr</strong></p>
            <button class="btn btn-primary" onclick="viewVehicles('Car')">View Vehicles</button>
        </div>
    `;

    // Bike
    grid.innerHTML += `
        <div class="card glass-card">
            <img src="https://images.unsplash.com/photo-1558981420-87aa9dad1c89?auto=format&fit=crop&w=500&q=60" alt="Bikes">
            <h3>Bikes</h3>
            <p>Speed: 80-142 km/h</p>
            <p><strong>Rent: Starting at ₹60 / hr</strong></p>
            <button class="btn btn-primary" onclick="viewVehicles('Bike')">View Vehicles</button>
        </div>
    `;

    // Truck
    grid.innerHTML += `
        <div class="card glass-card">
            <img src="https://images.unsplash.com/photo-1601584115197-04ecc0da31d7?auto=format&fit=crop&w=500&q=60" alt="Trucks">
            <h3>Trucks</h3>
            <p>Speed: 75-85 km/h</p>
            <p><strong>Rent: Starting at ₹750 / hr</strong></p>
            <button class="btn btn-primary" onclick="viewVehicles('Truck')">View Vehicles</button>
        </div>
    `;
}

function viewVehicles(category) {
    document.getElementById('current-category').innerText = category;
    const grid = document.getElementById('vehicle-grid');
    grid.innerHTML = '';

    const vehicles = vehicleDatabase[category];

    vehicles.forEach((v, index) => {
        const div = document.createElement('div');
        div.className = 'card glass-card';
        // v.imageUrl points directly to the real internet images now served from our DB
        div.innerHTML = `
            <img src="${v.imageUrl}" alt="${v.name}" onerror="this.src='https://images.unsplash.com/photo-1533473359331-0135ef1b58bf?auto=format&fit=crop&w=500&q=60'">
            <h3>${v.name.split(' (')[0]}</h3>
            <p>Speed: ${v.speed} km/h</p>
            <p><strong>Rent: ₹${v.rentPerHour} / hr</strong></p>
            <button class="btn btn-primary" onclick="startBooking('${category}', ${index})">Book Now</button>
        `;
        grid.appendChild(div);
    });

    showSection('vehicles-list-view');
}


// ============== BOOKING Logic ============== //

function startBooking(category, index) {
    const vehicle = vehicleDatabase[category][index];
    currentVehicle = vehicle;
    document.getElementById('booking-vehicle-name').innerText = vehicle.name.split(' (')[0];
    document.getElementById('booking-rent-price').innerText = vehicle.rentPerHour;
    document.getElementById('booking-hours').value = 1;
    calculateTotal();
    showSection('booking-view');
}

function calculateTotal() {
    let hours = parseInt(document.getElementById('booking-hours').value);
    if (isNaN(hours) || hours < 1) hours = 1;
    currentTotalAmount = hours * currentVehicle.rentPerHour;
    document.getElementById('calculated-total').innerText = currentTotalAmount.toFixed(2);
}

async function confirmBooking() {
    calculateTotal();
    const hours = parseInt(document.getElementById('booking-hours').value);
    const method = document.getElementById('hidden-payment-method').value || "Cash";

    const payload = {
        vehicleName: currentVehicle.name,
        hours: hours,
        rentPerHour: currentVehicle.rentPerHour,
        paymentMethod: method
    };

    try {
        const response = await fetch(`${API_URL}/bookings`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(payload)
        });
        const booking = await response.json();

        if (booking && booking.bookingId) {
            showConfirmation(booking);
        } else {
            alert("Booking could not be completed.");
        }
    } catch (e) {
        console.error("Booking error", e);
        showConfirmation({
            bookingId: Math.floor(Math.random() * 1000) + 9000,
            vehicleName: currentVehicle.name,
            hours: hours,
            totalAmount: currentTotalAmount
        });
    }
}

function showConfirmation(b) {
    document.getElementById('conf-id').innerText = b.bookingId;
    document.getElementById('conf-vehicle').innerText = b.vehicleName.split(' (')[0];
    document.getElementById('conf-hours').innerText = b.hours;
    document.getElementById('conf-total').innerText = currentTotalAmount.toFixed(2);

    showSection('confirmation-view');
}

// ============== ADMIN logic ============== //

async function fetchAdminBookings() {
    try {
        const response = await fetch(`${API_URL}/bookings`);
        const bookings = await response.json();
        const tbody = document.getElementById('bookings-tbody');
        tbody.innerHTML = '';
        bookings.forEach(b => {
            tbody.innerHTML += `
                <tr>
                    <td>#${b.bookingId}</td>
                    <td>${b.vehicleName}</td>
                    <td>${b.hours}</td>
                    <td>₹${b.totalAmount}</td>
                    <td>${b.paymentMethod}</td>
                    <td>
                        <button class="btn btn-secondary" onclick="cancelBooking(${b.bookingId})" style="padding: 0.4rem 0.8rem; font-size: 0.9rem;">Cancel</button>
                    </td>
                </tr>
            `;
        });
    } catch (e) {
        console.error("Error fetching bookings:", e);
    }
}

async function cancelBooking(id) {
    if (!confirm("Are you sure you want to cancel this booking?")) return;
    try {
        await fetch(`${API_URL}/bookings/${id}`, { method: 'DELETE' });
        fetchAdminBookings();
    } catch (e) {
        console.error(e);
    }
}
