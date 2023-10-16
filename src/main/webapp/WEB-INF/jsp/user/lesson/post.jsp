<%-- 
    Document   : post
    Created on : Jul 17, 2023, 5:40:06 AM
    Author     : TTNhan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="m-4">
    <%
        out.print(post.getContent());
    %>
</div>

<a href="/learn/markLessonComplete/<%out.print(lesson.getLessonID());%>" class="w-100 d-flex justify-content-center"><div class="btn btn-success m-4">Mark as completed</div></a>