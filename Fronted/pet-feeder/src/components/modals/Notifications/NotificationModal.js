import React, { useState } from "react";
import Modal from "react-bootstrap/Modal";

const NotificationModal = ({ show, handleClose, notifications, targetRef }) => {
  const [hoveredIndex, setHoveredIndex] = useState(null);

  const modalBodyStyle = {
    maxHeight: "400px",
    overflowY: "auto",
    fontFamily: "Poppins",
  };

  const titleStyle = {
    borderBottom: "1px solid #E6E6E6",
    paddingBottom: "10px",
    fontFamily: "Poppins",
    fontSize: "13px",
  };

  const notificationItemStyle = {
    marginBottom: "10px",
    padding: "15px",
    borderBottom: "1px solid #E6E6E6",
    transition: "background-color 0.3s, border-radius 0.3s",
    borderRadius: "5px", // Set your desired border radius
  };

  const notificationItemHoverStyle = {
    backgroundColor: "#eee",
    borderRadius: "10px", // Set your desired border radius on hover
  };

  return (
    <Modal
      show={show}
      onHide={handleClose}
      dialogClassName="notification-modal"
    >
      <Modal.Header closeButton style={titleStyle}>
        <Modal.Title style={{ fontSize: "15px" }}>notifications</Modal.Title>
      </Modal.Header>
      <Modal.Body style={modalBodyStyle}>
        {notifications.length > 0 ? (
          <div>
            {notifications.map((notification, index) => (
              <div
                key={index}
                style={{
                  ...notificationItemStyle,
                  ...(index === hoveredIndex && notificationItemHoverStyle),
                }}
                onMouseEnter={() => setHoveredIndex(index)}
                onMouseLeave={() => setHoveredIndex(null)}
              >
                {notification}
              </div>
            ))}
          </div>
        ) : (
          <p>No notifications received</p>
        )}
      </Modal.Body>
    </Modal>
  );
};

export default NotificationModal;
