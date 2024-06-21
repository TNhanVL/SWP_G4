<%-- 
    Document   : updateAnswers.jsp
    Created on : Oct 7, 2023, 12:56:59 AM
    Author     : TTNhan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<script>
    function sendUpdateAnswer(questionId) {
        let paramsSend = "_";
        let answers = $("input[name=" + questionId + "]");
        for (let i = 0; i < answers.length; i++) {
            if (answers[i].checked) {
                paramsSend += answers[i].value + "_";
            }
        }

        fetch("/learn/updateChosenAnswer/<%out.print(quizResult.getID());%>/" + questionId.slice(8) + "/" + paramsSend, {method: 'POST'}).catch(error => console.error(error));
    }
</script>