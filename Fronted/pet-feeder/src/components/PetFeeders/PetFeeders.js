
import React, { useState, useEffect } from 'react';
import axios from "../../api/axios";
import './PetFeeders.css';



const NotFound = () => (
    <div>
      <h1>Not Found FUCKERSSS</h1>
      {/* Add any additional content for the NotFound component */}
    </div>
  );
  

const PetFeeders = () => {
  const [data, setData] = useState ([]);
  const [loading, setLoading] = useState (true);
  const PETFEEDERS_URL = "/petfeeder/";
  // todo add Marta's token here in the const token and then put this config inside of every api call (wherever there is axios, add config
  const token = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjQ5LCJzdWIiOiJsYXJlYkB2aWEuZGsiLCJpYXQiOjE3MDEyODUxOTgsImV4cCI6MTcwMTI4ODc5OH0.okURAoeiewc8CLA94Nj6r-6QUORNFjBeuVjb3wj_Mek";

  const url = "https://peefee.azurewebsites.net/petfeeder/";

  const config = {
    headers: { Authorization: `Bearer ${token}` }
  };

  // useEffect is the first thing that happens when a component is rendered
  useEffect(() => {
  const fetchData = async ()=> {
    try {
      // const response = await axios.get(PETFEEDERS_URL, config);
      const response = await fetch(url, {
        method: "GET",
        headers: {
        "Content-Type": "application/json",
        "Authorization": `Bearer ${token}`
        },
      });

      const data = await response.json();
      setLoading (false);
      setData(data);
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
    
      <div className="petfeeder-section-wrapper">
        <div className="petfeeder-section-top"></div>
        <div className="petfeeder-section-bottom">
          {workInfoData.map((data) => (
            <div className="petfeeder-section-info" key={data.title}>
              <div className="button-container">
                <button className="right-button">X</button>
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
