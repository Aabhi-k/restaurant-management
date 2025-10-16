import { useState, useEffect } from 'react';
import './App.css'
import MenuPage from './pages/MenuPage'
import CartPage from './pages/CartPage'
import { cartAPI } from './api/api';

function App() {
  const [currentPage, setCurrentPage] = useState('menu');
  const [cartItemCount, setCartItemCount] = useState(0);

  useEffect(() => {
    updateCartCount();
  }, []);

  const updateCartCount = async () => {
    try {
      const sessionId = sessionStorage.getItem('cartSessionId');
      if (!sessionId) {
        setCartItemCount(0);
        return;
      }

      const response = await cartAPI.getCart(sessionId);
      setCartItemCount(response.data.totalItems || 0);
    } catch (err) {
      console.error('Error fetching cart count:', err);
      setCartItemCount(0);
    }
  };

  return (
    <div className="app">
      <nav className="navbar">
        <div className="nav-container">
          <h2 className="nav-brand">Restaurant Manager</h2>
          <div className="nav-links">
            <button
              className={`nav-link ${currentPage === 'menu' ? 'active' : ''}`}
              onClick={() => setCurrentPage('menu')}
            >
              Menu
            </button>
            <button
              className={`nav-link ${currentPage === 'cart' ? 'active' : ''}`}
              onClick={() => setCurrentPage('cart')}
            >
              Cart {cartItemCount > 0 && <span className="cart-badge">{cartItemCount}</span>}
            </button>
          </div>
        </div>
      </nav>

      {currentPage === 'menu' && <MenuPage onCartUpdate={updateCartCount} />}
      {currentPage === 'cart' && <CartPage onCartUpdate={updateCartCount} />}
    </div>
  )
}

export default App
