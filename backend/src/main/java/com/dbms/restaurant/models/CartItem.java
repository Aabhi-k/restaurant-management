package com.dbms.restaurant.models;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "cart_item")
public class CartItem {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_item_id")
    private Long cartItemId;
    
    @Column(name = "quantity", nullable = false)
    private Integer quantity;
    
    @Column(name = "item_price_at_time", nullable = false, precision = 10, scale = 2)
    private BigDecimal itemPriceAtTime;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "item_id", nullable = false)
    private MenuItem menuItem;
    
    @Column(name = "special_instructions", length = 500)
    private String specialInstructions;
    
    public CartItem() {}
    
    public CartItem(Integer quantity, MenuItem menuItem, Cart cart) {
        this.quantity = quantity;
        this.menuItem = menuItem;
        this.cart = cart;
        this.itemPriceAtTime = menuItem.getPrice();
    }
    
    public CartItem(Integer quantity, MenuItem menuItem, Cart cart, String specialInstructions) {
        this(quantity, menuItem, cart);
        this.specialInstructions = specialInstructions;
    }
    
    public BigDecimal getTotalPrice() {
        return itemPriceAtTime.multiply(new BigDecimal(quantity));
    }
    
    public void updateQuantity(Integer newQuantity) {
        if (newQuantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }
        this.quantity = newQuantity;
    }
    
    public void increaseQuantity(Integer amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than 0");
        }
        this.quantity += amount;
    }
    
    public void decreaseQuantity(Integer amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than 0");
        }
        if (this.quantity - amount <= 0) {
            throw new IllegalArgumentException("Cannot decrease quantity below 1");
        }
        this.quantity -= amount;
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
    
    public BigDecimal getItemPriceAtTime() {
        return itemPriceAtTime;
    }
    
    public void setItemPriceAtTime(BigDecimal itemPriceAtTime) {
        this.itemPriceAtTime = itemPriceAtTime;
    }
    
    public Cart getCart() {
        return cart;
    }
    
    public void setCart(Cart cart) {
        this.cart = cart;
    }
    
    public MenuItem getMenuItem() {
        return menuItem;
    }
    
    public void setMenuItem(MenuItem menuItem) {
        this.menuItem = menuItem;
    }
    
    public String getSpecialInstructions() {
        return specialInstructions;
    }
    
    public void setSpecialInstructions(String specialInstructions) {
        this.specialInstructions = specialInstructions;
    }
}
