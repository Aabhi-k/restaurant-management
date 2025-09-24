package com.dbms.restaurant.repository;

import com.dbms.restaurant.models.Cart;
import com.dbms.restaurant.models.CartItem;
import com.dbms.restaurant.models.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    
    List<CartItem> findByCart(Cart cart);
    
    List<CartItem> findByMenuItem(MenuItem menuItem);
    
    // Find a specific cart item by cart and menu item (useful for checking if item already exists)
    Optional<CartItem> findByCartAndMenuItem(Cart cart, MenuItem menuItem);
    
    // Get all cart items for a specific session
    @Query("SELECT ci FROM CartItem ci JOIN ci.cart c WHERE c.sessionId = :sessionId")
    List<CartItem> findByCartSessionId(@Param("sessionId") String sessionId);
    
    // Count items for a specific cart
    @Query("SELECT COUNT(ci) FROM CartItem ci WHERE ci.cart.cartId = :cartId")
    Long countByCartId(@Param("cartId") Long cartId);
    
    // Get total quantity of a specific menu item across all carts (useful for inventory checks)
    @Query("SELECT COALESCE(SUM(ci.quantity), 0) FROM CartItem ci WHERE ci.menuItem.itemId = :menuItemId")
    Integer getTotalQuantityForMenuItem(@Param("menuItemId") Long menuItemId);
    
    // Delete all cart items for a specific cart
    void deleteByCart(Cart cart);
    
    // Delete cart items by cart session ID
    @Query("DELETE FROM CartItem ci WHERE ci.cart.sessionId = :sessionId")
    void deleteByCartSessionId(@Param("sessionId") String sessionId);
}
