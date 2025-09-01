package com.dbms.restaurant.models;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "menu_item")
public class MenuItem {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long itemId;
    
    @Column(name = "name", nullable = false, length = 100)
    private String name;
    
    @Column(name = "description", length = 500)
    private String description;
    
    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;
    
    @Column(name = "availability_status", nullable = false)
    private Boolean availabilityStatus;
    
    // Many-to-One relationship with MenuCategory
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", nullable = false)
    private MenuCategory menuCategory;
    
    // One-to-Many relationship with OrderItem
    @OneToMany(mappedBy = "menuItem", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderItem> orderItems;
    
   
    public MenuItem() {}
    
    public MenuItem(String name, String description, BigDecimal price, Boolean availabilityStatus, MenuCategory menuCategory) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.availabilityStatus = availabilityStatus;
        this.menuCategory = menuCategory;
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
    
    public MenuCategory getMenuCategory() {
        return menuCategory;
    }
    
    public void setMenuCategory(MenuCategory menuCategory) {
        this.menuCategory = menuCategory;
    }
    
    public List<OrderItem> getOrderItems() {
        return orderItems;
    }
    
    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
   
}
