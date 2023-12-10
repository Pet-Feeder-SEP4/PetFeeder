import React, { createContext, useContext, useEffect, useState } from 'react';
import io from 'socket.io-client';

const SocketContext = createContext();

export const useSocket = () => {
  return useContext(SocketContext);
};

export const SocketProvider = ({ children, petFeederId }) => {
  const [socket, setSocket] = useState(null);

  useEffect(() => {
    // Check if the petFeederId is available before creating the socket connection
    if (petFeederId) {
      const socketInstance = io('wss://petfeederapi.azurewebsites.net/petfeeder/info/', {
        query: { petFeederId },
        transports: ['websocket'],
      });

      // Log socket connection information
      
      console.log(`Socket ID: ${socketInstance.id}`);

      // Set up event listeners or additional configuration as needed

      setSocket(socketInstance);

      // Clean up the socket connection when the context is unmounted
      return () => {
        console.log(`Disconnecting socket with ID: ${socketInstance.id}`);
        socketInstance.disconnect();
      };
    }
  }, [petFeederId]);

  return (
    <SocketContext.Provider value={socket}>
      {children}
    </SocketContext.Provider>
  );
};


