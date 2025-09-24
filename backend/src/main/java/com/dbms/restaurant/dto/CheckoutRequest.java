package com.dbms.restaurant.dto;

public class CheckoutRequest {
    private String sessionId;
    
    public CheckoutRequest() {}
    
    public CheckoutRequest(String sessionId) {
        this.sessionId = sessionId;
    }
    
    public String getSessionId() {
        return sessionId;
    }
    
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
