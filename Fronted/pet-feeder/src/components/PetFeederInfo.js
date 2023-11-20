
import React from 'react';
import './PetFeederInfo.css';

const PetFeederInfo = () => {

  
  return (
    <div className='aboutb'>
      <div className="container pet-feeder-container">
        <div className="col-md-6 text-center mb-4 mt-4 leftpart">
          <img
            src="/assets/aboutUsPhoto.jpg"
            alt="Pet"
            className="pet-image"
          />
        </div>
        <div className="col-md-6 text-center font-weight-light rightpart">
          <div className="textcont">
            <h1 className=" mb-3 texttitle ">About Us</h1>
            <p className="textcontent" >Welcome to PetFeeder, where we blend passion and innovation to revolutionize pet care.
              At our core, we're dedicated to creating advanced pet feeders that seamlessly integrate into your lifestyle, ensuring your furry
              friends receive the love and nourishment they deserve, even when you're away.

              What sets us apart is our commitment to merging technology and design. Our intelligent, user-friendly pet feeders boast features like programmable
              schedules and smart connectivity, empowering pet owners to provide optimal care from anywhere in the world.

              We prioritize quality, safety, and sustainability, adhering to the highest industry standards. Join us at PetFeeder
              as we redefine pet feeding with cutting-edge solutions, because your pet's well-being is our top priority.
            </p>
          </div>
          <div className='card mt-5 shadow-lg'>
            <div className='row'>
              <div class="col-md-8">
                <div class="card-body">
                  <h5 class="card-title">Quality Guarantee</h5>
                  <p class="card-text mt-3">
                    Best Automated Pet Feeding Station 2023 Award
                  </p>
                  <p class="card-text">
                    <small class="text-muted">
                      Approved by FEDIAF
                    </small>
                  </p>
                </div>
              </div>
              <div class="col-md-4">
                <img
                  src="/assets/badgeGreen.png"
                  alt="badge"
                  className='badgeImg mt-1'
                />
              </div>       
          </div>
        </div>
          </div>
          
      </div>
    </div>

  );
};

export default PetFeederInfo;
