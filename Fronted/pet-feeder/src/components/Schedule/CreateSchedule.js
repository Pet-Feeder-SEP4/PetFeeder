import React, { useState } from 'react';
import { Button, ButtonGroup, Form } from 'react-bootstrap';
import moment from 'moment';
import TimePicker from 'rc-time-picker';
import 'rc-time-picker/assets/index.css';
import '../Schedule/CreateSchedule.css';
import axios from 'axios';

const NewScheduleForm = ({ onSetSchedule }) => {
  const [selectedDays, setSelectedDays] = useState([]);
  const [selectedTime, setSelectedTime] = useState(moment());
  const [loading, setLoading] = useState(false);

  const handleDayClick = (day) => {
    const updatedDays = selectedDays.includes(day)
      ? selectedDays.filter((d) => d !== day)
      : [...selectedDays, day];
    setSelectedDays(updatedDays);
  };

  const handleTimeChange = (value) => {
    setSelectedTime(value);
  };

  const isEveryday = selectedDays.length === 7;

  const handleSave = async () => {
    try {
      setLoading(true);
      // Send data to the backend using axios
      const response = await axios.post('/api/schedules', {
        days: isEveryday ? ['everyday'] : selectedDays,
        time: selectedTime.format('HH:mm'),
      });
      console.log('Schedule saved successfully:', response.data);
      // Call the onSetSchedule prop with the updated schedule
      onSetSchedule(response.data);
    } catch (error) {
      console.error('Error saving schedule:', error);
    } finally {
      setLoading(false);
    }
  };

  const componentStyle = {
    fontFamily: "'Poppins', sans-serif", // Apply the desired font style
  };

  return (
    <Form className="d-flex flex-column align-items-start m-3 new-schedule-form" style={componentStyle}>
      <p className='mt-3'>New Schedule</p>
      <Form.Group controlId="days" className="mb-3">
        <ButtonGroup>
          {['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'].map((day) => (
            <Button
              key={day}
              variant={selectedDays.includes(day) ? 'primary' : 'light'}
              onClick={() => handleDayClick(day)}
              className="mx-1"
              style={{
                backgroundColor: selectedDays.includes(day) ? '#fff' : '#06350D',
                color: selectedDays.includes(day) ? '#06350D' : '#fff',
                borderRadius: '0.2rem',
                borderColor: selectedDays.includes(day) ? '#06350D' : 'transparent',
                outline: 'none',
                boxShadow: 'none',
              }}
            >
              {day}
            </Button>
          ))}
        </ButtonGroup>
      </Form.Group>

      <p style={{ marginLeft: "8px" }}>time</p>

      <Form.Group controlId="time" className="mb-3">
        <TimePicker
          onChange={handleTimeChange}
          value={selectedTime}
          showSecond={false}
          minuteStep={15}
          className="form-control custom-time-picker"
          clearIcon={<span style={{ display: 'none' }}></span>}
          style={{ width: '150px' }}
        />
      </Form.Group>

      <Button
        variant="primary"
        onClick={handleSave}
        disabled={loading}
        className="rounded-1 mt-3"
        style={{
          backgroundColor: isEveryday ? '#fff' : '#06350D',
          color: isEveryday ? '#06350D' : '#fff',
          width: '150px',
          outline: 'none',
          boxShadow: 'none',
          borderColor: '#06350D',
        }}
      >
        {loading ? 'Saving...' : 'Save'}
      </Button>
    </Form>
  );
};

export default NewScheduleForm;
