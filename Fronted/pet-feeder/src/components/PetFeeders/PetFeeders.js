
import React, { useState, useEffect } from 'react';
import axios from "../../api/axios";
import './PetFeeders.css';



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
  

const PetFeeders = () => {
  const [data, setData] = useState ([]);
  const [loading, setLoading] = useState (true);
  const PETFEEDERS_URL = "/petfeeder/";
  // todo add Marta's token here in the const token and then put this config inside of every api call (wherever there is axios, add config
  const token = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjYyLCJzdWIiOiJob2xhMkB2aWEuZGsiLCJpYXQiOjE3MDEzNjM0NTEsImV4cCI6MTcwMTM2NzA1MX0.g0uMHetpAwG1SSqAr02v9EzgRJ1hsM0xEZxtvQ_598E";

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
      title: "Pet feeder name",
      subtitle: "online",
      src: `${process.env.PUBLIC_URL}/assets/feeder.png`
    },
    {
      title: "Pet feeder name",
      subtitle: "offline",
      src: `${process.env.PUBLIC_URL}/assets/feeder.png`
    },
    {
      title: "Pet feeder name",
      subtitle: "offline",
      src: `${process.env.PUBLIC_URL}/assets/feeder.png`
    },
    {
      title: "Pet feeder name",
      subtitle: "offline",
      src: `${process.env.PUBLIC_URL}/assets/feeder.png`
    },
    {
      title: "Pet feeder name",
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
              <button className="bottom-button">Associate pet</button>
            </div>
          ))}
        </div>
      </div>
    
  );
};

export default PetFeeders;
