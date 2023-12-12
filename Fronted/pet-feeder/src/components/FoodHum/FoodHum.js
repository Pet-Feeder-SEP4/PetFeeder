import React from 'react';
import './FoodHum.css';

const FoodHum = ({ petFeederData }) => {
    return (
        <div className="hum-card mt-3 ">
            <div className="hum-card-header rounded-top">
                Food Humidity
            </div>
            <div className="hum-card-body">
                {petFeederData && (
                    <div>
                        <h2 className="hum-card-title text-black text-center">
                            {petFeederData.foodHumidity}&deg;C
                        </h2>
                    </div>
                )}
            </div>
        </div>
    );
};

export default FoodHum;
