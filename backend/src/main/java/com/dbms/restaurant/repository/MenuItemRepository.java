package com.dbms.restaurant.repository;

import com.dbms.restaurant.models.MenuItem;
import com.dbms.restaurant.models.MenuCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
    List<MenuItem> findByMenuCategory(MenuCategory menuCategory);
    List<MenuItem> findByAvailabilityStatus(Boolean availabilityStatus);
    List<MenuItem> findByNameContainingIgnoreCase(String name);
    List<MenuItem> findByMenuCategoryCategoryName(String categoryName);
}
