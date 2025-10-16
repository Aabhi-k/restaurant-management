import { useState, useEffect } from 'react';
import { cartAPI } from '../api/api';

function CartPage({ onCartUpdate }) {
  const [cart, setCart] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [updating, setUpdating] = useState({});

  useEffect(() => {
    fetchCart();
  }, []);

  const fetchCart = async () => {
    try {
      setLoading(true);
      const sessionId = sessionStorage.getItem('cartSessionId');
      
      if (!sessionId) {
        setCart({ items: [], totalItems: 0, totalPrice: 0 });
        setLoading(false);
        return;
      }

      const response = await cartAPI.getCart(sessionId);
      console.log('Cart response:', response.data); // Debug log
      setCart(response.data);
      setError(null);
    } catch (err) {
      if (err.response?.status === 404) {
        setCart({ cartItems: [], totalItems: 0, totalAmount: 0 });
      } else {
        setError(err.message || 'Failed to fetch cart');
        console.error('Error fetching cart:', err);
      }
    } finally {
      setLoading(false);
    }
  };

  const handleUpdateQuantity = async (cartItemId, newQuantity) => {
    if (newQuantity < 1) return;

    try {
      setUpdating(prev => ({ ...prev, [cartItemId]: true }));
      await cartAPI.updateCartItem(cartItemId, { quantity: newQuantity });
      await fetchCart();
      
      if (onCartUpdate) {
        onCartUpdate();
      }
    } catch (err) {
      console.error('Error updating quantity:', err);
      alert('Failed to update quantity');
    } finally {
      setUpdating(prev => ({ ...prev, [cartItemId]: false }));
    }
  };

  const handleRemoveItem = async (cartItemId) => {
    if (!confirm('Remove this item from cart?')) return;

    try {
      setUpdating(prev => ({ ...prev, [cartItemId]: true }));
      const sessionId = sessionStorage.getItem('cartSessionId');
      if (!sessionId) {
        alert('Session not found');
        return;
      }
      await cartAPI.removeFromCart(sessionId, cartItemId);
      await fetchCart();
      
      if (onCartUpdate) {
        onCartUpdate();
      }
    } catch (err) {
      console.error('Error removing item:', err);
      alert('Failed to remove item');
    } finally {
      setUpdating(prev => ({ ...prev, [cartItemId]: false }));
    }
  };

  const handleClearCart = async () => {
    if (!confirm('Clear entire cart?')) return;

    try {
      const sessionId = sessionStorage.getItem('cartSessionId');
      if (!sessionId) return;

      await cartAPI.clearCart(sessionId);
      await fetchCart();
      
      if (onCartUpdate) {
        onCartUpdate();
      }
    } catch (err) {
      console.error('Error clearing cart:', err);
      alert('Failed to clear cart');
    }
  };

  if (loading) {
    return <div className="container">Loading cart...</div>;
  }

  if (error) {
    return (
      <div className="container">
        <div className="error">Error: {error}</div>
        <button onClick={fetchCart}>Retry</button>
      </div>
    );
  }

  if (!cart || !cart.cartItems || cart.cartItems.length === 0) {
    return (
      <div className="container">
        <h1>Shopping Cart</h1>
        <div className="empty-cart">
          <p>Your cart is empty</p>
          <p className="empty-cart-subtitle">Add some delicious items from the menu!</p>
        </div>
      </div>
    );
  }

  return (
    <div className="container">
      <h1>Shopping Cart</h1>
      
      <div className="cart-summary">
        <div className="cart-stats">
          <p>Total Items: {cart.totalItems}</p>
          <p className="cart-total">Total: ${cart.totalAmount?.toFixed(2) || '0.00'}</p>
        </div>
      </div>

      <div className="cart-items">
        {cart.cartItems.map((item) => (
          <div key={item.cartItemId} className="cart-item">
            <div className="cart-item-details">
              <h3>{item.menuItemName}</h3>
              <p className="cart-item-price">${item.itemPriceAtTime?.toFixed(2)} each</p>
              {item.specialInstructions && (
                <p className="special-instructions">Note: {item.specialInstructions}</p>
              )}
            </div>

            <div className="cart-item-actions">
              <div className="quantity-controls">
                <button
                  className="qty-btn"
                  onClick={() => handleUpdateQuantity(item.cartItemId, item.quantity - 1)}
                  disabled={updating[item.cartItemId] || item.quantity <= 1}
                >
                  −
                </button>
                <span className="quantity">{item.quantity}</span>
                <button
                  className="qty-btn"
                  onClick={() => handleUpdateQuantity(item.cartItemId, item.quantity + 1)}
                  disabled={updating[item.cartItemId]}
                >
                  +
                </button>
              </div>

              <div className="cart-item-total">
                <strong>${item.totalPrice?.toFixed(2)}</strong>
              </div>

              <button
                className="remove-btn"
                onClick={() => handleRemoveItem(item.cartItemId)}
                disabled={updating[item.cartItemId]}
              >
                Remove
              </button>
            </div>
          </div>
        ))}
      </div>

      <div className="cart-actions">
        <button className="clear-cart-btn" onClick={handleClearCart}>
          Clear Cart
        </button>
        <button className="checkout-btn">
          Proceed to Checkout
        </button>
      </div>
    </div>
  );
}

export default CartPage;
