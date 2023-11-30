import React from 'react';
import NavBar from '../../components/Navbar/Navbar';

const FeedingSchedule = () => {
  const schedulestyle = {
    fontFamily: "'Poppins', sans-serif",
    color: '#fff', 
    fontSize: '18px',
    
  };
  return (
    <div>
      <NavBar />
      <div style={{ backgroundColor: '#06350D', height: '20vh', width: '100%', position: 'absolute', zIndex: -1, display: 'flex', justifyContent: 'center', alignItems: 'center' }}>
        <h1 style={schedulestyle}>FEEDING SCHEDULE</h1>
      </div>

      <div style={{ marginTop: '20vh', padding: '0 20px', boxSizing: 'border-box' }}>
       {/* add create schedule */}
      </div>
    </div>
  );
};

export default FeedingSchedule;
