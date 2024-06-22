<%-- 
    Document   : quiz
    Created on : Oct 5, 2023, 8:55:55 PM
    Author     : TTNhan
--%>

<%@page import="java.util.Date"%>
<%@page import="com.swp_project_g4.Model.QuizResult"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    QuizResult quizResult = (QuizResult) request.getAttribute("quizResult");
    if (quizResult == null) {
        //If not take quiz yet
%>
<%@include file="./quiz/preTakeQuiz.jsp" %>
<%
} else if (quizResult.getEndAt().after(new Date())) {
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