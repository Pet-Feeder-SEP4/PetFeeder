import React, { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import SideBar from "../../components/SideBar/SideBar";
import NavBar from "../../components/Navbar/Navbar";
import DispensePop from "../../components/SideBar/DispensePop";
import axios from "../../api/axios";

import WaterTemp from '../../components/WaterTemp/WaterTemp';
import { Col, Row } from 'react-bootstrap';
import EditNotifications from '../../components/modals/EditNotifications/EditNotificationsModal';
import FoodHum from '../../components/FoodHum/FoodHum';
import FoodLevel from '../../components/FoodLevel/FoodLevel';
import DashChart from '../../components/DashChart/DashChart';
import WaterLevel from '../../components/WaterLevel/WaterLevel';
import './Dashboard.css'



const Dashboard = () => {
  const { petFeederId } = useParams();
  const [isPopupVisible, setPopupVisible] = useState(false);
  const [isModalVisible, setModalVisible] = useState(false);
  const [notificationData, setNotificationData] = useState({
    notificationId: 0,
    foodLevel: 0,
    foodHumidity: 0,
    waterTemperture: 0,
    waterLevel: 0,
    active: true,
    petFeederId: 0,
    petFeeder: 0,
  });

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await axios.get(
          `/notifications/pet-feeder/${petFeederId}`
        );
        setNotificationData(response.data);
      } catch (error) {
        console.error("Error fetching notification data:", error);
      }
    };
    fetchData();
  }, [petFeederId]);

  const [petFeederData, setPetFeederData] = useState(null);
  useEffect(() => {
    const socket = new WebSocket(
      "wss://petfeederapi.azurewebsites.net/petfeeder/info/" + petFeederId
    );

    socket.addEventListener("open", (event) => {
      console.log("WebSocket connection opened:", event);
    });

    socket.addEventListener("message", (event) => {
      const data = JSON.parse(event.data);
      setPetFeederData(data);
    });

    socket.addEventListener("close", (event) => {
      console.log("WebSocket connection closed:", event);
    });

    return () => {
      socket.close();
    };
  }, [petFeederId]);

  const handleDispenseClick = () => {
    setPopupVisible(true);
    console.log("Popup visible:", isPopupVisible);
  };

  const handleClosePopup = () => {
    setPopupVisible(false);
  };

  const handleDispense = async (portionSize) => {
    const formData = {
      portionSize: portionSize,
      petFeederId: petFeederId,
    };
    console.log("data:", formData);
    try {
      const response = await axios.post(
        `petfeeder/sendPortion/${petFeederId}/${portionSize}`,
        formData
      );
      console.log("data2:", formData);
      console.log("Dispense successful:", response.data);
      alert("Dispense successful!");
    } catch (error) {
      console.error("Error dispensing:", error);
      alert("Error dispensing. Please try again.");
    }
  };

  const handleEditClick = () => {
    setModalVisible(true);
  };

  const handleCloseModal = () => {
    setModalVisible(false);
  };

  const handleSaveChanges = async (formData) => {
    try {
      await axios.put(`/notifications/${formData.notificationId}`, formData);
      alert("Changes saved successfully!");
      handleCloseModal();
    } catch (error) {
      console.error("Error saving changes:", error);
      alert("Error saving changes. Please try again.");
    }
  };

  const handleActivate = async (notificationId) => {
    try {
      const response = await axios.put(
        `/notifications/${notificationId}/activate`
      );
      setNotificationData((prevData) => ({ ...prevData, active: true }));
      alert("Notification activated successfully!");
      console.log(response.data);
    } catch (error) {
      console.error("Error activating notification:", error);
      alert("Error activating notification. Please try again.");
    }
  };

  const handleDeactivate = async (notificationId) => {
    try {
      await axios.put(`/notifications/${notificationId}/deactivate`);
      setNotificationData((prevData) => ({ ...prevData, active: false }));
      alert("Notification deactivated successfully!");
    } catch (error) {
      console.error("Error deactivating notification:", error);
      alert("Error deactivating notification. Please try again.");
    }
  };

  return (
    <>
      <NavBar />
      {isPopupVisible && (
        <DispensePop onClose={handleClosePopup} onDispense={handleDispense} />
      )}

      {isModalVisible && (
        <EditNotifications
          onClose={handleCloseModal}
          onSaveChanges={handleSaveChanges}
          onActivate={handleActivate}
          onDeactivate={handleDeactivate}
          petFeederId={petFeederId}
          notificationData={notificationData}
        />
      )}

      <div className="dash container-fluid">
        <Row className="dashrow">
          <Col xs={2} className="sideCol">
            <SideBar
              onDispenseClick={handleDispenseClick}
              onEditClick={handleEditClick}
            />
          </Col>
          <Col xs={10} >
            <div className="container-upper mt-5">
              <Row className="upper">
                <Col xxl={3} xl={6} lg={6} md={9} sm={12} className="waterTemp">
                  <WaterTemp petFeederData={petFeederData} />
                </Col>
                <Col  xxl={3} xl={6}  md={9} sm={12} className="foodHum">
                  <FoodHum petFeederData={petFeederData} />
                </Col>
                <Col  xxl={3} xl={6}  md={9} sm={12} className="waterLevel">
                  <WaterLevel petFeederData={petFeederData} />
                </Col>
                <Col  xxl={3} xl={6}  md={9} sm={12} className="foodLevel">
                  <FoodLevel petFeederData={petFeederData} />
                </Col>
              </Row>
            </div>

            <div className="container-upper mt-5">
              <Row className="lower">
                <Col xs={12} className="chartt mt-5">
                  <DashChart petFeederId={petFeederId} />
                </Col>
              </Row>
            </div>
          </Col>
        </Row>
      </div>
    </>
  );
};

export default Dashboard;