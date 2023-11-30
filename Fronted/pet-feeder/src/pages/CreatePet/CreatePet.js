import axios from "../../api/axios";
import NavBar from "../../components/Navbar/Navbar";
import React, { useState, useEffect } from 'react';
import { Form, Button } from 'react-bootstrap';
import './CreatePet.css'
import useVerifyToken from '../../hooks/useVerifyToken';
import { useNavigate } from 'react-router-dom';

const CreatePet = () => {
    // check user token
    const isTokenValid = useVerifyToken();   
    const navigate = useNavigate();
    const userId = localStorage.getItem('userId');

    useEffect(() => {    
        if (localStorage.getItem('token')!=null) {
            console.log("its valid bro");     
        } else {
            console.log("log in bro");
            navigate('/LogIn');
        }
    }, [isTokenValid, navigate]);

    // State to store form data

    const [formData, setFormData] = useState({
        userId: userId, 
        petFeederId: null, //you can create pet feeder w/o pet, but to associate them it need to be set to the pet feeder id 
        name: '',
        birthdate: '',
        weight: 0,
        breed: '',
    });

    // Handle input changes

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setFormData({
            ...formData,
            [name]: value,
        });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        if (!formData.breed || !formData.name || !formData.birthdate || !formData.weight) {
            alert("Please fill in all required fields.");
            return;
        }

        const storedToken = localStorage.getItem('token');
        
        // Check if userData exists and has the 'token' property
        if (storedToken) {
            try {
                const response = await axios.post('/pets/', formData, {
                    headers: {
                        'Content-Type': 'application/json',
                    },
                });

                console.log('Pet created successfully:', response.data);

                alert("Pet created successfully!");

                setFormData({
                    breed: '',
                    name: '',
                    birthdate: '',
                    weight: '',
                });
                
            } catch (error) {
                console.error('Error creating pet:', error);
            }
        } else {
            console.error('userData or token is null');
        }
    };

    return (
        <div className="bbb">
            <NavBar />

            <div className="createPetBlock">
                <h1 className="blockText">ADD PET</h1>
            </div>
            <div className="container createpetb">
                <div className="row">

                    <div className="col-lg-6 col-md-12 createPetBox ">
                        <div className="marginthing ">

                            <Form onSubmit={handleSubmit} >
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




                                {/* Submit Button */}
                                <Button type="submit" className="btncreate">
                                    CREATE
                                </Button>
                            </Form>
                        </div>

                    </div>
                    <div className="col-lg-6 col-md-12 text-center ">
                        <img
                            src="/assets/createpetPhoto.jpg"
                            alt="pettio"
                            className='petImg shadow-lg'
                        />
                    </div>

                </div>
            </div>
        </div>



    );
};

export default CreatePet;