import React from 'react';
import './HeroSection.css'; 

const HeroSection = () => {
  return (
    <div className="hero-section">
      <div className="hero-content">
        <h1 className="display-3 ">Forgot to feed your pet again? Try PetFeeder!</h1>
        <p className="display-6">Automated feeding station for your best friend.</p>
        <button type="button" className="btn btn-light ">Log in</button>
        <button type="button" className="btn btn-light ms-2 ">Sign up</button>
      </div>
    </div>
  );
}

export default HeroSection;
