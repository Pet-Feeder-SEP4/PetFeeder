import React from "react";
import { Modal, Button } from "react-bootstrap";

const ConnectionModal = ({
  showModal,
  handleCloseModal,
  setPetFeederId,
  handlePetFeederSubmit,
}) => {
  const handleInputChange = (e) => {
    setPetFeederId(e.target.value);
  };

  const fontFamilyStyles = {
    fontFamily: "Poppins, sans-serif",
  };

  return (
    <Modal show={showModal} onHide={handleCloseModal} centered>
      <Modal.Header closeButton>
        <Modal.Title style={{ ...fontFamilyStyles, fontSize: "15px" }}>
          add a pet feeder
        </Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <div className="container">
          <div className="row">
            <div className="col-md-12">
              <div className="form-group">
                <label htmlFor="petFeederID" style={fontFamilyStyles}>
                  Pet Feeder ID Code
                </label>
                <input
                  type="text"
                  className="form-control"
                  id="petFeederName"
                  placeholder="********"
                  onChange={handleInputChange}
                />
              </div>
            </div>
          </div>
        </div>
      </Modal.Body>
      <Modal.Footer>
        <Button
          style={{
            ...fontFamilyStyles,
            backgroundColor: "#AAC88F",
            border: "1px solid #AAC88F",
            fontSize: "12px",
          }}
          onClick={handlePetFeederSubmit} // Call the submit function
        >
          add
        </Button>
      </Modal.Footer>
    </Modal>
  );
};

export default ConnectionModal;
