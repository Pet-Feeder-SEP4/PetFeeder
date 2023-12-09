/* eslint-disable react-hooks/rules-of-hooks */
import React, { useState, useEffect } from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faTimes } from '@fortawesome/free-solid-svg-icons';
import NavBar from '../../../components/Navbar/Navbar';
import axios from '../../../api/axios';
import { useParams, useNavigate } from 'react-router-dom';
import useVerifyToken from '../../../hooks/useVerifyToken'; // replace with the actual path

const Time = () => {
  const { scheduleId, scheduleLabel } = useParams();
  const [timeLabel, setTimeLabel] = useState('');
  const [time, setTime] = useState('');
  const [loadingAdd, setLoadingAdd] = useState(false);
  const [loadingRemove, setLoadingRemove] = useState(false);
  const [times, setTimes] = useState([]);
  const [portionSize, setPortionSize] = useState('');

  const isTokenValid = useVerifyToken();
  const navigate = useNavigate();

  useEffect(() => {
    if (isTokenValid === false) {
      // Redirect to login if the token is not valid
      navigate('/LogIn'); 
    } 
  }, [isTokenValid, navigate]);


  const handleAddTime = async () => {
    try {
      setLoadingAdd(true);

      if (!portionSize || isNaN(portionSize) || portionSize < 1 || portionSize > 999) {
        console.error('Portion size is not valid');
        return;
      }

      const response = await axios.post(`/time/${scheduleId}`, {
        scheduleId: scheduleId,
        timeLabel: timeLabel,
        time: time,
        portionSize: portionSize,
      });

      console.log('Data added successfully:', response.data);

      // Update the list of times
      fetchTimes();

      // You can perform any additional actions after successful time addition.

    } catch (error) {
      console.error('Error adding time:', error);
    } finally {
      setLoadingAdd(false);
    }
  };

  const handleRemoveTime = async (timeId) => {
    try {
      setLoadingRemove(true);

      // Call the endpoint to remove the specific time
      await axios.delete(`/time/${timeId}`);

      // Update the list of times
      fetchTimes();

      // Additional actions after successful removal can be performed here.

    } catch (error) {
      console.error('Error removing time:', error);
    } finally {
      setLoadingRemove(false);
    }
  };

  const fetchTimes = async () => {
    try {
      const response = await axios.get(`/time/schedule/${scheduleId}`);
      setTimes(response.data);
    } catch (error) {
      console.error('Error fetching times:', error);
    }
  };

  useEffect(() => {
    fetchTimes();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  const inputStyle = {
    fontFamily: 'Poppins, sans-serif',
    fontSize: '16px',
    padding: '8px',
    marginBottom: '10px',
    borderRadius: '4px',
    border: '1px solid #BBB',
    backgroundColor: 'white',
    color: '#06350D',
    width: '100%',
    maxWidth: '550px',
  };

  const timeInputStyle = {
    ...inputStyle,
    marginTop: '0',
    maxWidth: '150px',
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
    width: '100%',
    maxWidth: '100px',
  };

  const font ={
    fontFamily: 'Poppins, sans-serif',
  }
  return (
    <div>
      <NavBar />
      <div style={{ backgroundColor: '#06350D', height: '20vh', width: '100%', position: 'absolute', zIndex: -1, display: 'flex', justifyContent: 'center', alignItems: 'center' }}>
        <h1 style={{ fontFamily: "'Poppins', sans-serif", color: '#fff', fontSize: '18px' }}>FEEDING SCHEDULE</h1>
      </div>
      <div style={{ marginTop: '20vh', padding: '0 20px', boxSizing: 'border-box' }}>
        <div style={{ marginTop: '230px' }}>
          <h3>
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

        <div style={{ marginBottom: '10px' }}>
          <label htmlFor="portionSize">Portion Size:</label>
          <br />
          <input
            type="number"
            id="portionSize"
            value={portionSize}
            onChange={(e) => setPortionSize(e.target.value)}
            style={timeInputStyle}
            min="1"
            max="999"
            maxLength="3"
          />
        </div>

        <button onClick={handleAddTime} disabled={loadingAdd} style={buttonStyle}>
          {loadingAdd ? 'Adding...' : 'Add'}
        </button>

        {/* Display created times */}
        <div style={{ marginTop: '20px', display: 'flex', flexWrap: 'wrap' }}>
          {times.map((timeItem) => (
            <div key={timeItem.timeId} className="card-container" style={{ marginBottom: '10px', marginRight: '10px', width: '300px', position: 'relative' }}>
              <div className="card" style={{ width: '100%' }}>
                <div className="card-body">
                  {/* X mark button to remove the time */}
                  <button
                    onClick={() => handleRemoveTime(timeItem.timeId)}
                    disabled={loadingRemove}
                    style={{ position: 'absolute', top: '10px', right: '10px', backgroundColor: 'transparent', border: 'none', cursor: 'pointer' }}
                  >
                    <FontAwesomeIcon icon={faTimes} style={{ color: '#FF6868', fontSize: '20px' }} />
                  </button>

                  <h5 className="card-title">{timeItem.timeLabel}</h5>
                  <p className="card-text">{timeItem.time}</p>
                  <p className="card-portion">Portion Size: {timeItem.portionSize}</p>
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
