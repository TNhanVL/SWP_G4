<%-- 
    Document   : updateAnswers.jsp
    Created on : Jul 7, 2023, 12:56:59 AM
    Author     : TTNhan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<script>
    function sendUpdateAnswer(questionID) {
        let paramsSend = "_";
        let answers = $("input[name=" + questionID + "]");
        for (let i = 0; i < answers.length; i++) {
            if (answers[i].checked) {
                paramsSend += answers[i].value + "_";
            }
        }

        fetch("/updateQuestionResult/<%out.print(quizResult.getID());%>/" + questionID.slice(8) + "/" + paramsSend, {method: 'POST'}).catch(error => console.error(error));
    }
</script>