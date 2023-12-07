import React from 'react';
import { Link } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faClock, faBox, faPaw, faHistory } from '@fortawesome/free-solid-svg-icons';
import './SideBar.css';

function SideBar() {
    return (
        <div className="sidebar ">
            <ul className="list-group ms-1">
                <Link to="/schedule/:petFeederId" className="list-group-item mt-4" style={{ backgroundColor: "#f6f6f6" }}>
                    <FontAwesomeIcon icon={faClock} className='me-2' />
                    <span className="d-none d-xl-inline">SCHEDULE</span>
                </Link>
                <Link to="/" className="list-group-item " style={{ backgroundColor: "#f6f6f6" }}> {/* add correct path */}
                    <FontAwesomeIcon icon={faBox} className='me-2' />
                    <span className="d-none d-xl-inline">DISPENSE</span> 
                </Link>
                <Link to="/" className="list-group-item " style={{ backgroundColor: "#f6f6f6" }}> {/* add correct path */}
                    <FontAwesomeIcon icon={faPaw} className='me-2' />
                    <span className="d-none d-xl-inline">PET</span>
                </Link>
                <Link to="/" className="list-group-item " style={{ backgroundColor: "#f6f6f6" }}> {/* add correct path */}
                    <FontAwesomeIcon icon={faHistory} className='me-2' />
                    <span className="d-none d-xl-inline">HISTORY</span>
                </Link>
            </ul>
        </div>
    );
}

export default SideBar;
