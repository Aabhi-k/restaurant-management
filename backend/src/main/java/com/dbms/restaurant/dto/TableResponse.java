package com.dbms.restaurant.dto;

import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.util.List;

public class TableResponse {
    
    private Long tableId;
    private Integer tableNumber;
    private Integer seatingCapacity;
    private String location;
    private String status;
    private Integer activeOrdersCount;
    private BigDecimal totalActiveAmount;
    private List<OrderSummaryResponse> activeOrders;
    private LocalDateTime lastOrderTime;
    
    public TableResponse() {}
    
    public TableResponse(Long tableId, Integer tableNumber, Integer seatingCapacity, String location) {
        this.tableId = tableId;
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
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public Integer getActiveOrdersCount() {
        return activeOrdersCount;
    }
    
    public void setActiveOrdersCount(Integer activeOrdersCount) {
        this.activeOrdersCount = activeOrdersCount;
    }
    
    public BigDecimal getTotalActiveAmount() {
        return totalActiveAmount;
    }
    
    public void setTotalActiveAmount(BigDecimal totalActiveAmount) {
        this.totalActiveAmount = totalActiveAmount;
    }
    
    public List<OrderSummaryResponse> getActiveOrders() {
        return activeOrders;
    }
    
    public void setActiveOrders(List<OrderSummaryResponse> activeOrders) {
        this.activeOrders = activeOrders;
    }
    
    public LocalDateTime getLastOrderTime() {
        return lastOrderTime;
    }
    
    public void setLastOrderTime(LocalDateTime lastOrderTime) {
        this.lastOrderTime = lastOrderTime;
    }
}
