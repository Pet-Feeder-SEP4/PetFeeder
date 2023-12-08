import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import { Modal, Button } from 'react-bootstrap';
import NavBar from '../../components/Navbar/Navbar';
import PetFeedersC from '../../components/PetFeedersC/PetFeedersC';
import MyPets from '../../components/MyPets/MyPets';
import ActivationPage from '../../components/modals/Activation/ActivationModal';
import './MainPage.css';

const MainPage = () => {
  const [showModal, setShowModal] = useState(false);

  const handleShow = () => setShowModal(true);
  const handleClose = () => setShowModal(false);

  return (
    <>
      <NavBar />
      <div className="MainContainer">
        <div className="buttonsinMain">
          <Button id="btnAdd" onClick={handleShow}>
            + Pet Feeder
          </Button>
          <Link to={`/CreatePet/`}>
            <button type="button" className="btn btn-success" id="btnAdd">
              + Pet
            </button>
          </Link>
        </div>

        <div className="row">
          <div className="Feeder-column col-8">
            <PetFeedersC />
          </div>
          <div className="Pet-column col">
            <MyPets />
          </div>
        </div>
      </div>

      <ActivationPage showModal={showModal} handleClose={handleClose} />
    </>
  );
};

export default MainPage;
