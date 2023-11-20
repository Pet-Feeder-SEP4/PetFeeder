// useVerifyToken.js
import { useEffect, useState } from 'react';
import AsyncStorage from 'react-native';

const useVerifyToken = () => {
  const [isTokenValid, setIsTokenValid] = useState(null);

  useEffect(() => {
    const verifyToken = async () => {
      try {
        const storedToken = await AsyncStorage.getItem('token');
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