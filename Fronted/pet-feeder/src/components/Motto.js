import './Motto.css';

const Motto = () => {
    return (
        <div className='mottob'>
            <div className="container">
                <div className="row">
                    <div className="col-lg-6  ">
                        <div className="mine messages ">
                            <div className="message last shadow-lg ">
                                <p className='cattext'>Follow our socials</p>
                            </div>
                        </div>
                        {/* chnage this to normal icons  */}
                        <a href="link_to_facebook_profile" target="_blank" rel="noreferrer">
                            <img
                                src="/assets/fg.png"
                                alt="fb"
                                className='social'
                            />
                        </a>

                        <a href="link_to_instagram_profile" target="_blank" rel="noreferrer">
                            <img
                                src="/assets/ig.png"
                                alt="ig"
                                className='social'
                            />
                        </a>

                        <a href="link_to_twitter_profile" target="_blank" rel="noreferrer">
                            <img
                                src="/assets/twitter.png"
                                alt="twit"
                                className='social'
                            />
                        </a>

                    </div>
                    <div className="col-lg-6">
                        <img
                            src="/assets/mottocat.png"
                            alt="mottocat"
                            className='cat'
                        />
                    </div>
                </div>
            </div>
        </div>
    );
};

export default Motto;