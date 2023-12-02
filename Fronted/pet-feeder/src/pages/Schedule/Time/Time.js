import React, { useState, useEffect } from 'react';
import NavBar from '../../../components/Navbar/Navbar';
import axios from '../../../api/axios';
import { useParams } from 'react-router-dom';

const Time = () => {
  const { scheduleId, scheduleLabel } = useParams();
  const [timeLabel, setTimeLabel] = useState('');
  const [time, setTime] = useState('');
  const [loading, setLoading] = useState(false);
  const [times, setTimes] = useState([]);

  const handleAddTime = async () => {
    try {
      setLoading(true);

      const response = await axios.post(`/time/${scheduleId}`, {
        scheduleId: scheduleId,
        timeLabel: timeLabel,
        time: time,
      });

      console.log('Time added successfully:', response.data);

      // Update the list of times
      fetchTimes();

      // You can perform any additional actions after successful time addition.

    } catch (error) {
      console.error('Error adding time:', error);

      // Handle errors or show a user-friendly message.
    } finally {
      setLoading(false);
    }
  };

  const fetchTimes = async () => {
    try {
      const response = await axios.get(`/time/schedule/${scheduleId}`, {
        scheduleId: scheduleId
      });
      setTimes(response.data);
    } catch (error) {
      console.error('Error fetching times:', error);
    }
  };

  useEffect(() => {
    // Fetch times when the component mounts
    fetchTimes();
  }, [scheduleId]);

  const inputStyle = {
    fontFamily: 'Poppins, sans-serif',
    fontSize: '16px',
    padding: '8px',
    marginBottom: '10px', // Add marginBottom to create space between inputs
    borderRadius: '4px',
    border: '1px solid #BBB',
    backgroundColor: 'white',
    color: '#06350D',
    width: '100%',
    maxWidth: '550px',
  };
  
  const timeInputStyle = {
    ...inputStyle, // Include the common styles
    marginTop: '0', 
    maxWidth: "150px"
  };

  const buttonStyle = {
    fontFamily: 'Poppins, sans-serif',
    fontSize: '16px',
    padding: '10px',
    borderRadius: '4px',
    border: 'none',
    backgroundColor: '#AAC88F',
    color: '#fff',
    cursor: 'pointer',
    width: '100%', // Added width for responsiveness
    maxWidth: "100px",
    
  };

  return (
    <div>
      <NavBar />
      <div style={{ backgroundColor: '#06350D', height: '20vh', width: '100%', position: 'absolute', zIndex: -1, display: 'flex', justifyContent: 'center', alignItems: 'center' }}>
        <h1 style={{ fontFamily: "'Poppins', sans-serif", color: '#fff', fontSize: '18px' }}>FEEDING SCHEDULE</h1>
      </div>
      <div style={{ marginTop: '20vh', padding: '0 20px', boxSizing: 'border-box' }}>
        <div style={{marginTop: "230px"}}>
          <h3 >
            Add a Time to <span style={{ color: '#AAC88F' }}>{scheduleLabel}</span> Schedule
          </h3>
        </div>

      

{/* Add Time Form */}
<div style={{ marginBottom: '20px' }}>
  <div style={{ marginBottom: '10px' }}>
    <label htmlFor="timeLabel">Time Label:</label>
    <br />
    <input
      type="text"
      id="timeLabel"
      value={timeLabel}
      onChange={(e) => setTimeLabel(e.target.value)}
      style={inputStyle}
    />
  </div>

  <div style={{ marginBottom: '10px' }}>
    <label htmlFor="time">Time:</label>
    <br />
    <input
      type="time"
      id="time"
      value={time}
      onChange={(e) => setTime(e.target.value)}
      style={timeInputStyle}
      step="1"
    />
  </div>
</div>


<button onClick={handleAddTime} disabled={loading} style={buttonStyle}>
  {loading ? 'Adding...' : 'Add'}
</button>

        {/* Display created times */}
        <div style={{ marginTop: '20px', display: 'flex', flexWrap: 'wrap' }}>
  {times.map((timeItem, index) => (
    <div key={timeItem.timeId} className="card-container" style={{ marginBottom: '10px', marginRight: '10px', width: '300px' }}>
      <div className="card" style={{ width: '100%' }}>
        <div className="card-body">
          <h5 className="card-title">{timeItem.timeLabel}</h5>
          <p className="card-text">{timeItem.time}</p>
        </div>
      </div>
    </div>
  ))}
</div>




      </div>
    </div>
  );
};

export default Time;
