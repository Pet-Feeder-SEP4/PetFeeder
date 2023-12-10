import { useState, useEffect } from 'react';
import { Modal, Form, Button } from 'react-bootstrap';
import axios from 'axios';

const EditNotifications = ({ onClose, petFeederId }) => {
  const [notificationData, setNotificationData] = useState({
    notificationId: 0,
    foodLevel: 0,
    foodHumidity: 0,
    waterTemperature: 0,
    waterLevel: 0,
    active: true,
    petFeederId: 0,
    petFeeder: 0,
  });

  const fontFamilyStyles = {
    fontFamily: 'Poppins, sans-serif',
  };

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await axios.get(`/notifications/pet-feeder/${petFeederId}`);
        setNotificationData(response.data);
      } catch (error) {
        console.error('Error fetching notification data:', error);
      }
    };

    fetchData();
  }, [petFeederId]);

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setNotificationData((prevData) => ({
      ...prevData,
      [name]: value,
    }));
  };

  const handleSaveChanges = async () => {
    try {
      // Make a PUT request to update the notification data
      await axios.put(`/notifications/${notificationData.notificationId}`, notificationData);
      alert('Changes saved successfully!');
      onClose(); // Close the modal after saving changes
    } catch (error) {
      console.error('Error saving changes:', error);
      alert('Error saving changes. Please try again.');
    }
  };

  const handleActivate = async () => {
    try {
      // Make a PUT request to activate the notification
      await axios.put(`/notifications/${notificationData.notificationId}/activate`);
      setNotificationData((prevData) => ({ ...prevData, active: true }));
      alert('Notification activated successfully!');
    } catch (error) {
      console.error('Error activating notification:', error);
      alert('Error activating notification. Please try again.');
    }
  };

  const handleDeactivate = async () => {
    try {
      // Make a PUT request to deactivate the notification
      await axios.put(`/notifications/${notificationData.notificationId}/deactivate`);
      setNotificationData((prevData) => ({ ...prevData, active: false }));
      alert('Notification deactivated successfully!');
    } catch (error) {
      console.error('Error deactivating notification:', error);
      alert('Error deactivating notification. Please try again.');
    }
  };

  return (
    <Modal show={true} onHide={onClose} centered>
      <Modal.Header closeButton>
        <Modal.Title style={fontFamilyStyles}>EDIT NOTIFICATIONS</Modal.Title>
      </Modal.Header>
      <Modal.Body style={fontFamilyStyles}>
        <Form>
          <Form.Group controlId="formFoodLevel">
            <Form.Label>Food Level</Form.Label>
            <Form.Control
              type="number"
              name="foodLevel"
              value={notificationData.foodLevel}
              onChange={handleInputChange}
            />
          </Form.Group>
          <Form.Group controlId="formFoodHumidity">
            <Form.Label>Food Humidity</Form.Label>
            <Form.Control
              type="number"
              name="foodHumidity"
              value={notificationData.foodHumidity}
              onChange={handleInputChange}
            />
          </Form.Group>
          <Form.Group controlId="formWaterTemperature">
            <Form.Label>Water Temperature</Form.Label>
            <Form.Control
              type="number"
              name="waterTemperature"
              value={notificationData.waterTemperature}
              onChange={handleInputChange}
            />
          </Form.Group>
          <Form.Group controlId="formWaterLevel">
            <Form.Label>Water Level</Form.Label>
            <Form.Control
              type="number"
              name="waterLevel"
              value={notificationData.waterLevel}
              onChange={handleInputChange}
            />
          </Form.Group>
          <Form.Group controlId="formActive">
            <Form.Label>Notification Status</Form.Label>
            <div>
              {notificationData.active ? (
                <Button variant="danger" onClick={handleDeactivate}>
                  Deactivate
                </Button>
              ) : (
                <Button variant="success" onClick={handleActivate}>
                  Activate
                </Button>
              )}
            </div>
          </Form.Group>
        </Form>
      </Modal.Body>
      <Modal.Footer style={fontFamilyStyles}>
        <Button variant="primary" onClick={handleSaveChanges}>
          Save Changes
        </Button>
      </Modal.Footer>
    </Modal>
  );
};

export default EditNotifications;
