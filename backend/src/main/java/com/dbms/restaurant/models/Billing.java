package com.dbms.restaurant.models;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "billing")
public class Billing {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bill_id")
    private Long billId;
    
    @Column(name = "total_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalAmount;
    
    @Column(name = "discount", precision = 10, scale = 2)
    private BigDecimal discount;
    
    @Column(name = "tax", nullable = false, precision = 10, scale = 2)
    private BigDecimal tax;
    
    @Column(name = "final_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal finalAmount;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status", nullable = false, length = 20)
    private PaymentStatus paymentStatus;
    
    @Column(name = "billing_time", nullable = false)
    private LocalDateTime billingTime;
    
    // One-to-One relationship with Order
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;
    
    // Many-to-One relationship with PaymentMethod
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "payment_method_id", nullable = false)
    private PaymentMethod paymentMethod;
    
    // Enum for Payment Status
    public enum PaymentStatus {
        PAID, UNPAID
    }
    

    public Billing() {
        this.billingTime = LocalDateTime.now();
        this.paymentStatus = PaymentStatus.UNPAID;
        this.discount = BigDecimal.ZERO;
    }
    
    public Billing(BigDecimal totalAmount, BigDecimal tax, Order order, PaymentMethod paymentMethod) {
        this();
        this.totalAmount = totalAmount;
        this.tax = tax;
        this.order = order;
        this.paymentMethod = paymentMethod;
        calculateFinalAmount();
    }
    
    
    public void calculateFinalAmount() {
        BigDecimal discountAmount = this.discount != null ? this.discount : BigDecimal.ZERO;
        this.finalAmount = this.totalAmount.subtract(discountAmount).add(this.tax);
    }

    public Long getBillId() {
        return billId;
    }
    
    public void setBillId(Long billId) {
        this.billId = billId;
    }
    
    public BigDecimal getTotalAmount() {
        return totalAmount;
    }
    
    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
        calculateFinalAmount();
    }
    
    public BigDecimal getDiscount() {
        return discount;
    }
    
    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
        calculateFinalAmount();
    }
    
    public BigDecimal getTax() {
        return tax;
    }
    
    public void setTax(BigDecimal tax) {
        this.tax = tax;
        calculateFinalAmount();
    }
    
    public BigDecimal getFinalAmount() {
        return finalAmount;
    }
    
    public void setFinalAmount(BigDecimal finalAmount) {
        this.finalAmount = finalAmount;
    }
    
    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }
    
    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
    
    public LocalDateTime getBillingTime() {
        return billingTime;
    }
    
    public void setBillingTime(LocalDateTime billingTime) {
        this.billingTime = billingTime;
    }
    
    public Order getOrder() {
        return order;
    }
    
    public void setOrder(Order order) {
        this.order = order;
    }
    
    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }
    
    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
