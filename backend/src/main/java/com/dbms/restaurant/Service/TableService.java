package com.dbms.restaurant.Service;

import com.dbms.restaurant.dto.OrderSummaryResponse;
import com.dbms.restaurant.dto.TableResponse;

import java.time.LocalDate;
import java.util.List;

public interface TableService {
    
    List<TableResponse> getAllTablesWithOrders();
    
    TableResponse getTableWithOrders(Long tableId);
    
    TableResponse getTableByNumberWithOrders(Integer tableNumber);
    
    List<TableResponse> getAvailableTables();
    
    List<TableResponse> getOccupiedTables();
    
    List<OrderSummaryResponse> getActiveOrdersForTable(Long tableId);
    
    List<OrderSummaryResponse> getAllOrdersForTable(Long tableId);
    
    List<OrderSummaryResponse> getOrdersForTableByDateRange(Long tableId, LocalDate startDate, LocalDate endDate);
    
    List<TableResponse> getTablesSummary();
    
    List<TableResponse> getTablesByLocation(String location);
    
    boolean isTableAvailable(Long tableId);
    
    String getTableStatus(Long tableId);
}
