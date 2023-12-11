import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import SideBar from '../../components/SideBar/SideBar';
import NavBar from '../../components/Navbar/Navbar';
import DispensePop from '../../components/SideBar/DispensePop';
import axios from "../../api/axios";
import WaterTemp from '../../components/WaterTemp/WaterTemp';

const Dashboard = () => {
  const { petFeederId } = useParams();
  const [isPopupVisible, setPopupVisible] = useState(false);
  const token = localStorage.getItem('token');

  const [petFeederData, setPetFeederData] = useState(null);
  useEffect(() => {
    const socket = new WebSocket('wss://petfeederapi.azurewebsites.net/petfeeder/info/' + petFeederId);

    socket.addEventListener('open', (event) => {
      console.log('WebSocket connection opened:', event);
    });

    socket.addEventListener('message', (event) => {
      const data = JSON.parse(event.data);
      setPetFeederData(data);
    });

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
            <div class="container-upper mt-5">
              <div class="row upper">
                <div class="col-4 waterTemp ">
                  <WaterTemp petFeederData={petFeederData} />
                </div>
                <div class="col-4">
                  
                </div>
                <div class="col-4">
                  
                
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

    </>
  );
};

export default Dashboard;
