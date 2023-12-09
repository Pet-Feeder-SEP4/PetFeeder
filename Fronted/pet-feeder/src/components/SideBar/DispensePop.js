import React, { useState } from 'react';
import { Modal, Button } from 'react-bootstrap';

const DispensePop = ({ onClose, onDispense }) => {
  const [portionSize, setPortionSize] = useState('');
  

  const fontFamilyStyles = {
    fontFamily: 'Poppins, sans-serif',
  };

  const handleDispense = () => {
    onDispense(portionSize);
    onClose();
  };

  return (
    <Modal show={true} onHide={onClose} centered > 
      <Modal.Header closeButton>
        <Modal.Title style={fontFamilyStyles}>DISPENSE NOW</Modal.Title>
      </Modal.Header>
      <Modal.Body style={fontFamilyStyles}>
        <h4 style={{fontSize: '18px'}}>Enter Portion Size</h4>
        <input style={{fontSize: '15px'}}
          type="text"
          className="form-control"
          value={portionSize}
          onChange={(e) => setPortionSize(e.target.value)}
          placeholder="Enter portion size in grams (g), three digits"
        
        />
      </Modal.Body>
      <Modal.Footer style={fontFamilyStyles}>
        <Button className='buttonDispense' onClick={handleDispense} style={{...fontFamilyStyles, backgroundColor: '#AAC88F', border: '1px solid #AAC88F'}}>
          Dispense
        </Button>
        <Button onClick={onClose} style={{...fontFamilyStyles, backgroundColor: '#ff0100', border: '1px solid #ff0000'}}> 
          Close
        </Button>
      </Modal.Footer>
    </Modal>
  );
};

export default DispensePop;
