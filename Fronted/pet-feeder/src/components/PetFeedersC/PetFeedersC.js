import React, { useState, useEffect } from "react";
import axios from "../../api/axios";
import { Row, Col, Button } from "react-bootstrap";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faXmark, faBone } from "@fortawesome/free-solid-svg-icons";
import { Link } from "react-router-dom";
import PetAssociationModal from "../modals/Associate/AssociatePetModal";
import "./PetFeedersC.css";

const NotFound = () => (
  <div className="mt-4" style={{ fontFamily: "Poppins, sans-serif" }}>
    <h4 className="titlepet mt-2">Pet Feeders</h4>
    <h5 className="subtitlepet mt-2">No Pet Feeders Found</h5>
    <p className="sometext mt-2">
      It looks like you haven't added a pet feeder yet. To get started, click
      the button + FEEDER to add a new pet feeder to your account.
    </p>
  </div>
);

const PetFeedersC = ({ userPetFeeders, setUserPetFeeders }) => {
  const userId = localStorage.getItem("userId");
  const [loading, setLoading] = useState(true);
  const [showModal, setShowModal] = useState(false);
  const [selectedPetFeeder, setSelectedPetFeeder] = useState(null);
  const [userPets, setUserPets] = useState([]);

  useEffect(() => {
    const fetchPetFeeders = async () => {
      if (userId) {
        try {
          const response = await axios.get(`/petfeeder/connected/${userId}`);
          setUserPetFeeders(response.data);
          setLoading(false);
        } catch (error) {
          console.error("Error fetching pet feeders:", error);
        }
      }
    };

    fetchPetFeeders();
  }, [userId, setUserPetFeeders]);

  const handleRemovePetFeeder = async (petFeederId) => {
    try {
      await axios.delete(`/petfeeder/${petFeederId}`);
      setUserPetFeeders((prevPetFeeders) =>
        prevPetFeeders.filter(
          (petfeeder) => petfeeder.petFeederId !== petFeederId
        )
      );
    } catch (error) {
      console.error("Error removing pet feeder:", error);
    }
  };

  const handleShowModal = async (petFeederId) => {
    try {
      const response = await axios.get(`/pets/user/${userId}`);
      console.log("petFeederId", petFeederId); // Add this line
      console.log("userPets", response.data);
      setUserPets(response.data);
      setSelectedPetFeeder(petFeederId);

      setShowModal(true);
    } catch (error) {
      console.error("Error fetching user pets:", error);
    }
  };

  const handleHideModal = () => {
    setShowModal(false);
  };

  const handleAssociatePet = async (petId) => {
    try {
      const associatedPetId = petId.id;
      console.log(associatedPetId);
      console.log("hey", selectedPetFeeder);
      console.log("yo", petId);

      const url = `/petfeeder/${selectedPetFeeder}/addPet/${petId}`;
      console.log("Request URL:", url);
      await axios.post(`/petfeeder/${selectedPetFeeder}/addPet/${petId}`);
      // You may want to update the state or fetch pet feeders again after association
      setShowModal(false);
    } catch (error) {
      console.error("Error associating pet:", error);
    }
  };

  if (loading) {
    return <div>Loading pet feeders...</div>;
  }

  if (!userPetFeeders.length) {
    return <NotFound />;
  }

  return (
    <div className="petFeederContainer">
      <h1 className="myPetsfeedersTitle">Pet Feeders</h1>
      <Row className="petfeederlist">
        {userPetFeeders.map((petfeeder) => (
          <Col key={petfeeder.petFeederId} sm={6} md={4} lg={4}>
            <div className="text-center shadow-lg" id="petfeederItem">
              <button
                className="btn"
                id="top-remove-button"
                onClick={() => handleRemovePetFeeder(petfeeder.petFeederId)}
              >
                <FontAwesomeIcon
                  icon={faXmark}
                  style={{
                    color: "#06350D",
                    fontSize: "18px",
                    alignItems: "center",
                    justifyContent: "center",
                  }}
                />
              </button>
              <Link
                to={`/dashboard/${petfeeder.petFeederId}`}
                style={{ textDecoration: "none" }}
              >
                <h3 className="petFeederLabel">{petfeeder.petFeederLabel}</h3>
                <div className="subtitle-container">
                  <div
                    className={`status-indicator ${
                      petfeeder.active ? "online" : "offline"
                    }`}
                  ></div>
                  <h6 className="subtitle">
                    {petfeeder.active ? "Online" : "Offline"}
                  </h6>
                </div>
              </Link>
              <div>
                <FontAwesomeIcon
                  icon={faBone}
                  style={{ color: "#06350D", fontSize: "32px" }}
                />
              </div>
              <Button
                className="btn"
                id="bottom-button"
                onClick={() => handleShowModal(petfeeder.petFeederId)}
              >
                <span className="text-lg">Associate Pet</span>
              </Button>
            </div>
          </Col>
        ))}
      </Row>

      <PetAssociationModal
        show={showModal}
        onHide={handleHideModal}
        pets={userPets}
        associatePet={handleAssociatePet}
      />
    </div>
  );
};

export default PetFeedersC;
