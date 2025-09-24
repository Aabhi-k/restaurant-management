package com.dbms.restaurant.dto;

import java.util.List;

public class MenuCategoryResponse {
    
    private Long categoryId;
    private String categoryName;
    private String categoryDescription;
    private Integer itemCount;
    private Integer availableItemCount;
    private List<MenuItemResponse> menuItems;
    private String iconUrl;
    
    public MenuCategoryResponse() {}
    
    public MenuCategoryResponse(Long categoryId, String categoryName, String categoryDescription) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.categoryDescription = categoryDescription;
    }
    
    public Long getCategoryId() {
        return categoryId;
    }
    
    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
    
    public String getCategoryName() {
        return categoryName;
    }
    
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    
    public String getCategoryDescription() {
        return categoryDescription;
    }
    
    public void setCategoryDescription(String categoryDescription) {
        this.categoryDescription = categoryDescription;
    }
    
    public Integer getItemCount() {
        return itemCount;
    }
    
    public void setItemCount(Integer itemCount) {
        this.itemCount = itemCount;
    }
    
    public Integer getAvailableItemCount() {
        return availableItemCount;
    }
    
    public void setAvailableItemCount(Integer availableItemCount) {
        this.availableItemCount = availableItemCount;
    }
    
    public List<MenuItemResponse> getMenuItems() {
        return menuItems;
    }
    
    public void setMenuItems(List<MenuItemResponse> menuItems) {
        this.menuItems = menuItems;
    }
    
    public String getIconUrl() {
        return iconUrl;
    }
    
    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }
}
