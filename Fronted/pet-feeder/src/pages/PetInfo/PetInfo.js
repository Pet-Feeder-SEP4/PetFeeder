import axios from "../../api/axios";
import NavBar from "../../components/Navbar/Navbar";
import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';

const PetInfo = () => {
  const [formData, setFormData] = useState({
    petId: null,
    userId: null,
    petFeederId: null,
    name: '',
    birthdate: '',
    weight: 0,
    breed: '',
  });

  const [petName, setPetName] = useState('');

  const { petId } = useParams();

  useEffect(() => {
    const userId = localStorage.getItem('userId');

    const fetchPetDetails = async () => {
      try {
        const response = await axios.get(`/pets/${petId}`);
        const petDetails = response.data;

        setPetName(petDetails.name);

        setFormData((prevFormData) => ({
          ...prevFormData,
          petId: petDetails.petId,
          userId: userId,
          petFeederId: petDetails.petFeederId,
          breed: petDetails.breed,
          name: petDetails.name,
          birthdate: formatBirthdate(petDetails.birthdate),
          weight: petDetails.weight,
        }));
      } catch (error) {
        console.error('Error fetching pet details:', error);
      }
    };

    fetchPetDetails();
  }, [petId]);

  const formatBirthdate = (dateString) => {
    const date = new Date(dateString);
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');
    return `${year}-${month}-${day}`;
  };

  return (
    <div className="bbbb">
      <NavBar />

      <div className="editPetBlock">
        <h1 className="editblockText">{` ${petName.toUpperCase()}`}</h1>
      </div>

      <div className="container editpetb">
        <div className="row">
          <div className="col-lg-6 col-md-12">
            <div className="editmarginthing">
              <h2 className="mb-4">Details</h2>
              <p><strong style={{color:'#06350D'}}>Breed:</strong> {formData.breed}</p>
              <p><strong style={{color:'#06350D'}}>Name:</strong> {formData.name}</p>
              <p><strong style={{color:'#06350D'}}>Birthdate:</strong> {formData.birthdate}</p>
              <p><strong style={{color:'#06350D'}}>Weight:</strong> {formData.weight}</p>
            </div>
          </div>

          <div className="col-lg-6 col-md-12 text-center">
            <img
              src="/assets/greycat.jpg"
              alt="pettio"
              className='petImg shadow-lg'
            />
          </div>
        </div>
      </div>
    </div>
  );
};

export default PetInfo;
