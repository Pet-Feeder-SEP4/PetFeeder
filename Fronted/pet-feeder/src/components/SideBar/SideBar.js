import React from 'react';
import { Link } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faClock, faBox, faPaw, faHistory } from '@fortawesome/free-solid-svg-icons';
import './SideBar.css';
import { useParams } from 'react-router-dom';

function SideBar() {
    const { petFeederId } = useParams();
    return (
        <div className="sidebar ">
            <ul className="list-group ms-1">
                <Link to={`/schedule/${petFeederId}`} className="list-group-item mt-4" style={{ backgroundColor: "#F8F9FA" }}>
                    <FontAwesomeIcon icon={faClock} className='me-2' />
                    <span className="d-none d-xl-inline">SCHEDULE</span>
                </Link>
                <Link to="/" className="list-group-item " style={{ backgroundColor: "#F8F9FA" }}> {/* add correct path */}
                    <FontAwesomeIcon icon={faBox} className='me-2' />
                    <span className="d-none d-xl-inline">DISPENSE</span>
                </Link>
                <Link to="/" className="list-group-item " style={{ backgroundColor: "#F8F9FA" }}> {/* add correct path */}
                    <FontAwesomeIcon icon={faPaw} className='me-2' />
                    <span className="d-none d-xl-inline">PET</span>
                </Link>
                <Link to="/" className="list-group-item " style={{ backgroundColor: "#F8F9FA" }}> {/* add correct path */}
                    <FontAwesomeIcon icon={faHistory} className='me-2' />
                    <span className="d-none d-xl-inline">HISTORY</span>
                </Link>
            </ul>
        </div>
    );
}

export default SideBar;
