package com.dbms.restaurant.Service;

import com.dbms.restaurant.dto.OrderItemResponse;
import com.dbms.restaurant.dto.OrderSummaryResponse;
import com.dbms.restaurant.dto.TableResponse;
import com.dbms.restaurant.models.*;
import com.dbms.restaurant.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class TableServiceImpl implements TableService {
    
    @Autowired
    private DiningTableRepository diningTableRepository;
    
    @Autowired
    private OrderRepository orderRepository;
    

    
    @Override
    public List<TableResponse> getAllTablesWithOrders() {
        List<DiningTable> tables = diningTableRepository.findAllByOrderByTableNumberAsc();
        return tables.stream()
                .map(this::convertToTableResponseWithOrders)
                .collect(Collectors.toList());
    }
    
    @Override
    public TableResponse getTableWithOrders(Long tableId) {
        DiningTable table = diningTableRepository.findById(tableId)
                .orElseThrow(() -> new RuntimeException("Table not found with ID: " + tableId));
        return convertToTableResponseWithOrders(table);
    }
    
    @Override
    public TableResponse getTableByNumberWithOrders(Integer tableNumber) {
        DiningTable table = diningTableRepository.findByTableNumber(tableNumber)
                .orElseThrow(() -> new RuntimeException("Table not found with number: " + tableNumber));
        return convertToTableResponseWithOrders(table);
    }
    
    @Override
    public List<TableResponse> getAvailableTables() {
        List<DiningTable> availableTables = diningTableRepository.findAvailableTables();
        return availableTables.stream()
                .map(this::convertToTableResponseSummary)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<TableResponse> getOccupiedTables() {
        List<DiningTable> occupiedTables = diningTableRepository.findTablesWithActiveOrders();
        return occupiedTables.stream()
                .map(this::convertToTableResponseWithOrders)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<OrderSummaryResponse> getActiveOrdersForTable(Long tableId) {
        List<Order> activeOrders = orderRepository.findActiveOrdersByTableId(tableId);
        return activeOrders.stream()
                .map(this::convertToOrderSummaryResponse)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<OrderSummaryResponse> getAllOrdersForTable(Long tableId) {
        DiningTable table = diningTableRepository.findById(tableId)
                .orElseThrow(() -> new RuntimeException("Table not found with ID: " + tableId));
        List<Order> orders = orderRepository.findByDiningTableOrderByOrderTimeDesc(table);
        return orders.stream()
                .map(this::convertToOrderSummaryResponse)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<OrderSummaryResponse> getOrdersForTableByDateRange(Long tableId, LocalDate startDate, LocalDate endDate) {
        DiningTable table = diningTableRepository.findById(tableId)
                .orElseThrow(() -> new RuntimeException("Table not found with ID: " + tableId));
        
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(23, 59, 59);
        
        List<Order> orders = orderRepository.findByDiningTableAndOrderTimeBetween(table, startDateTime, endDateTime);
        return orders.stream()
                .map(this::convertToOrderSummaryResponse)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<TableResponse> getTablesSummary() {
        List<DiningTable> tables = diningTableRepository.findAllByOrderByTableNumberAsc();
        return tables.stream()
                .map(this::convertToTableResponseSummary)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<TableResponse> getTablesByLocation(String location) {
        List<DiningTable> tables = diningTableRepository.findByLocation(location);
        return tables.stream()
                .map(this::convertToTableResponseWithOrders)
                .collect(Collectors.toList());
    }
    
    @Override
    public boolean isTableAvailable(Long tableId) {
        Long activeOrdersCount = diningTableRepository.countActiveOrdersForTable(tableId);
        return activeOrdersCount == 0;
    }
    
    @Override
    public String getTableStatus(Long tableId) {
        Long activeOrdersCount = diningTableRepository.countActiveOrdersForTable(tableId);
        if (activeOrdersCount == 0) {
            return "AVAILABLE";
        } else {
            return "OCCUPIED";
        }
    }
    
    // Helper method to convert DiningTable to TableResponse with full order details
    private TableResponse convertToTableResponseWithOrders(DiningTable table) {
        TableResponse response = new TableResponse();
        response.setTableId(table.getTableId());
        response.setTableNumber(table.getTableNumber());
        response.setSeatingCapacity(table.getSeatingCapacity());
        response.setLocation(table.getLocation());
        
        // Get active orders
        List<Order> activeOrders = orderRepository.findActiveOrdersByTableId(table.getTableId());
        response.setActiveOrdersCount(activeOrders.size());
        response.setStatus(activeOrders.isEmpty() ? "AVAILABLE" : "OCCUPIED");
        
        // Calculate total active amount
        BigDecimal totalAmount = activeOrders.stream()
                .flatMap(order -> order.getOrderItems().stream())
                .map(item -> item.getItemPriceAtOrderTime().multiply(new BigDecimal(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        response.setTotalActiveAmount(totalAmount);
        
        // Convert orders to summary responses
        List<OrderSummaryResponse> orderSummaries = activeOrders.stream()
                .map(this::convertToOrderSummaryResponse)
                .collect(Collectors.toList());
        response.setActiveOrders(orderSummaries);
        
        // Set last order time
        Order mostRecentOrder = orderRepository.findMostRecentOrderByTableId(table.getTableId());
        if (mostRecentOrder != null) {
            response.setLastOrderTime(mostRecentOrder.getOrderTime());
        }
        
        return response;
    }
    
    // Helper method to convert DiningTable to TableResponse with summary only
    private TableResponse convertToTableResponseSummary(DiningTable table) {
        TableResponse response = new TableResponse();
        response.setTableId(table.getTableId());
        response.setTableNumber(table.getTableNumber());
        response.setSeatingCapacity(table.getSeatingCapacity());
        response.setLocation(table.getLocation());
        
        // Get basic status info
        Long activeOrdersCount = diningTableRepository.countActiveOrdersForTable(table.getTableId());
        response.setActiveOrdersCount(activeOrdersCount.intValue());
        response.setStatus(activeOrdersCount == 0 ? "AVAILABLE" : "OCCUPIED");
        
        // Set last order time
        Order mostRecentOrder = orderRepository.findMostRecentOrderByTableId(table.getTableId());
        if (mostRecentOrder != null) {
            response.setLastOrderTime(mostRecentOrder.getOrderTime());
        }
        
        return response;
    }
    
    // Helper method to convert Order to OrderSummaryResponse
    private OrderSummaryResponse convertToOrderSummaryResponse(Order order) {
        OrderSummaryResponse response = new OrderSummaryResponse();
        response.setOrderId(order.getOrderId());
        response.setOrderTime(order.getOrderTime());
        response.setStatus(order.getStatus().toString());
        
        // Staff information
        if (order.getStaff() != null) {
            response.setStaffId(order.getStaff().getStaffId());
            response.setStaffName(order.getStaff().getFirstName() + " " + order.getStaff().getLastName());
        }
        
        // Order items
        List<OrderItem> orderItems = order.getOrderItems();
        response.setTotalItems(orderItems.stream().mapToInt(OrderItem::getQuantity).sum());
        
        BigDecimal totalAmount = orderItems.stream()
                .map(item -> item.getItemPriceAtOrderTime().multiply(new BigDecimal(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        response.setTotalAmount(totalAmount);
        
        // Convert order items
        List<OrderItemResponse> orderItemResponses = orderItems.stream()
                .map(this::convertToOrderItemResponse)
                .collect(Collectors.toList());
        response.setOrderItems(orderItemResponses);
        
        // Estimated completion time (simple calculation - add 30 minutes to order time)
        response.setEstimatedCompletionTime(order.getOrderTime().plusMinutes(30));
        
        return response;
    }
    
    // Helper method to convert OrderItem to OrderItemResponse
    private OrderItemResponse convertToOrderItemResponse(OrderItem orderItem) {
        OrderItemResponse response = new OrderItemResponse();
        response.setOrderItemId(orderItem.getOrderItemId());
        response.setMenuItemId(orderItem.getMenuItem().getItemId());
        response.setMenuItemName(orderItem.getMenuItem().getName());
        response.setMenuItemDescription(orderItem.getMenuItem().getDescription());
        response.setQuantity(orderItem.getQuantity());
        response.setItemPriceAtOrderTime(orderItem.getItemPriceAtOrderTime());
        response.setTotalPrice(orderItem.getTotalPrice());
        
        if (orderItem.getMenuItem().getMenuCategory() != null) {
            response.setCategoryName(orderItem.getMenuItem().getMenuCategory().getCategoryName());
        }
        
        // For now, set item status same as order status
        // In a more complex system, each item might have its own status
        response.setItemStatus("ORDERED");
        
        return response;
    }
}
