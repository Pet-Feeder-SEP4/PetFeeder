import React from 'react';
import {  useNavigate } from 'react-router-dom';
import SideBar from '../../components/SideBar/SideBar';
import NavBar from '../../components/Navbar/Navbar';
import useVerifyToken from '../../hooks/useVerifyToken'; // replace with the actual path

const Dashboard = () => {
  
  const navigate = useNavigate();
  const isTokenValid = useVerifyToken();

  if (isTokenValid === null) {
    // You can show a loading spinner or some other indication while verifying the token
    return <p>Loading...</p>;
  }

  if (!isTokenValid) {
    // Redirect to login page if token is not valid
    navigate('/LogIn');
    return null;
  }

  return (
    <>
      <NavBar />
      <div>
        <SideBar />
        {/* Render your dashboard content here */}
      </div>
    </>
  );
};

export default Dashboard;
