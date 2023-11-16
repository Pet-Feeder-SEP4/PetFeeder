// HeroSection.js
import React, { useState, useEffect } from 'react';
import './HeroSection.css';

const HeroSection = () => {
    const [isContentVisible, setContentVisibility] = useState(false);

    useEffect(() => {
        setContentVisibility(true);
    }, []);

    return (
        <div className="hero-section">
            <div className={`hero-content ${isContentVisible ? 'fadeInRight' : ''}`}>
                <h1 className="display-5">Forgot to feed your pet again? Try PetFeeder!</h1>
                <p className="lead">Automated feeding station for your best friend.</p>
                <button type="button" className="btn btn-light btn-sm">Log in</button>
                <button type="button" className="btn btn-light btn-sm ms-2">Sign up</button>
            </div>
            <div className="logo-container">
                <img
                    src={`${process.env.PUBLIC_URL}/assets/PetWhite.png`}
                    alt="Logo"
                    className="logo"
                />

            </div>
        </div>
    );
}

export default HeroSection;
