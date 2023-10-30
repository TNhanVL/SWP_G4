function Footer() {

    return (
        <div id="footer">
            <div className="contact row">
                <div className="introduction col-md-6">
                    <div className="logo-name">
                        <img src="./assets/imgs/logo.png" alt=""/>
                        <h2>Yojihan</h2>
                    </div>
                    <p>
                        Instructors from around the world teach millions of students on Udemy. We provide the tools and
                        skills to teach what you love.
                    </p>
                    <div className="social-media">
                        <i className="fa-brands fa-instagram"></i>
                        <i className="fa-brands fa-facebook"></i>
                        <i className="fa-brands fa-github"></i>
                        <i className="fa-brands fa-twitter"></i>
                    </div>
                </div>
                <div className="head help col-md-2">HELP
                    <p>Help</p>
                    <p>Discuss</p>
                    <p>Contact us</p>

                </div>
                <div className="head pay col-md-2">PAYMENT METHODS
                    <p>Paypal</p>
                    <p>Visa</p>
                </div>
                <div className="head info col-md-2">INFORMATION
                    <p>About us</p>
                </div>
            </div>
            <div className="copyright">
                <p>Powered by GR2 © 2023. All Rights Reserved. rev 25/05/2023 02:25:34 PM</p>
            </div>
        </div>
    );
}

export default Footer