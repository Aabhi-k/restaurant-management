package com.dbms.restaurant.Controller;

import com.dbms.restaurant.Service.TableService;
import com.dbms.restaurant.dto.OrderSummaryResponse;
import com.dbms.restaurant.dto.TableResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tables")
public class TableController {
    
    @Autowired
    private TableService tableService;
    
    @GetMapping
    public ResponseEntity<?> getAllTables() {
        try {
            List<TableResponse> tables = tableService.getAllTablesWithOrders();
            return ResponseEntity.ok(tables);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Internal error", "Failed to retrieve tables"));
        }
    }
    
    @GetMapping("/summary")
    public ResponseEntity<?> getTablesSummary() {
        try {
            List<TableResponse> tables = tableService.getTablesSummary();
            return ResponseEntity.ok(tables);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Internal error", "Failed to retrieve tables summary"));
        }
    }
    
    @GetMapping("/{tableId}")
    public ResponseEntity<?> getTableById(@PathVariable Long tableId) {
        try {
            TableResponse table = tableService.getTableWithOrders(tableId);
            return ResponseEntity.ok(table);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(createErrorResponse("Table not found", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Internal error", "Failed to retrieve table"));
        }
    }
    
    @GetMapping("/number/{tableNumber}")
    public ResponseEntity<?> getTableByNumber(@PathVariable Integer tableNumber) {
        try {
            TableResponse table = tableService.getTableByNumberWithOrders(tableNumber);
            return ResponseEntity.ok(table);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(createErrorResponse("Table not found", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Internal error", "Failed to retrieve table"));
        }
    }
    
    @GetMapping("/available")
    public ResponseEntity<?> getAvailableTables() {
        try {
            List<TableResponse> tables = tableService.getAvailableTables();
            return ResponseEntity.ok(tables);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Internal error", "Failed to retrieve available tables"));
        }
    }
    
    @GetMapping("/occupied")
    public ResponseEntity<?> getOccupiedTables() {
        try {
            List<TableResponse> tables = tableService.getOccupiedTables();
            return ResponseEntity.ok(tables);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Internal error", "Failed to retrieve occupied tables"));
        }
    }
    
    @GetMapping("/location/{location}")
    public ResponseEntity<?> getTablesByLocation(@PathVariable String location) {
        try {
            List<TableResponse> tables = tableService.getTablesByLocation(location);
            return ResponseEntity.ok(tables);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Internal error", "Failed to retrieve tables by location"));
        }
    }
    
    @GetMapping("/{tableId}/orders/active")
    public ResponseEntity<?> getActiveOrdersForTable(@PathVariable Long tableId) {
        try {
            List<OrderSummaryResponse> orders = tableService.getActiveOrdersForTable(tableId);
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Internal error", "Failed to retrieve active orders"));
        }
    }
    
    @GetMapping("/{tableId}/orders")
    public ResponseEntity<?> getAllOrdersForTable(@PathVariable Long tableId) {
        try {
            List<OrderSummaryResponse> orders = tableService.getAllOrdersForTable(tableId);
            return ResponseEntity.ok(orders);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(createErrorResponse("Table not found", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Internal error", "Failed to retrieve orders"));
        }
    }
    
    @GetMapping("/{tableId}/orders/date-range")
    public ResponseEntity<?> getOrdersForTableByDateRange(
            @PathVariable Long tableId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        try {
            List<OrderSummaryResponse> orders = tableService.getOrdersForTableByDateRange(tableId, startDate, endDate);
            return ResponseEntity.ok(orders);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(createErrorResponse("Table not found", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Internal error", "Failed to retrieve orders by date range"));
        }
    }
    
    @GetMapping("/{tableId}/available")
    public ResponseEntity<?> isTableAvailable(@PathVariable Long tableId) {
        try {
            boolean isAvailable = tableService.isTableAvailable(tableId);
            String status = tableService.getTableStatus(tableId);
            
            Map<String, Object> response = new HashMap<>();
            response.put("tableId", tableId);
            response.put("isAvailable", isAvailable);
            response.put("status", status);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Internal error", "Failed to check table availability"));
        }
    }
    
    @GetMapping("/{tableId}/status")
    public ResponseEntity<?> getTableStatus(@PathVariable Long tableId) {
        try {
            String status = tableService.getTableStatus(tableId);
            
            Map<String, Object> response = new HashMap<>();
            response.put("tableId", tableId);
            response.put("status", status);
            response.put("timestamp", java.time.LocalDateTime.now());
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Internal error", "Failed to get table status"));
        }
    }
    
    @GetMapping("/overview")
    public ResponseEntity<?> getRestaurantOverview() {
        try {
            List<TableResponse> allTables = tableService.getTablesSummary();
            List<TableResponse> availableTables = tableService.getAvailableTables();
            List<TableResponse> occupiedTables = tableService.getOccupiedTables();
            
            Map<String, Object> overview = new HashMap<>();
            overview.put("totalTables", allTables.size());
            overview.put("availableTables", availableTables.size());
            overview.put("occupiedTables", occupiedTables.size());
            overview.put("tables", allTables);
            overview.put("timestamp", java.time.LocalDateTime.now());
            
            return ResponseEntity.ok(overview);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Internal error", "Failed to get restaurant overview"));
        }
    }
    
    private Map<String, String> createErrorResponse(String error, String message) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", error);
        errorResponse.put("message", message);
        errorResponse.put("timestamp", java.time.LocalDateTime.now().toString());
        return errorResponse;
    }
}
