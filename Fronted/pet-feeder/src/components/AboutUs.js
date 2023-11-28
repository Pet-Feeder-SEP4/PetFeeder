import React from 'react';
import './AboutUs.css';

const Aboutpage = () => {
  const workInfoData = [
    {
      image: '/assets/calendar.png',
      title: "Feeding schedules",
      text: "Simplifying pet feeding routines through meticulously designed diets.",
    },
    {
      image: '/assets/crayon.png',
      title: "Feeding sessions",
      text: "At any time, through the web application, in case you want to give the pet an extra meal.",
    },
    {
      image: '/assets/animal.png',
      title: "Multiple pets at once",
      text: "Do not forget your multiple friends, add them with all their details to get a specific diet!",
    },
  ];

  return (
    <div className="work-section-wrapper">
      <div className="work-section-top">
        <h1 className="primary-heading white-text">HOW IT WORKS</h1>
        <p className="txxxt white-text">
          With the ability to set multiple feeding schedules, specify portion sizes, and trigger extra meals manually, our system provides the utmost flexibility and customization
        </p>
      </div>
      <div className="work-section-bottom">
        {workInfoData.map((data) => (
          <div className="work-section-info white-text" id="card-about" key={data.title}>
            <div className="info-boxes-img-container">
              <img src={process.env.PUBLIC_URL + data.image} alt={`Icon for ${data.title}`} />
            </div>
            <h2 className='titttle'>{data.title}</h2>
            <p className='paraph'>{data.text}</p>
          </div>
        ))}
      </div>
    </div>
  );
};

export default Aboutpage;
