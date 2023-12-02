
import './PetFeedersC.css';
import React, { useState, useEffect } from 'react';
import axios from '../../api/axios';



//behaviour when it doesnt find the data

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
    const token = localStorage.getItem('token');
    const [loading, setLoading] = useState (true);

    useEffect(() => {

        const fetchPetFeeders = async () => {
            if (userId && token) {
                try {
                    const response = await axios.get(`/petfeeder/user/${userId}`, {
                        headers: {
                            'Authorization': `Bearer ${token}`,
                            'Content-Type': 'application/json',
                        },
                    });
                    

                    setUserPetFeeders(response.data); 
                    setLoading (false);
                    console.log('data:', response.data);
                    
                } catch (error) {
                    console.error('Error fetching pet feeders:', error);
                }
            }
        };

        fetchPetFeeders();

    }, [userId, token, userPetFeeders]); 

    if (loading) {
        return <div>Loading pet feeders...</div>;
      }
      
      if (!userPetFeeders.length) {
        return <NotFound />;
      }


    return (
        <div className='petListContainer'>
          <h1 className='myPetsTitle'>Pet Feeders</h1>
          <div className='petfeederlist'>
            {userPetFeeders.map((petfeeder) => (
              <div key={petfeeder.petFeederId} className="petItem">
                <div className="buttonactions-container">
                  <button className="top-right-button">X</button>
                </div>
                <h3>{petfeeder.petFeederLabel}</h3>
                <div className="subtitle-container">
            <div className={`status-indicator ${petfeeder.active ? 'online' : 'offline'}`}></div>
            <h6 className="subtitle">{petfeeder.active ? 'Online' : 'Offline'}</h6>
          </div>
                <div className="info-boxes-img-container">
              
                  <img src="\assets\feeder.png" alt="" />
                </div>
                <button className="bottom-button">Associate pet</button>
              </div>
            ))}
          </div>
        </div>
      );
    };

export default PetFeedersC;