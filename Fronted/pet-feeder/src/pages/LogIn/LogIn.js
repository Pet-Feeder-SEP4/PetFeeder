import React, { useRef, useState, useEffect } from 'react';
import './LogIn.css';
import axios from '../../api/axios';
import { useNavigate } from 'react-router-dom';

const LOGIN_URL = '/auth/authenticate';

const LogIn = () => {
  const userRef = useRef();
  const errRef = useRef();
  const navigate = useNavigate();

  const [user, setUser] = useState('');
  const [pwd, setPwd] = useState('');
  const [errMsg, setErrMsg] = useState('');
  const [success, setSuccess] = useState(false);

  useEffect(() => {
    userRef.current.focus();
  }, []);

  useEffect(() => {
    setErrMsg('');
  }, [user, pwd]);

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.post(
        LOGIN_URL,
        JSON.stringify({ email: user,password: pwd }),
        {
          headers: { 'Content-Type': 'application/json' },
          withCredentials: true,
        }
      );
      console.log(JSON.stringify(response?.data));

      const token = response?.data?.token;
      localStorage.setItem("token", token);
    

      setUser('');
      setPwd('');
      setSuccess(true);
      navigate('/MainPage');
     
    
    } catch (err) {
      if (!err?.response) {
        setErrMsg('No Server Response');
      } else if (err.response?.status === 400) {
        setErrMsg('Missing email or Password');
      } else if (err.response?.status === 401) {
        setErrMsg('Unauthorized');
      } else {
        setErrMsg('Login failed');
      }
      errRef.current.focus();
    }
  };

  return (
    <>
     <div className='b'>
     
        <section>
          <p
            ref={errRef}
            className={errMsg ? 'errmsg' : 'offscreen'}
            aria-live="assertive"
          >
            {errMsg}
          </p>
          <h1 className='l-title'>Sign In</h1>
          <form onSubmit={handleSubmit}>
            <label htmlFor="email">Email:</label>
            <input
              type="text"
              id="email-l"
              className="form-control"
              ref={userRef}
              autoComplete="off"
              onChange={(e) => setUser(e.target.value)}
              value={user}
              required
            />

            <label htmlFor="password">Password:</label>
            <input
              type="password"
              id="password-l"
              className="form-control"
              onChange={(e) => setPwd(e.target.value)}
              value={pwd}
              required
            />
            <button className="btn" id="bttn" type="submit">Sign in</button>
          </form>

          <p>
            Not a member? <br />
            <span className="line">
        
              <a href="/Register">Sign Up</a>
            </span>
          </p>
        </section>
      
     </div>
    </>
  );
};

export default LogIn;
