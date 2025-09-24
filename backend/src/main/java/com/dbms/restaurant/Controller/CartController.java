package com.dbms.restaurant.Controller;

import com.dbms.restaurant.Service.CartService;
import com.dbms.restaurant.dto.AddToCartRequest;
import com.dbms.restaurant.dto.CartResponse;
import com.dbms.restaurant.dto.CheckoutRequest;
import com.dbms.restaurant.dto.CheckoutResponse;
import com.dbms.restaurant.dto.UpdateCartItemRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin(origins = "*") // Configure appropriately for production
public class CartController {
    
    @Autowired
    private CartService cartService;
    
    @PostMapping("/add")
    public ResponseEntity<?> addToCart(@RequestBody AddToCartRequest request) {
        try {
            CartResponse cartResponse = cartService.addToCart(request);
            return ResponseEntity.ok(cartResponse);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(createErrorResponse("Invalid input", e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(createErrorResponse("Error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Internal error", "An unexpected error occurred"));
        }
    }
    
    @GetMapping("/{sessionId}")
    public ResponseEntity<?> getCart(@PathVariable String sessionId) {
        try {
            CartResponse cartResponse = cartService.getCartBySessionId(sessionId);
            return ResponseEntity.ok(cartResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Internal error", "An unexpected error occurred"));
        }
    }
    
    @PutMapping("/update")
    public ResponseEntity<?> updateCartItem(@RequestBody UpdateCartItemRequest request) {
        try {
            CartResponse cartResponse = cartService.updateCartItem(request);
            return ResponseEntity.ok(cartResponse);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(createErrorResponse("Invalid input", e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(createErrorResponse("Error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Internal error", "An unexpected error occurred"));
        }
    }
    
    @DeleteMapping("/{sessionId}/item/{cartItemId}")
    public ResponseEntity<?> removeFromCart(@PathVariable String sessionId, @PathVariable Long cartItemId) {
        try {
            CartResponse cartResponse = cartService.removeFromCart(sessionId, cartItemId);
            return ResponseEntity.ok(cartResponse);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(createErrorResponse("Invalid input", e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(createErrorResponse("Error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Internal error", "An unexpected error occurred"));
        }
    }
    
    @DeleteMapping("/{sessionId}/clear")
    public ResponseEntity<?> clearCart(@PathVariable String sessionId) {
        try {
            cartService.clearCart(sessionId);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Cart cleared successfully");
            response.put("sessionId", sessionId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Internal error", "An unexpected error occurred"));
        }
    }
    
    @GetMapping("/{sessionId}/exists")
    public ResponseEntity<?> cartExists(@PathVariable String sessionId) {
        try {
            boolean exists = cartService.cartExists(sessionId);
            Map<String, Object> response = new HashMap<>();
            response.put("exists", exists);
            response.put("sessionId", sessionId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Internal error", "An unexpected error occurred"));
        }
    }
    
    @GetMapping("/{sessionId}/summary")
    public ResponseEntity<?> getCartSummary(@PathVariable String sessionId) {
        try {
            CartResponse cartResponse = cartService.getCartBySessionId(sessionId);
            Map<String, Object> summary = new HashMap<>();
            summary.put("sessionId", sessionId);
            summary.put("totalItems", cartResponse.getTotalItems());
            summary.put("totalAmount", cartResponse.getTotalAmount());
            summary.put("cartId", cartResponse.getCartId());
            return ResponseEntity.ok(summary);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Internal error", "An unexpected error occurred"));
        }
    }
    
    @PostMapping("/batch-add")
    public ResponseEntity<?> batchAddToCart(@RequestBody BatchAddToCartRequest request) {
        try {
            String sessionId = request.getSessionId();
            CartResponse finalCart = null;
            
            for (AddToCartRequest item : request.getItems()) {
                item.setSessionId(sessionId); // Ensure all items use the same session
                finalCart = cartService.addToCart(item);
            }
            
            return ResponseEntity.ok(finalCart);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(createErrorResponse("Invalid input", e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(createErrorResponse("Error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Internal error", "An unexpected error occurred"));
        }
    }
    
    private Map<String, String> createErrorResponse(String error, String message) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", error);
        errorResponse.put("message", message);
        errorResponse.put("timestamp", java.time.LocalDateTime.now().toString());
        return errorResponse;
    }
    
    public static class BatchAddToCartRequest {
        private String sessionId;
        private java.util.List<AddToCartRequest> items;
        
        public String getSessionId() {
            return sessionId;
        }
        
        public void setSessionId(String sessionId) {
            this.sessionId = sessionId;
        }
        
        public java.util.List<AddToCartRequest> getItems() {
            return items;
        }
        
        public void setItems(java.util.List<AddToCartRequest> items) {
            this.items = items;
        }
    }
}
