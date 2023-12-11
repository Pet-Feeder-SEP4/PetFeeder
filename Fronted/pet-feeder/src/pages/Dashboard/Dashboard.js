
import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import SideBar from '../../components/SideBar/SideBar';
import NavBar from '../../components/Navbar/Navbar';
import DispensePop from '../../components/SideBar/DispensePop';
import axios from "../../api/axios";


const Dashboard = () => {
  const { petFeederId } = useParams();
  const [isPopupVisible, setPopupVisible] = useState(false);
  const token = localStorage.getItem('token');
  //for sockets
  const [petFeederData, setPetFeederData] = useState(null);
  useEffect(() => {
    const socket = new WebSocket('wss://petfeederapi.azurewebsites.net/petfeeder/info/' + petFeederId);
    //krissy my pissy
    socket.addEventListener('open', (event) => {
      console.log('WebSocket connection opened:', event);
    });

    socket.addEventListener('message', (event) => {
      const data = JSON.parse(event.data);
      setPetFeederData(data);
    });
    //krissy pissy make suree you call this if server crashes or something goes wrong 
    socket.addEventListener('close', (event) => {
      console.log('WebSocket connection closed:', event);
    });

    return () => {
      socket.close();
    };
  }, [petFeederId]);

  const handleDispenseClick = () => {
    setPopupVisible(true);
    console.log('Popup visible:', isPopupVisible);
  };

  const handleClosePopup = () => {
    setPopupVisible(false);
  };

  const handleDispense = async (portionSize) => {
    const formData = {
      portionSize: portionSize,
      petFeederId: petFeederId,
    };
    console.log('data:', formData);
    try {
      const response = await axios.post(
        `petfeeder/sendPortion/${petFeederId}/${portionSize}`,
        formData,
        {
          headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json',
          },
        }
      );
      console.log('data2:', formData);
      console.log('Dispense successful:', response.data);
      alert('Dispense successful!');
    } catch (error) {
      console.error('Error dispensing:', error);
      alert('Error dispensing. Please try again.');
    }
  };

  return (
    <>
      
        <NavBar />
        {isPopupVisible && (
          <DispensePop onClose={handleClosePopup} onDispense={handleDispense} />
        )}
        <div className='dash'>
          <div className='row dashrow'>
            <div className='col-2 sideCol'>
              <SideBar onDispenseClick={handleDispenseClick} />
            </div>
            <div className='col-10'>
              {petFeederData && (
                <div>
                  <h2>PetFeederDetailss</h2>
                  <p>PetFeederId: {petFeederData.petFeederId}</p>
                  <p>PetFeederLabel: {petFeederData.petFeederLabel}</p>
                  <p>FooodLevel: {petFeederData.foodLevel}</p>
                  <p>FoodHumidity: {petFeederData.foodHumidity}</p>
                  <p>WaterTempserature: {petFeederData.waterTemperture}</p>
                </div>
              )}
            </div>
          </div>
        </div>
     
    </>
  );
};

export default Dashboard;
