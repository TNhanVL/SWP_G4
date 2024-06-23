<%-- 
    Document   : multipleChoiceOneOption
    Created on : Oct 5, 2023, 6:45:38 PM
    Author     : TTNhan
--%>

<%@page import="com.swp_project_g4.Model.Question"%>
<%@page import="java.util.Collections"%>
<%@page import="com.swp_project_g4.Database.AnswerDAO"%>
<%@page import="com.swp_project_g4.Model.Answer"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<p><b>Choose 1 answer:</b></p>
<%
//    Question question = null;
    ArrayList<Answer> answers = new ArrayList<>((List<Answer>) request.getAttribute("answers_of_question_" + question.getID()));
    if (!quizResult.isFinished()) {
        Collections.shuffle(answers);
    }
    for (Answer answer : answers) {
        String answerName = "answer" + answer.getID();
        Boolean checked = (Boolean) request.getAttribute("checked_" + answer.getID());
        if (checked == null) checked = false;
        String correctClass = "";
        if (quizResult.isFinished() && checked) {
            correctClass = answer.isCorrect() ? "correct" : "incorrect";
        }
%>
<label for="<%out.print(answerName);%>" <%if (correctClass != "") {
        out.print("class=\"" + correctClass + "\"");
    }%>><input type="radio" id="<%out.print(answerName);%>" name="question<%out.print(question.getID());%>"
        value="<%out.print(answer.getID());%>"
        <%if (checked) {
                out.print(" checked");
            }
            if (quizResult.isFinished()) {
                out.print(" disabled");
            }
        %>
        ><%out.print(answer.getContent());%></label><br>
    <%
        }
    %>
