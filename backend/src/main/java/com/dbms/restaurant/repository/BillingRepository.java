package com.dbms.restaurant.repository;

import com.dbms.restaurant.models.Billing;
import com.dbms.restaurant.models.Billing.PaymentStatus;
import com.dbms.restaurant.models.Order;
import com.dbms.restaurant.models.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface BillingRepository extends JpaRepository<Billing, Long> {
    Optional<Billing> findByOrder(Order order);
    List<Billing> findByPaymentStatus(PaymentStatus paymentStatus);
    List<Billing> findByPaymentMethod(PaymentMethod paymentMethod);
    List<Billing> findByBillingTimeBetween(LocalDateTime startTime, LocalDateTime endTime);
    
   
}
