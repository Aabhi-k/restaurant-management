package com.dbms.restaurant.dto;

public class UpdateCartItemRequest {
    
    private String sessionId;
    
    private Long cartItemId;
    
    private Integer quantity;
    
    private String specialInstructions;
    
    public UpdateCartItemRequest() {}
    
    public UpdateCartItemRequest(String sessionId, Long cartItemId, Integer quantity) {
        this.sessionId = sessionId;
        this.cartItemId = cartItemId;
        this.quantity = quantity;
    }
    
    public String getSessionId() {
        return sessionId;
    }
    
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
    
    public Long getCartItemId() {
        return cartItemId;
    }
    
    public void setCartItemId(Long cartItemId) {
        this.cartItemId = cartItemId;
    }
    
    public Integer getQuantity() {
        return quantity;
    }
    
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    
    public String getSpecialInstructions() {
        return specialInstructions;
    }
    
    public void setSpecialInstructions(String specialInstructions) {
        this.specialInstructions = specialInstructions;
    }
}
