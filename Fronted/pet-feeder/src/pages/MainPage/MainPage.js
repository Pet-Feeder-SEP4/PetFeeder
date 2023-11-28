import React, { useState, useEffect } from 'react';
import  './MainPage.css';
import axios from "../../api/axios";
import PetFeeders from '../../components/PetFeeders/PetFeeders';




const MainPage = () => {
    return (
      <div>
        <div className="button-container">
          <button className="top-button">ADD NEW PET</button>
          <button className="top-button">ADD NEW FEEDER</button>
        </div>
  
        <PetFeeders />
      </div>
    );
  };
  
  export default MainPage;
  

