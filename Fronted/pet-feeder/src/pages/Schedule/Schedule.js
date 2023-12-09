import React, { useEffect, useState } from 'react';
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
      const response = await axios.get(`/schedules/petFeeder/${petFeederId}`);
      setSchedules(response.data);
    } catch (error) {
      console.error('Error fetching user schedules:', error);
    }
  };

  useEffect(() => {
    fetchUserSchedules();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  const handleActivateDeactivate = async (scheduleId, isActive) => {
    try {
      const response = await axios.put(`/schedules/activate/${scheduleId}`, {
        active: !isActive, // Toggle the current state
      });

      console.log(`Schedule ${isActive ? 'deactivated' : 'activated'} successfully:`, response.data);

      // Update the list of schedules
      fetchUserSchedules();

      // You can perform any additional actions after successful activation/deactivation.

    } catch (error) {
      console.error('Error activating/deactivating schedule:', error);

      // Handle errors or show a user-friendly message.
    }
  };

  return (
    <div>
      <NavBar />
      <div className=" text-white d-flex align-items-center justify-content-center p-4" style={{ height: '20vh', backgroundColor: '#06350D'}}>
        <h1 className="display-4" style={{fontFamily: 'Poppins, sans-serif', fontSize: '20px', fontWeight: '500'}}>FEEDING SCHEDULE</h1>
      </div>

      <div className="container mt-5">
        {/* Schedule Creation Form */}
        <div className="row">
          <div className="col-md-6">
            <div className="mb-3">
              <p className="mb-3 mt-3 font-size-20 font-weight-bold" style={{ fontFamily: 'Poppins, sans-serif' }}>Create a Schedule</p>
              <label htmlFor="scheduleLabel" className="mb-3" style={{ fontFamily: 'Poppins, sans-serif' }}>
                Schedule Label:
              </label>
              <div className="justify-content-center text-center input-group mb-3">
                <input
                  type="text"
                  id="scheduleLabel"
                  value={scheduleLabel}
                  onChange={(e) => setScheduleLabel(e.target.value)}
                  className="form-control"
                  style={{ border: '1px solid #BBB', borderRadius: '9px' }}
                />
              </div>
              <button
                onClick={handleCreateSchedule}
                disabled={loading}
                className="btn btn-primary mt-2" style={{ fontFamily: 'Poppins, sans-serif', borderRadius: '9px', border: '1px solid #AAC88F', backgroundColor: '#AAC88F' }}
              >
                {loading ? 'Creating...' : 'Create'}
              </button>
            </div>
          </div>
        </div>
        {/* Display created schedules */}
        <div className="row mt-4">
          <div className="col-md-12">
            <h2 className="font-weight-bold mb-3" style={{ fontFamily: 'Poppins, sans-serif', fontSize: '15px'}}>Your Schedules</h2>
            <div>
              {schedules
                .sort((a, b) => (b.active ? 1 : 0) - (a.active ? 1 : 0)) // Sort based on the active status
                .map((schedule) => (
                  <div
                    key={schedule.scheduleId}
                    className="shadow mb-3 p-3 d-flex align-items-center flex-wrap"
                    style={{ border: '1px', borderRadius: '10px', alignItems: 'center', minHeight: '110px' }}
                  >
                    <div className="mr-3" style={{ fontFamily: 'Poppins, sans-serif', fontWeight: '500'}}>{schedule.scheduleLabel}</div>
                    <div className="mr-3">
                      <div
                        style={{
                          width: '12px',
                          height: '12px',
                          borderRadius: '50%',
                          backgroundColor: schedule.active ? '#AAC88F' : '#FF6961',
                          display: 'inline-block',
                          marginRight: '5px',
                        }}
                      ></div>

                    </div>
                    <div className="ml-auto">
                      <button
                        onClick={() => handleActivateDeactivate(schedule.scheduleId, schedule.active)}
                        className="btn"
                        style={{ fontFamily: 'Poppins, sans-serif', borderRadius: '9px', border: '1px solid #AAC88F', backgroundColor: '#AAC88F', color: 'white', fontSize:'12px', minWidth:'50px', fontWeight: '500'}}
                      >
                        {schedule.active ? 'Deactivate' : 'Activate'}
                      </button>
                      <Link
                        to={`/add-time/${schedule.scheduleId}/${encodeURIComponent(schedule.scheduleLabel)}`}
                        className="text-decoration-none"
                      >
                        <button className="btn" style={{ color: '#06350D'}}>
                          <span>+</span> <FontAwesomeIcon icon={faClock} className="me-1" />
                        </button>
                      </Link>
                    </div>
                  </div>
                ))}
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default FeedingSchedule;
