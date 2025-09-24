package com.dbms.restaurant.repository;

import com.dbms.restaurant.models.MenuItem;
import com.dbms.restaurant.models.MenuCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
    List<MenuItem> findByMenuCategory(MenuCategory menuCategory);
    List<MenuItem> findByAvailabilityStatus(Boolean availabilityStatus);
    List<MenuItem> findByNameContainingIgnoreCase(String name);
    List<MenuItem> findByMenuCategoryCategoryName(String categoryName);
    
    // Find available items by category
    List<MenuItem> findByMenuCategoryAndAvailabilityStatus(MenuCategory menuCategory, Boolean availabilityStatus);
    
    // Find items by category name and availability
    List<MenuItem> findByMenuCategoryCategoryNameAndAvailabilityStatus(String categoryName, Boolean availabilityStatus);
    
    // Find all available items ordered by category and name
    @Query("SELECT mi FROM MenuItem mi WHERE mi.availabilityStatus = true ORDER BY mi.menuCategory.categoryName ASC, mi.name ASC")
    List<MenuItem> findAllAvailableItemsOrderedByCategoryAndName();
    
    // Find all items ordered by category and name
    @Query("SELECT mi FROM MenuItem mi ORDER BY mi.menuCategory.categoryName ASC, mi.name ASC")
    List<MenuItem> findAllItemsOrderedByCategoryAndName();
    
    // Find items by price range
    List<MenuItem> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);
    
    // Find items by price range and availability
    List<MenuItem> findByPriceBetweenAndAvailabilityStatus(BigDecimal minPrice, BigDecimal maxPrice, Boolean availabilityStatus);
    
    // Get minimum and maximum prices
    @Query("SELECT MIN(mi.price) FROM MenuItem mi WHERE mi.availabilityStatus = true")
    BigDecimal findMinPrice();
    
    @Query("SELECT MAX(mi.price) FROM MenuItem mi WHERE mi.availabilityStatus = true")
    BigDecimal findMaxPrice();
    
    // Count items by category
    Long countByMenuCategory(MenuCategory menuCategory);
    
    // Count available items by category
    Long countByMenuCategoryAndAvailabilityStatus(MenuCategory menuCategory, Boolean availabilityStatus);
    
    // Search items by name or description
    @Query("SELECT mi FROM MenuItem mi WHERE (LOWER(mi.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(mi.description) LIKE LOWER(CONCAT('%', :searchTerm, '%'))) AND mi.availabilityStatus = :available")
    List<MenuItem> searchByNameOrDescription(@Param("searchTerm") String searchTerm, @Param("available") Boolean available);
}
