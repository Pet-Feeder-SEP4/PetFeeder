import React from 'react';
import HeroSection from '../../components/HeroSection/HeroSection';
import PetFeederInfo from '../../components/PetFeederInfo/PetFeederInfo';
import Motto from '../../components/Motto/Motto';
import Aboutpage from '../../components/AboutUs';

const Welcome = () => {
  return (
    <>
    <HeroSection/>
    <PetFeederInfo/>
    <Aboutpage/>
    <Motto/>
    </>    
  );
};

export default Welcome;
