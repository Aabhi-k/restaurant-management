import { useState, useEffect } from 'react';
import { menuAPI, cartAPI } from '../api/api';

const MenuPage = ({ onCartUpdate }) => {
  const [menu, setMenu] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [addingToCart, setAddingToCart] = useState({});

  useEffect(() => {
    fetchMenu();
  }, []);

  const fetchMenu = async () => {
    try {
      setLoading(true);
      const response = await menuAPI.getCompleteMenu();
      setMenu(response.data);
      setError(null);
    } catch (err) {
      setError(err.message || 'Failed to fetch menu');
      console.error('Error fetching menu:', err);
    } finally {
      setLoading(false);
    }
  };

  const handleAddToCart = async (item) => {
    try {
      setAddingToCart(prev => ({ ...prev, [item.itemId]: true }));
      
      // Get or create session ID
      let sessionId = sessionStorage.getItem('cartSessionId');
      if (!sessionId) {
        sessionId = `session_${Date.now()}_${Math.random().toString(36).substr(2, 9)}`;
        sessionStorage.setItem('cartSessionId', sessionId);
      }

      const cartData = {
        sessionId: sessionId,
        menuItemId: item.itemId,
        quantity: 1
      };

      await cartAPI.addToCart(cartData);
      
      // Notify parent component about cart update
      if (onCartUpdate) {
        onCartUpdate();
      }

      // Show success feedback
      alert(`${item.name} added to cart!`);
    } catch (err) {
      console.error('Error adding to cart:', err);
      alert('Failed to add item to cart. Please try again.');
    } finally {
      setAddingToCart(prev => ({ ...prev, [item.itemId]: false }));
    }
  };

  if (loading) {
    return <div className="container">Loading menu...</div>;
  }

  if (error) {
    return (
      <div className="container">
        <div className="error">Error: {error}</div>
        <button onClick={fetchMenu}>Retry</button>
      </div>
    );
  }

  if (!menu || !menu.categories || menu.categories.length === 0) {
    return <div className="container">No menu items available</div>;
  }

  return (
    <div className="container">
      <h1>Restaurant Menu</h1>
      
      <div className="menu-stats">
        <p>Total Items: {menu.totalItems}</p>
        <p>Available Items: {menu.totalAvailableItems}</p>
        <p>Categories: {menu.totalCategories}</p>
      </div>

      {menu.categories.map((category) => (
        <div key={category.categoryId} className="category-section">
          <h2>{category.categoryName}</h2>
          {category.categoryDescription && (
            <p className="category-description">{category.categoryDescription}</p>
          )}
          
          <div className="menu-items">
            {category.menuItems && category.menuItems.length > 0 ? (
              category.menuItems.map((item) => (
                <div key={item.itemId} className="menu-item">
                  <div className="item-header">
                    <h3>{item.name}</h3>
                    <span className="item-price">${item.price.toFixed(2)}</span>
                  </div>
                  
                  {item.description && (
                    <p className="item-description">{item.description}</p>
                  )}
                  
                  <div className="item-footer">
                    <span className={`availability ${item.availabilityStatus ? 'available' : 'unavailable'}`}>
                      {item.availabilityStatus ? '✓ Available' : '✗ Not Available'}
                    </span>
                    
                    {item.allergenInfo && (
                      <span className="dietary-info">{item.allergenInfo}</span>
                    )}
                  </div>
                  
                  <button
                    className="add-to-cart-btn"
                    onClick={() => handleAddToCart(item)}
                    disabled={!item.availabilityStatus || addingToCart[item.itemId]}
                  >
                    {addingToCart[item.itemId] ? 'Adding...' : 'Add to Cart'}
                  </button>
                </div>
              ))
            ) : (
              <p>No items in this category</p>
            )}
          </div>
        </div>
      ))}
    </div>
  );
}

export default MenuPage;
