<%-- 
    Document   : popUpMessage
    Created on : Jul 3, 2023, 12:10:29 AM
    Author     : TTNhan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<div id="toast"></div>

<script src="/public/assets/js/popUpScript.js"></script>

<script>
    <%
        String message = (String) request.getSession().getAttribute("success");
        if (message != null && message != "") {
            out.println("showSuccessToast(\"" + message + "\");");
            request.getSession().removeAttribute("success");
        }
        message = (String) request.getSession().getAttribute("error");
        if (message != null && message != "") {
            out.println("showErrorToast(\"" + message + "\");");
            request.getSession().removeAttribute("error");
        }
    %>
</script>