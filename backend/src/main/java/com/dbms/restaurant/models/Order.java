package com.dbms.restaurant.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders") 
public class Order {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId;
    
    @Column(name = "order_time", nullable = false)
    private LocalDateTime orderTime;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private OrderStatus status;
    
    // Many-to-One relationship with DiningTable
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "table_id", nullable = false)
    private DiningTable diningTable;
    
    // Many-to-One relationship with Staff
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "staff_id", nullable = false)
    private Staff staff;
    
    // One-to-Many relationship with OrderItem
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderItem> orderItems;
    
    // One-to-One relationship with Billing
    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Billing billing;
    
    // Enum for Order Status
    public enum OrderStatus {
        PENDING, PREPARING, SERVED, CLOSED
    }
    
   
    public Order() {
        this.orderTime = LocalDateTime.now();
        this.status = OrderStatus.PENDING;
    }
    
    public Order(DiningTable diningTable, Staff staff) {
        this();
        this.diningTable = diningTable;
        this.staff = staff;
    }
    
    
    public Long getOrderId() {
        return orderId;
    }
    
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
    
    public LocalDateTime getOrderTime() {
        return orderTime;
    }
    
    public void setOrderTime(LocalDateTime orderTime) {
        this.orderTime = orderTime;
    }
    
    public OrderStatus getStatus() {
        return status;
    }
    
    public void setStatus(OrderStatus status) {
        this.status = status;
    }
    
    public DiningTable getDiningTable() {
        return diningTable;
    }
    
    public void setDiningTable(DiningTable diningTable) {
        this.diningTable = diningTable;
    }
    
    public Staff getStaff() {
        return staff;
    }
    
    public void setStaff(Staff staff) {
        this.staff = staff;
    }
    
    public List<OrderItem> getOrderItems() {
        return orderItems;
    }
    
    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
    
    public Billing getBilling() {
        return billing;
    }
    
    public void setBilling(Billing billing) {
        this.billing = billing;
    }
    
   
}
