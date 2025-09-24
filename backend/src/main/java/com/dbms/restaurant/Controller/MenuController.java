package com.dbms.restaurant.Controller;

import com.dbms.restaurant.Service.MenuService;
import com.dbms.restaurant.dto.MenuCategoryResponse;
import com.dbms.restaurant.dto.MenuItemResponse;
import com.dbms.restaurant.dto.MenuResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/menu")
public class MenuController {
    
    @Autowired
    private MenuService menuService;
    
    @GetMapping
    public ResponseEntity<?> getCompleteMenu() {
        try {
            MenuResponse menu = menuService.getCompleteMenu();
            return ResponseEntity.ok(menu);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Internal error", "Failed to retrieve complete menu"));
        }
    }
    
    @GetMapping("/available")
    public ResponseEntity<?> getAvailableMenu() {
        try {
            MenuResponse menu = menuService.getAvailableMenu();
            return ResponseEntity.ok(menu);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Internal error", "Failed to retrieve available menu"));
        }
    }
    
    @GetMapping("/categories")
    public ResponseEntity<?> getAllCategories() {
        try {
            List<MenuCategoryResponse> categories = menuService.getAllCategories();
            return ResponseEntity.ok(categories);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Internal error", "Failed to retrieve categories"));
        }
    }
    
    @GetMapping("/categories/available")
    public ResponseEntity<?> getCategoriesWithAvailableItems() {
        try {
            List<MenuCategoryResponse> categories = menuService.getCategoriesWithAvailableItems();
            return ResponseEntity.ok(categories);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Internal error", "Failed to retrieve available categories"));
        }
    }
    
    @GetMapping("/categories/{categoryId}")
    public ResponseEntity<?> getCategoryWithItems(@PathVariable Long categoryId) {
        try {
            MenuCategoryResponse category = menuService.getCategoryWithItems(categoryId);
            return ResponseEntity.ok(category);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(createErrorResponse("Category not found", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Internal error", "Failed to retrieve category"));
        }
    }
    
    @GetMapping("/categories/name/{categoryName}")
    public ResponseEntity<?> getCategoryByNameWithItems(@PathVariable String categoryName) {
        try {
            MenuCategoryResponse category = menuService.getCategoryByNameWithItems(categoryName);
            return ResponseEntity.ok(category);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(createErrorResponse("Category not found", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Internal error", "Failed to retrieve category"));
        }
    }
    
    @GetMapping("/items")
    public ResponseEntity<?> getAllMenuItems() {
        try {
            List<MenuItemResponse> items = menuService.getAllMenuItems();
            return ResponseEntity.ok(items);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Internal error", "Failed to retrieve menu items"));
        }
    }
    
    @GetMapping("/items/available")
    public ResponseEntity<?> getAvailableMenuItems() {
        try {
            List<MenuItemResponse> items = menuService.getAvailableMenuItems();
            return ResponseEntity.ok(items);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Internal error", "Failed to retrieve available menu items"));
        }
    }
    
    @GetMapping("/items/{itemId}")
    public ResponseEntity<?> getMenuItemById(@PathVariable Long itemId) {
        try {
            MenuItemResponse item = menuService.getMenuItemById(itemId);
            return ResponseEntity.ok(item);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(createErrorResponse("Menu item not found", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Internal error", "Failed to retrieve menu item"));
        }
    }
    
    @GetMapping("/categories/{categoryId}/items")
    public ResponseEntity<?> getMenuItemsByCategory(@PathVariable Long categoryId) {
        try {
            List<MenuItemResponse> items = menuService.getMenuItemsByCategory(categoryId);
            return ResponseEntity.ok(items);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(createErrorResponse("Category not found", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Internal error", "Failed to retrieve menu items"));
        }
    }
    
    @GetMapping("/categories/{categoryId}/items/available")
    public ResponseEntity<?> getAvailableMenuItemsByCategory(@PathVariable Long categoryId) {
        try {
            List<MenuItemResponse> items = menuService.getAvailableMenuItemsByCategory(categoryId);
            return ResponseEntity.ok(items);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(createErrorResponse("Category not found", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Internal error", "Failed to retrieve available menu items"));
        }
    }
    
    @GetMapping("/categories/name/{categoryName}/items")
    public ResponseEntity<?> getMenuItemsByCategoryName(@PathVariable String categoryName) {
        try {
            List<MenuItemResponse> items = menuService.getMenuItemsByCategoryName(categoryName);
            return ResponseEntity.ok(items);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Internal error", "Failed to retrieve menu items"));
        }
    }
    
    @GetMapping("/search")
    public ResponseEntity<?> searchMenuItems(
            @RequestParam("q") String searchTerm,
            @RequestParam(value = "availableOnly", defaultValue = "true") Boolean availableOnly) {
        try {
            if (searchTerm == null || searchTerm.trim().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(createErrorResponse("Invalid input", "Search term is required"));
            }
            
            List<MenuItemResponse> items = menuService.searchMenuItems(searchTerm, availableOnly);
            
            Map<String, Object> response = new HashMap<>();
            response.put("searchTerm", searchTerm);
            response.put("availableOnly", availableOnly);
            response.put("totalResults", items.size());
            response.put("items", items);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Internal error", "Failed to search menu items"));
        }
    }
    
    @GetMapping("/items/price-range")
    public ResponseEntity<?> getMenuItemsByPriceRange(
            @RequestParam BigDecimal minPrice,
            @RequestParam BigDecimal maxPrice,
            @RequestParam(value = "availableOnly", defaultValue = "true") Boolean availableOnly) {
        try {
            if (minPrice.compareTo(maxPrice) > 0) {
                return ResponseEntity.badRequest()
                        .body(createErrorResponse("Invalid input", "Minimum price cannot be greater than maximum price"));
            }
            
            List<MenuItemResponse> items = menuService.getMenuItemsByPriceRange(minPrice, maxPrice, availableOnly);
            
            Map<String, Object> response = new HashMap<>();
            response.put("minPrice", minPrice);
            response.put("maxPrice", maxPrice);
            response.put("availableOnly", availableOnly);
            response.put("totalResults", items.size());
            response.put("items", items);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Internal error", "Failed to retrieve menu items by price range"));
        }
    }
    
    @GetMapping("/statistics")
    public ResponseEntity<?> getMenuStatistics() {
        try {
            MenuResponse statistics = menuService.getMenuStatistics();
            return ResponseEntity.ok(statistics);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Internal error", "Failed to retrieve menu statistics"));
        }
    }
    
    @GetMapping("/overview")
    public ResponseEntity<?> getMenuOverview() {
        try {
            List<MenuCategoryResponse> categories = menuService.getAllCategories();
            MenuResponse statistics = menuService.getMenuStatistics();
            
            Map<String, Object> overview = new HashMap<>();
            overview.put("totalCategories", statistics.getTotalCategories());
            overview.put("totalItems", statistics.getTotalItems());
            overview.put("totalAvailableItems", statistics.getTotalAvailableItems());
            overview.put("priceRange", Map.of(
                "min", statistics.getPriceRangeMin(),
                "max", statistics.getPriceRangeMax()
            ));
            overview.put("categories", categories);
            overview.put("lastUpdated", statistics.getMenuLastUpdated());
            
            return ResponseEntity.ok(overview);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Internal error", "Failed to retrieve menu overview"));
        }
    }
    
    private Map<String, String> createErrorResponse(String error, String message) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", error);
        errorResponse.put("message", message);
        errorResponse.put("timestamp", java.time.LocalDateTime.now().toString());
        return errorResponse;
    }
}
