package com.dbms.restaurant.Service;

import com.dbms.restaurant.dto.AddToCartRequest;
import com.dbms.restaurant.dto.CartItemResponse;
import com.dbms.restaurant.dto.CartResponse;
import com.dbms.restaurant.dto.CheckoutRequest;
import com.dbms.restaurant.dto.CheckoutResponse;
import com.dbms.restaurant.dto.OrderItemResponse;
import com.dbms.restaurant.dto.UpdateCartItemRequest;
import com.dbms.restaurant.models.*;
import com.dbms.restaurant.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class CartServiceImpl implements CartService {
    
    @Autowired
    private CartRepository cartRepository;
    
    @Autowired
    private CartItemRepository cartItemRepository;
    
    @Autowired
    private MenuItemRepository menuItemRepository;
    
    @Autowired
    private DiningTableRepository diningTableRepository;
    
    @Autowired
    private StaffRepository staffRepository;
    
    @Autowired
    private OrderRepository orderRepository;
    
    @Autowired
    private OrderItemRepository orderItemRepository;
    
    @Autowired
    private BillingRepository billingRepository;
    
    @Autowired
    private PaymentMethodRepository paymentMethodRepository;
    
    @Override
    public CartResponse addToCart(AddToCartRequest request) {
        // Validate input
        if (request.getSessionId() == null || request.getSessionId().trim().isEmpty()) {
            throw new IllegalArgumentException("Session ID is required");
        }
        if (request.getMenuItemId() == null) {
            throw new IllegalArgumentException("Menu item ID is required");
        }
        if (request.getQuantity() == null || request.getQuantity() <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }
        
        // Get or create cart
        Cart cart = getOrCreateCart(request.getSessionId(), request.getTableId(), request.getStaffId());
        
        // Find menu item
        MenuItem menuItem = menuItemRepository.findById(request.getMenuItemId())
                .orElseThrow(() -> new RuntimeException("Menu item not found"));
        
        // Check if menu item is available
        if (!menuItem.getAvailabilityStatus()) {
            throw new RuntimeException("Menu item is currently unavailable");
        }
        
        // Check if item already exists in cart
        Optional<CartItem> existingCartItem = cartItemRepository.findByCartAndMenuItem(cart, menuItem);
        
        if (existingCartItem.isPresent()) {
            // Update existing item quantity
            CartItem cartItem = existingCartItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + request.getQuantity());
            if (request.getSpecialInstructions() != null) {
                cartItem.setSpecialInstructions(request.getSpecialInstructions());
            }
            cartItemRepository.save(cartItem);
        } else {
            // Create new cart item
            CartItem newCartItem = new CartItem(request.getQuantity(), menuItem, cart, request.getSpecialInstructions());
            cartItemRepository.save(newCartItem);
            cart.addCartItem(newCartItem);
        }
        
        // Save cart (this will update the updatedAt timestamp)
        cart = cartRepository.save(cart);
        
        return convertToCartResponse(cart);
    }
    
    @Override
    public CartResponse getCartBySessionId(String sessionId) {
        Optional<Cart> cart = cartRepository.findBySessionId(sessionId);
        if (cart.isPresent()) {
            return convertToCartResponse(cart.get());
        } else {
            // Return empty cart response
            CartResponse emptyCart = new CartResponse();
            emptyCart.setSessionId(sessionId);
            emptyCart.setTotalAmount(java.math.BigDecimal.ZERO);
            emptyCart.setTotalItems(0);
            emptyCart.setCartItems(List.of());
            return emptyCart;
        }
    }
    
    @Override
    public CartResponse updateCartItem(UpdateCartItemRequest request) {
        if (request.getSessionId() == null || request.getSessionId().trim().isEmpty()) {
            throw new IllegalArgumentException("Session ID is required");
        }
        if (request.getCartItemId() == null) {
            throw new IllegalArgumentException("Cart item ID is required");
        }
        if (request.getQuantity() == null || request.getQuantity() <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }
        
        // Find cart item
        CartItem cartItem = cartItemRepository.findById(request.getCartItemId())
                .orElseThrow(() -> new RuntimeException("Cart item not found"));
        
        // Verify the cart item belongs to the correct session
        if (!cartItem.getCart().getSessionId().equals(request.getSessionId())) {
            throw new RuntimeException("Cart item does not belong to this session");
        }
        
        // Update cart item
        cartItem.setQuantity(request.getQuantity());
        if (request.getSpecialInstructions() != null) {
            cartItem.setSpecialInstructions(request.getSpecialInstructions());
        }
        
        cartItemRepository.save(cartItem);
        
        // Update cart timestamp
        Cart cart = cartItem.getCart();
        cart.setUpdatedAt(LocalDateTime.now());
        cartRepository.save(cart);
        
        return convertToCartResponse(cart);
    }
    
    @Override
    public CartResponse removeFromCart(String sessionId, Long cartItemId) {
        if (sessionId == null || sessionId.trim().isEmpty()) {
            throw new IllegalArgumentException("Session ID is required");
        }
        if (cartItemId == null) {
            throw new IllegalArgumentException("Cart item ID is required");
        }
        
        // Find cart item
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));
        
        // Verify the cart item belongs to the correct session
        if (!cartItem.getCart().getSessionId().equals(sessionId)) {
            throw new RuntimeException("Cart item does not belong to this session");
        }
        
        Cart cart = cartItem.getCart();
        cart.removeCartItem(cartItem);
        cartItemRepository.delete(cartItem);
        cartRepository.save(cart);
        
        return convertToCartResponse(cart);
    }
    
    @Override
    public void clearCart(String sessionId) {
        Optional<Cart> cart = cartRepository.findBySessionId(sessionId);
        if (cart.isPresent()) {
            cartItemRepository.deleteByCart(cart.get());
            cart.get().clearCart();
            cartRepository.save(cart.get());
        }
    }
    
    @Override
    public List<CartItem> getCartItems(String sessionId) {
        return cartItemRepository.findByCartSessionId(sessionId);
    }
    
    @Override
    public Cart getOrCreateCart(String sessionId, Long tableId, Long staffId) {
        Optional<Cart> existingCart = cartRepository.findBySessionId(sessionId);
        
        if (existingCart.isPresent()) {
            return existingCart.get();
        }
        
        // Create new cart
        Cart newCart = new Cart(sessionId);
        
        // Set table if provided
        if (tableId != null) {
            DiningTable table = diningTableRepository.findById(tableId)
                    .orElseThrow(() -> new RuntimeException("Dining table not found"));
            newCart.setDiningTable(table);
        }
        
        // Set staff if provided
        if (staffId != null) {
            Staff staff = staffRepository.findById(staffId)
                    .orElseThrow(() -> new RuntimeException("Staff not found"));
            newCart.setStaff(staff);
        }
        
        return cartRepository.save(newCart);
    }
    
    @Override
    public Long convertCartToOrder(String sessionId) {
        Optional<Cart> cartOpt = cartRepository.findBySessionId(sessionId);
        if (!cartOpt.isPresent()) {
            throw new RuntimeException("Cart not found for session: " + sessionId);
        }
        
        Cart cart = cartOpt.get();
        if (cart.getCartItems().isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }
        
        if (cart.getDiningTable() == null) {
            throw new RuntimeException("Table information is required for checkout");
        }
        
        if (cart.getStaff() == null) {
            throw new RuntimeException("Staff information is required for checkout");
        }
        
        // Create new order
        Order order = new Order(cart.getDiningTable(), cart.getStaff());
        order = orderRepository.save(order);
        
        // Create order items from cart items
        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItem cartItem : cart.getCartItems()) {
            OrderItem orderItem = new OrderItem(
                cartItem.getQuantity(),
                cartItem.getItemPriceAtTime(),
                order,
                cartItem.getMenuItem()
            );
            orderItems.add(orderItem);
        }
        orderItemRepository.saveAll(orderItems);
        order.setOrderItems(orderItems);
        
        // Create billing record with cash payment (since payment is cash only)
        PaymentMethod cashPayment = paymentMethodRepository.findByMethodName("CASH")
                .orElseGet(() -> {
                    PaymentMethod cash = new PaymentMethod("CASH", "Cash Payment");
                    return paymentMethodRepository.save(cash);
                });
        
        BigDecimal totalAmount = cart.getTotalAmount();
        BigDecimal tax = totalAmount.multiply(new BigDecimal("0.10")); // 10% tax
        
        Billing billing = new Billing(totalAmount, tax, order, cashPayment);
        billing = billingRepository.save(billing);
        order.setBilling(billing);
        
        // Clear the cart after successful order creation
        cartItemRepository.deleteByCart(cart);
        cartRepository.delete(cart);
        
        return order.getOrderId();
    }
    
    @Override
    public CheckoutResponse checkout(CheckoutRequest request) {
        if (request.getSessionId() == null || request.getSessionId().trim().isEmpty()) {
            throw new IllegalArgumentException("Session ID is required");
        }
        
        try {
            // Convert cart to order
            Long orderId = convertCartToOrder(request.getSessionId());
            
            // Fetch the created order with all details
            Order order = orderRepository.findById(orderId)
                    .orElseThrow(() -> new RuntimeException("Order not found after creation"));
            
            // Build checkout response
            CheckoutResponse response = new CheckoutResponse();
            response.setOrderId(order.getOrderId());
            response.setSessionId(request.getSessionId());
            response.setOrderTime(order.getOrderTime());
            response.setOrderStatus(order.getStatus().toString());
            
            // Table information
            if (order.getDiningTable() != null) {
                response.setTableId(order.getDiningTable().getTableId());
                response.setTableName("Table " + order.getDiningTable().getTableNumber());
            }
            
            // Staff information
            if (order.getStaff() != null) {
                response.setStaffId(order.getStaff().getStaffId());
                response.setStaffName(order.getStaff().getFirstName() + " " + order.getStaff().getLastName());
            }
            
            // Billing information
            if (order.getBilling() != null) {
                Billing billing = order.getBilling();
                response.setTotalAmount(billing.getTotalAmount());
                response.setTax(billing.getTax());
                response.setFinalAmount(billing.getFinalAmount());
                response.setPaymentStatus(billing.getPaymentStatus().toString());
            }
            
            // Order items
            if (order.getOrderItems() != null && !order.getOrderItems().isEmpty()) {
                List<OrderItemResponse> orderItemResponses = order.getOrderItems().stream()
                        .map(this::convertToOrderItemResponse)
                        .collect(Collectors.toList());
                response.setOrderItems(orderItemResponses);
            }
            
            response.setMessage("Checkout completed successfully");
            return response;
            
        } catch (Exception e) {
            CheckoutResponse errorResponse = new CheckoutResponse();
            errorResponse.setMessage("Checkout failed: " + e.getMessage());
            throw new RuntimeException("Checkout failed: " + e.getMessage(), e);
        }
    }
    
    @Override
    public boolean cartExists(String sessionId) {
        return cartRepository.existsBySessionId(sessionId);
    }
    
    @Override
    public void cleanupStaleCarts(int daysOld) {
        LocalDateTime cutoffTime = LocalDateTime.now().minusDays(daysOld);
        List<Cart> staleCarts = cartRepository.findStaleCartsBefore(cutoffTime);
        
        for (Cart cart : staleCarts) {
            cartItemRepository.deleteByCart(cart);
            cartRepository.delete(cart);
        }
    }
    
    // Helper method to convert Cart entity to CartResponse DTO
    private CartResponse convertToCartResponse(Cart cart) {
        CartResponse response = new CartResponse();
        response.setCartId(cart.getCartId());
        response.setSessionId(cart.getSessionId());
        response.setCreatedAt(cart.getCreatedAt());
        response.setUpdatedAt(cart.getUpdatedAt());
        response.setTotalAmount(cart.getTotalAmount());
        response.setTotalItems(cart.getTotalItems());
        
        // Set table info if present
        if (cart.getDiningTable() != null) {
            response.setTableId(cart.getDiningTable().getTableId());
            response.setTableName("Table " + cart.getDiningTable().getTableNumber());
        }
        
        // Set staff info if present
        if (cart.getStaff() != null) {
            response.setStaffId(cart.getStaff().getStaffId());
            response.setStaffName(cart.getStaff().getFirstName() + " " + cart.getStaff().getLastName());
        }
        
        // Convert cart items
        List<CartItemResponse> cartItemResponses = cart.getCartItems().stream()
                .map(this::convertToCartItemResponse)
                .collect(Collectors.toList());
        response.setCartItems(cartItemResponses);
        
        return response;
    }
    
    // Helper method to convert CartItem entity to CartItemResponse DTO
    private CartItemResponse convertToCartItemResponse(CartItem cartItem) {
        CartItemResponse response = new CartItemResponse();
        response.setCartItemId(cartItem.getCartItemId());
        response.setMenuItemId(cartItem.getMenuItem().getItemId());
        response.setMenuItemName(cartItem.getMenuItem().getName());
        response.setMenuItemDescription(cartItem.getMenuItem().getDescription());
        response.setItemPriceAtTime(cartItem.getItemPriceAtTime());
        response.setQuantity(cartItem.getQuantity());
        response.setTotalPrice(cartItem.getTotalPrice());
        response.setSpecialInstructions(cartItem.getSpecialInstructions());
        response.setIsAvailable(cartItem.getMenuItem().getAvailabilityStatus());
        
        if (cartItem.getMenuItem().getMenuCategory() != null) {
            response.setCategoryName(cartItem.getMenuItem().getMenuCategory().getCategoryName());
        }
        
        return response;
    }
    
    // Helper method to convert OrderItem entity to OrderItemResponse DTO
    private OrderItemResponse convertToOrderItemResponse(OrderItem orderItem) {
        OrderItemResponse response = new OrderItemResponse();
        response.setOrderItemId(orderItem.getOrderItemId());
        response.setMenuItemId(orderItem.getMenuItem().getItemId());
        response.setMenuItemName(orderItem.getMenuItem().getName());
        response.setQuantity(orderItem.getQuantity());
        response.setItemPriceAtOrderTime(orderItem.getItemPriceAtOrderTime());
        response.setTotalPrice(orderItem.getTotalPrice());
        
        if (orderItem.getMenuItem().getMenuCategory() != null) {
            response.setCategoryName(orderItem.getMenuItem().getMenuCategory().getCategoryName());
        }
        
        return response;
    }
}
