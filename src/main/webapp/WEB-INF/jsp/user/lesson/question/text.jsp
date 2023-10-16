<%-- 
    Document   : text
    Created on : Jul 17, 2023, 5:12:42 AM
    Author     : TTNhan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="quizContent" id="question<%out.print(question.getIndex());%>">
    <div class="question">
        <p class="textQuestion"><%out.print(question.getContent());%></p>
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