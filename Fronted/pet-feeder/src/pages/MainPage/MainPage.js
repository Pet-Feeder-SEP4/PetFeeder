import React from 'react';
import PetFeedersC from '../../components/PetFeedersC/PetFeedersC';
import MyPets from '../../components/MyPets/MyPets';
import NavBar from '../../components/Navbar/Navbar';
import { Link, useNavigate } from 'react-router-dom';
import useVerifyToken from '../../hooks/useVerifyToken'; 
import './MainPage.css'; // Import the CSS file for styling

const MainPage = () => {

  const navigate = useNavigate();
  const isTokenValid = useVerifyToken();

  if (isTokenValid === null) {
    // You can show a loading spinner or some other indication while verifying the token
    return <p>Loading...</p>;
  }

  if (!isTokenValid) {
    // Redirect to login page if token is not valid
    navigate('/Login');
    return null;
  }

  return (
    <>
      <NavBar />
      <div className="MainContainer">
        {/* Buttons with absolute positioning */}
        <div className="buttonsinMain">
          <Link to={`/putActivationPageLink/`}>
            <button type="button"  className="btn btn-success " id="btnAdd">
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
          <div className="Feeder-column col-8">
            <PetFeedersC />
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

