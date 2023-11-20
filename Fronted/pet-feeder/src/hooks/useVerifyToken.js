// useVerifyToken.js
import { useEffect, useState } from 'react';


const useVerifyToken = () => {
  const [isTokenValid, setIsTokenValid] = useState(null);

  useEffect(() => {
    const verifyToken = async () => {
      try {
        const storedToken = localStorage.getItem('token');
        setIsTokenValid(!!storedToken);
      } catch (error) {
        console.error('Error verifying token:', error);
        setIsTokenValid(false);
      }
    };

    verifyToken();
  }, []); // Only runs on mount

  return isTokenValid;
};

export default useVerifyToken;