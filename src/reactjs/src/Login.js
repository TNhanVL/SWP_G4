import './Login.css';
import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom'
import UserService from './services/userService'
import Backend from './services/backend'
import { GoogleLogin, GoogleOAuthProvider } from '@react-oauth/google';
import Cookies from 'js-cookie'

function ErrorAlert({ errMessage }) {

    return (
        errMessage && <div className='ms-5 me-5 mb-3'>
            <div className="ErrorAlert badge bg-danger w-100 p-2" style={{ fontSize: 16 }}>
                {errMessage}
            </div>
        </div>
    );

}

export default function Login({ user, setUser }) {

    const navigate = useNavigate();

    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [errMessage, setErrMessage] = useState("");

    function handleUsername(e) {
        setUsername(e.target.value)
    }

    function handlePassword(e) {
        setPassword(e.target.value)
    }

    function handleErrMessage(e) {
        setErrMessage(e)
    }

    function loginSucceed(user, accessToken) {
        Cookies.set('accessToken', accessToken)
        setUser(user)
        let redirectUrl = localStorage.getItem('redirectIfLoggedIn')
        if (redirectUrl) {
            window.location.replace(redirectUrl)
            localStorage.setItem('redirectIfLoggedIn', '')
            return
        }

        if (UserService.isAdmin(user)) {
            navigate('/admin')
        } else {
            navigate('/')
        }
    }

    function tryLogin(e) {
        e.target.disabled = true;
        e.preventDefault();
        Backend.post('user/login', {
            username,
            password
        }).then(res => {
            if (res.success) {
                loginSucceed(res.user, res.accessToken)
            } else {
                handleErrMessage("Wrong username or password")
            }
            e.target.disabled = false;
        })
    }

    function loginGGSuccess(credentialResponse) {
        Backend.post('user/loginWithGG', {
            credential: credentialResponse
        }).then(res => {
            if (res.success) {
                loginSucceed(res.user, res.accessToken)
            } else {
                handleErrMessage("Wrong username or password")
            }
        })
    }

    function cancel() {
        setUsername('')
        setPassword('')
        setErrMessage('')
    }

    return (
        <div className="Login">

            {/* <div className='loginForm'>
                <h3>Admin Login</h3>
                <ErrorAlert errMessage={errMessage} />
                <form>
                    <div>
                        <label>Username</label>
                        <input type='text' value={username} onChange={handleUsername}></input>
                    </div>
                    <div>
                        <label>Password</label>
                        <input type='password' value={password} onChange={handlePassword}></input>
                    </div>
                    <div>
                        <input type='submit' value="Login" onClick={tryLogin}></input>
                    </div>
                </form>
            </div> */}

            <div className="modal modal-sheet d-block bg-body-secondary p-4 py-md-5" tabIndex="-1" role="dialog" id="modalSignin">
                <div className="modal-dialog" role="document">
                    <div className="modal-content rounded-4 shadow">
                        <div className="modal-header pt-5 border-bottom-0 ms-auto me-auto d-block">
                            <img className="d-inline" src="/anhngusimple_no_background.png" height={50}></img>
                        </div>
                        <div className="modal-header p-5 pb-4 pt-0 border-bottom-0 ms-auto me-auto d-block">
                            <h1 className="fw-bold mb-0 fs-2">Anh Ngữ Simple Cần Thơ</h1>
                        </div>

                        {/* <div id="errorMessage"
                            className="modal-header border-bottom-0 mb-3 ms-5 me-5 rounded-3 bg-danger text-white">
                            <div>
                                <h5 className="mb-0"></h5>
                            </div>
                        </div> */}

                        <ErrorAlert errMessage={errMessage} />

                        <div className="modal-body p-5 pt-0">
                            <div className="form-floating mb-3">
                                <input type="text" className="form-control rounded-3" id="username"
                                    placeholder="Username" value={username} onChange={handleUsername} required></input>
                                <label htmlFor="username">Username</label>
                            </div>
                            <div className="form-floating mb-3">
                                <input type="password" className="form-control rounded-3" id="password"
                                    placeholder="Password" value={password} onChange={handlePassword} required></input>
                                <label htmlFor="password">Password</label>
                            </div>

                            <div className="form-floating mb-3 row">
                                <div className="col-sm-5">
                                    <button className="w-100 mb-2 btn rounded-3 btn-primary col-md-3" onClick={tryLogin}>Login</button>
                                </div>
                                <div className="col-sm-2"></div>
                                <div className="col-sm-5">
                                    <button className="w-100 mb-2 btn rounded-3 btn-danger col-md-3" type="button"
                                        id="cancelButton" onClick={cancel}>Cancel</button>
                                </div>
                            </div>

                            {/* <!-- <button className="w-100 mb-2 btn btn-lg rounded-3 btn-primary" type="submit">Login</button> -->
                                <!-- <small className="text-body-secondary">By clicking Sign up, you agree to the terms of use.</small> --> */}

                            <GoogleOAuthProvider clientId="53091835834-p29ui8qhqpehu9juqkdthbp7vcsnr39f.apps.googleusercontent.com">
                                <GoogleLogin
                                    onSuccess={loginGGSuccess}
                                    onError={() => {
                                        console.log('Login Failed');
                                    }}
                                    shape='pill'
                                />
                            </GoogleOAuthProvider>


                        </div>
                    </div>
                </div>
            </div>
        </div>
    );

}