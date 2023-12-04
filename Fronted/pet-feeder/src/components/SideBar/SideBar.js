import React from 'react';
import { Link } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faClock, faBox, faPaw, faHistory } from '@fortawesome/free-solid-svg-icons';
import './SideBar.css';

function SideBar() {
    
    return (
        <div className="sidebar" >
            <ul className="list-group"  >
                <Link to="/schedule/:petFeederId" className="list-group-item" >
                    <FontAwesomeIcon icon={faClock} className='me-2 ' /> SCHEDULE
                </Link>
                <Link to="/" className="list-group-item">
                    <FontAwesomeIcon icon={faBox} className='me-2'  /> DISPENSE
                </Link>
                <Link to="/" className="list-group-item">
                    <FontAwesomeIcon icon={faPaw} className='me-2' /> PET
                </Link>
                <Link to="/" className="list-group-item">
                    <FontAwesomeIcon icon={faHistory} className='me-2'  /> HISTORY
                </Link>
            </ul>
        </div>
    );
}

export default SideBar;
