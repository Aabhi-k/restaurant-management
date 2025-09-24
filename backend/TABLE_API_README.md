# Restaurant Management System - Table & Orders API

## Overview
This documentation covers the Table management API endpoints for viewing dining tables and their associated orders in the restaurant management system. These endpoints are perfect for displaying table status and orders on menu screens, dashboards, and management interfaces.

## API Endpoints

### Base URL
All table-related endpoints are prefixed with `/api/tables`

---

## Table Management Endpoints

### 1. Get All Tables with Orders
**Endpoint:** `GET /api/tables`

**Description:** Retrieves all tables with their current status and active orders.

**Response:**
```json
[
    {
        "tableId": 1,
        "tableNumber": 1,
        "seatingCapacity": 4,
        "location": "Main Hall",
        "status": "OCCUPIED",
        "activeOrdersCount": 2,
        "totalActiveAmount": 45.50,
        "lastOrderTime": "2023-10-15T14:30:00",
        "activeOrders": [
            {
                "orderId": 123,
                "orderTime": "2023-10-15T14:30:00",
                "status": "PREPARING",
                "staffName": "John Doe",
                "staffId": 1,
                "totalItems": 3,
                "totalAmount": 25.50,
                "estimatedCompletionTime": "2023-10-15T15:00:00",
                "orderItems": [
                    {
                        "orderItemId": 1,
                        "menuItemId": 10,
                        "menuItemName": "Chicken Burger",
                        "menuItemDescription": "Grilled chicken with cheese",
                        "quantity": 1,
                        "itemPriceAtOrderTime": 12.50,
                        "totalPrice": 12.50,
                        "categoryName": "Main Course",
                        "itemStatus": "ORDERED"
                    }
                ]
            }
        ]
    }
]
```

### 2. Get Tables Summary
**Endpoint:** `GET /api/tables/summary`

**Description:** Retrieves all tables with basic information only (no detailed orders).

**Response:**
```json
[
    {
        "tableId": 1,
        "tableNumber": 1,
        "seatingCapacity": 4,
        "location": "Main Hall",
        "status": "OCCUPIED",
        "activeOrdersCount": 2,
        "lastOrderTime": "2023-10-15T14:30:00"
    }
]
```

### 3. Get Table by ID
**Endpoint:** `GET /api/tables/{tableId}`

**Description:** Retrieves a specific table with all its orders.

**Path Parameters:**
- `tableId`: Number - The table identifier

**Response:** Same as individual table object from "Get All Tables" endpoint

### 4. Get Table by Number
**Endpoint:** `GET /api/tables/number/{tableNumber}`

**Description:** Retrieves a table by its table number.

**Path Parameters:**
- `tableNumber`: Number - The table number

**Response:** Same as individual table object from "Get All Tables" endpoint

### 5. Get Available Tables
**Endpoint:** `GET /api/tables/available`

**Description:** Retrieves all tables that have no active orders.

**Response:** Array of table objects with status "AVAILABLE"

### 6. Get Occupied Tables
**Endpoint:** `GET /api/tables/occupied`

**Description:** Retrieves all tables that have active orders.

**Response:** Array of table objects with status "OCCUPIED"

### 7. Get Tables by Location
**Endpoint:** `GET /api/tables/location/{location}`

**Description:** Retrieves all tables in a specific location.

**Path Parameters:**
- `location`: String - The location name (e.g., "Main Hall", "Patio", "VIP Section")

**Response:** Array of table objects in the specified location

---

## Order Management Endpoints

### 8. Get Active Orders for Table
**Endpoint:** `GET /api/tables/{tableId}/orders/active`

**Description:** Retrieves only active orders (PENDING, PREPARING) for a specific table.

**Path Parameters:**
- `tableId`: Number - The table identifier

**Response:**
```json
[
    {
        "orderId": 123,
        "orderTime": "2023-10-15T14:30:00",
        "status": "PREPARING",
        "staffName": "John Doe",
        "staffId": 1,
        "totalItems": 3,
        "totalAmount": 25.50,
        "estimatedCompletionTime": "2023-10-15T15:00:00",
        "orderItems": [...]
    }
]
```

### 9. Get All Orders for Table
**Endpoint:** `GET /api/tables/{tableId}/orders`

**Description:** Retrieves all orders (including completed ones) for a specific table.

**Path Parameters:**
- `tableId`: Number - The table identifier

**Response:** Array of order summary objects

### 10. Get Orders by Date Range
**Endpoint:** `GET /api/tables/{tableId}/orders/date-range`

**Description:** Retrieves orders for a table within a specific date range.

**Path Parameters:**
- `tableId`: Number - The table identifier

**Query Parameters:**
- `startDate`: Date (YYYY-MM-DD) - Start date for the range
- `endDate`: Date (YYYY-MM-DD) - End date for the range

**Example:** `/api/tables/1/orders/date-range?startDate=2023-10-01&endDate=2023-10-31`

**Response:** Array of order summary objects within the date range

---

## Status Check Endpoints

### 11. Check Table Availability
**Endpoint:** `GET /api/tables/{tableId}/available`

**Description:** Checks if a specific table is available (has no active orders).

**Path Parameters:**
- `tableId`: Number - The table identifier

**Response:**
```json
{
    "tableId": 1,
    "isAvailable": false,
    "status": "OCCUPIED"
}
```

### 12. Get Table Status
**Endpoint:** `GET /api/tables/{tableId}/status`

**Description:** Gets the current status of a specific table.

**Path Parameters:**
- `tableId`: Number - The table identifier

**Response:**
```json
{
    "tableId": 1,
    "status": "OCCUPIED",
    "timestamp": "2023-10-15T15:30:00"
}
```

### 13. Restaurant Overview
**Endpoint:** `GET /api/tables/overview`

**Description:** Gets a complete overview of the restaurant including all tables and summary statistics.

**Response:**
```json
{
    "totalTables": 20,
    "availableTables": 12,
    "occupiedTables": 8,
    "timestamp": "2023-10-15T15:30:00",
    "tables": [
        // Array of all tables with summary info
    ]
}
```

---

## Status Values

### Table Status
- `AVAILABLE` - No active orders
- `OCCUPIED` - Has active orders

### Order Status
- `PENDING` - Order placed, not started
- `PREPARING` - Order being prepared
- `SERVED` - Order completed and served
- `CLOSED` - Order finalized

### Item Status
- `ORDERED` - Item ordered
- `PREPARING` - Item being prepared
- `READY` - Item ready to serve
- `SERVED` - Item served

---

## Usage Examples

### Get all tables for display on a dashboard
```bash
curl -X GET http://localhost:8080/api/tables/summary
```

### Get detailed information for a specific table
```bash
curl -X GET http://localhost:8080/api/tables/1
```

### Check which tables are available for seating
```bash
curl -X GET http://localhost:8080/api/tables/available
```

### Get active orders for a table (for kitchen display)
```bash
curl -X GET http://localhost:8080/api/tables/1/orders/active
```

### Get restaurant overview for management dashboard
```bash
curl -X GET http://localhost:8080/api/tables/overview
```

### Get orders for a table by date range (for reporting)
```bash
curl -X GET "http://localhost:8080/api/tables/1/orders/date-range?startDate=2023-10-01&endDate=2023-10-31"
```

---

## Error Responses

### 404 Not Found
```json
{
    "error": "Table not found",
    "message": "Table not found with ID: 999",
    "timestamp": "2023-10-15T15:30:00"
}
```

### 500 Internal Server Error
```json
{
    "error": "Internal error",
    "message": "Failed to retrieve tables",
    "timestamp": "2023-10-15T15:30:00"
}
```

---

## Integration Examples

### Frontend Dashboard Integration
```javascript
// Get all tables for restaurant overview
const getRestaurantOverview = async () => {
    const response = await fetch('/api/tables/overview');
    const data = await response.json();
    return data;
};

// Get active orders for a specific table
const getTableOrders = async (tableId) => {
    const response = await fetch(`/api/tables/${tableId}/orders/active`);
    const orders = await response.json();
    return orders;
};

// Check table availability
const checkTableAvailability = async (tableId) => {
    const response = await fetch(`/api/tables/${tableId}/available`);
    const status = await response.json();
    return status.isAvailable;
};
```

### Kitchen Display System
```javascript
// Get all active orders across all tables
const getAllActiveOrders = async () => {
    const response = await fetch('/api/tables/occupied');
    const occupiedTables = await response.json();
    
    const allActiveOrders = occupiedTables.flatMap(table => 
        table.activeOrders || []
    );
    
    return allActiveOrders;
};
```

---

## Database Relationships

The API leverages the following database relationships:
- `DiningTable` → `Order` (One-to-Many)
- `Order` → `OrderItem` (One-to-Many)
- `OrderItem` → `MenuItem` (Many-to-One)
- `Order` → `Staff` (Many-to-One)

---

## Performance Considerations

1. **Lazy Loading**: Order items are loaded only when needed
2. **Indexing**: Ensure database indexes on frequently queried fields (table_id, order_time, status)
3. **Caching**: Consider implementing caching for frequently accessed table status information
4. **Pagination**: For restaurants with many tables, consider implementing pagination

---

## Security Notes

1. Configure CORS properly for production environments
2. Implement authentication/authorization as needed
3. Consider rate limiting for public-facing endpoints
4. Validate all input parameters

This API provides comprehensive table and order management functionality perfect for restaurant management systems, kitchen displays, and customer-facing applications.
