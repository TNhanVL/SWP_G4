<%-- 
    Document   : popUpMessage
    Created on : Jun 29, 2023, 2:38:53 AM
    Author     : TTNhan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<div id="toast"></div>

<script>
    <%
        String message = (String) request.getSession().getAttribute("success");
        if(message != null && message != ""){
            out.println("showSuccesToast('" + message + "');");
            request.getSession().removeAttribute("success");
        }
        message = (String) request.getSession().getAttribute("error");
        if(message != null && message != ""){
            out.println("showErrorToast('" + message + "');");
            request.getSession().removeAttribute("error");
        }
    %>
</script>