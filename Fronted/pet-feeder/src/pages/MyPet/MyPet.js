import React, { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import axios from "../../api/axios";
import NavBar from "../../components/Navbar/Navbar";

function MyPet() {
  const { petFeederId } = useParams();
  const [petData, setPetData] = useState(null);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await axios.get(`/petfeeder/${petFeederId}/pet`);
        setPetData(response.data);
      } catch (error) {
        console.error("Error fetching pet data:", error);
      }
    };

    fetchData();
  }, [petFeederId]);

  return (
    <div
      style={{
        fontFamily: "Poppins",
        display: "flex",
        flexDirection: "column",
      }}
    >
      <NavBar />
      <div
        className="text-white d-flex align-items-center justify-content-center p-4"
        style={{ height: "20vh", backgroundColor: "#06350D" }}
      >
        <h1
          className="display-4"
          style={{
            fontFamily: "Poppins, sans-serif",
            fontSize: "20px",
            fontWeight: "500",
          }}
        >
          YOUR PET
        </h1>
      </div>
      <div
        style={{
          display: "flex",
          justifyContent: "space-between",
          alignItems: "flex-start",
          margin: "0 20px",
        }}
      >
        {petData ? (
          <div className="m-4" style={{ maxWidth: "400px" }}>
            <div className="">
              <h1 className="mb-4" style={{ color: "#AAC88F" }}>
                {petData.name}
              </h1>
              <p className="">
                <strong>Birthdate:</strong>{" "}
                {new Date(petData.birthdate).toLocaleDateString()}
              </p>
              <p className="">
                <strong>Weight:</strong> {petData.weight} lbs
              </p>
              <p className="">
                <strong>Breed:</strong> {petData.breed}
              </p>
            </div>
          </div>
        ) : (
          <p className="text-center">Loading pet data...</p>
        )}
        <div className="col-lg-6 col-md-12 text-center ">
          <img
            src="/assets/cutehamster.jpg"
            alt="pettio"
            className="petImg shadow-lg"
          />
        </div>
      </div>
    </div>
  );
}

export default MyPet;
