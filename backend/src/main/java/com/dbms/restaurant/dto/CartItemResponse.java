package com.dbms.restaurant.dto;

import java.math.BigDecimal;

public class CartItemResponse {
    
    private Long cartItemId;
    private Long menuItemId;
    private String menuItemName;
    private String menuItemDescription;
    private BigDecimal itemPriceAtTime;
    private Integer quantity;
    private BigDecimal totalPrice;
    private String specialInstructions;
    private String categoryName;
    private Boolean isAvailable;
    
    public CartItemResponse() {}
    
    public CartItemResponse(Long cartItemId, Long menuItemId, String menuItemName, 
                           BigDecimal itemPriceAtTime, Integer quantity, BigDecimal totalPrice) {
        this.cartItemId = cartItemId;
        this.menuItemId = menuItemId;
        this.menuItemName = menuItemName;
        this.itemPriceAtTime = itemPriceAtTime;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }
    
    public Long getCartItemId() {
        return cartItemId;
    }
    
    public void setCartItemId(Long cartItemId) {
        this.cartItemId = cartItemId;
    }
    
    public Long getMenuItemId() {
        return menuItemId;
    }
    
    public void setMenuItemId(Long menuItemId) {
        this.menuItemId = menuItemId;
    }
    
    public String getMenuItemName() {
        return menuItemName;
    }
    
    public void setMenuItemName(String menuItemName) {
        this.menuItemName = menuItemName;
    }
    
    public String getMenuItemDescription() {
        return menuItemDescription;
    }
    
    public void setMenuItemDescription(String menuItemDescription) {
        this.menuItemDescription = menuItemDescription;
    }
    
    public BigDecimal getItemPriceAtTime() {
        return itemPriceAtTime;
    }
    
    public void setItemPriceAtTime(BigDecimal itemPriceAtTime) {
        this.itemPriceAtTime = itemPriceAtTime;
    }
    
    public Integer getQuantity() {
        return quantity;
    }
    
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    
    public BigDecimal getTotalPrice() {
        return totalPrice;
    }
    
    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
    
    public String getSpecialInstructions() {
        return specialInstructions;
    }
    
    public void setSpecialInstructions(String specialInstructions) {
        this.specialInstructions = specialInstructions;
    }
    
    public String getCategoryName() {
        return categoryName;
    }
    
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    
    public Boolean getIsAvailable() {
        return isAvailable;
    }
    
    public void setIsAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable;
    }
}
