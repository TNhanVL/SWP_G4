<%-- 
    Document   : rightSideCourse
    Created on : Oct 3, 2023, 10:40:20 PM
    Author     : TTNhan
--%>

<%@page import="com.swp_project_g4.Database.CourseDAO"%>
<%@page import="com.swp_project_g4.Model.Course"%>
<%@page import="com.swp_project_g4.Database.ChapterDAO"%>
<%@page import="com.swp_project_g4.Model.Chapter"%>
<%@page import="com.swp_project_g4.Model.Lesson"%>
<%@page import="com.swp_project_g4.Service.CookieServices"%>
<%@page import="com.swp_project_g4.Database.LearnerDAO"%>
<%@page import="com.swp_project_g4.Model.User"%>
<%@page import="com.swp_project_g4.Database.LessonDAO"%>
<%@ page import="com.swp_project_g4.Database.LessonDAO" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%

    User user1 = LearnerDAO.getUserByUsername(CookieServices.getUserNameOfLearner(request.getCookies()));
    Lesson lesson1 = LessonDAO.getLesson(Integer.parseInt(request.getParameter("lessonID")));
    Chapter chapter1 = ChapterDAO.getChapter(lesson1.getChapterID());
    Course course1 = CourseDAO.getCourse(chapter1.getCourseID());
    
%>

<div class="rightSide">
    <h4><%out.print(course1.getName());%></h4>

    <!-- part -->
    <div class="part open">
        <div class="partHeader">
            <h5><%out.print(chapter1.getName());%></h5>
            <i class="fa-solid fa-sort-down"></i>
        </div>

        <div class="partBody">
            <!-- each lesson is a div -->
            <!-- Start lesson -->
            <div class="lesson">
                <span class="lesson-status">
                    <!-- checked -->
                    <i class="fa-solid fa-square-check">
                        <!-- unchecked -->
                        <!-- <i class="fa-regular fa-square"></i> -->
                    </i></span>
                <div class="lesson-content">
                    <p class="title">1. Row A, Ka</p>
                    <span class="description">
                        <span class="type">
                            <!-- type video -->
                            <i class="fa-brands fa-youtube"></i>
                            <!-- type text -->
                            <!-- <i class="fa-solid fa-file-lines"></i> -->
                        </span>
                        <span class="time">13 minute</span>
                    </span>
                </div>
            </div>
            <!-- End lesson -->

            <!-- Start lesson -->
            <div class="lesson">
                <span class="lesson-status">
                    <!-- checked -->
                    <!-- <i class="fa-solid fa-square-check"> -->
                    <!-- unchecked -->
                    <i class="fa-regular fa-square"></i>
                    </i></span>
                <div class="lesson-content">
                    <p class="title">1. Row A, Ka</p>
                    <span class="description">
                        <span class="type">
                            <!-- type video -->
                            <!-- <i class="fa-brands fa-youtube"></i> -->
                            <!-- type text -->
                            <i class="fa-solid fa-file-lines"></i>
                        </span>
                        <span class="time">13 minute</span>
                    </span>
                </div>
            </div>
            <!-- End lesson -->
            <!-- Start lesson -->
            <div class="lesson">
                <span class="lesson-status">
                    <!-- checked -->
                    <!-- <i class="fa-solid fa-square-check"> -->
                    <!-- unchecked -->
                    <i class="fa-regular fa-square"></i>
                    </i></span>
                <div class="lesson-content">
                    <p class="title">1. Row A, Ka</p>
                    <span class="description">
                        <span class="type">
                            <!-- type video -->
                            <!-- <i class="fa-brands fa-youtube"></i> -->
                            <!-- type text -->
                            <i class="fa-solid fa-file-lines"></i>
                        </span>
                        <span class="time">13 minute</span>
                    </span>
                </div>
            </div>
            <!-- End lesson -->
            <!-- Start lesson -->
            <div class="lesson">
                <span class="lesson-status">
                    <!-- checked -->
                    <!-- <i class="fa-solid fa-square-check"> -->
                    <!-- unchecked -->
                    <i class="fa-regular fa-square"></i>
                    </i></span>
                <div class="lesson-content">
                    <p class="title">1. Row A, Ka</p>
                    <span class="description">
                        <span class="type">
                            <!-- type video -->
                            <!-- <i class="fa-brands fa-youtube"></i> -->
                            <!-- type text -->
                            <i class="fa-solid fa-file-lines"></i>
                        </span>
                        <span class="time">13 minute</span>
                    </span>
                </div>
            </div>
            <!-- End lesson -->
            <!-- Start lesson -->
            <div class="lesson">
                <span class="lesson-status">
                    <!-- checked -->
                    <!-- <i class="fa-solid fa-square-check"> -->
                    <!-- unchecked -->
                    <i class="fa-regular fa-square"></i>
                    </i></span>
                <div class="lesson-content">
                    <p class="title">1. Row A, Ka</p>
                    <span class="description">
                        <span class="type">
                            <!-- type video -->
                            <!-- <i class="fa-brands fa-youtube"></i> -->
                            <!-- type text -->
                            <i class="fa-solid fa-file-lines"></i>
                        </span>
                        <span class="time">13 minute</span>
                    </span>
                </div>
            </div>
            <!-- End lesson --><!-- Start lesson -->
            <div class="lesson">
                <span class="lesson-status">
                    <!-- checked -->
                    <!-- <i class="fa-solid fa-square-check"> -->
                    <!-- unchecked -->
                    <i class="fa-regular fa-square"></i>
                    </i></span>
                <div class="lesson-content">
                    <p class="title">1. Row A, Ka</p>
                    <span class="description">
                        <span class="type">
                            <!-- type video -->
                            <!-- <i class="fa-brands fa-youtube"></i> -->
                            <!-- type text -->
                            <i class="fa-solid fa-file-lines"></i>
                        </span>
                        <span class="time">13 minute</span>
                    </span>
                </div>
            </div>
            <!-- End lesson -->


        </div>

    </div>
    <!-- end part -->

    <!-- part -->
    <div class="part open">
        <div class="partHeader">
            <h5>Part 1: Hiragana 。ひらがな</h5>
            <i class="fa-solid fa-sort-down"></i>
        </div>

        <div class="partBody">
            <!-- each lesson is a div -->
            <!-- Start lesson -->
            <div class="lesson">
                <span class="lesson-status">
                    <!-- checked -->
                    <i class="fa-solid fa-square-check">
                        <!-- unchecked -->
                        <!-- <i class="fa-regular fa-square"></i> -->
                    </i></span>
                <div class="lesson-content">
                    <p class="title">1. Row A, Ka</p>
                    <span class="description">
                        <span class="type">
                            <!-- type video -->
                            <i class="fa-brands fa-youtube"></i>
                            <!-- type text -->
                            <!-- <i class="fa-solid fa-file-lines"></i> -->
                        </span>
                        <span class="time">13 minute</span>
                    </span>
                </div>
            </div>
            <!-- End lesson -->

            <!-- Start lesson -->
            <div class="lesson">
                <span class="lesson-status">
                    <!-- checked -->
                    <!-- <i class="fa-solid fa-square-check"> -->
                    <!-- unchecked -->
                    <i class="fa-regular fa-square"></i>
                    </i></span>
                <div class="lesson-content">
                    <p class="title">1. Row A, Ka</p>
                    <span class="description">
                        <span class="type">
                            <!-- type video -->
                            <!-- <i class="fa-brands fa-youtube"></i> -->
                            <!-- type text -->
                            <i class="fa-solid fa-file-lines"></i>
                        </span>
                        <span class="time">13 minute</span>
                    </span>
                </div>
            </div>
            <!-- End lesson -->
            <!-- Start lesson -->
            <div class="lesson">
                <span class="lesson-status">
                    <!-- checked -->
                    <!-- <i class="fa-solid fa-square-check"> -->
                    <!-- unchecked -->
                    <i class="fa-regular fa-square"></i>
                    </i></span>
                <div class="lesson-content">
                    <p class="title">1. Row A, Ka</p>
                    <span class="description">
                        <span class="type">
                            <!-- type video -->
                            <!-- <i class="fa-brands fa-youtube"></i> -->
                            <!-- type text -->
                            <i class="fa-solid fa-file-lines"></i>
                        </span>
                        <span class="time">13 minute</span>
                    </span>
                </div>
            </div>
            <!-- End lesson -->
            <!-- Start lesson -->
            <div class="lesson">
                <span class="lesson-status">
                    <!-- checked -->
                    <!-- <i class="fa-solid fa-square-check"> -->
                    <!-- unchecked -->
                    <i class="fa-regular fa-square"></i>
                    </i></span>
                <div class="lesson-content">
                    <p class="title">1. Row A, Ka</p>
                    <span class="description">
                        <span class="type">
                            <!-- type video -->
                            <!-- <i class="fa-brands fa-youtube"></i> -->
                            <!-- type text -->
                            <i class="fa-solid fa-file-lines"></i>
                        </span>
                        <span class="time">13 minute</span>
                    </span>
                </div>
            </div>
            <!-- End lesson -->
            <!-- Start lesson -->
            <div class="lesson">
                <span class="lesson-status">
                    <!-- checked -->
                    <!-- <i class="fa-solid fa-square-check"> -->
                    <!-- unchecked -->
                    <i class="fa-regular fa-square"></i>
                    </i></span>
                <div class="lesson-content">
                    <p class="title">1. Row A, Ka</p>
                    <span class="description">
                        <span class="type">
                            <!-- type video -->
                            <!-- <i class="fa-brands fa-youtube"></i> -->
                            <!-- type text -->
                            <i class="fa-solid fa-file-lines"></i>
                        </span>
                        <span class="time">13 minute</span>
                    </span>
                </div>
            </div>
            <!-- End lesson --><!-- Start lesson -->
            <div class="lesson">
                <span class="lesson-status">
                    <!-- checked -->
                    <!-- <i class="fa-solid fa-square-check"> -->
                    <!-- unchecked -->
                    <i class="fa-regular fa-square"></i>
                    </i></span>
                <div class="lesson-content">
                    <p class="title">1. Row A, Ka</p>
                    <span class="description">
                        <span class="type">
                            <!-- type video -->
                            <!-- <i class="fa-brands fa-youtube"></i> -->
                            <!-- type text -->
                            <i class="fa-solid fa-file-lines"></i>
                        </span>
                        <span class="time">13 minute</span>
                    </span>
                </div>
            </div>
            <!-- End lesson -->


        </div>

    </div>
    <!-- end part -->

    <!-- part -->
    <div class="part open">
        <div class="partHeader">
            <h5>Part 1: Hiragana 。ひらがな</h5>
            <i class="fa-solid fa-sort-down"></i>
        </div>

        <div class="partBody">
            <!-- each lesson is a div -->
            <!-- Start lesson -->
            <div class="lesson">
                <span class="lesson-status">
                    <!-- checked -->
                    <i class="fa-solid fa-square-check">
                        <!-- unchecked -->
                        <!-- <i class="fa-regular fa-square"></i> -->
                    </i></span>
                <div class="lesson-content">
                    <p class="title">1. Row A, Ka</p>
                    <span class="description">
                        <span class="type">
                            <!-- type video -->
                            <i class="fa-brands fa-youtube"></i>
                            <!-- type text -->
                            <!-- <i class="fa-solid fa-file-lines"></i> -->
                        </span>
                        <span class="time">13 minute</span>
                    </span>
                </div>
            </div>
            <!-- End lesson -->

            <!-- Start lesson -->
            <div class="lesson">
                <span class="lesson-status">
                    <!-- checked -->
                    <!-- <i class="fa-solid fa-square-check"> -->
                    <!-- unchecked -->
                    <i class="fa-regular fa-square"></i>
                    </i></span>
                <div class="lesson-content">
                    <p class="title">1. Row A, Ka</p>
                    <span class="description">
                        <span class="type">
                            <!-- type video -->
                            <!-- <i class="fa-brands fa-youtube"></i> -->
                            <!-- type text -->
                            <i class="fa-solid fa-file-lines"></i>
                        </span>
                        <span class="time">13 minute</span>
                    </span>
                </div>
            </div>
            <!-- End lesson -->
            <!-- Start lesson -->
            <div class="lesson">
                <span class="lesson-status">
                    <!-- checked -->
                    <!-- <i class="fa-solid fa-square-check"> -->
                    <!-- unchecked -->
                    <i class="fa-regular fa-square"></i>
                    </i></span>
                <div class="lesson-content">
                    <p class="title">1. Row A, Ka</p>
                    <span class="description">
                        <span class="type">
                            <!-- type video -->
                            <!-- <i class="fa-brands fa-youtube"></i> -->
                            <!-- type text -->
                            <i class="fa-solid fa-file-lines"></i>
                        </span>
                        <span class="time">13 minute</span>
                    </span>
                </div>
            </div>
            <!-- End lesson -->
            <!-- Start lesson -->
            <div class="lesson">
                <span class="lesson-status">
                    <!-- checked -->
                    <!-- <i class="fa-solid fa-square-check"> -->
                    <!-- unchecked -->
                    <i class="fa-regular fa-square"></i>
                    </i></span>
                <div class="lesson-content">
                    <p class="title">1. Row A, Ka</p>
                    <span class="description">
                        <span class="type">
                            <!-- type video -->
                            <!-- <i class="fa-brands fa-youtube"></i> -->
                            <!-- type text -->
                            <i class="fa-solid fa-file-lines"></i>
                        </span>
                        <span class="time">13 minute</span>
                    </span>
                </div>
            </div>
            <!-- End lesson -->
            <!-- Start lesson -->
            <div class="lesson">
                <span class="lesson-status">
                    <!-- checked -->
                    <!-- <i class="fa-solid fa-square-check"> -->
                    <!-- unchecked -->
                    <i class="fa-regular fa-square"></i>
                    </i></span>
                <div class="lesson-content">
                    <p class="title">1. Row A, Ka</p>
                    <span class="description">
                        <span class="type">
                            <!-- type video -->
                            <!-- <i class="fa-brands fa-youtube"></i> -->
                            <!-- type text -->
                            <i class="fa-solid fa-file-lines"></i>
                        </span>
                        <span class="time">13 minute</span>
                    </span>
                </div>
            </div>
            <!-- End lesson --><!-- Start lesson -->
            <div class="lesson">
                <span class="lesson-status">
                    <!-- checked -->
                    <!-- <i class="fa-solid fa-square-check"> -->
                    <!-- unchecked -->
                    <i class="fa-regular fa-square"></i>
                    </i></span>
                <div class="lesson-content">
                    <p class="title">1. Row A, Ka</p>
                    <span class="description">
                        <span class="type">
                            <!-- type video -->
                            <!-- <i class="fa-brands fa-youtube"></i> -->
                            <!-- type text -->
                            <i class="fa-solid fa-file-lines"></i>
                        </span>
                        <span class="time">13 minute</span>
                    </span>
                </div>
            </div>
            <!-- End lesson -->


        </div>

    </div>
    <!-- end part -->

</div>
