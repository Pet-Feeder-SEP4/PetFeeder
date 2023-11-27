import React, { useState, useEffect } from 'react';
import feeder from '../../Assets/feeder.png';
import NotFound from '../../components/NotFound';

import './MainPage.css';

const MainPage = () => {
  const [workInfoData, setWorkInfoData] = useState([]);
  const [error, setError] = useState(null);

  // Simulate fetching data (replace this with your actual data fetching logic)
  const fetchData = () => {
    // Simulate an error by uncommenting the next line
    // throw new Error("Data not available");
    return [
      {
        title: "PET FEEDER NAME",
        subtitle: "online",
        image: feeder,
      },
      {
        title: "PET FEEDER NAME",
        subtitle: "offline",
        image: feeder,
      },
      {
        title: "PET FEEDER NAME",
        subtitle: "offline",
        image: feeder,
      },
    ];
  };

  useEffect(() => {
    try {
      const data = fetchData();
      setWorkInfoData(data);
    } catch (error) {
      console.error(error.message);
      setError(error);
    }
  }, []);

  // Display the "Not Found" page if there is an error or no data
  if (error || workInfoData.length === 0) {
    return <NotFound />;
  }

  return (
    <div>
      <div className="button-container">
        <button className="top-button">ADD NEW PET</button>
        <button className="top-button">ADD NEW FEEDER</button>
      </div>

      <div className="work-section-wrapper">
        <div className="work-section-top"></div>
        <div className="work-section-bottom">
          {workInfoData.map((data) => (
            <div className="work-section-info" key={data.title}>
              {/* rest of your component */}
            </div>
          ))}
        </div>
      </div>
    </div>
  );
};

export default MainPage;
