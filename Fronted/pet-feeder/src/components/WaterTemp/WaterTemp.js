import React from 'react';
import './WaterTemp.css'; 

const WaterTemp = ({ petFeederData }) => {
  return (
    <div className="custom-card mt-3">
      <div className="custom-card-header rounded-top">
        Water Temperature
      </div>
      <div className="custom-card-body">
        {petFeederData && (
          <div>
            <h2 className="custom-card-title text-black text-center">
              {petFeederData.waterTemperture}&deg;C
            </h2>
          </div>
        )}
      </div>
    </div>
  );
};

export default WaterTemp;
