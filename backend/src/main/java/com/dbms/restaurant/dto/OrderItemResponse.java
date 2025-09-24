package com.dbms.restaurant.dto;

import java.math.BigDecimal;

public class OrderItemResponse {
    
    private Long orderItemId;
    private Long menuItemId;
    private String menuItemName;
    private String menuItemDescription;
    private Integer quantity;
    private BigDecimal itemPriceAtOrderTime;
    private BigDecimal totalPrice;
    private String categoryName;
    private String specialInstructions;
    private String itemStatus;
    
    public OrderItemResponse() {}
    
    public OrderItemResponse(Long orderItemId, Long menuItemId, String menuItemName, 
                           Integer quantity, BigDecimal itemPriceAtOrderTime) {
        this.orderItemId = orderItemId;
        this.menuItemId = menuItemId;
        this.menuItemName = menuItemName;
        this.quantity = quantity;
        this.itemPriceAtOrderTime = itemPriceAtOrderTime;
        this.totalPrice = itemPriceAtOrderTime.multiply(new BigDecimal(quantity));
    }
    
    public Long getOrderItemId() {
        return orderItemId;
    }
    
    public void setOrderItemId(Long orderItemId) {
        this.orderItemId = orderItemId;
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
    
    public Integer getQuantity() {
        return quantity;
    }
    
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    
    public BigDecimal getItemPriceAtOrderTime() {
        return itemPriceAtOrderTime;
    }
    
    public void setItemPriceAtOrderTime(BigDecimal itemPriceAtOrderTime) {
        this.itemPriceAtOrderTime = itemPriceAtOrderTime;
    }
    
    public BigDecimal getTotalPrice() {
        return totalPrice;
    }
    
    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
    
    public String getCategoryName() {
        return categoryName;
    }
    
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    
    public String getSpecialInstructions() {
        return specialInstructions;
    }
    
    public void setSpecialInstructions(String specialInstructions) {
        this.specialInstructions = specialInstructions;
    }
    
    public String getItemStatus() {
        return itemStatus;
    }
    
    public void setItemStatus(String itemStatus) {
        this.itemStatus = itemStatus;
    }
}
