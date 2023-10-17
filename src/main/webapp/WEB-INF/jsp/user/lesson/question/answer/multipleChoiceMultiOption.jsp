<%-- 
    Document   : multipleChoiceMultiOption
    Created on : Oct 5, 2023, 6:50:44 PM
    Author     : TTNhan
--%>

<%@page import="com.swp_project_g4.Database.ChosenAnswerDAO"%>
<%@page import="com.swp_project_g4.Model.Question"%>
<%@page import="java.util.Collections"%>
<%@page import="com.swp_project_g4.Database.AnswerDAO"%>
<%@page import="com.swp_project_g4.Model.Answer"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<p><b>Choose all correct answer:</b></p>
<%
//    Question question = null;
    ArrayList<Answer> answers = AnswerDAO.getAnswersByQuestionID(question.getQuestionID());
    if (!quizFinished) {
        Collections.shuffle(answers);
    }
    for (Answer answer : answers) {
        String answerName = "answer" + answer.getAnswerID();
        boolean checked = ChosenAnswerDAO.CheckChosenAnswer(quizResult.getQuizResultID(), question.getQuestionID(), answer.getAnswerID());
        String correctClass = "";
        if (quizFinished && checked) {
            correctClass = AnswerDAO.getAnswer(answer.getAnswerID()).isCorrect() ? "correct" : "incorrect";
        }
%>
<label for="<%out.print(answerName);%>" <%if (correctClass != "") {
        out.print("class=\"" + correctClass + "\"");
    }%>><input type="checkbox" id="<%out.print(answerName);%>" name="question<%out.print(question.getQuestionID());%>"
        value="<%out.print(answer.getAnswerID());%>"
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
