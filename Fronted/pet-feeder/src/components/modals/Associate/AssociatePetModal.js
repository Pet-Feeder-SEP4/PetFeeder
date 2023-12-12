import React, { useState } from "react";
import { Modal, Button } from "react-bootstrap";

const PetAssociationModal = ({ show, onHide, pets, associatePet }) => {
  const [selectedPetId, setSelectedPetId] = useState(null);

  const handleSelectChange = (event) => {
    setSelectedPetId(event.target.value);
  };

  const handleAssociatePet = () => {
    if (selectedPetId) {
      associatePet(selectedPetId);
      onHide(); // Optionally close the modal after association
    } else {
      // Handle the case where no pet is selected
      console.error("Please select a pet before saving.");
    }
  };

  return (
    <Modal show={show} onHide={onHide}>
      <Modal.Header closeButton>
        <Modal.Title
          style={{ fontFamily: "Poppins, sans-serif", fontSize: "15px" }}
        >
          associate a pet
        </Modal.Title>
      </Modal.Header>
      <Modal.Body style={{ padding: "35px" }}>
        <p style={{ fontFamily: "Poppins, sans-serif" }}>
          Select a pet to associate:
        </p>
        <select
          className="custom-select"
          onChange={handleSelectChange}
          value={selectedPetId}
        >
          {pets.map((pet) => (
            <option key={pet.petId} value={pet.petId}>
              {pet.name}
            </option>
          ))}
        </select>
      </Modal.Body>
      <Modal.Footer>
        <Button
          variant="primary"
          onClick={handleAssociatePet}
          style={{ backgroundColor: "#AAC88F", border: " 1px solid #AAC88F" }}
        >
          Save
        </Button>
      </Modal.Footer>
    </Modal>
  );
};

export default PetAssociationModal;
