<%-- 
    Document   : multipleChoiceOneOption
    Created on : Jul 5, 2023, 6:45:38 PM
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
    ArrayList<Answer> answers = AnswerDAO.getAnswersByQuestionID(question.getID());
    if (!quizFinished) {
        Collections.shuffle(answers);
    }
    for (Answer answer : answers) {
        String answerName = "answer" + answer.getID();
        boolean checked = QuestionResultDAO.CheckQuestionResult(quizResult.getID(), question.getID(), answer.getID());
        String correctClass = "";
        if (quizFinished && checked) {
            correctClass = AnswerDAO.getAnswer(answer.getID()).isCorrect() ? "correct" : "incorrect";
        }
%>
<label for="<%out.print(answerName);%>" <%if (correctClass != "") {
        out.print("class=\"" + correctClass + "\"");
    }%>><input type="radio" id="<%out.print(answerName);%>" name="question<%out.print(question.getID());%>"
        value="<%out.print(answer.getID());%>"
        <%if (checked) {
                out.print(" checked");
            }
            if (quizFinished) {
                out.print(" disabled");
            }
        %>
        ><%out.print(answer.getContent());%></label><br>
    <%
        }
    %>
