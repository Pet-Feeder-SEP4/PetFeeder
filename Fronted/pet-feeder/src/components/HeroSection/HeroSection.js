// HeroSection.js
import React, { useState, useEffect } from 'react';
import './HeroSection.css';
import { Link } from 'react-router-dom';

const HeroSection = () => {
    const [isContentVisible, setContentVisibility] = useState(false);

    useEffect(() => {
        setContentVisibility(true);
    }, []);

    return (
        <div className="hero-section">
            <div className={`hero-content ${isContentVisible ? 'fadeInRight' : ''}`}>
                <h1 className="bigT">Forgot to feed your pet again? Try PetFeeder!</h1>
                <p className="smallT">Automated feeding station for your best friend.</p>
                <Link to="#"> {/* replace w lpgin*/}
                <button type="button" className="btnHero btn-light btn-sm">LOG IN</button>
                </Link>
                <Link to="/Register">
                <button type="button" className="btnHero btn-light btn-sm ms-2">SIGN UP</button>
                </Link>
                
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
