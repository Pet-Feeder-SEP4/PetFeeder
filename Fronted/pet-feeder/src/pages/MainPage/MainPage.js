import React, { useState } from "react";
import PetFeedersC from "../../components/PetFeedersC/PetFeedersC";
import MyPets from "../../components/MyPets/MyPets";
import NavBar from "../../components/Navbar/Navbar";
import { Link, useNavigate } from "react-router-dom";
import useVerifyToken from "../../hooks/useVerifyToken";
import axios from "../../api/axios";

import "./MainPage.css";
import ConnectionModal from "../../components/modals/Connection/ConnectionModal";

const MainPage = () => {
  const navigate = useNavigate();
  const isTokenValid = useVerifyToken();

  const [showModal, setShowModal] = useState(false);
  const [petfeederId, setPetFeederId] = useState(""); // State to store the entered petFeederId
  const [userPetFeeders, setUserPetFeeders] = useState([]); // State to store the list of pet feeders

  const handleCloseModal = () => setShowModal(false);
  const handleShowModal = () => setShowModal(true);

  const handlePetFeederSubmit = async () => {
    const userId = localStorage.getItem("userId");

    try {
      const response = await axios.post(
        `/petfeeder/{petfeederId}/connected?userId=${userId}&petFeederId=${petfeederId}`
      );

      // Fetch the updated list of pet feeders after successfully adding a new one
      const updatedPetFeedersResponse = await axios.get(
        `/petfeeder/connected/${userId}`
      );
      setUserPetFeeders(updatedPetFeedersResponse.data);

      handleCloseModal();
    } catch (error) {
      console.error("Error making POST request", error.response);
      // Handle error as needed
    }
  };
  if (isTokenValid === null) {
    return <p>Loading...</p>;
  }

  if (!isTokenValid) {
    navigate("/LogIn");
    return null;
  }

  return (
    <>
      <NavBar />
      <div className="MainContainer">
        <div className="buttonsinMain">
          <button
            type="button"
            className="btn btn-success"
            id="btnAdd"
            onClick={handleShowModal}
          >
            + Pet Feeder
          </button>

          <Link to={`/CreatePet/`}>
            <button type="button" className="btn btn-success" id="btnAdd">
              + Pet
            </button>
          </Link>
        </div>

        <ConnectionModal
          showModal={showModal}
          handleCloseModal={handleCloseModal}
          setPetFeederId={setPetFeederId} // Pass the state setter function to the modal
          handlePetFeederSubmit={handlePetFeederSubmit} // Pass the submit function to the modal
        />

        <div className="row">
          <div className="Feeder-column col-8">
            <PetFeedersC
              userPetFeeders={userPetFeeders}
              setUserPetFeeders={setUserPetFeeders}
            />
          </div>
          <div className="Pet-column col">
            <MyPets />
          </div>
        </div>
      </div>
    </>
  );
};

export default MainPage;
