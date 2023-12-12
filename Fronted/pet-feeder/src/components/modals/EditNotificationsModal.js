// EditNotifications.jsx
import React, { useState, useEffect } from "react";
import { Form, Button, Modal } from "react-bootstrap";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faTemperatureHalf } from "@fortawesome/free-solid-svg-icons";
import { faDroplet } from "@fortawesome/free-solid-svg-icons";
import { faBowlFood } from "@fortawesome/free-solid-svg-icons";
import { faGlassWater } from "@fortawesome/free-solid-svg-icons";

const EditNotifications = ({
  onClose,
  onSaveChanges,
  onActivate,
  onDeactivate,
  petFeederId,
  notificationData,
}) => {
  const [formData, setFormData] = useState(notificationData);
  const [isActive, setIsActive] = useState(false);

  const fontFamilyStyles = {
    fontFamily: "Poppins, sans-serif",
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
    <Modal
      show={true}
      onHide={onClose}
      centered
      dialogClassName="notification-modal"
    >
      <Modal.Header closeButton>
        <Modal.Title style={{ ...fontFamilyStyles, fontSize: "15px" }}>
          edit notifications
        </Modal.Title>
      </Modal.Header>
      <Modal.Body style={fontFamilyStyles}>
        <Form className="m-2">
          <Form.Group controlId="formFoodLevel">
            <div className="ml-2">
              <FontAwesomeIcon
                icon={faBowlFood}
                className=" me-2"
                style={{ color: "#AAC88F" }}
              />
              <Form.Label>Food Level</Form.Label>
            </div>
            <Form.Control
              type="number"
              name="foodLevel"
              value={formData.foodLevel}
              onChange={handleInputChange}
            />
          </Form.Group>
          <Form.Group controlId="formFoodHumidity">
            <div className="ml-2">
              <FontAwesomeIcon
                icon={faDroplet}
                className=" me-2"
                style={{ color: "#AAC88F" }}
              />
              <Form.Label>Food Humidity</Form.Label>
            </div>
            <Form.Control
              type="number"
              name="foodHumidity"
              value={formData.foodHumidity}
              onChange={handleInputChange}
            />
          </Form.Group>
          <Form.Group controlId="formWaterTemperature">
            <div className="ml-2">
              <FontAwesomeIcon
                icon={faTemperatureHalf}
                className=" me-2"
                style={{ color: "#AAC88F" }}
              />
              <Form.Label>Water Temperature</Form.Label>
            </div>
            <Form.Control
              type="number"
              name="waterTemperature"
              value={formData.waterTemperture}
              onChange={handleInputChange}
            />
          </Form.Group>
          <Form.Group controlId="formWaterLevel">
            <div className="ml-2">
              <FontAwesomeIcon
                icon={faGlassWater}
                className=" me-2"
                style={{ color: "#AAC88F" }}
              />
              <Form.Label>Water Level</Form.Label>
            </div>
            <Form.Control
              type="number"
              name="waterLevel"
              value={formData.waterLevel}
              onChange={handleInputChange}
            />
          </Form.Group>
          <Form.Group className="mt-3" controlId="formActive">
            <div>
              {isActive ? (
                <Button variant="dark" onClick={handleDeactivateClick}>
                  Deactivate
                </Button>
              ) : (
                <Button variant="light" onClick={handleActivateClick}>
                  Activate
                </Button>
              )}
              <div className="mt-2">
                <p>Current Status: {isActive ? "Active" : "Not Active"}</p>
              </div>
            </div>
          </Form.Group>
        </Form>
      </Modal.Body>
      <Modal.Footer style={fontFamilyStyles}>
        <Button
          style={{ backgroundColor: "#AAC88F", border: "1px solid #AAC88F" }}
          onClick={handleSaveChangesClick}
        >
          Save Changes
        </Button>
      </Modal.Footer>
    </Modal>
  );
};

export default EditNotifications;
