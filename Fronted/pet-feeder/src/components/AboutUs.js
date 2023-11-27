import React from 'react';
import calendar from '../../Assets/calendar.png';
import animal from '../../Assets/animal.png';
import Crayon from '../../Assets/crayon.png';
import './AboutPage.css';


const Aboutpage = () => {
    const workInfoData = [
      {
        image: calendar,
        title: "Feeding schedules",
        text: "Simplifying pet feeding routines through meticulously designed diets.",
      },
      {
        image: Crayon,
        title: "Feeding sessions",
        text: "At any time, through the web application, in case you want to give the pet an extra meal.",
      },
      {
        image: animal,
        title: "Multiple pets at once",
        text: "Do not forget your multiple friends, add them with all their details to get a specific diet!",
      },
    ];
    return (
      <div className="work-section-wrapper">
  <div className="work-section-top">
    <h1 className="primary-heading white-text">How It Works</h1>
    <p className="primary-text white-text">
    With the ability to set multiple feeding schedules, specify portion sizes, and trigger extra meals manually, our system provides the utmost flexibility and customization
    </p>
  </div>
  <div className="work-section-bottom">
    {workInfoData.map((data) => (
      <div className="work-section-info white-text" id="card-about" key={data.title}>
        <div className="info-boxes-img-container">
          <img src={data.image} alt="" />
        </div>
        <h2>{data.title}</h2>
        <p>{data.text}</p>
      </div>
    ))}
  </div>
</div>


    );
  };
  
  export default Aboutpage;