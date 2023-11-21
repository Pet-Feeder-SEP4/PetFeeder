import React from 'react';
import './PetFeederInfo.css';

const PetFeederInfo = () => {
  return (
    <div className='aboutb'>
      <div className="container pet-feeder-container">
        <div className="row">
          <div className="col-lg-6 col-md-12 text-center mb-4 mt-4">
            <img
              src="/assets/aboutUsPhoto.jpg"
              alt="Pet"
              className="pet-image"
            />
          </div>
          <div className="col-lg-6 col-md-12 text-center font-weight-light mb-4 mt-4">
            <div className="textcont mt-4">
              <h1 className="mb-3 texttitle">ABOUT US</h1>
              <p className="textcontent lh-base">
                What sets us apart is our dedication to innovation and quality.
                Our pet feeders are designed with precision and care, incorporating
                the latest technology to ensure convenience, reliability, and the well-being
                of your furry companions. From programmable schedules to portion control,
                we offer a range of features that simplify feeding routines while maintaining
                optimal nutrition.
                Moreover, we understand the importance of trust when it comes to pet care.
                That's why our products undergo rigorous testing to guarantee their safety and functionality.
                We stand by our commitment to reliability, ensuring that your pets are always fed on time, regardless of your schedule.
                Ultimately, our aim is to make your life easier while fostering a healthier and happier environment
                for your pets. With our pet feeders, you're not just investing in a product
                you're investing in peace of mind and the well-being of your furry family members.
              </p>
            </div>
            <div className='card mt-5 shadow-lg award'>
              <div className='row'>
                <div className="col-lg-8 col-md-12">
                  <div className="card-body">
                    <h5 className="card-title">Quality Guarantee</h5>
                    <p className="card-text mt-3">
                      Best Automated Pet Feeding Station 2023 Award
                    </p>
                    <p className="card-text">
                      <small className="text-muted">Approved by FEDIAF</small>
                    </p>
                  </div>
                </div>
                <div className="col-lg-4 col-md-12 awardcont">
                  <img
                    src="/assets/award.png"
                    alt="badge"
                    className='badgeImg mt-2'
                  />
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default PetFeederInfo;
