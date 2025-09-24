package com.dbms.restaurant.models;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "order_item")
public class OrderItem {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Long orderItemId;
    
    @Column(name = "quantity", nullable = false)
    private Integer quantity;
    
    @Column(name = "item_price_at_order_time", nullable = false, precision = 10, scale = 2)
    private BigDecimal itemPriceAtOrderTime;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "item_id", nullable = false)
    private MenuItem menuItem;
    

    public OrderItem() {}
    
    public OrderItem(Integer quantity, BigDecimal itemPriceAtOrderTime, Order order, MenuItem menuItem) {
        this.quantity = quantity;
        this.itemPriceAtOrderTime = itemPriceAtOrderTime;
        this.order = order;
        this.menuItem = menuItem;
    }
    
    public BigDecimal getTotalPrice() {
        return itemPriceAtOrderTime.multiply(new BigDecimal(quantity));
    }
    
    public Long getOrderItemId() {
        return orderItemId;
    }
    
    public void setOrderItemId(Long orderItemId) {
        this.orderItemId = orderItemId;
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
    
    public Order getOrder() {
        return order;
    }
    
    public void setOrder(Order order) {
        this.order = order;
    }
    
    public MenuItem getMenuItem() {
        return menuItem;
    }
    
    public void setMenuItem(MenuItem menuItem) {
        this.menuItem = menuItem;
    }
}
