import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Modal, Button } from 'react-bootstrap';
import 'bootstrap/dist/css/bootstrap.min.css';
import useVerifyToken from '../../../hooks/useVerifyToken';

function ActivationModal({ showModal, handleClose }) {
  const isTokenValid = useVerifyToken();
  const [activationCode, setActivationCode] = useState('');

  const fontFamilyStyles = {
    fontFamily: 'Poppins, sans-serif',
  };

  const activateAccount = async () => {
    const apiEndpoint = 'YOUR_API_ENDPOINT';

    try {
      const response = await axios.post(apiEndpoint, { activationCode });
      console.log('Activation Response:', response.data);
      // You may want to handle the activation success here, e.g., close the modal
      handleClose();
    } catch (error) {
      console.error('Activation Request Failed:', error.message);
    }
  };

  useEffect(() => {
    if (!isTokenValid) {
      console.log('Invalid token. Redirecting to login page.');
    }
  }, [isTokenValid]);

  return (
    <Modal show={showModal} onHide={handleClose} centered>
      <Modal.Header closeButton>
        <Modal.Title style={fontFamilyStyles}>Activation Code</Modal.Title>
      </Modal.Header>
      <Modal.Body  style={fontFamilyStyles}>
        <p style={{fontSize: '13px'}}>Insert the your Pet Feeder code below to link your pet feeder</p>
        <input
          type="text"
          className="form-control"
          value={activationCode}
          onChange={(e) => setActivationCode(e.target.value)}
        />
      </Modal.Body>
      <Modal.Footer>
        <Button variant="primary" onClick={activateAccount} style={{...fontFamilyStyles, backgroundColor: '#AAC88F', border: '1px solid #AAC88F'}}>
          Activate 
        </Button>
      </Modal.Footer>
    </Modal>
  );
}

export default ActivationModal;
