package com.dbms.restaurant.repository;

import com.dbms.restaurant.models.Order;
import com.dbms.restaurant.models.Order.OrderStatus;
import com.dbms.restaurant.models.DiningTable;
import com.dbms.restaurant.models.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByStatus(OrderStatus status);
    List<Order> findByDiningTable(DiningTable diningTable);
    List<Order> findByStaff(Staff staff);
    List<Order> findByOrderTimeBetween(LocalDateTime startTime, LocalDateTime endTime);
}
