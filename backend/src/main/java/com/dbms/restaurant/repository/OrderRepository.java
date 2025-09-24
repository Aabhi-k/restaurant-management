package com.dbms.restaurant.repository;

import com.dbms.restaurant.models.Order;
import com.dbms.restaurant.models.Order.OrderStatus;
import com.dbms.restaurant.models.DiningTable;
import com.dbms.restaurant.models.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByStatus(OrderStatus status);
    List<Order> findByDiningTable(DiningTable diningTable);
    List<Order> findByStaff(Staff staff);
    List<Order> findByOrderTimeBetween(LocalDateTime startTime, LocalDateTime endTime);
    
    // Find active orders for a specific table
    List<Order> findByDiningTableAndStatusIn(DiningTable diningTable, List<OrderStatus> statuses);
    
    // Find orders for a specific table ordered by order time
    List<Order> findByDiningTableOrderByOrderTimeDesc(DiningTable diningTable);
    
    // Find active orders for a specific table ID
    @Query("SELECT o FROM Order o WHERE o.diningTable.tableId = :tableId AND o.status IN ('PENDING', 'PREPARING')")
    List<Order> findActiveOrdersByTableId(@Param("tableId") Long tableId);
    
    // Find all active orders across all tables
    @Query("SELECT o FROM Order o WHERE o.status IN ('PENDING', 'PREPARING') ORDER BY o.orderTime ASC")
    List<Order> findAllActiveOrders();
    
    // Get the most recent order for a table
    @Query("SELECT o FROM Order o WHERE o.diningTable.tableId = :tableId ORDER BY o.orderTime DESC LIMIT 1")
    Order findMostRecentOrderByTableId(@Param("tableId") Long tableId);
    
    // Count orders by status for a specific table
    @Query("SELECT COUNT(o) FROM Order o WHERE o.diningTable.tableId = :tableId AND o.status = :status")
    Long countOrdersByTableIdAndStatus(@Param("tableId") Long tableId, @Param("status") OrderStatus status);
    
    // Find orders by date range and table
    List<Order> findByDiningTableAndOrderTimeBetween(DiningTable diningTable, LocalDateTime startTime, LocalDateTime endTime);
}
