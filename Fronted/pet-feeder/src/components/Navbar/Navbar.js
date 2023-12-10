import { useState } from 'react';
import Container from 'react-bootstrap/Container';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';
import axios from '../../api/axios';

function NavBar() {
  const [loggingOut, setLoggingOut] = useState(false);


  const handleLogout = async () => {
    try {
      setLoggingOut(true);

      // Make a POST request to logout
      const response = await axios.post('/auth/logout');

      // Log the response data
      console.log('Logout Response:', response.data);
      // Redirect to the welcome page after successful logout  and reload page
      window.location.href = '/';
    } catch (error) {
      console.error('Error logging out:', error);
      // Handle error if needed
    } finally {
      setLoggingOut(false);
    }
  };

  return (
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
            <Nav.Link style={{ fontFamily: 'Poppins' }} onClick={handleLogout} disabled={loggingOut}>
              Logout
            </Nav.Link>
        
          </Nav>
        </Navbar.Collapse>
      </Container>
    </Navbar>
  );
}

export default NavBar;
