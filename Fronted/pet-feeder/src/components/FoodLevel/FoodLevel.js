import React from 'react';
import './FoodLevel.css'; // Make sure to create a CSS file for styling

const FoodLevel = ({ petFeederData }) => {
    return (
        <div className="level-card mt-3 ">
            <div className="level-card-header rounded-top">
                Food Level
            </div>
            <div className="level-card-body">
                {petFeederData && (
                    <div>
                        <div className="progress">

                            <div
                                className="progress-text"
                                style={{
                                    position: 'absolute',
                                    top: '50%',
                                    left: '50%',
                                    transform: 'translate(-50%, -50%)',
                                    zIndex: 2,
                                    fontSize: '40px',
                                }}
                            >
                                {petFeederData.foodLevel}%
                            </div>


                            <div
                                className="progress-bar"
                                role="progressbar"
                                style={{ width: `${petFeederData.foodLevel}%`, backgroundColor: '#AAC88F' }}
                                aria-valuenow={petFeederData.foodLevel}
                                aria-valuemin="0"
                                aria-valuemax="100"

                            />
                        </div>
                    </div>
                )}
            </div>
        </div>
    );
};

export default FoodLevel;
