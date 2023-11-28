
import React, { useState, useEffect } from 'react';
import axios from "../../api/axios";
import './PetFeeder.css';



const NotFound = () => (
    <div>
      <h1>Not Found FUCKERSSS</h1>
      {/* Add any additional content for the NotFound component */}
    </div>
  );
  

const PetFeeders = () => {
  const [data, setData] = useState ([]);
  const [loading, setLoading] = useState (true);
  const PETFEEDERS_URL = "/petfeeder/user/{petfeederId}";

  useEffect(() => {
  const fetchData = async ()=> {
    try {
      const response = await axios.get(PETFEEDERS_URL);
      setLoading (false);
      setData(response.data);
    } catch (error){
      console.error ('Error trying to fetch the data', error);
      setLoading(false);
    }
  };

  fetchData();
}, []); // The empty dependency array ensures that the effect runs only once when the component mounts

if (loading) {
  return <div>Loading...</div>;
}

if (!data.length) {
  return <NotFound />;
}
const workInfoData = [
    {
      title: "PET FEEDER NAME",
      subtitle: "online",
      src: `${process.env.PUBLIC_URL}/assets/feeder.png`
    },
    {
      title: "PET FEEDER NAME",
      subtitle: "offline",
      src: `${process.env.PUBLIC_URL}/assets/feeder.png`
    },
    {
      title: "PET FEEDER NAME",
      subtitle: "offline",
      src: `${process.env.PUBLIC_URL}/assets/feeder.png`
    },
  ];

  return (
    
      <div className="work-section-wrapper">
        <div className="work-section-top"></div>
        <div className="work-section-bottom">
          {workInfoData.map((data) => (
            <div className="work-section-info" key={data.title}>
              <div className="button-container">
                <button className="right-button">X remove</button>
              </div>
              <h2>{data.title}</h2>
              <div className="subtitle-container">
                <div className={`status-indicator ${data.subtitle}`}></div>
                <h3 className="subtitle">{data.subtitle}</h3>
              </div>
              <div className="info-boxes-img-container">
                <img src={data.image} alt="" />
              </div>
              <p>{data.text}</p>
              <button className="bottom-button">Associate a pet</button>
            </div>
          ))}
        </div>
      </div>
    
  );
};

export default PetFeeders;
