<%-- 
    Document   : quiz
    Created on : Jul 5, 2023, 8:55:55 PM
    Author     : TTNhan
--%>

<%@page import="java.util.Date"%>
<%@page import="com.swp_project_g4.Model.QuizResult"%>
<%@page import="com.swp_project_g4.Database.QuizResultDAO"%>
<%@page import="com.swp_project_g4.Database.QuestionDAO"%>
<%@page import="com.swp_project_g4.Model.Question"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    QuizResult quizResult = QuizResultDAO.getLastQuizResult(user.getID(), lesson.getID());
    boolean quizFinished = false;
    if (quizResult == null) {
        //If not take quiz yet
%>
<%@include file="./quiz/preTakeQuiz.jsp" %>
<%
} else if (quizResult.getEndTime().after(new Date())) {
    //if are taking quiz
%>
<%@include file="./quiz/quizTaking.jsp" %>
<%
} else {
%>
<%@include file="./quiz/quizResult.jsp" %>
<%
    }
%>