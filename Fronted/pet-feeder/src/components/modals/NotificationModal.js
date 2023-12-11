// NotificationModal.js
import React from 'react';
import Modal from 'react-bootstrap/Modal';

const NotificationModal = ({ show, handleClose, notifications }) => {
  const modalBodyStyle = {
    maxHeight: '400px', // Set the maximum height for the modal body
    overflowY: 'auto', 
    fontFamily: 'Poppins'
    
  };

  const titleStyle = {
    borderBottom: '1px solid #ccc', // Add a line under the modal title
    paddingBottom: '10px',           // Add some bottom padding to the modal title
    boxShadow: '0 2px 4px rgba(0, 0, 0, 0.1)', 
    fontFamily: 'Poppins'
  };

  return (
    <Modal show={show} onHide={handleClose}>
      <Modal.Header closeButton style={titleStyle}>
        <Modal.Title>Notifications</Modal.Title>
      </Modal.Header>
      <Modal.Body style={modalBodyStyle}>
        {notifications.length > 0 ? (
          <div>
            {notifications.map((notification, index) => (
              <div key={index} style={{ marginBottom: '10px', padding: '15px', borderBottom: '1px solid #E6E6E6' }}>
                {notification}
              </div>
            ))}
          </div>
        ) : (
          <p>No notifications received</p>
        )}
      </Modal.Body>
      <Modal.Footer>
        {/* Additional modal footer content if needed */}
      </Modal.Footer>
    </Modal>
  );
};

export default NotificationModal;
