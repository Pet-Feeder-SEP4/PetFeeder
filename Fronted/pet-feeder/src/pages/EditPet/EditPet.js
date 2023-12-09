import axios from "../../api/axios";
import NavBar from "../../components/Navbar/Navbar";
import React, { useState, useEffect } from 'react';
import { Form, Button } from 'react-bootstrap';
import './EditPet.css'
import useVerifyToken from '../../hooks/useVerifyToken';
import { useNavigate } from 'react-router-dom';
import { useParams } from 'react-router-dom';

const EditPet = () => {
    // check user token
    const isTokenValid = useVerifyToken();
    const navigate = useNavigate();
    const userId = localStorage.getItem('userId');
    const token = localStorage.getItem('token');
    const { petId } = useParams();

    // State to store form data
    const [formData, setFormData] = useState({
        petId: petId,
        userId: userId,
        petFeederId: null,
        name: '',
        birthdate: '',
        weight: 0,
        breed: '',
    });
    
    useEffect(() => {
        if (localStorage.getItem('token') != null) {
            console.log("its valid bro");
        } else {
            console.log("log in bro");
            navigate('/LogIn');
        }
        // get the pet data
        const fetchPetDetails = async () => {
            if (userId && token && petId) {
                try {
                    const response = await axios.get(`/pets/${petId}`, {
                        headers: {
                            'Authorization': `Bearer ${token}`,
                            'Content-Type': 'application/json',
                        },
                    });

                    const petDetails = response.data;

                    // Set the pet name to display in the heading
                    setPetName(petDetails.name);

                    // Set other pet details in formData if needed
                    setFormData(prevFormData => ({
                        ...prevFormData,
                        breed: petDetails.breed,
                        name: petDetails.name,
                        birthdate: petDetails.birthdate,
                        weight: petDetails.weight,
                    }));

                } catch (error) {
                    console.error('Error fetching pet details:', error);
                }
            }
        };
        fetchPetDetails();
    }, [isTokenValid, navigate, userId, token, petId]);



    // State to store pet name
    const [petName, setPetName] = useState('');

    // Handle input changes
    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setFormData({
            ...formData,
            [name]: value,
        });
    };

    // Function to save changes
    const handleSave = async (e) => {
        e.preventDefault();

        if (!formData.breed || !formData.name || !formData.birthdate || !formData.weight) {
            alert("Please fill in all required fields.");
            return;
        }

        const storedToken = localStorage.getItem('token');

        if (storedToken) {
            try {
                const response = await axios.put(`/pets/${petId}`, formData, {
                    headers: {
                        'Authorization': `Bearer ${storedToken}`,
                        'Content-Type': 'application/json',
                    },
                });

                console.log('Pet edited successfully:', response.data);

                alert("Pet edited successfully!");

                // Additional logic if needed after saving changes

            } catch (error) {
                console.error('Error editing pet:', error);
            }
        } else {
            console.error('userData or token is null');
        }
    };

    // Function to handle canceling the edit and navigating back to the main page
    const handleCancel = () => {
        navigate('/MainPage');
    };

    return (
        <div className="bbbb">
            <NavBar />

            <div className="editPetBlock">
                <h1 className="editblockText">{`EDIT ${petName.toUpperCase()}`}</h1> {/* edit and the i have to put actual pet name  */}
            </div>
            <div className="container editpetb">
                <div className="row">

                    <div className="col-lg-6 col-md-12  ">
                        <div className="editmarginthing ">

                            <Form >
                                {/* Breed Input */}
                                <Form.Group controlId="breed">
                                    <Form.Label>Breed</Form.Label>
                                    <Form.Control
                                        type="text"
                                        placeholder="Enter pet breed"
                                        name="breed"
                                        value={formData.breed}
                                        onChange={handleInputChange}
                                    />
                                </Form.Group>

                                {/* Name Input */}
                                <Form.Group controlId="name">
                                    <Form.Label>Name</Form.Label>
                                    <Form.Control
                                        type="text"
                                        placeholder="Enter pet name"
                                        name="name"
                                        value={formData.name}
                                        onChange={handleInputChange}
                                    />
                                </Form.Group>

                                {/* Birthdate Input */}
                                <Form.Group controlId="birthdate">
                                    <Form.Label>Birthdate</Form.Label>
                                    <Form.Control
                                        type="date"
                                        name="birthdate"
                                        value={formData.birthdate}
                                        onChange={handleInputChange}
                                    />
                                </Form.Group>

                                {/* Weight Input */}
                                <Form.Group controlId="weight">
                                    <Form.Label>Weight</Form.Label>
                                    <Form.Control
                                        type="number"
                                        placeholder="Enter weight"
                                        value={formData.weight}
                                        name="weight"
                                        onChange={handleInputChange}
                                    />
                                </Form.Group>

                                {/* Submit Buttons */}
                                <div>
                                    <Button type="submit" className="btnsave" onClick={handleSave}>
                                        SAVE
                                    </Button>
                                    <Button type="button" className="btncancel" onClick={handleCancel}>
                                        CANCEL
                                    </Button>
                                </div>
                            </Form>
                        </div>
                    </div>
                    <div className="col-lg-6 col-md-12 text-center ">
                        <img
                            src="/assets/editPetPhoto.jpg"
                            alt="pettio"
                            className='petImg shadow-lg'
                        />
                    </div>

                </div>
            </div>
        </div>
    );
};

export default EditPet;
