import React from "react";
import { Link } from "react-router-dom";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faClock,
  faBox,
  faPaw,
  faHistory,
  faFilePen,
} from "@fortawesome/free-solid-svg-icons";
import "./SideBar.css";
import { useParams } from "react-router-dom";

function SideBar({ onDispenseClick, onEditClick }) {
  const { petFeederId } = useParams();
  return (
    <div className="sidebar ">
      <ul className="list-group">
        <Link
          to={`/schedule/${petFeederId}`}
          className="list-group-item mt-4"
          style={{ backgroundColor: "#F8F9FA" }}
        >
          <FontAwesomeIcon icon={faClock} className="me-2" />
          <span className="d-none d-xl-inline">SCHEDULE</span>
        </Link>
        <li className="list-group-item " style={{ backgroundColor: "#F8F9FA" }}>
          <FontAwesomeIcon
            icon={faBox}
            className="me-2"
            onClick={onDispenseClick}
          />
          <span className="d-none d-xl-inline" onClick={onDispenseClick}>
            DISPENSE
          </span>
        </li>
        <Link
          to="/"
          className="list-group-item "
          style={{ backgroundColor: "#F8F9FA" }}
        >
          {" "}
          {/* add correct path */}
          <FontAwesomeIcon icon={faPaw} className="me-2" />
          <span className="d-none d-xl-inline">PET</span>
        </Link>
        <Link
          to="/"
          className="list-group-item "
          style={{ backgroundColor: "#F8F9FA" }}
        >
          {" "}
          {/* add correct path */}
          <FontAwesomeIcon icon={faHistory} className="me-2" />
          <span className="d-none d-xl-inline">HISTORY</span>
        </Link>
        <li className="list-group-item " style={{ backgroundColor: "#F8F9FA" }}>
          <FontAwesomeIcon
            icon={faFilePen}
            className="me-2"
            onClick={onEditClick}
          />
          <span className="d-none d-xl-inline" onClick={onEditClick}>
            NOTIFICATIONS
          </span>
        </li>
      </ul>
    </div>
  );
}

export default SideBar;
