import React from 'react';
import { Link, useParams } from 'react-router-dom';

const Dashboard = () => {
  const { petFeederId } = useParams();
  console.log("yo work:", petFeederId)
  return (
    <div>
        <h1>dash</h1>
      
      {/* Your Dashboard content */}
      <Link to={`/schedule/${petFeederId}`}>
        <button>Schedule</button>
      </Link>
      {/* Your Dashboard content */}
    </div>
  );
};

export default Dashboard;
