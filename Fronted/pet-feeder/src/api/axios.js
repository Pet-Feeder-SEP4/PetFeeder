import axios from 'axios';

export default axios.create({
    baseURL: 'https://peefee.azurewebsites.net'
});

In that case, you can conditionally exclude the Authorization header for the login and register requests. Here's how you can modify the interceptor to handle this:

jsx
Copy code
// axios.js

import axios from 'axios';

const instance = axios.create({
  // your axios instance configuration
  baseURL: 'your_base_url',
  headers: {
    'Content-Type': 'application/json',
  },
  withCredentials: true, // assuming you need this
});

// Add a request interceptor to include the token in the header
instance.interceptors.request.use(
  (config) => {
    // Exclude Authorization header for login and register requests
    if (config.url === '/auth/authenticate' || config.url === '/auth/register') {
      return config;
    }

    const token = localStorage.getItem('token') || 'invalid_token'; // You can set it to an invalid value or empty string if there's no token
    config.headers['Authorization'] = `Bearer ${token}`;
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);