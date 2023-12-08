import axios from 'axios';

const instance = axios.create({

    baseURL:'https://petfeederapi.azurewebsites.net/'

});




instance.interceptors.request.use(
    (config) => {
      // Exclude Authorization header for login and register requests
      if (config.url === '/auth/authenticate' || config.url === '/auth/register') {
        return config;
      }
  
      const token = localStorage.getItem('token') || 'invalid_token'; 
      config.headers['Authorization'] = `Bearer ${token}`;
      return config;
    },
    (error) => {
      return Promise.reject(error);
    }
  );
  
  export default instance;