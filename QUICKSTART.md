# Restaurant Management System - Quick Start Guide

## Prerequisites
- Java 21
- MySQL (running on localhost:3306)
- Node.js & npm
- Database: `restaurantManagement` (will be created automatically by Spring Boot)

## Backend Setup

1. Navigate to backend directory:
```bash
cd backend
```

2. Make sure MySQL is running and the database exists:
```sql
CREATE DATABASE IF NOT EXISTS restaurantManagement;
```

3. Update database credentials in `src/main/resources/application.properties` if needed:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/restaurantManagement
spring.datasource.username=root
spring.datasource.password=1234
```

4. Run the backend:
```bash
./mvnw spring-boot:run
```

Backend will start on `http://localhost:8080`

## Frontend Setup

1. Navigate to frontend directory:
```bash
cd frontend
```

2. Install dependencies (if not already installed):
```bash
npm install
```

3. Start the development server:
```bash
npm run dev
```

Frontend will start on `http://localhost:5173` (or another port if 5173 is busy)

## Testing the Connection

1. Make sure the backend is running first
2. Start the frontend
3. Open your browser to `http://localhost:5173`
4. You should see the menu page displaying all menu items from the database

## API Documentation

- **Menu API**: See `backend/MENU_API_README.md`
- **Cart API**: See `backend/CART_API_README.md`
- **Table API**: See `backend/TABLE_API_README.md`

## Current Features

### Frontend (Currently Implemented)
- ✅ Menu display page showing all items by category
- ✅ Shopping cart functionality with add/remove/update
- ✅ Navigation between Menu and Cart pages
- ✅ Cart badge showing item count
- ✅ Session-based cart management
- ✅ API integration with axios
- ✅ Modern UI with flexbox layout and animations
- ✅ Loading and error states
- ✅ Responsive design for mobile devices

### Backend (Fully Implemented)
- ✅ Complete REST API for Menu, Cart, Tables, Orders
- ✅ MySQL database integration
- ✅ Spring Boot with JPA/Hibernate

## How to Use

1. **Browse Menu**: View all menu items organized by category
2. **Add to Cart**: Click "Add to Cart" on any available item
3. **View Cart**: Click the "Cart" button in navigation (shows item count)
4. **Update Quantities**: Use +/- buttons to adjust item quantities
5. **Remove Items**: Click "Remove" to delete items from cart
6. **Clear Cart**: Remove all items at once

## Next Steps

You can now extend the frontend with:
- Checkout process with table and staff selection
- Order history and tracking
- Table management dashboard
- Staff management interface
- Payment processing
