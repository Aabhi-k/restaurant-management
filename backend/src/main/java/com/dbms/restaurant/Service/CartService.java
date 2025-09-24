package com.dbms.restaurant.Service;

import com.dbms.restaurant.dto.AddToCartRequest;
import com.dbms.restaurant.dto.CartResponse;
import com.dbms.restaurant.dto.CheckoutRequest;
import com.dbms.restaurant.dto.CheckoutResponse;
import com.dbms.restaurant.dto.UpdateCartItemRequest;
import com.dbms.restaurant.models.Cart;
import com.dbms.restaurant.models.CartItem;

import java.util.List;

public interface CartService {
    
    CartResponse addToCart(AddToCartRequest request);
    
    CartResponse getCartBySessionId(String sessionId);
    
    CartResponse updateCartItem(UpdateCartItemRequest request);
    
    CartResponse removeFromCart(String sessionId, Long cartItemId);
    
    void clearCart(String sessionId);
    
    List<CartItem> getCartItems(String sessionId);
    
    Cart getOrCreateCart(String sessionId, Long tableId, Long staffId);
    
    Long convertCartToOrder(String sessionId);
    
    CheckoutResponse checkout(CheckoutRequest request);
    
    boolean cartExists(String sessionId);
    
    void cleanupStaleCarts(int daysOld);
}
