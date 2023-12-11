import React, { useState } from 'react';
import axios from '../../api/axios';
import DatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';
import { useParams } from 'react-router-dom';
import { Card, Row, Col } from 'react-bootstrap';
import NavBar from '../../components/Navbar/Navbar';
import '../History/History.css';

const PetFeederHistory = () => {
  const [startDate, setStartDate] = useState(null);
  const [endDate, setEndDate] = useState(null);
  const [selectedPartOfDay, setSelectedPartOfDay] = useState('all');
  const [selectedFilter, setSelectedFilter] = useState('all');
  const [historyData, setHistoryData] = useState(null);

  const { petFeederId } = useParams();

  const handleDateChange = (date, dateType) => {
    if (dateType === 'start') {
      setStartDate(date);
    } else {
      setEndDate(date);
    }
  };

  const handlePartOfDayChange = (partOfDay) => {
    setSelectedPartOfDay(partOfDay);
  };

  const handleToggleField = (option) => {
    setSelectedFilter(option);
  };

  const fetchHistoryData = async () => {
    if (startDate) {
      try {
        let response;

        if (endDate) {
          response = await axios.post('/petFeederHistory/getHistoryByDateInterval', {
            petFeederId,
            startDate: startDate.toISOString().split('T')[0],
            endDate: endDate.toISOString().split('T')[0],
          });
        } else {
          response = await axios.post('/petFeederHistory/history', {
            petFeederId,
            date: startDate.toISOString().split('T')[0],
          });
        }

        const filteredData = response.data
          .filter((item) => {
            const time = item.time.split('.')[0];
            const hour = parseInt(time.split(':')[0], 10);

            if (selectedPartOfDay === 'morning') {
              return hour >= 6 && hour < 12;
            } else if (selectedPartOfDay === 'afternoon') {
              return hour >= 12 && hour < 18;
            } else if (selectedPartOfDay === 'night') {
              return hour >= 18 || hour < 6;
            }

            return true;
          })
          .map((item) => ({
            time: item.time.split('.')[0],
            ...(selectedFilter === 'all' && { foodLevel: item.foodLevel, foodHumidity: item.foodHumidity, waterTemperature: item.waterTemperature }),
            ...(selectedFilter !== 'all' && { [selectedFilter]: item[selectedFilter] }),
          }));

        setHistoryData(filteredData);
        console.log(
          petFeederId,
          startDate.toISOString().split('T')[0],
          endDate && endDate.toISOString().split('T')[0]
        );
        console.log(filteredData);
      } catch (error) {
        console.error('Error fetching history data:', error);
      }
    } else {
      console.warn('Please select the start date.');
    }
  };

  const handleClearDates = () => {
    setStartDate(null);
    setEndDate(null);
  };

  return (
    <div style={{ fontFamily: 'Poppins, sans-serif' }}>
      <NavBar />
      <div className="text-white d-flex align-items-center justify-content-center p-4" style={{ height: '20vh', backgroundColor: '#06350D' }}>
        <h1 className="display-4" style={{ fontFamily: 'Poppins, sans-serif', fontSize: '20px', fontWeight: '500' }}>PET FEEDER HISTORY</h1>
      </div>
      <Row className='m-4'>
        <Col sm={6} md={4} lg={3}>
          <label className='mr-2'>Start Date</label>
          <div>
            <DatePicker
              selected={startDate}
              onChange={(date) => handleDateChange(date, 'start')}
              className="form-control"
              id='datepicker'
            />
          </div>
        </Col>
        <Col sm={6} md={4} lg={3}>
          <label className='mr-2'>End Date</label>
          <div>
            <DatePicker
              selected={endDate}
              onChange={(date) => handleDateChange(date, 'end')}
              className="form-control"
              id='datepicker'
            />
          </div>
        </Col>
        <Col sm={6} md={4} lg={3}>
          <label className='mr-2'>Part of Day</label>
          <div>
            <select
              value={selectedPartOfDay}
              onChange={(e) => handlePartOfDayChange(e.target.value)}
              className="form-control"
            >
              <option value="all">All Day</option>
              <option value="morning">Morning</option>
              <option value="afternoon">Afternoon</option>
              <option value="night">Night</option>
            </select>
          </div>
        </Col>
        <Col sm={6} md={4} lg={3}>
          <label>Filter</label>
          <div>
            <select
              value={selectedFilter}
              onChange={(e) => handleToggleField(e.target.value)}
              className="form-control"
            >
              <option value="all">All</option>
              <option value="foodLevel">Food Level</option>
              <option value="foodHumidity">Food Humidity</option>
              <option value="waterTemperature">Water Temperature</option>
            </select>
          </div>
        </Col>
      </Row>
      <Row>
        <Col>
          <button style={{ backgroundColor: '#AAC88F', color: 'white' }} onClick={fetchHistoryData} className="btn m-4">
            Fetch
          </button>
          <button style={{ backgroundColor: '#ff6961', color: 'white', marginLeft: '10px' }} onClick={handleClearDates} className="btn m-4">
            Clear Dates
          </button>
        </Col>
      </Row>
      {historyData && (
        <div className='m-4'>
          <h4 className='mb-4'>History</h4>
          <Row>
            {historyData.map((item, index) => (
              <Col key={index} xs={12} sm={6} md={4} lg={3} xl={3} className="mb-3">
                <Card className='shadow' style={{ border: '1px solid white', borderRadius: '8px', marginBottom: '15px' }}>
                  <Card.Body>
                    <Card.Title style={{ color: '#06350D', fontSize: '18px' }}>Time: {item.time}</Card.Title>
                    {item.foodLevel && <Card.Text>Food Level: <span style={{ color: '#829B6A' }}>{item.foodLevel} </span></Card.Text>}
                    {item.foodHumidity && <Card.Text>Food Humidity: <span style={{ color: '#829B6A' }}>{item.foodHumidity} </span></Card.Text>}
                    {item.waterTemperature && (
                      <Card.Text> Water Temperature: <span style={{ color: '#829B6A' }}> {item.waterTemperature}</span> </Card.Text>
                    )}
                  </Card.Body>
                </Card>
              </Col>
            ))}
          </Row>
        </div>
      )}
    </div>
  );
};

export default PetFeederHistory;
