
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

    useEffect(() => {
        if (!isTokenValid) {
            console.log("Invalid token, please log in")
            // navigate('/login');  
        } else {
            // ... (your existing component logic)
        }
    }, [isTokenValid, navigate]);


    // State to store form data
    const [formData, setFormData] = useState({
        userId: 21,
        petFeederId: 2,
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
        try {
            const response = await axios.get('https://peefee.azurewebsites.net/auth/user', formData, {
                headers: {
                    'Authorization': 'Bearer eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjIxLCJzdWIiOiJhZG1pbkBhZG1pbi5jb20iLCJpYXQiOjE3MDExODc0MzYsImV4cCI6MTcwMTE5MTAzNn0.j4a5tAI1gsxmCU7WkDpBP3Hzbf0Ohq631839p3d4JT4',
                    'Content-Type': 'application/json',  // Adjust the content type if needed
                },
            });

            console.log('Pet created successfully:', response.data);
        } catch (error) {
            console.error('Error creating pet:', error);
        }

        try {
            const response = await axios.post('https://peefee.azurewebsites.net/pets/', formData, {
                headers: {
                    'Authorization': 'Bearer eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjIxLCJzdWIiOiJhZG1pbkBhZG1pbi5jb20iLCJpYXQiOjE3MDExODc0MzYsImV4cCI6MTcwMTE5MTAzNn0.j4a5tAI1gsxmCU7WkDpBP3Hzbf0Ohq631839p3d4JT4',
                    'Content-Type': 'application/json',  // Adjust the content type if needed
                },
            });

            console.log('Pet created successfully:', response.data);
        } catch (error) {
            console.error('Error creating pet:', error);
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