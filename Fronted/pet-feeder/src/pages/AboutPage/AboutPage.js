import React from 'react';
import clock from '../../Assets/clock.png';
import Pickhand from '../../Assets/pickhand.png';
import Crayon from '../../Assets/crayon.png';
import './AboutPage.css';




const Aboutpage = () => {
    const workInfoData = [
      {
        image: Pickhand,
        title: "Pick Meals",
        text: "Lorem ipsum dolor sit amet consectetur. Maecenas orci et sagittis duis elementum interdum facilisi bibendum.",
      },
      {
        image: clock,
        title: "Choose time",
        text: " Lorem ipsum dolor sit amet consectetur. Maecenas orci et sagittis duis elementum interdum facilisi bibendum.",
      },
      {
        image: Crayon,
        title: "Edit feeders",
        text: " Lorem ipsum dolor sit amet consectetur. Maecenas orci et sagittis duis elementum interdum facilisi bibendum.",
      },
    ];
    return (
      <div className="work-section-wrapper">
  <div className="work-section-top">
    <p className="primary-subheading white-text">Work</p>
    <h1 className="primary-heading white-text">How It Works</h1>
    <p className="primary-text white-text">
      Lorem ipsum dolor sit amet consectetur. Non tincidunt magna non et elit. Dolor turpis molestie dui magnis facilisis at fringilla quam.
    </p>
  </div>
  <div className="work-section-bottom">
    {workInfoData.map((data) => (
      <div className="work-section-info white-text" key={data.title}>
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