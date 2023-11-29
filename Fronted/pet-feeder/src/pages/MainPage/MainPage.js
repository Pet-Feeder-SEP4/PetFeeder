import React, { useState, useEffect } from 'react';
import  './MainPage.css';
import axios from "../../api/axios";
import PetFeeders from '../../components/PetFeeders/PetFeeders';


const MainPage = () => {
    return (
      <div className="main-page">
        <PetFeeders />
        <div className="side-thing">
        <div className="side-button-container">
          <button className="side-button">+ PET</button>
          <button className="side-button">+ FEEDER</button>
        </div>
        </div>
      </div>
    );
  };
  
  export default MainPage;
  

