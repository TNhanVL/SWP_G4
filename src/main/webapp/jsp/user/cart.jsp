<%-- 
    Document   : cart
    Created on : Jul 4, 2023, 10:56:30 PM
    Author     : TTNhan
--%>

<%@page import="com.swp_project_g4.Model.Course"%>
<%@page import="java.util.ArrayList"%>
<%@ page import="com.swp_project_g4.Database.CourseDAO" %>
<%@ page import="com.swp_project_g4.Database.UserDAO" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
    //check user loggedIn
    User user = null;
    if (!CookieServices.checkUserLoggedIn(request.getCookies())) {
        request.getSession().setAttribute("error", "You must be logged in before enter cart!");
        response.sendRedirect("./login");
        return;
    } else {
        user = UserDAO.getUserByUsername(CookieServices.getUserName(request.getCookies()));
    }

    ArrayList<Course> courses = CourseDAO.getAllOrderCourses(user.getID());
%>

<!DOCTYPE html>
<html lang="en">

    <head>
        <jsp:include page="head.jsp">
            <jsp:param name="title" value="Cart"/>
        </jsp:include>
        <link rel="stylesheet" href="/public/assets/css/paysite.css">
    </head>

    <body>
        <!-- HEADER -->
        <%@include file="header.jsp" %>
        <!--END HEADER -->

        <div id="body-paysite">
            <div class="shopping-cart">
                <h2 class="shopping-cart-header">
                    Shopping Cart
                </h2>

                <h4 class="number-of-course">
                    <%out.print(courses.size());%> course in Cart
                </h4>
                <!-- When cart is empty display this -->
                <div class="keepShopping ">
                    <!-- <video src="/public/assets/videos/DoggieCorgi-4.mp4"></video> -->
                    <img src="/public/assets/imgs/logoooooo.png" alt="" class="description-img">
                    <h5>Your cart is empty, keeping shopping to find a course!</h5>
                    <div class="keepshopping-btn"><a href="">Keep Shopping</a></div>
                </div>
                <!-- Course in cart -->
                <form class="courseInCart" method="post" action="checkOut">

                    <div class="items">

                        <%
                            int courseIndex = 0;
                            for (Course course : courses) {
                                courseIndex++;
                        %>

                        <!-- an item -->
                        <div class="item">
                            <input type="checkbox" id="course<%out.print(courseIndex);%>" name="course" value="<%out.print(course.getID());%>">

                            <a href="/course/<%out.print(course.getID());%>">
                                <img src="/public/media/course/<%out.print(course.getID() + "/" + course.getImage());%>" class="itemImg" alt="">
                                <div class="itemInformation">
                                    <p class="itemName"><%out.print(course.getTitle());%></p>
                                    <div class="itemRating"><%out.print(course.getRate());%><i class="fa-solid fa-star"></i> (2503 reviewer)</div>
                                </div>
                            </a>
                            <p class="itemPrice"><%out.print(course.getPrice());%>$</p>

                            <div class="itemEdit"><a href="./cart/deleteOrder/<%out.print(course.getID());%>"><i class="fa-solid fa-trash"></i></a></div>
                        </div>
                        <!-- end item -->

                        <%}%>

                    </div>
                    <div class="popup-checkout">
                        <i class="fa-solid fa-xmark close"></i>
                        <h4>Order Summary</h4>
                        <div class="component balance">
                            <p>Total Price</p><span>$0</span>
                        </div>
                        <div class="component price">
                            <p>Sale Amount</p><span>$0</span>
                        </div>
                        <!-- <div class="component remainBalance">
                            <p>Remaining Balance</p><span>$0</span>
                        </div> -->
                        <div class="component totalCost">
                            <p>Total Cost</p> <span>$0</span>
                        </div>
                        <!-- <div class="component promotionCode">
                            <form action="" method="post">
                                <input type="text" placeholder="Promotion Code">
                                <input type="submit" value="Apply">
                            </form>
                        </div> -->
                        <div class="checkout-btn"><input type="submit" value="Check out"></div>
                    </div>
                </form>
            </div>
        </div>

        <%@include file="footer.jsp" %>

        <%@include file="foot.jsp" %>

        <script src="/public/assets/js/cart.js"></script>

        <%@include file="popUpMessage.jsp" %>

    </body>

</html>