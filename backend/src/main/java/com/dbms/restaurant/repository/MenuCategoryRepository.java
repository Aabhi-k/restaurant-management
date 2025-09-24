package com.dbms.restaurant.repository;

import com.dbms.restaurant.models.MenuCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MenuCategoryRepository extends JpaRepository<MenuCategory, Long> {
    Optional<MenuCategory> findByCategoryName(String categoryName);
    
    // Find all categories ordered by name
    List<MenuCategory> findAllByOrderByCategoryNameAsc();
    
    // Find categories that have available items
    @Query("SELECT DISTINCT mc FROM MenuCategory mc JOIN mc.menuItems mi WHERE mi.availabilityStatus = true ORDER BY mc.categoryName")
    List<MenuCategory> findCategoriesWithAvailableItems();
    
    // Find categories by name containing (case insensitive)
    List<MenuCategory> findByCategoryNameContainingIgnoreCase(String categoryName);
}
