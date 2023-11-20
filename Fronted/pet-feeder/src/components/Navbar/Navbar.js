import Container from 'react-bootstrap/Container';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';


function NavBar() {
  return (
    <Navbar collapseOnSelect expand="lg" className="bg-body-tertiary" >
      <Container>
        <Navbar.Brand href="#home"><img  src={`${process.env.PUBLIC_URL}/assets/PetDarkGreen.png`} alt=''  className="d-inline-block align-top img-fluid"
            style={{ maxHeight: '90px' }}/></Navbar.Brand>
        <Navbar.Toggle aria-controls="responsive-navbar-nav" />
        <Navbar.Collapse id="responsive-navbar-nav">
          <Nav className="me-auto">
           
           
          </Nav>
          <Nav>
            <Nav.Link href="#home">Home</Nav.Link> {/* replace link */}
            
          </Nav>
        </Navbar.Collapse>
      </Container>
    </Navbar>
  );
}

export default NavBar;