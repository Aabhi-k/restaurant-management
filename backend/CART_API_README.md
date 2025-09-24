# Restaurant Management System - Cart API

## Overview
This documentation covers the Cart management API endpoints for adding, updating, and managing shopping cart items in the restaurant management system.

## API Endpoints

### Base URL
All cart-related endpoints are prefixed with `/api/cart`

### 1. Add Item to Cart
**Endpoint:** `POST /api/cart/add`

**Description:** Adds an item to the cart or updates the quantity if the item already exists.

**Request Body:**
```json
{
    "sessionId": "string",
    "menuItemId": "number",
    "quantity": "number",
    "specialInstructions": "string (optional)",
    "tableId": "number (optional)",
    "staffId": "number (optional)"
}
```

**Response:**
```json
{
    "cartId": "number",
    "sessionId": "string",
    "createdAt": "datetime",
    "updatedAt": "datetime",
    "totalAmount": "decimal",
    "totalItems": "number",
    "tableId": "number",
    "tableName": "string",
    "staffId": "number",
    "staffName": "string",
    "cartItems": [
        {
            "cartItemId": "number",
            "menuItemId": "number",
            "menuItemName": "string",
            "menuItemDescription": "string",
            "itemPriceAtTime": "decimal",
            "quantity": "number",
            "totalPrice": "decimal",
            "specialInstructions": "string",
            "categoryName": "string",
            "isAvailable": "boolean"
        }
    ]
}
```

### 2. Get Cart
**Endpoint:** `GET /api/cart/{sessionId}`

**Description:** Retrieves the current cart for a session.

**Path Parameters:**
- `sessionId`: String - The session identifier

**Response:** Same as Add Item to Cart response

### 3. Update Cart Item
**Endpoint:** `PUT /api/cart/update`

**Description:** Updates the quantity or special instructions for a cart item.

**Request Body:**
```json
{
    "sessionId": "string",
    "cartItemId": "number",
    "quantity": "number",
    "specialInstructions": "string (optional)"
}
```

**Response:** Same as Add Item to Cart response

### 4. Remove Item from Cart
**Endpoint:** `DELETE /api/cart/{sessionId}/item/{cartItemId}`

**Description:** Removes a specific item from the cart.

**Path Parameters:**
- `sessionId`: String - The session identifier
- `cartItemId`: Number - The cart item identifier

**Response:** Same as Add Item to Cart response

### 5. Clear Cart
**Endpoint:** `DELETE /api/cart/{sessionId}/clear`

**Description:** Removes all items from the cart.

**Path Parameters:**
- `sessionId`: String - The session identifier

**Response:**
```json
{
    "message": "Cart cleared successfully",
    "sessionId": "string"
}
```

### 6. Check if Cart Exists
**Endpoint:** `GET /api/cart/{sessionId}/exists`

**Description:** Checks if a cart exists for the given session.

**Path Parameters:**
- `sessionId`: String - The session identifier

**Response:**
```json
{
    "exists": "boolean",
    "sessionId": "string"
}
```

### 7. Get Cart Summary
**Endpoint:** `GET /api/cart/{sessionId}/summary`

**Description:** Gets a summary of the cart (total items and amount only).

**Path Parameters:**
- `sessionId`: String - The session identifier

**Response:**
```json
{
    "sessionId": "string",
    "totalItems": "number",
    "totalAmount": "decimal",
    "cartId": "number"
}
```

### 8. Batch Add Items
**Endpoint:** `POST /api/cart/batch-add`

**Description:** Adds multiple items to the cart in a single request.

**Request Body:**
```json
{
    "sessionId": "string",
    "items": [
        {
            "menuItemId": "number",
            "quantity": "number",
            "specialInstructions": "string (optional)"
        }
    ]
}
```

**Response:** Same as Add Item to Cart response

## Error Responses

All endpoints return appropriate HTTP status codes with error messages:

### 400 Bad Request
```json
{
    "error": "Invalid input",
    "message": "Specific error message",
    "timestamp": "datetime"
}
```

### 404 Not Found
```json
{
    "error": "Error",
    "message": "Resource not found message",
    "timestamp": "datetime"
}
```

### 500 Internal Server Error
```json
{
    "error": "Internal error",
    "message": "An unexpected error occurred",
    "timestamp": "datetime"
}
```

## Usage Examples

### Adding an item to cart
```bash
curl -X POST http://localhost:8080/api/cart/add \
  -H "Content-Type: application/json" \
  -d '{
    "sessionId": "user-session-123",
    "menuItemId": 1,
    "quantity": 2,
    "specialInstructions": "Extra spicy please"
  }'
```

### Getting a cart
```bash
curl -X GET http://localhost:8080/api/cart/user-session-123
```

### Updating cart item
```bash
curl -X PUT http://localhost:8080/api/cart/update \
  -H "Content-Type: application/json" \
  -d '{
    "sessionId": "user-session-123",
    "cartItemId": 1,
    "quantity": 3,
    "specialInstructions": "Medium spicy"
  }'
```

### Removing an item
```bash
curl -X DELETE http://localhost:8080/api/cart/user-session-123/item/1
```

### Clearing the cart
```bash
curl -X DELETE http://localhost:8080/api/cart/user-session-123/clear
```

## Database Schema

### Cart Table
- `cart_id` (Primary Key)
- `session_id` (Unique)
- `created_at`
- `updated_at`
- `table_id` (Foreign Key, Optional)
- `staff_id` (Foreign Key, Optional)

### Cart Item Table
- `cart_item_id` (Primary Key)
- `cart_id` (Foreign Key)
- `item_id` (Foreign Key to menu_item)
- `quantity`
- `item_price_at_time`
- `special_instructions`

## Notes

1. **Session Management**: The cart system uses session IDs to track carts before user authentication. In a production environment, you might want to associate carts with user accounts once authenticated.

2. **Price Consistency**: When items are added to the cart, the current price is captured (`item_price_at_time`) to maintain consistency even if menu prices change later.

3. **Table and Staff Association**: Carts can optionally be associated with dining tables and staff members for restaurant workflow management.

4. **Automatic Cleanup**: The system includes functionality to clean up old/stale carts, which can be implemented as a scheduled task.

5. **Inventory Consideration**: The system checks menu item availability before adding to cart, but you may want to implement additional inventory checks depending on your business requirements.
