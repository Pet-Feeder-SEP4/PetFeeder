
import './PetFeedersC.css';
import React, { useState, useEffect } from 'react';
import axios from '../../api/axios';
import { Link } from 'react-router-dom';
import { Row, Col } from 'react-bootstrap'; // Import Bootstrap grid components
import 'bootstrap/dist/css/bootstrap.min.css'; // Import Bootstrap CSS
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faXmark } from '@fortawesome/free-solid-svg-icons';
import { faBone } from '@fortawesome/free-solid-svg-icons';
import { Button } from 'react-bootstrap';



const NotFound = () => (
  <div>
    <h4 className='titlepet'>Pet Feeders</h4>
    <h5 className='subtitlepet'>No Pet Feeders Found</h5>
    <p className='sometext'>It looks like you haven't added a pet feeder yet. To get started, click the button + FEEDER to add a new pet feeder to your account.</p>

    <img
      src="/assets/dog.png"
      alt="Dog"
      className="dogphoto"
    />
  </div>
);

const PetFeedersC = () => {
  const [userPetFeeders, setUserPetFeeders] = useState([]);
  const userId = localStorage.getItem('userId');
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchPetFeeders = async () => {
      if (userId) {
        try {
          const response = await axios.get(`/petfeeder/user/${userId}`);

          setUserPetFeeders(response.data);
          setLoading(false);
        } catch (error) {
          console.error('Error fetching pet feeders:', error);
        }
      }
    };

    fetchPetFeeders();
  }, [userId, userPetFeeders]);

  if (loading) {
    return <div>Loading pet feeders...</div>;
  }

  if (!userPetFeeders.length) {
    return <NotFound />;
  }

  return (
    <div className='petFeederContainer'>
      <h1 className='myPetsfeedersTitle'>Pet Feeders</h1>
      <Row className='petfeederlist'>
        {userPetFeeders.map((petfeeder) => (
          <Col key={petfeeder.petFeederId} sm={6} md={4} lg={4} >
            <Link to={`/dashboard/${petfeeder.petFeederId}`} style={{ textDecoration: "none" }}>
              <div className="text-center shadow-lg" id="petfeederItem">
                <div className="buttonactions-container">
                  <button className="btn" id="top-remove-button"> <FontAwesomeIcon icon={faXmark} style={{color: "#06350D", fontSize: "18px", alignItems: "center", justifyContent: "center" }}  /></button>
                </div>
                <h3 className='petFeederLabel'>{petfeeder.petFeederLabel}</h3>
                <div className="subtitle-container">
                  <div className={`status-indicator ${petfeeder.active ? 'online' : 'offline'}`}></div>
                  <h6 className="subtitle">{petfeeder.active ? 'Online' : 'Offline'}</h6>
                </div>
                <div>
            
                <FontAwesomeIcon icon={faBone} style={{color: "#06350D", fontSize: "32px" }}  />
                </div>
                <Button className="btn"  id="bottom-button" ><span className="text-lg">Associate Pet</span></Button>
              </div>
            </Link>
          </Col>
        ))}
      </Row>
    </div>
  );
};

export default PetFeedersC;
