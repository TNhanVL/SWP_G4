import './Navbar.css'
import {useNavigate} from 'react-router-dom'
import Cookies from 'js-cookie'

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

function Navbar({learner}) {

    const navigate = useNavigate();

    function logout() {
        Cookies.set('accessToken', '')
        navigate('/login')
    }

    return (
        <div id="header">
            <div className="left-side">
                <a href="index.html">
                    <img src="/public/assets/imgs/logo.png" alt="logo" className="logo"></img>
                </a>
                <form action="">
                    <input type="text" className="search-course" name="headerSearch" placeholder="Searching"></input>
                </form>
                <div className="course-opption">
                    <a href="/">Courses</a>
                </div>
                <div className="quesAndAns">
                    <a href="#">Expert Q&A</a>
                </div>
            </div>

            <div className="right-side">
                <a href="paysite.html" className="cart">
                    <i className="fa-solid fa-cart-shopping"></i>
                    <div className="quantity">3</div>
                </a>
                <a href="" className="notification">
                    <i className="fa-sharp fa-solid fa-bell"></i>
                    <div className="quantity">3</div>
                </a>
                <div onClick={openMenu} id="user" className="user">
                    <a href="#">
                        <img src={"/public/media/user/" + learner.id + "/" + learner.picture} alt="avatar"></img>
                        <span
                            className="userInfor">{learner ? learner.firstName + ' ' + learner.lastName : usertmp.usertmp}</span>
                    </a>

                    <div id="userMenu" className="userMenu close">
                        <a href="profile.html">
                            <i className="fa-solid fa-user"></i>
                            <span>Profile</span>
                        </a>
                        <a href="#">
                            <i className="fa-solid fa-gear"></i>
                            <span>Setting</span>
                        </a>
                        <a href="#">
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