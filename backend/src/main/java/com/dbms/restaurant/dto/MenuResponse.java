package com.dbms.restaurant.dto;

import java.math.BigDecimal;
import java.util.List;

public class MenuResponse {
    
    private List<MenuCategoryResponse> categories;
    private Integer totalCategories;
    private Integer totalItems;
    private Integer totalAvailableItems;
    private BigDecimal priceRangeMin;
    private BigDecimal priceRangeMax;
    private String restaurantName;
    private String menuLastUpdated;
    
    public MenuResponse() {}
    
    public MenuResponse(List<MenuCategoryResponse> categories) {
        this.categories = categories;
    }
    
    public List<MenuCategoryResponse> getCategories() {
        return categories;
    }
    
    public void setCategories(List<MenuCategoryResponse> categories) {
        this.categories = categories;
    }
    
    public Integer getTotalCategories() {
        return totalCategories;
    }
    
    public void setTotalCategories(Integer totalCategories) {
        this.totalCategories = totalCategories;
    }
    
    public Integer getTotalItems() {
        return totalItems;
    }
    
    public void setTotalItems(Integer totalItems) {
        this.totalItems = totalItems;
    }
    
    public Integer getTotalAvailableItems() {
        return totalAvailableItems;
    }
    
    public void setTotalAvailableItems(Integer totalAvailableItems) {
        this.totalAvailableItems = totalAvailableItems;
    }
    
    public BigDecimal getPriceRangeMin() {
        return priceRangeMin;
    }
    
    public void setPriceRangeMin(BigDecimal priceRangeMin) {
        this.priceRangeMin = priceRangeMin;
    }
    
    public BigDecimal getPriceRangeMax() {
        return priceRangeMax;
    }
    
    public void setPriceRangeMax(BigDecimal priceRangeMax) {
        this.priceRangeMax = priceRangeMax;
    }
    
    public String getRestaurantName() {
        return restaurantName;
    }
    
    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }
    
    public String getMenuLastUpdated() {
        return menuLastUpdated;
    }
    
    public void setMenuLastUpdated(String menuLastUpdated) {
        this.menuLastUpdated = menuLastUpdated;
    }
}
