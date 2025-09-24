# Restaurant Management System - Menu API

## Overview
This documentation covers the Menu API endpoints for displaying menu items and categories to customers. These endpoints are perfect for customer-facing applications, digital menu boards, ordering systems, and management interfaces.

## API Endpoints

### Base URL
All menu-related endpoints are prefixed with `/api/menu`

---

## Complete Menu Endpoints

### 1. Get Complete Menu
**Endpoint:** `GET /api/menu`

**Description:** Retrieves the complete menu with all categories and items (including unavailable items).

**Response:**
```json
{
    "categories": [
        {
            "categoryId": 1,
            "categoryName": "Appetizers",
            "categoryDescription": "Start your meal with these delicious appetizers",
            "itemCount": 5,
            "availableItemCount": 4,
            "menuItems": [
                {
                    "itemId": 1,
                    "name": "Chicken Wings",
                    "description": "Spicy buffalo wings served with ranch dressing",
                    "price": 12.99,
                    "availabilityStatus": true,
                    "categoryName": "Appetizers",
                    "categoryId": 1,
                    "imageUrl": null,
                    "allergenInfo": null,
                    "isVegetarian": false,
                    "isVegan": false,
                    "isGlutenFree": false,
                    "preparationTime": null
                }
            ]
        }
    ],
    "totalCategories": 6,
    "totalItems": 25,
    "totalAvailableItems": 22,
    "priceRangeMin": 8.99,
    "priceRangeMax": 35.99,
    "restaurantName": null,
    "menuLastUpdated": "2023-10-15T15:30:00"
}
```

### 2. Get Available Menu
**Endpoint:** `GET /api/menu/available`

**Description:** Retrieves only available menu items and categories that have available items.

**Response:** Same structure as complete menu, but only includes available items.

---

## Category Endpoints

### 3. Get All Categories
**Endpoint:** `GET /api/menu/categories`

**Description:** Retrieves all menu categories with summary information.

**Response:**
```json
[
    {
        "categoryId": 1,
        "categoryName": "Appetizers",
        "categoryDescription": "Start your meal with these delicious appetizers",
        "itemCount": 5,
        "availableItemCount": 4,
        "menuItems": null,
        "iconUrl": null
    }
]
```

### 4. Get Available Categories
**Endpoint:** `GET /api/menu/categories/available`

**Description:** Retrieves only categories that have available items.

**Response:** Array of category objects that have at least one available item.

### 5. Get Category by ID
**Endpoint:** `GET /api/menu/categories/{categoryId}`

**Description:** Retrieves a specific category with all its items.

**Path Parameters:**
- `categoryId`: Number - The category identifier

**Response:** Single category object with full menu items array.

### 6. Get Category by Name
**Endpoint:** `GET /api/menu/categories/name/{categoryName}`

**Description:** Retrieves a category by its name with all items.

**Path Parameters:**
- `categoryName`: String - The category name (e.g., "Appetizers", "Main Course")

**Response:** Single category object with full menu items array.

---

## Menu Items Endpoints

### 7. Get All Menu Items
**Endpoint:** `GET /api/menu/items`

**Description:** Retrieves all menu items across all categories.

**Response:**
```json
[
    {
        "itemId": 1,
        "name": "Chicken Wings",
        "description": "Spicy buffalo wings served with ranch dressing",
        "price": 12.99,
        "availabilityStatus": true,
        "categoryName": "Appetizers",
        "categoryId": 1,
        "imageUrl": null,
        "allergenInfo": null,
        "isVegetarian": false,
        "isVegan": false,
        "isGlutenFree": false,
        "preparationTime": null
    }
]
```

### 8. Get Available Menu Items
**Endpoint:** `GET /api/menu/items/available`

**Description:** Retrieves only available menu items.

**Response:** Array of available menu item objects.

### 9. Get Menu Item by ID
**Endpoint:** `GET /api/menu/items/{itemId}`

**Description:** Retrieves a specific menu item by its ID.

**Path Parameters:**
- `itemId`: Number - The menu item identifier

**Response:** Single menu item object.

### 10. Get Items by Category
**Endpoint:** `GET /api/menu/categories/{categoryId}/items`

**Description:** Retrieves all items in a specific category.

**Path Parameters:**
- `categoryId`: Number - The category identifier

**Response:** Array of menu item objects in the category.

### 11. Get Available Items by Category
**Endpoint:** `GET /api/menu/categories/{categoryId}/items/available`

**Description:** Retrieves only available items in a specific category.

**Path Parameters:**
- `categoryId`: Number - The category identifier

**Response:** Array of available menu item objects in the category.

### 12. Get Items by Category Name
**Endpoint:** `GET /api/menu/categories/name/{categoryName}/items`

**Description:** Retrieves all items in a category by category name.

**Path Parameters:**
- `categoryName`: String - The category name

**Response:** Array of menu item objects in the named category.

---

## Search & Filter Endpoints

### 13. Search Menu Items
**Endpoint:** `GET /api/menu/search`

**Description:** Search menu items by name or description.

**Query Parameters:**
- `q`: String (required) - Search term
- `availableOnly`: Boolean (default: true) - Include only available items

**Example:** `/api/menu/search?q=chicken&availableOnly=true`

**Response:**
```json
{
    "searchTerm": "chicken",
    "availableOnly": true,
    "totalResults": 3,
    "items": [
        // Array of matching menu items
    ]
}
```

### 14. Get Items by Price Range
**Endpoint:** `GET /api/menu/items/price-range`

**Description:** Get menu items within a specific price range.

**Query Parameters:**
- `minPrice`: Decimal (required) - Minimum price
- `maxPrice`: Decimal (required) - Maximum price
- `availableOnly`: Boolean (default: true) - Include only available items

**Example:** `/api/menu/items/price-range?minPrice=10.00&maxPrice=25.00&availableOnly=true`

**Response:**
```json
{
    "minPrice": 10.00,
    "maxPrice": 25.00,
    "availableOnly": true,
    "totalResults": 8,
    "items": [
        // Array of menu items in price range
    ]
}
```

---

## Statistics & Overview Endpoints

### 15. Get Menu Statistics
**Endpoint:** `GET /api/menu/statistics`

**Description:** Get statistical information about the menu.

**Response:**
```json
{
    "categories": null,
    "totalCategories": 6,
    "totalItems": 25,
    "totalAvailableItems": 22,
    "priceRangeMin": 8.99,
    "priceRangeMax": 35.99,
    "restaurantName": null,
    "menuLastUpdated": "2023-10-15T15:30:00"
}
```

### 16. Get Menu Overview
**Endpoint:** `GET /api/menu/overview`

**Description:** Get a comprehensive overview of the menu with categories and statistics.

**Response:**
```json
{
    "totalCategories": 6,
    "totalItems": 25,
    "totalAvailableItems": 22,
    "priceRange": {
        "min": 8.99,
        "max": 35.99
    },
    "categories": [
        // Array of categories with item counts
    ],
    "lastUpdated": "2023-10-15T15:30:00"
}
```

---

## Usage Examples

### Display Digital Menu Board
```bash
# Get only available items for customer display
curl -X GET http://localhost:8080/api/menu/available
```

### Category Navigation
```bash
# Get all categories for menu navigation
curl -X GET http://localhost:8080/api/menu/categories/available

# Get specific category items
curl -X GET http://localhost:8080/api/menu/categories/1/items/available
```

### Search Functionality
```bash
# Search for items containing "chicken"
curl -X GET "http://localhost:8080/api/menu/search?q=chicken&availableOnly=true"

# Filter by price range
curl -X GET "http://localhost:8080/api/menu/items/price-range?minPrice=10.00&maxPrice=20.00"
```

### Management Dashboard
```bash
# Get complete menu overview for management
curl -X GET http://localhost:8080/api/menu/overview

# Get detailed statistics
curl -X GET http://localhost:8080/api/menu/statistics
```

---

## Frontend Integration Examples

### React/JavaScript Integration
```javascript
// Get available menu for customer display
const getAvailableMenu = async () => {
    const response = await fetch('/api/menu/available');
    const menu = await response.json();
    return menu;
};

// Search menu items
const searchMenu = async (searchTerm) => {
    const response = await fetch(`/api/menu/search?q=${encodeURIComponent(searchTerm)}&availableOnly=true`);
    const results = await response.json();
    return results.items;
};

// Get items by category for category filtering
const getItemsByCategory = async (categoryId) => {
    const response = await fetch(`/api/menu/categories/${categoryId}/items/available`);
    const items = await response.json();
    return items;
};

// Get menu overview for dashboard
const getMenuOverview = async () => {
    const response = await fetch('/api/menu/overview');
    const overview = await response.json();
    return overview;
};
```

### Menu Display Component Example
```javascript
// React component for displaying menu
const MenuDisplay = () => {
    const [menu, setMenu] = useState(null);
    const [selectedCategory, setSelectedCategory] = useState(null);
    
    useEffect(() => {
        // Load available menu on component mount
        getAvailableMenu()
            .then(setMenu)
            .catch(console.error);
    }, []);
    
    const handleCategorySelect = (categoryId) => {
        setSelectedCategory(categoryId);
        // Load items for selected category
        getItemsByCategory(categoryId)
            .then(items => {
                // Update display with category items
                console.log('Category items:', items);
            });
    };
    
    return (
        <div className="menu-display">
            {menu && (
                <>
                    <div className="categories">
                        {menu.categories.map(category => (
                            <button
                                key={category.categoryId}
                                onClick={() => handleCategorySelect(category.categoryId)}
                                className={selectedCategory === category.categoryId ? 'active' : ''}
                            >
                                {category.categoryName} ({category.availableItemCount})
                            </button>
                        ))}
                    </div>
                    <div className="menu-items">
                        {/* Display menu items here */}
                    </div>
                </>
            )}
        </div>
    );
};
```

---

## Error Responses

### 404 Not Found
```json
{
    "error": "Category not found",
    "message": "Category not found with ID: 999",
    "timestamp": "2023-10-15T15:30:00"
}
```

### 400 Bad Request
```json
{
    "error": "Invalid input",
    "message": "Search term is required",
    "timestamp": "2023-10-15T15:30:00"
}
```

### 500 Internal Server Error
```json
{
    "error": "Internal error",
    "message": "Failed to retrieve menu",
    "timestamp": "2023-10-15T15:30:00"
}
```

---

## Use Cases

### Customer-Facing Applications
1. **Digital Menu Boards** - Display available items with prices
2. **Mobile Ordering Apps** - Browse menu by category
3. **Table-Side Tablets** - Interactive menu with search
4. **Website Menu** - Online menu display with filtering

### Management Applications
1. **Menu Management Dashboard** - Overview of all items and categories
2. **Availability Management** - Monitor which items are available
3. **Pricing Analysis** - View price ranges and statistics
4. **Inventory Planning** - Track popular categories and items

### Integration Scenarios
1. **POS Systems** - Get current menu for order taking
2. **Kitchen Display** - Show item details and preparation info
3. **Inventory Systems** - Check which items are currently available
4. **Reporting Systems** - Menu statistics and analysis

---

## Performance Considerations

1. **Caching** - Consider caching frequently accessed endpoints like `/available` and `/categories`
2. **Database Indexing** - Ensure indexes on `availability_status`, `category_id`, and `price` fields
3. **Lazy Loading** - Menu items within categories are loaded on demand
4. **Search Optimization** - Search queries are optimized for performance

---

## Security Notes

1. **Read-Only Operations** - All endpoints are read-only for customer safety
2. **Rate Limiting** - Consider implementing rate limiting for search endpoints
3. **Input Validation** - All search terms and parameters are validated
4. **CORS Configuration** - Configure CORS settings for your frontend domains

This Menu API provides comprehensive functionality for displaying restaurant menus to customers and managing menu information for staff. The API is designed to be flexible, performant, and easy to integrate with various frontend applications.
