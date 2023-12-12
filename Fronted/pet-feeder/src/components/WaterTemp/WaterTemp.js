import React from 'react';
import './WaterTemp.css';

const WaterTemp = ({ petFeederData }) => {
  return (
    <div className="temp-card mt-3 ">
      <div className="temp-card-header rounded-top">
        Water Temperature
      </div>
      <div className="temp-card-body">
        {petFeederData && (
          <div>
            <h2 className="temp-card-title text-black text-center">
              {petFeederData.waterTemperture}&deg;C
            </h2>
          </div>
        )}
      </div>
    </div>
  );
};

export default WaterTemp;
