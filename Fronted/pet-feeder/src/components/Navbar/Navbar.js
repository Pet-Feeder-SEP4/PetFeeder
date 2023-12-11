import '../Navbar/Navbar.css'
import React, { useEffect, useState } from 'react';
import Container from 'react-bootstrap/Container';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';
import axios from '../../api/axios';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faBell } from '@fortawesome/free-solid-svg-icons';
import NotificationModal from '../modals/NotificationModal';

function NavBar() {
  const [loggingOut, setLoggingOut] = useState(false);
  const [showNotification, setShowNotification] = useState(false);
  const [notificationCount, setNotificationCount] = useState(0);

  const userId = localStorage.getItem('userId');

  //for sockets
  const [notifications, setNotifications] = useState([]);

  useEffect(() => {
    const socket = new WebSocket('wss://petfeederapi.azurewebsites.net/notificationsWS/' + userId);

    socket.addEventListener('open', (event) => {
      console.log('WebSocket connection opened:', event);
    });

    socket.addEventListener('message', (event) => {
      const data = event.data;
      setNotifications((prevNotifications) => [data, ...prevNotifications]);
      setNotificationCount((prevCount) => prevCount + 1);
      console.log('caralho', data);
    });

    socket.addEventListener('close', (event) => {
      console.log('WebSocket connection closed:', event);
    });

    return () => {
      socket.close();
    };
  }, [userId]);

  const handleLogout = async () => {
    try {
      setLoggingOut(true);
      const response = await axios.post('/auth/logout');
      console.log('Logout Response:', response.data);
      window.location.href = '/';
    } catch (error) {
      console.error('Error logging out:', error);
    } finally {
      setLoggingOut(false);
    }
  };

  const handleNotificationClick = () => {
    setShowNotification(true);
    setNotificationCount(0); // Reset notification count when the modal is opened
  };

  const handleCloseNotification = () => {
    setShowNotification(false);
  };

  return (
    <>
      <Navbar collapseOnSelect expand="lg" className="bg-body-tertiary">
        <Container>
          <Navbar.Brand href="/MainPage">
            <img
              src={`${process.env.PUBLIC_URL}/assets/PetBlack.png`}
              alt=""
              className="d-inline-block align-top img-fluid"
              style={{ maxHeight: '56px' }}
            />
          </Navbar.Brand>
          <Navbar.Toggle aria-controls="responsive-navbar-nav" />
          <Navbar.Collapse id="responsive-navbar-nav">
            <Nav className="me-auto">{/* Add additional navigation items here if needed */}</Nav>
            <Nav>
              <Nav.Link style={{ fontFamily: 'Poppins' }} href="/MainPage/">
                Home
              </Nav.Link>
              <Nav.Link
                style={{ fontFamily: 'Poppins' }}
                onClick={handleLogout}
                disabled={loggingOut}
              >
                Logout
              </Nav.Link>
              <Nav.Link
  style={{ fontFamily: 'Poppins' }}
  onClick={handleNotificationClick}
  disabled={loggingOut}
>
  <div style={{ position: 'relative', display: 'inline-block' }}>
    <FontAwesomeIcon style={{fontSize: '20px'}} icon={faBell} className="me-2" />
    {notificationCount > 0 && (
      <span className="notification-count">{notificationCount}</span>
    )}
  </div>
</Nav.Link>
            </Nav>
          </Navbar.Collapse>
        </Container>
      </Navbar>

      {/* Notification Modal */}
      <NotificationModal show={showNotification} handleClose={handleCloseNotification} notifications={notifications} />
    </>
  );
}

export default NavBar;
