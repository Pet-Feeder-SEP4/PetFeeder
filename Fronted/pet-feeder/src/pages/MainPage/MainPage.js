import React, { useState, useEffect } from 'react';
import  './MainPage.css';
import axios from "../../api/axios";
import PetFeeders from '../../components/PetFeeders/PetFeeders';
import MyPets from '../../components/MyPets';


const MainPage = () => {

  // do useEffect on the main page
    return (
      <div className="main-page">
        <PetFeeders />
        <MyPets/>
      </div>
    );
  };
  
  export default MainPage;
  

