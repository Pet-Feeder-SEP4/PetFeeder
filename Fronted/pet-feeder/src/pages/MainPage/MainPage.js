import React from 'react';
import PetFeedersC from '../../components/PetFeedersC/PetFeedersC';
import MyPets from '../../components/MyPets/MyPets';
import NavBar from '../../components/Navbar/Navbar';
import { Link } from 'react-router-dom';
import './MainPage.css'; // Import the CSS file for styling

const MainPage = () => {
  return (
    <>
      <NavBar />
      <div className="MainContainer">
        {/* Buttons with absolute positioning */}
        <div className="buttonsinMain">
          <Link to={`/putActivationPageLink/`}>
            <button type="button"  className="btn btn-success" id="btnAdd">
              + Pet Feeder
            </button>
          </Link>
          <Link to={`/CreatePet/`}>
            <button type="button" className="btn btn-success" id="btnAdd">
              + Pet
            </button>
          </Link>
        </div>

        <div className="row">
          <div className="Feeder-column col-lg-9">
            <PetFeedersC />
          </div>
          <div className="Pet-column col-lg-3">
            <MyPets />
          </div>
        </div>
      </div>
    </>
  );
};

export default MainPage;
