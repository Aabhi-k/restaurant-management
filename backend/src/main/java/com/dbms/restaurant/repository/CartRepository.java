package com.dbms.restaurant.repository;

import com.dbms.restaurant.models.Cart;
import com.dbms.restaurant.models.DiningTable;
import com.dbms.restaurant.models.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    
    Optional<Cart> findBySessionId(String sessionId);
    
    List<Cart> findByDiningTable(DiningTable diningTable);
    
    List<Cart> findByStaff(Staff staff);
    
    List<Cart> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
    
    // Find carts that haven't been updated for a certain period (for cleanup)
    @Query("SELECT c FROM Cart c WHERE c.updatedAt < :cutoffTime")
    List<Cart> findStaleCartsBefore(@Param("cutoffTime") LocalDateTime cutoffTime);
    
    // Check if a cart exists for a specific session
    boolean existsBySessionId(String sessionId);
    
    // Count total items in a cart
    @Query("SELECT COALESCE(SUM(ci.quantity), 0) FROM Cart c JOIN c.cartItems ci WHERE c.cartId = :cartId")
    Integer countTotalItemsInCart(@Param("cartId") Long cartId);
    
    // Get total amount for a cart
    @Query("SELECT COALESCE(SUM(ci.quantity * ci.itemPriceAtTime), 0) FROM Cart c JOIN c.cartItems ci WHERE c.cartId = :cartId")
    Double getTotalAmountForCart(@Param("cartId") Long cartId);
    
    void deleteBySessionId(String sessionId);
}
