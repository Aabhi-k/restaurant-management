package com.dbms.restaurant.Service;

import com.dbms.restaurant.dto.MenuCategoryResponse;
import com.dbms.restaurant.dto.MenuItemResponse;
import com.dbms.restaurant.dto.MenuResponse;
import com.dbms.restaurant.models.MenuCategory;
import com.dbms.restaurant.models.MenuItem;
import com.dbms.restaurant.repository.MenuCategoryRepository;
import com.dbms.restaurant.repository.MenuItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class MenuServiceImpl implements MenuService {
    
    @Autowired
    private MenuItemRepository menuItemRepository;
    
    @Autowired
    private MenuCategoryRepository menuCategoryRepository;
    
    @Override
    public MenuResponse getCompleteMenu() {
        List<MenuCategory> categories = menuCategoryRepository.findAllByOrderByCategoryNameAsc();
        List<MenuCategoryResponse> categoryResponses = categories.stream()
                .map(category -> convertToCategoryResponseWithItems(category, false))
                .collect(Collectors.toList());
        
        return buildMenuResponse(categoryResponses);
    }
    
    @Override
    public MenuResponse getAvailableMenu() {
        List<MenuCategory> categories = menuCategoryRepository.findCategoriesWithAvailableItems();
        List<MenuCategoryResponse> categoryResponses = categories.stream()
                .map(category -> convertToCategoryResponseWithItems(category, true))
                .collect(Collectors.toList());
        
        return buildMenuResponse(categoryResponses);
    }
    
    @Override
    public List<MenuCategoryResponse> getAllCategories() {
        List<MenuCategory> categories = menuCategoryRepository.findAllByOrderByCategoryNameAsc();
        return categories.stream()
                .map(this::convertToCategoryResponseSummary)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<MenuCategoryResponse> getCategoriesWithAvailableItems() {
        List<MenuCategory> categories = menuCategoryRepository.findCategoriesWithAvailableItems();
        return categories.stream()
                .map(this::convertToCategoryResponseSummary)
                .collect(Collectors.toList());
    }
    
    @Override
    public MenuCategoryResponse getCategoryWithItems(Long categoryId) {
        MenuCategory category = menuCategoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found with ID: " + categoryId));
        return convertToCategoryResponseWithItems(category, false);
    }
    
    @Override
    public MenuCategoryResponse getCategoryByNameWithItems(String categoryName) {
        MenuCategory category = menuCategoryRepository.findByCategoryName(categoryName)
                .orElseThrow(() -> new RuntimeException("Category not found with name: " + categoryName));
        return convertToCategoryResponseWithItems(category, false);
    }
    
    @Override
    public List<MenuItemResponse> getAllMenuItems() {
        List<MenuItem> menuItems = menuItemRepository.findAllItemsOrderedByCategoryAndName();
        return menuItems.stream()
                .map(this::convertToMenuItemResponse)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<MenuItemResponse> getAvailableMenuItems() {
        List<MenuItem> menuItems = menuItemRepository.findAllAvailableItemsOrderedByCategoryAndName();
        return menuItems.stream()
                .map(this::convertToMenuItemResponse)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<MenuItemResponse> getMenuItemsByCategory(Long categoryId) {
        MenuCategory category = menuCategoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found with ID: " + categoryId));
        List<MenuItem> menuItems = menuItemRepository.findByMenuCategory(category);
        return menuItems.stream()
                .map(this::convertToMenuItemResponse)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<MenuItemResponse> getAvailableMenuItemsByCategory(Long categoryId) {
        MenuCategory category = menuCategoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found with ID: " + categoryId));
        List<MenuItem> menuItems = menuItemRepository.findByMenuCategoryAndAvailabilityStatus(category, true);
        return menuItems.stream()
                .map(this::convertToMenuItemResponse)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<MenuItemResponse> getMenuItemsByCategoryName(String categoryName) {
        List<MenuItem> menuItems = menuItemRepository.findByMenuCategoryCategoryName(categoryName);
        return menuItems.stream()
                .map(this::convertToMenuItemResponse)
                .collect(Collectors.toList());
    }
    
    @Override
    public MenuItemResponse getMenuItemById(Long itemId) {
        MenuItem menuItem = menuItemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Menu item not found with ID: " + itemId));
        return convertToMenuItemResponse(menuItem);
    }
    
    @Override
    public List<MenuItemResponse> searchMenuItems(String searchTerm, Boolean availableOnly) {
        Boolean availability = availableOnly != null ? availableOnly : true;
        List<MenuItem> menuItems = menuItemRepository.searchByNameOrDescription(searchTerm, availability);
        return menuItems.stream()
                .map(this::convertToMenuItemResponse)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<MenuItemResponse> getMenuItemsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice, Boolean availableOnly) {
        List<MenuItem> menuItems;
        if (availableOnly != null && availableOnly) {
            menuItems = menuItemRepository.findByPriceBetweenAndAvailabilityStatus(minPrice, maxPrice, true);
        } else {
            menuItems = menuItemRepository.findByPriceBetween(minPrice, maxPrice);
        }
        return menuItems.stream()
                .map(this::convertToMenuItemResponse)
                .collect(Collectors.toList());
    }
    
    @Override
    public MenuResponse getMenuStatistics() {
        List<MenuCategory> allCategories = menuCategoryRepository.findAll();
        List<MenuItem> allItems = menuItemRepository.findAll();
        List<MenuItem> availableItems = menuItemRepository.findByAvailabilityStatus(true);
        
        MenuResponse response = new MenuResponse();
        response.setTotalCategories(allCategories.size());
        response.setTotalItems(allItems.size());
        response.setTotalAvailableItems(availableItems.size());
        
        // Set price range
        BigDecimal minPrice = menuItemRepository.findMinPrice();
        BigDecimal maxPrice = menuItemRepository.findMaxPrice();
        response.setPriceRangeMin(minPrice != null ? minPrice : BigDecimal.ZERO);
        response.setPriceRangeMax(maxPrice != null ? maxPrice : BigDecimal.ZERO);
        
        response.setMenuLastUpdated(LocalDateTime.now().toString());
        
        return response;
    }
    
    // Helper method to convert MenuItem to MenuItemResponse
    private MenuItemResponse convertToMenuItemResponse(MenuItem menuItem) {
        MenuItemResponse response = new MenuItemResponse();
        response.setItemId(menuItem.getItemId());
        response.setName(menuItem.getName());
        response.setDescription(menuItem.getDescription());
        response.setPrice(menuItem.getPrice());
        response.setAvailabilityStatus(menuItem.getAvailabilityStatus());
        
        if (menuItem.getMenuCategory() != null) {
            response.setCategoryName(menuItem.getMenuCategory().getCategoryName());
            response.setCategoryId(menuItem.getMenuCategory().getCategoryId());
        }
        
        return response;
    }
    
    // Helper method to convert MenuCategory to MenuCategoryResponse with items
    private MenuCategoryResponse convertToCategoryResponseWithItems(MenuCategory category, boolean availableOnly) {
        MenuCategoryResponse response = new MenuCategoryResponse();
        response.setCategoryId(category.getCategoryId());
        response.setCategoryName(category.getCategoryName());
        response.setCategoryDescription(category.getCategoryDescription());
        
        List<MenuItem> menuItems;
        if (availableOnly) {
            menuItems = menuItemRepository.findByMenuCategoryAndAvailabilityStatus(category, true);
        } else {
            menuItems = menuItemRepository.findByMenuCategory(category);
        }
        
        List<MenuItemResponse> menuItemResponses = menuItems.stream()
                .map(this::convertToMenuItemResponse)
                .collect(Collectors.toList());
        
        response.setMenuItems(menuItemResponses);
        response.setItemCount(menuItems.size());
        
        Long availableCount = menuItemRepository.countByMenuCategoryAndAvailabilityStatus(category, true);
        response.setAvailableItemCount(availableCount.intValue());
        
        return response;
    }
    
    // Helper method to convert MenuCategory to MenuCategoryResponse summary only
    private MenuCategoryResponse convertToCategoryResponseSummary(MenuCategory category) {
        MenuCategoryResponse response = new MenuCategoryResponse();
        response.setCategoryId(category.getCategoryId());
        response.setCategoryName(category.getCategoryName());
        response.setCategoryDescription(category.getCategoryDescription());
        
        Long totalCount = menuItemRepository.countByMenuCategory(category);
        Long availableCount = menuItemRepository.countByMenuCategoryAndAvailabilityStatus(category, true);
        
        response.setItemCount(totalCount.intValue());
        response.setAvailableItemCount(availableCount.intValue());
        
        return response;
    }
    
    // Helper method to build complete MenuResponse
    private MenuResponse buildMenuResponse(List<MenuCategoryResponse> categoryResponses) {
        MenuResponse response = new MenuResponse();
        response.setCategories(categoryResponses);
        response.setTotalCategories(categoryResponses.size());
        
        int totalItems = categoryResponses.stream()
                .mapToInt(cat -> cat.getItemCount() != null ? cat.getItemCount() : 0)
                .sum();
        int totalAvailableItems = categoryResponses.stream()
                .mapToInt(cat -> cat.getAvailableItemCount() != null ? cat.getAvailableItemCount() : 0)
                .sum();
        
        response.setTotalItems(totalItems);
        response.setTotalAvailableItems(totalAvailableItems);
        
        // Set price range
        BigDecimal minPrice = menuItemRepository.findMinPrice();
        BigDecimal maxPrice = menuItemRepository.findMaxPrice();
        response.setPriceRangeMin(minPrice != null ? minPrice : BigDecimal.ZERO);
        response.setPriceRangeMax(maxPrice != null ? maxPrice : BigDecimal.ZERO);
        
        response.setMenuLastUpdated(LocalDateTime.now().toString());
        
        return response;
    }
}
