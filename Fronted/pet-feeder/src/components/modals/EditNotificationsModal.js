// EditNotifications.jsx
import React, { useState, useEffect } from 'react';
import { Modal, Form, Button } from 'react-bootstrap';

const EditNotifications = ({ onClose, onSaveChanges, onActivate, onDeactivate, petFeederId, notificationData }) => {
  const [formData, setFormData] = useState(notificationData);
  const [isActive, setIsActive] = useState(false);

  const fontFamilyStyles = {
    fontFamily: 'Poppins, sans-serif',
  };

  useEffect(() => {
    setIsActive(formData.active);
  }, [formData]);

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData((prevData) => ({
      ...prevData,
      [name]: value,
    }));
  };

  const handleSaveChangesClick = () => {
    onSaveChanges(formData);
  };

  const handleActivateClick = () => {
    onActivate(formData.notificationId);
    setIsActive(true);
  };

  const handleDeactivateClick = () => {
    onDeactivate(formData.notificationId);
    setIsActive(false);
  };

  return (
    <Modal show={true} onHide={onClose} centered>
      <Modal.Header closeButton>
        <Modal.Title style={fontFamilyStyles}>edit notidications</Modal.Title>
      </Modal.Header>
      <Modal.Body style={fontFamilyStyles}>
        <Form>
          <Form.Group controlId="formFoodLevel">
            <Form.Label>Food Level</Form.Label>
            <Form.Control
              type="number"
              name="foodLevel"
              value={formData.foodLevel}
              onChange={handleInputChange}
            />
          </Form.Group>
          <Form.Group controlId="formFoodHumidity">
            <Form.Label>Food Humidity</Form.Label>
            <Form.Control
              type="number"
              name="foodHumidity"
              value={formData.foodHumidity}
              onChange={handleInputChange}
            />
          </Form.Group>
          <Form.Group controlId="formWaterTemperature">
            <Form.Label>Water Temperature</Form.Label>
            <Form.Control
              type="number"
              name="waterTemperature"
              value={formData.waterTemperture}
              onChange={handleInputChange}
            />
          </Form.Group>
          <Form.Group controlId="formWaterLevel">
            <Form.Label>Water Level</Form.Label>
            <Form.Control
              type="number"
              name="waterLevel"
              value={formData.waterLevel}
              onChange={handleInputChange}
            />
          </Form.Group>
          <Form.Group className='mt-3' controlId="formActive">
            <div>
              {isActive ? (
                <Button variant="danger" onClick={handleDeactivateClick}>
                  Deactivate
                </Button>
              ) : (
                <Button variant="success" onClick={handleActivateClick}>
                  Activate
                </Button>
              )}
              <div className="mt-2">
                <p>Current Status: {isActive ? 'Active' : 'Not Active'}</p>
              </div>
            </div>
          </Form.Group>
        </Form>
      </Modal.Body>
      <Modal.Footer style={fontFamilyStyles}>
        <Button style={{backgroundColor: '#AAC88F', border: '1px solid #AAC88F'}} onClick={handleSaveChangesClick}>
          Save Changes
        </Button>
      </Modal.Footer>
    </Modal>
  );
};

export default EditNotifications;
