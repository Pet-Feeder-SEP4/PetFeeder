import React, { useState, useEffect } from 'react';
import NavBar from '../../components/Navbar/Navbar';
import { useParams } from 'react-router-dom';
import axios from '../../api/axios';
import { Link } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faClock } from '@fortawesome/free-solid-svg-icons';

const FeedingSchedule = () => {
  const { petFeederId } = useParams();
  const userId = localStorage.getItem('userId');

  const [scheduleLabel, setScheduleLabel] = useState('');
  const [loading, setLoading] = useState(false);
  const [schedules, setSchedules] = useState([]);

  const handleCreateSchedule = async () => {
    try {
      setLoading(true);

      const response = await axios.post('/schedules', {
        scheduleLabel: scheduleLabel,
        userId: userId,
        petFeederId: petFeederId,
      });

      console.log('Schedule created successfully:', response.data);

      // Clear the input field
      setScheduleLabel('');

      // Update the list of schedules
      fetchUserSchedules();

      // You can perform any additional actions after successful schedule creation.

    } catch (error) {
      console.error('Error creating schedule:', error);

      // Handle errors or show a user-friendly message.
    } finally {
      setLoading(false);
    }
  };

  const fetchUserSchedules = async () => {
    try {
      const response = await axios.get(`/schedules/user/${userId}`);
      setSchedules(response.data);
    } catch (error) {
      console.error('Error fetching user schedules:', error);
    }
  };



  const schedulestyle = {
    fontFamily: 'Poppins, sans-serif',
    color: 'black',
    fontSize: '18px',
  };

  const inputStyle = {
    fontFamily: 'Poppins, sans-serif',
    fontSize: '16px',
    padding: '8px',
    marginRight: '8px',
    borderRadius: '4px',
    border: '1px solid #BBB',
    backgroundColor: 'white',
    color: '#06350D',
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
  };

  return (
    <div>
      <NavBar />
      <div style={{ backgroundColor: '#06350D', height: '20vh', width: '100%', position: 'absolute', zIndex: -1, display: 'flex', justifyContent: 'center', alignItems: 'center' }}>
        <h1 style={{fontFamily: "'Poppins', sans-serif",
    color: '#fff',
    fontSize: '18px',}}>FEEDING SCHEDULE</h1>
      </div>

      <div style={{ marginTop: '20vh', padding: '0 20px', boxSizing: 'border-box' }}>
        {/* Schedule Creation Form */}
        <div style={{ marginTop: '20px', display: 'flex', flexDirection: 'column' }}>
         
        <p htmlFor="scheduleLabel" style={{ ...schedulestyle, marginBottom: '10px', marginTop: '10px', fontSize:"20px", fontWeight:"500" }}>
            Create a Schedule
          </p>
          <label htmlFor="scheduleLabel" style={{ ...schedulestyle, marginBottom: '10px' }}>
            Schedule Label:
          </label>
           <input
            type="text"
            id="scheduleLabel"
            value={scheduleLabel}
            onChange={(e) => setScheduleLabel(e.target.value)}
            style={{ ...inputStyle, width: '250px', boxSizing: 'border-box', marginBottom: '10px' }}
            />
            <button onClick={handleCreateSchedule} disabled={loading} style={{ ...buttonStyle, width: '100%', maxWidth: '150px' }}>
              {loading ? 'Creating...' : 'Create'}
            </button>
        </div>
        {/* Display created schedules */}
        <div style={{ marginTop: '20px' }}>
          <h2 style={schedulestyle}>Your Schedules:</h2>
          <div>
            {schedules.map((schedule) => (
              <div key={schedule.scheduleId} style={{ marginBottom: '10px', border: '1px solid', backgroundColor:"white", borderColor: "#06350D", borderRadius:"4px", padding: '10px', width:"50%", display: "flex", justifyContent: "space-between", alignItems: "center", flexWrap: "wrap" }}>
                <div style={{ marginBottom: '10px', flexBasis: "calc(50% - 10px)" }}>
                  {schedule.scheduleLabel}
                </div>
                <Link to={`/add-time/${schedule.scheduleId}/${encodeURIComponent(schedule.scheduleLabel)}`} style={{ textDecoration: "none", boxSizing: "border-box", marginTop: '10px' }}>
                  <button title="Add Time" style={{ ...buttonStyle, backgroundColor: "transparent", color:"#06350D", borderColor:"#06350D", border:"1.5px solid" ,borderRadius:"16px", maxWidth: "200px", marginLeft: "auto" }}><span>+</span> <FontAwesomeIcon icon={faClock} style={{ marginRight: '5px' }} /></button>
                </Link>
              </div>
               
              ))}
          </div>
        </div>
        

        
      </div>
    </div>
  );
};

export default FeedingSchedule;