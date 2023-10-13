<%-- 
    Document   : image
    Created on : Jul 5, 2023, 6:33:17 PM
    Author     : TTNhan
--%>

<%@page import="com.swp_project_g4.Model.Question"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="quizContent" id="question<%out.print(question.getIndex());%>">
    <div class="question">
        <%
            if ((question.getType() / 10) % 2 == 0) {
        %>
        <img src="/public/media/question/<%out.print(question.getID() + "/" + question.getContent());%>" alt="">
        <%
        } else {
        %>
        <img src="<%out.print(question.getContent());%>" alt="">
        <%
            }
        %>
    </div>
    <div class="answer">
        <%
            switch (question.getType() % 10) {
                case 0: {%>
        <%@include file="answer/multipleChoiceOneOption.jsp" %>
        <%                break;
            }
            case 1: {%>
        <%@include file="answer/multipleChoiceMultiOption.jsp" %>
        <%}
                default: {
                    break;
                }
            }
        %>

    </div>
</div>