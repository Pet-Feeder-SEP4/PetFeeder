import './Motto.css';

const Motto = () => {
    return (
        <div className='mottob'>
            <div className="container">
                <div className="row">
                    <div className="col-lg-2 col-md-12">

                    </div>
                    <div className="col-lg-6 col-md-12 ">
                        <blockquote class="otro-blockquote shadow-lg ">
                            <p className='quoteTXT'>
                                Kindness towards animals is a reflection of our humanity.
                                Embrace compassion, extend a gentle hand, and cherish the
                                silent beauty that surrounds us. In their eyes, we find a
                                language of trust, and in our actions, we define a world
                                where every heartbeat matters.
                            </p>
                            <span>Jack Spinola</span>
                        </blockquote>
                    </div>
                    <div className="col-lg-4 col-md-12 ">
                        <img
                            src="/assets/PetBlack.png"
                            alt="logofoot"
                            className='img-fluid logofoot'
                        />
                    </div>
                    

                </div>
            </div>
        </div>
    );
};

export default Motto;