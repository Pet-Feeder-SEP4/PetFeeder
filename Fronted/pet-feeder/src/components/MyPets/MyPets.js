import React, { useState, useEffect } from 'react';
import axios from '../../api/axios';
import './MyPets.css';
import { Link } from 'react-router-dom';

const MyPets = () => {
  const [userPets, setUserPets] = useState([]);
  const userId = localStorage.getItem('userId');
  const token = localStorage.getItem('token');

  useEffect(() => {

    const fetchUserPets = async () => {
      if (userId && token) {
        try {
          const response = await axios.get(`/pets/user/${userId}`, {
            headers: {
              'Authorization': `Bearer ${token}`,
              'Content-Type': 'application/json',
            },
          });

          setUserPets(response.data);
          console.log('data:', response.data);

        


        } catch (error) {
          console.error('Error fetching user pets:', error);
        }
      }
    };

    fetchUserPets();
  }, [userId, token]);

  // handleRemove should be working, error on their end 

  const handleRemove = async (petId) => {
    console.log('Pet ID to be removed:', petId);
  
    try {
    
      const response = await axios.delete(`https://peefee.azurewebsites.net/pets/${petId}`, {
        headers: {
          'Authorization': `Bearer ${token}`,
          'Content-Type': 'application/json',
        },
      });
  
      // Check if the deletion was successful
      if (response.status === 200) {
        // Update the userPets state by filtering out the removed pet
        setUserPets((prevPets) => prevPets.filter((pet) => pet.id !== petId));
        console.log(`Pet with ID ${petId} has been removed.`);
      } else {
        console.error('Failed to remove pet. Server returned:', response.status, response.data);
      }
    } catch (error) {
      console.error('Error removing pet:', error);
    }
  };
  

  return (
    <div className='petListContainer'>
      <h1 className='myPetsTitle'>MY PETS</h1>
      <div className='listOfPets'>
        <ul style={{ listStyleType: 'none', padding: 0 }}>
          {userPets.map((pet, index, ) => (
            
            <li key={pet?.id || index} className="petItem">
              <span className='petLine'>{pet?.name}</span>
              <div className="btn-group-managepets">
                <Link to={`/edit-pet/${pet?.petId}`}>  {/*edit the link to EditPet */}
                  <button type="button" className="btnEditPet">
                    Edit
                  </button>
                </Link>
                <button
                  type="button"
                  className="btnRemovePet"
                  onClick={() => handleRemove(pet?.petId) }>
                      Remove
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
