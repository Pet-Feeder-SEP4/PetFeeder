
import axios from "../../api/axios";
import NavBar from "../../components/Navbar/Navbar";
import React, { useState } from 'react';
import { Form, Button } from 'react-bootstrap';
import './CreatePet.css'

const CreatePet = () => {
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
            const response = await axios.post('https://peefee.azurewebsites.net/pets/', formData, {
                headers: {
                    'Authorization': 'Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbkBhZG1pbi5jb20iLCJpYXQiOjE3MDEwMDMxODUsImV4cCI6MTcwMTAwNjc4NX0.KKRHv3tmrECZeqjm2gu5AZZ6Qs8zd5bLS-GFZzWCWXA',
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
            <div className="container createpetb">
                <div className="row">

                    <div className="col-lg-6 col-md-12 createPetBox ">
                        <div className="marginthing ">
                            <h3>ADD NEW PET</h3>
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
                                <Button  type="submit" className="btncreate">
                                    CREATE
                                </Button>
                            </Form>
                        </div>

                    </div>
                    <div className="col-lg-6 col-md-12 text-center createPetBox">
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