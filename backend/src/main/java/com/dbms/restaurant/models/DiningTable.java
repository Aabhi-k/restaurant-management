package com.dbms.restaurant.models;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "dining_table")
public class DiningTable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "table_id")
    private Long tableId;
    
    @Column(name = "table_number", nullable = false, unique = true)
    private Integer tableNumber;
    
    @Column(name = "seating_capacity", nullable = false)
    private Integer seatingCapacity;
    
    @Column(name = "location", length = 50)
    private String location;
    
    // One-to-Many relationship with Order
    @OneToMany(mappedBy = "diningTable", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Order> orders;
   
    public DiningTable() {}
    
    public DiningTable(Integer tableNumber, Integer seatingCapacity, String location) {
        this.tableNumber = tableNumber;
        this.seatingCapacity = seatingCapacity;
        this.location = location;
    }
    

    public Long getTableId() {
        return tableId;
    }
    
    public void setTableId(Long tableId) {
        this.tableId = tableId;
    }
    
    public Integer getTableNumber() {
        return tableNumber;
    }
    
    public void setTableNumber(Integer tableNumber) {
        this.tableNumber = tableNumber;
    }
    
    public Integer getSeatingCapacity() {
        return seatingCapacity;
    }
    
    public void setSeatingCapacity(Integer seatingCapacity) {
        this.seatingCapacity = seatingCapacity;
    }
    
    public String getLocation() {
        return location;
    }
    
    public void setLocation(String location) {
        this.location = location;
    }
    
    public List<Order> getOrders() {
        return orders;
    }
    
    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
    
}
