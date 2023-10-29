import './Navbar.css'
import { useNavigate } from 'react-router-dom'
import Cookies from 'js-cookie'

var usertmp = {
    name: "Guest",
    picture: "https://icons.veryicon.com/png/o/miscellaneous/youyinzhibo/guest.png"
}

function Navbar({ user }) {

    const navigate = useNavigate();

    function logout() {
        Cookies.set('accessToken', '')
        navigate('/login')
    }

    return (
        <div id="header">
            <div class="left-side">
                <a href="index.html">
                    <img src="./assets/imgs/logo.png" alt="logo" class="logo"></img>
                </a>
                <form action="">
                    <input type="text" class="search-course" name="headerSearch" placeholder="Searching"></input>
                </form>
                <div class="course-opption">
                    <a href="courseShopping.html">Courses</a>
                </div>
                <div class="quesAndAns">
                    <a href="#">Expert Q&A</a>
                </div>
            </div>

            <div class="right-side">
                <a href="paysite.html" class="cart">
                    <i class="fa-solid fa-cart-shopping"></i>
                    <div class="quantity">3</div>
                </a>
                <a href="" class="notification">
                    <i class="fa-sharp fa-solid fa-bell"></i>
                    <div class="quantity">3</div>
                </a>
                <div onclick="openMenu()" id="user" class="user">
                    <a href="#">
                        <img src="./assets/imgs/D1.jpg" alt="avatar"></img>
                            <span class="userInfor">
                                Thanh Duong
                            </span>
                    </a>

                    <div id="userMenu" class="userMenu close">
                        <a href="profile.html">
                            <i class="fa-solid fa-user"></i>
                            <span>Profile</span>
                        </a>
                        <a href="#">
                            <i class="fa-solid fa-gear"></i>
                            <span>Setting</span>
                        </a>
                        <a href="#">
                            <i class="fa-solid fa-right-from-bracket"></i>
                            <span>Logout</span>
                        </a>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default Navbar;