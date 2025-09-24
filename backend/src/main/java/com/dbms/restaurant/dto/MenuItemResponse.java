package com.dbms.restaurant.dto;

import java.math.BigDecimal;

public class MenuItemResponse {
    
    private Long itemId;
    private String name;
    private String description;
    private BigDecimal price;
    private Boolean availabilityStatus;
    private String categoryName;
    private Long categoryId;
    private String imageUrl;
    private String allergenInfo;
    private Boolean isVegetarian;
    private Boolean isVegan;
    private Boolean isGlutenFree;
    private Integer preparationTime;
    
    public MenuItemResponse() {}
    
    public MenuItemResponse(Long itemId, String name, String description, BigDecimal price, 
                           Boolean availabilityStatus, String categoryName, Long categoryId) {
        this.itemId = itemId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.availabilityStatus = availabilityStatus;
        this.categoryName = categoryName;
        this.categoryId = categoryId;
    }
    
    public Long getItemId() {
        return itemId;
    }
    
    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public BigDecimal getPrice() {
        return price;
    }
    
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    
    public Boolean getAvailabilityStatus() {
        return availabilityStatus;
    }
    
    public void setAvailabilityStatus(Boolean availabilityStatus) {
        this.availabilityStatus = availabilityStatus;
    }
    
    public String getCategoryName() {
        return categoryName;
    }
    
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    
    public Long getCategoryId() {
        return categoryId;
    }
    
    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
    
    public String getImageUrl() {
        return imageUrl;
    }
    
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    
    public String getAllergenInfo() {
        return allergenInfo;
    }
    
    public void setAllergenInfo(String allergenInfo) {
        this.allergenInfo = allergenInfo;
    }
    
    public Boolean getIsVegetarian() {
        return isVegetarian;
    }
    
    public void setIsVegetarian(Boolean isVegetarian) {
        this.isVegetarian = isVegetarian;
    }
    
    public Boolean getIsVegan() {
        return isVegan;
    }
    
    public void setIsVegan(Boolean isVegan) {
        this.isVegan = isVegan;
    }
    
    public Boolean getIsGlutenFree() {
        return isGlutenFree;
    }
    
    public void setIsGlutenFree(Boolean isGlutenFree) {
        this.isGlutenFree = isGlutenFree;
    }
    
    public Integer getPreparationTime() {
        return preparationTime;
    }
    
    public void setPreparationTime(Integer preparationTime) {
        this.preparationTime = preparationTime;
    }
}
