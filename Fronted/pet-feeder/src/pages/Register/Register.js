import { useRef, useState, useEffect } from "react";

import './Register.css';
import axios from "../../api/axios";


// validation of user input for email and password
const EMAIL_REGEX = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
const PWD_REGEX = /^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%]).{8,24}$/;
const REGISTER_URL = "/auth/register"; // should be endpoint for registration in backend api


const Register = () => {
    // set the focus on the user input when comp loads
    const userRef = useRef();
    // error ref - if error pops focus changes to it
    const errRef = useRef();

    const [user, setUser] = useState('');
    const [validEmail, setValidEmail] = useState(false); // check email validation
    const [userFocus, setUserFocus] = useState(false); // wether we have focus on input field

    const [pwd, setPwd] = useState('');
    const [validPwd, setValidPwd] = useState(false); // check password validation
    const [pwdFocus, setPwdFocus] = useState(false); // wether we have focus on input field

    const [matchPwd, setMatchPwd] = useState('');
    const [validMatch, setValidMatch] = useState(false); // check password match validation
    const [ setMatchFocus] = useState(false); // wether we have focus on input field

    const [firstName, setFirstName] = useState('');
    const [lastName, setLastName] = useState('');

    // error and success message states
    const [errMSg, setErrMsg] = useState('');
    const [success, setSuccess] = useState(false);

    useEffect(() => {
        userRef.current.focus(); // set the focus when the component loads
    }, []);

    // applied to validate email
    useEffect(() => {
        const result = EMAIL_REGEX.test(user);
        setValidEmail(result);
    }, [user]);

    // validation of password - checking if it matches
    useEffect(() => {
        const result = PWD_REGEX.test(pwd);
        setValidPwd(result);
        // confirmation is defined with match
        const match = pwd === matchPwd;
        setValidMatch(match);
    }, [pwd, matchPwd]);

    // error message to be cleared anytime info state is changed
    useEffect(() => {
        setErrMsg('');
    }, [user, pwd, matchPwd]);

    const handleSubmit = async (e) => {
        e.preventDefault();
        // if button enabled with JS hack
        const v1 = EMAIL_REGEX.test(user);
        const v2 = PWD_REGEX.test(pwd);
        if (!v1 || !v2) {
            setErrMsg("Invalid Entry");
            return;
        }
       try {
         // user and pwd is what backend is expecting
        const response = await axios.post(REGISTER_URL, JSON.stringify({email: user, password: pwd, firstName, lastName}),{
            headers: { 'Content-Type': 'application/json'},
            withCredentials: true
        }
        );

        const token = response.data.token;

        localStorage.setItem('token', token);


        console.log(JSON.stringify(response));
        setSuccess(true);
        
       } catch (error) {
        if (!error?.response) {
            setErrMsg('No Server Response');
        } else if (error.response?.status === 409) {
            setErrMsg('User Taken');
        } else {
            setErrMsg('Registration Failed')
        }
        errRef.current.focus();
       }
    };

    return (
        <>
        <div className="b">
            {success ? (
                <section >
                    <p>
                        {/* replace with react router link */}
                        <a href="/" className="link">Sign In</a>
                    </p>
                </section>
            ) : (
                <section >
                    <p ref={errRef} className={errMSg ? "errmsg" : "offscreen"} aria-live="assertive">{errMSg}</p>
                    <h1 className="title">Create Account</h1>
                    <form onSubmit={handleSubmit}>
                        <label htmlFor="firstName" className="form-label">
                            First Name:
                        </label>
                        <input
                            type="text"
                            id="firstName"
                            className="form-control"
                            autoComplete="off"
                            onChange={(e) => setFirstName(e.target.value)}
                            value={firstName}
                        />
                        <label htmlFor="lastName" className="form-label">
                            Last Name:
                        </label>
                        <input
                            type="text"
                            id="lastName"
                            className="form-control"
                            autoComplete="off"
                            onChange={(e) => setLastName(e.target.value)}
                            value={lastName}
                        />
                        <label htmlFor="email" className="form-label">
                            Email:
                            <span  className={validEmail ? "valid" : "hide"} />
                            <apan  className={validEmail || !user ? "hide" : "invalid"} />
                        </label>
                        <input
                            type="text"
                            id="email"
                            
                            className="form-control input-text"
                            ref={userRef}
                            autoComplete="off"
                            onChange={(e) => setUser(e.target.value)}
                            value={user}
                            required
                            aria-invalid={validEmail ? "false" : "true"}
                            aria-describedby="uidnote"
                            onFocus={() => setUserFocus(true)}
                            onBlur={() => setUserFocus(false)}
                        />
                        <p id="uidnote" className={userFocus && user && !validEmail ? "instructions" : "offscreen"}>
                            
                            Must contain @.<br />
                            Letters, numbers, underscores, hyphens allowed.
                        </p>

                        <label htmlFor="password" className="form-label">
                            Password:
                            <span className={validPwd ? "valid" : "hide"} />
                            <span className={validPwd || !pwd ? "hide" : "invalid"} />
                        </label>
                        <input
                            type="password"
                            id="password"
                            className="form-control"
                            onChange={(e) => setPwd(e.target.value)}
                            value={pwd}
                            required
                            aria-invalid={validPwd ? "false" : "true"}
                            aria-describedby="pwdnote"
                            onFocus={() => setPwdFocus(true)}
                            onBlur={() => setPwdFocus(false)}
                        />
                        <p id="pwdnote" className={pwdFocus && !validPwd ? "instructions" : "offscreen"}>
                          
                            8 to 24 characters.<br />
                            Must include uppercase and lowercase letters, a number, and a special character.<br />
                            Allowed special characters: <span aria-label="exclamation mark">!</span> <span aria-label="at symbol">@</span> <span aria-label="hashtag">#</span> <span aria-label="dollar sign">$</span> <span aria-label="percent">%</span>
                        </p>

                        <label htmlFor="confirm_pwd" className="form-label">
                            Confirm Password:
                            <span className={validMatch && matchPwd ? "valid" : "hide"} />
                            <span className={validMatch || !matchPwd ? "hide" : "invalid"} />
                        </label>
                        <input
                            type="password"
                            id="confirm_pwd"
                            className="form-control"
                            onChange={(e) => setMatchPwd(e.target.value)}
                            value={matchPwd}
                            required
                            aria-invalid={validMatch ? "false" : "true"}
                            aria-describedby="confirmnote"
                            onFocus={() => setMatchFocus(true)}
                            onBlur={() => setMatchFocus(false)}
                        />

                        <button  className="btn" id="bttn"disabled={!validEmail || !validPwd || !validMatch || !firstName || !lastName ? true : false}>Sign Up</button>

                    </form>
                    <p>
                        Already registered?<br />
                        <span className="line">
                            {/* replace router link here!! */}
                            <a className="link" href="/">Sign In</a>{/* replace w lpgin*/}
                        </span>
                    </p>
                </section>

            )} 
            </div>
            

        </>
    )
}

export default Register;
