package com.dbms.restaurant.models;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "payment_method")
public class PaymentMethod {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_method_id")
    private Long paymentMethodId;
    
    @Column(name = "method_name", nullable = false, length = 50)
    private String methodName;
    
    @Column(name = "method_description", length = 255)
    private String methodDescription;
    
    @OneToMany(mappedBy = "paymentMethod", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Billing> billings;
    
    public PaymentMethod() {}
    
    public PaymentMethod(String methodName, String methodDescription) {
        this.methodName = methodName;
        this.methodDescription = methodDescription;
    }
    
    public Long getPaymentMethodId() {
        return paymentMethodId;
    }
    
    public void setPaymentMethodId(Long paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }
    
    public String getMethodName() {
        return methodName;
    }
    
    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }
    
    public String getMethodDescription() {
        return methodDescription;
    }
    
    public void setMethodDescription(String methodDescription) {
        this.methodDescription = methodDescription;
    }
    
    public List<Billing> getBillings() {
        return billings;
    }
    
    public void setBillings(List<Billing> billings) {
        this.billings = billings;
    }
    
    @Override
    public String toString() {
        return "PaymentMethod{" +
                "paymentMethodId=" + paymentMethodId +
                ", methodName='" + methodName + '\'' +
                ", methodDescription='" + methodDescription + '\'' +
                '}';
    }
}
