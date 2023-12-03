import React from 'react';
import PetFeedersC from '../../components/PetFeedersC/PetFeedersC';
import MyPets from '../../components/MyPets/MyPets';
import NavBar from '../../components/Navbar/Navbar';
import { Link } from 'react-router-dom';

const MainPage = () => {
  return (
    <>
      <NavBar />
      <div className="MainContainer">
        <div className='buttonsinMain '>
          <Link to={`/putActivationPageLink/`}>
          <button type="button" className="btnAddPetFeeder">
            + PETFEEDER
          </button>
          </Link>
          <Link to={`/CreatePet/`}>
          <button type="button" className="btnAddPet">
            + PET
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