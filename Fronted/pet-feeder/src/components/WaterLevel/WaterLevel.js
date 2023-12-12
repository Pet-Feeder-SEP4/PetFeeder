import React from 'react';
import './WaterLevel.css'; 

const WaterLevel = ({ petFeederData }) => {
    return (
        <div className="levelwater-card mt-3 ">
            <div className="levelwater-card-header rounded-top">
                Water Level
            </div>
            <div className="levelwater-card-body">
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
                                {petFeederData.waterLevel}%
                            </div>


                            <div
                                className="progress-bar progress-bar-striped progress-bar-animated"
                                role="progressbar"
                                style={{ width: `${petFeederData.waterLevel}%`, backgroundColor: '#AAC88F' }}
                                aria-valuenow={petFeederData.waterLevel}
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

export default WaterLevel;
