import axios from "../api/axios";
import { useEffect, useState } from "react";
import Pet from "./Pet";
import  './MyPets.css';

const MyPets = () => {

    const [pets, setPets] = useState([]);
    const [loading, setLoading] = useState (true);
    const [showAllPets, setShowAllPets] = useState(false);
    //TODO change to /pets/user/userID when you can get the userId from auth
  const PETS_URL = "/pets";

  // todo add Marta's token here in the const token and then put this config inside of every api call (wherever there is axios, add config
  const token = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjYyLCJzdWIiOiJob2xhMkB2aWEuZGsiLCJpYXQiOjE3MDEzNjM0NTEsImV4cCI6MTcwMTM2NzA1MX0.g0uMHetpAwG1SSqAr02v9EzgRJ1hsM0xEZxtvQ_598E";

    const url = 'https://peefee.azurewebsites.net/pets/';

  const config = {
    headers: { Authorization: `Bearer ${token}` }
  };

    useEffect(() => {
        const fetchData = async ()=> {
            try {
                // const response = await axios.get(PETFEEDERS_URL, config);
                const response = await fetch(url, {
                  method: "GET",
                  headers: {
                  "Content-Type": "application/json",
                  "Authorization": `Bearer ${token}`
                  },
                });
          
                const data = await response.json();
                setLoading (false);
                setPets(data);
          } catch (error){
            // possibility of error handling
            // you can add a new state (like lines 9 and 10) except for fail
            // and then make it show conditionally like we are doing with loading in line ~53
            console.error ('Error trying to fetch the data', error);
            setLoading(false);
          }
        };
      
        fetchData();
      }, []);

      const handleTogglePets = () => {
        setShowAllPets(!showAllPets);
      };
    
      

    // Marta that wheb she stores the auth,
    // the auth also needs to have the userId so we can pass it to the API calls
      return (
        
          <div className="side-thing">
          <div className="side-button-container">
            <button className="side-button">+ PET</button>
            <button className="side-button">+ FEEDER</button>
          </div>
         
      <div className="my-pets-header">
        <h5 className="mytitleforpets">Your Pets</h5>
        <div className="buttonsforactions">
      
          <button className="edit-button">&#9998;</button>
        
          <button className="remove-button">X</button>
        </div>
      </div>
       

 
       {loading ? (
         <div className="loading">Loading pets...</div>
        ) : (
        <div onClick={handleTogglePets} className="pet-box">
        <Pet pet={pets[0]} />
  
        {showAllPets &&
          pets.slice(1).map((p) => <Pet key={p.id} pet={p} />)}
          <div className="listext">
          {showAllPets ? "Show Less" : "Show More"}
          </div>
       </div>
)}
</div>
);
};
    
    export default MyPets;