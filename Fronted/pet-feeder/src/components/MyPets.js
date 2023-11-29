import axios from "../api/axios";
import { useEffect, useState } from "react";
import Pet from "./Pet";
import  './MyPets.css';

const MyPets = () => {

    const [pets, setPets] = useState([]);
    const [loading, setLoading] = useState (true);
    //TODO change to /pets/user/userID when you can get the userId from Marta's auth
  const PETS_URL = "/pets";
  // todo add Marta's token here in the const token and then put this config inside of every api call (wherever there is axios, add config
  const token = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjQ5LCJzdWIiOiJsYXJlYkB2aWEuZGsiLCJpYXQiOjE3MDEyODUxOTgsImV4cCI6MTcwMTI4ODc5OH0.okURAoeiewc8CLA94Nj6r-6QUORNFjBeuVjb3wj_Mek";

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


      

    // tell Marta that when she stores the auth,
    // the auth also needs to have the userId so we can pass it to the API calls
      return (
        
          <div className="side-thing">
          <div className="side-button-container">
            <button className="side-button">+ PET</button>
            <button className="side-button">+ FEEDER</button>
          </div>
          {/** add here the "Your Pets" header */}

          {
                loading ? <div className="loading">loading... style if needed</div> 
                : pets.map((p) => <Pet pet={p}/>)
            }
          </div>
      );
    };
    
    export default MyPets;