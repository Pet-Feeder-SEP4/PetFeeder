import React, { useState, useEffect } from 'react';
import axios from '../../api/axios';
import './MyPets.css';
import { Link } from 'react-router-dom';
import { faPenToSquare } from '@fortawesome/free-solid-svg-icons';
import { faXmark } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';


const MyPets = () => {
  const [userPets, setUserPets] = useState([]);
  const userId = localStorage.getItem('userId');

  useEffect(() => {
    fetchUserPets();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [userId]);

  const fetchUserPets = async () => {
    if (userId) {
      try {
        const response = await axios.get(`/pets/user/${userId}`);

        setUserPets(response.data);
        console.log('data:', response.data);




      } catch (error) {
        console.error('Error fetching user pets:', error);
      }
    }
  };

  // handleRemove should be working, error on their end 
  const handleRemove = async (petId) => {
    console.log('Pet ID to be removed:', petId);
    try {

      await axios.delete(`/pets/${petId}`);


      // Check if the deletion was successful
      setUserPets((prevPets) => prevPets.filter((pet) => pet.id !== petId));
      fetchUserPets();
    } catch (error) {
      console.error('Error removing pet:', error);
    }
  };

  return (
    <div className='petListContainer'>
      <h1 className='myPetsTitle'>MY PETS</h1>
      <div className='listOfPets'>
        <ul style={{ listStyleType: 'none', padding: 0 }}>
          {userPets.map((pet, index,) => (

            <li key={pet?.id || index} className="petItem">
              <Link style={{textDecoration: 'none'}} to={`/PetInfo/${pet?.petId}`}>
              <span className='petLine'>{pet?.name}</span>
              </Link>
              <div className="btn-group-managepets">
                <Link to={`/EditPet/${pet?.petId}`}>  {/*edit the link to EditPet */}
                  <button type="button" className="btnEditPet">
                  <FontAwesomeIcon icon={faPenToSquare} style={{color: "#06350D", fontSize: "12px" }}  />
                  </button>
                </Link>
                <button
                  type="button"
                  className="btnRemovePet"
                  onClick={() => handleRemove(pet?.petId)}>
                  <FontAwesomeIcon icon={faXmark} style={{color: "#06350D", fontSize: "12px" }}  />
                </button>
              </div>
            </li>

          ))}
        </ul>
      </div>
    </div>
  );
}

export default MyPets;
