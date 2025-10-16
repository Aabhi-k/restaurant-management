import axios from 'axios';

const API_BASE_URL = '/api';

const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

// Menu API calls
export const menuAPI = {
  // Get complete menu with all categories and items
  getCompleteMenu: () => api.get('/menu'),
  
  // Get available menu items only
  getAvailableMenu: () => api.get('/menu/available'),
  
  // Get all menu items
  getAllMenuItems: () => api.get('/menu/items'),
  
  // Get menu item by ID
  getMenuItemById: (id) => api.get(`/menu/items/${id}`),
  
  // Search menu items
  searchMenuItems: (query) => api.get('/menu/items/search', { params: { query } }),
  
  // Get items by category
  getItemsByCategory: (categoryId) => api.get(`/menu/items/category/${categoryId}`),
  
  // Get all categories
  getAllCategories: () => api.get('/menu/categories'),
};

// Cart API calls
export const cartAPI = {
  // Get cart by session ID
  getCart: (sessionId) => api.get(`/cart/${sessionId}`),
  
  // Add item to cart
  addToCart: (data) => api.post('/cart/add', data),
  
  // Update cart item
  updateCartItem: (cartItemId, data) => api.put(`/cart/items/${cartItemId}`, data),
  
  // Remove item from cart
  removeFromCart: (sessionId, cartItemId) => api.delete(`/cart/${sessionId}/item/${cartItemId}`),
  
  // Clear cart
  clearCart: (sessionId) => api.delete(`/cart/${sessionId}/clear`),
  
  // Checkout
  checkout: (sessionId, data) => api.post(`/cart/${sessionId}/checkout`, data),
};

// Table API calls
export const tableAPI = {
  // Get all tables
  getAllTables: () => api.get('/tables'),
  
  // Get table by ID
  getTableById: (id) => api.get(`/tables/${id}`),
  
  // Get available tables
  getAvailableTables: () => api.get('/tables/available'),
  
  // Get occupied tables
  getOccupiedTables: () => api.get('/tables/occupied'),
  
  // Get active orders for a table
  getActiveOrders: (tableId) => api.get(`/tables/${tableId}/orders/active`),
  
  // Get all orders for a table
  getAllOrders: (tableId) => api.get(`/tables/${tableId}/orders`),
  
  // Get restaurant overview
  getRestaurantOverview: () => api.get('/tables/overview'),
};

// Staff API calls
export const staffAPI = {
  // Get all staff
  getAllStaff: () => api.get('/staff'),
  
  // Get staff by ID
  getStaffById: (id) => api.get(`/staff/${id}`),
};

export default api;
