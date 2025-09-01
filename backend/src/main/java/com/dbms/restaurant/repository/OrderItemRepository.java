package com.dbms.restaurant.repository;

import com.dbms.restaurant.models.OrderItem;
import com.dbms.restaurant.models.Order;
import com.dbms.restaurant.models.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findByOrder(Order order);
    List<OrderItem> findByMenuItem(MenuItem menuItem);
}
