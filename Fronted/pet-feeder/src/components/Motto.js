import './Motto.css';

const Motto = () => {
    return (
        <div className='mottob'>
            <div className="container">
                <div className="row">
                    <div className="col-lg-4 catquote ">
                        <figure class="text-end mt-5">
                            <blockquote class="blockquote">
                                <p className="catquote">"No need for human if there is PetFeeder"</p>
                            </blockquote>
                            <figcaption class="blockquote-footes catfoot">
                                - Cat from <cite title="horsens" className="catfoot">Horsens</cite>
                            </figcaption>
                        </figure>
                    </div>
                    <div className="col-lg-4 text-center">
                        <img
                            src="/assets/mottocat.png"
                            alt="mottocat"
                            className='img-fluid cat'
                        />
                    </div>
                    <div className="col-lg-4 ">
                    <img
                            src="/assets/ig.png"
                            alt="mottocat"
                            className='img-fluid social '
                        />
                        <img
                            src="/assets/fg.png"
                            alt="mottocat"
                            className='img-fluid social'
                        />
                        <img
                            src="/assets/twitter.png"
                            alt="mottocat"
                            className='img-fluid social'
                        />
                    </div>
                </div>
            </div>
        </div>
    );
};

export default Motto;