import React from 'react';
import { useParams } from 'react-router-dom';
import SideBar from '../../components/SideBar/SideBar';
import NavBar from '../../components/Navbar/Navbar';

const Dashboard = () => {
  const { petFeederId } = useParams();
  console.log("yo work:", petFeederId)
  return (
    <>
      <NavBar />
      <div>
        <SideBar />
      </div>
    </>
  );
};

export default Dashboard;
