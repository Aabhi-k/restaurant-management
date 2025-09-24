package com.dbms.restaurant.repository;

import com.dbms.restaurant.models.DiningTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface DiningTableRepository extends JpaRepository<DiningTable, Long> {
    Optional<DiningTable> findByTableNumber(Integer tableNumber);
    List<DiningTable> findByLocation(String location);
    List<DiningTable> findBySeatingCapacityGreaterThanEqual(Integer capacity);
    
    // Find tables with active orders (orders that are not SERVED or CLOSED)
    @Query("SELECT DISTINCT dt FROM DiningTable dt JOIN dt.orders o WHERE o.status IN ('PENDING', 'PREPARING')")
    List<DiningTable> findTablesWithActiveOrders();
    
    // Find available tables (tables without active orders)
    @Query("SELECT dt FROM DiningTable dt WHERE dt.tableId NOT IN " +
           "(SELECT DISTINCT o.diningTable.tableId FROM Order o WHERE o.status IN ('PENDING', 'PREPARING'))")
    List<DiningTable> findAvailableTables();
    
    // Get tables ordered by table number
    List<DiningTable> findAllByOrderByTableNumberAsc();
    
    // Count active orders for a specific table
    @Query("SELECT COUNT(o) FROM Order o WHERE o.diningTable.tableId = :tableId AND o.status IN ('PENDING', 'PREPARING')")
    Long countActiveOrdersForTable(@Param("tableId") Long tableId);
}
