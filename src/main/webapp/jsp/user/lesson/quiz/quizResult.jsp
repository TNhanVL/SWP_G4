<%-- 
    Document   : quizResult
    Created on : Jul 7, 2023, 2:58:05 PM
    Author     : TTNhan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    //if have finished quiz
    quizFinished = true;
    ArrayList<Question> questions = QuestionDAO.getQuestionByLessonID(lesson.getID());
%>

<div class="quiz-type1 finished">
    <form action="" method="post">
        <div class="leftSide">
            <p class="quizProgress">Question: <span>1/<%out.print(questions.size());%></span> </p>

            <%
                //show all question
                for (Question question : questions) {
                    switch (question.getType() / 10) {
                        case 0: {
            %>
            <%@include file="../question/image.jsp" %>
            <%                    break;
                }
                case 1: {
            %>
            <%@include file="../question/image.jsp" %>
            <%                    break;
                }
                case 2: {
                    %>
            <%@include file="../question/text.jsp" %>
            <%                            break;
                        }
                        default: {
                            break;
                        }
                    }

                }
                %>

            <%@include file="../question/updateAnswers.jsp" %>

            <div class="btns">
                <p>Next question</p>
                <!-- <button>Check</button> -->
            </div>
        </div>
        <div class="rightSide">
            <div class="time">
                <h5>Last submission</h5>
                <i class="fa-regular fa-clock"></i>
                <span><%
                    //calculate remain time then format and print out
                    long timeSecond = (quizResult.getEndTime().getTime() - quizResult.getStartTime().getTime()) / 1000;
                    if (timeSecond < 0) {
                        timeSecond = 0;
                    }
                    long seconds = timeSecond % 60;
                    timeSecond /= 60;
                    long minutes = timeSecond % 60;
                    timeSecond /= 60;
                    long hours = timeSecond;

                    String timeString = "";
                    if (hours > 0) {
                        timeString += hours + ":";
                    }
                    if (minutes < 10) {
                        timeString += "0";
                    }
                    timeString += minutes + ":";
                    if (seconds < 10) {
                        timeString += "0";
                    }
                    timeString += seconds;
                    out.print(timeString);
                    %>/<%
                        //calculate remain time then format and print out
                        timeSecond = lesson.getTime() * 60;
                        if (timeSecond < 0) {
                            timeSecond = 0;
                        }
                        seconds = timeSecond % 60;
                        timeSecond /= 60;
                        minutes = timeSecond % 60;
                        timeSecond /= 60;
                        hours = timeSecond;

                        timeString = "";
                        if (hours > 0) {
                            timeString += hours + ":";
                        }
                        if (minutes < 10) {
                            timeString += "0";
                        }
                        timeString += minutes + ":";
                        if (seconds < 10) {
                            timeString += "0";
                        }
                        timeString += seconds;
                        out.print(timeString);
                    %></span>
            </div>
            <div class="listQuestion">
                <h5><%out.print(QuizResultDAO.getQuizResultPoint(quizResult.getID()));%>/<%out.print(QuestionDAO.getNumberQuestionByLessonID(lesson.getID()));%> corrects!</h5>
                <ul>
                    <%                        //show all questionLabel
                        for (int i = 1; i <= questions.size(); i++) {
                            String correctClass = QuestionResultDAO.CheckQuestionResultCorrect(quizResult.getID(), questions.get(i - 1).getID()) ? "correct" : "incorrect";
                    %>
                    <li class="<%out.print(correctClass);%>"><%out.print(i);%></li>
                        <%
                            }
                        %>
                </ul>
            </div>

            <div class="finishBtn">
                <a href="/startAQuiz/<%out.print(lesson.getID());%>">
                    <p>Retake</p>
                </a>
            </div>
        </div>
    </form>
</div>
