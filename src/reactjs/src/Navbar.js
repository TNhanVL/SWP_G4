import './Navbar.css'
import {useNavigate} from 'react-router-dom'
import Cookies from 'js-cookie'
import {useEffect} from "react";

var usertmp = {
    name: "Guest",
    picture: "https://icons.veryicon.com/png/o/miscellaneous/youyinzhibo/guest.png"
}

function openMenu() {

    var b = document.getElementById("userMenu")

    if (b.classList.contains("close")) {
        b.classList.remove("close")
        // console.log("Show")
    } else {
        b.classList.add("close")
        // console.log("Remove")
    }
}

function Navbar({instructor, admin}) {

    const navigate = useNavigate();

    function logout() {
        Cookies.set('accessToken', '')
        navigate('/login')
    }

    return (
        <div id="header">
            <div className="left-side">
                <a href="/">
                    <img src="/public/assets/imgs/logo.png" alt="logo" className="logo"></img>
                </a>
                <form action="">
                    <input type="text" className="search-course" name="headerSearch" placeholder="Searching"></input>
                </form>
                <div className="course-opption">
                    <a href="/course/all">Courses</a>
                </div>
            </div>

            <div className="right-side">
                <div onClick={openMenu} id="user" className="user">
                    <a href="#">
                        <img
                            src={instructor ? "/public/media/instructor/" + instructor.id + "/" + instructor.picture : usertmp.picture}
                            alt="avatar"></img>
                        <span
                            className="userInfor">{instructor ? instructor.username + "(instructor)" : (admin ? 'admin' : usertmp.name)}</span>
                    </a>

                    <div id="userMenu" className="userMenu close">
                        {(instructor) &&
                            <a href={"/profile/instructor/" + instructor.username}>
                                <i className="fa-solid fa-user"></i>
                                <span>Profile</span>
                            </a>}
                        <a href="/logout?token=instructor">
                            <i className="fa-solid fa-right-from-bracket"></i>
                            <span>Logout</span>
                        </a>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default Navbar;