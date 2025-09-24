package com.dbms.restaurant.Service;

import com.dbms.restaurant.dto.MenuCategoryResponse;
import com.dbms.restaurant.dto.MenuItemResponse;
import com.dbms.restaurant.dto.MenuResponse;

import java.math.BigDecimal;
import java.util.List;

public interface MenuService {
    
    MenuResponse getCompleteMenu();
    
    MenuResponse getAvailableMenu();
    
    List<MenuCategoryResponse> getAllCategories();
    
    List<MenuCategoryResponse> getCategoriesWithAvailableItems();
    
    MenuCategoryResponse getCategoryWithItems(Long categoryId);
    
    MenuCategoryResponse getCategoryByNameWithItems(String categoryName);
    
    List<MenuItemResponse> getAllMenuItems();
    
    List<MenuItemResponse> getAvailableMenuItems();
    
    List<MenuItemResponse> getMenuItemsByCategory(Long categoryId);
    
    List<MenuItemResponse> getAvailableMenuItemsByCategory(Long categoryId);
    
    List<MenuItemResponse> getMenuItemsByCategoryName(String categoryName);
    
    MenuItemResponse getMenuItemById(Long itemId);
    
    List<MenuItemResponse> searchMenuItems(String searchTerm, Boolean availableOnly);
    
    List<MenuItemResponse> getMenuItemsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice, Boolean availableOnly);
    
    MenuResponse getMenuStatistics();
}
