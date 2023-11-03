<%-- 
    Document   : head
    Created on : Oct 3, 2023, 12:51:12 AM
    Author     : TTNhan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
    request.getSession().setAttribute("contextPath", request.getContextPath());
%>

<meta charset="UTF-8">
<link rel="icon" href="/public/assets/imgs/logo.png" type="image/x-icon"/>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css">
<link href='https://fonts.googleapis.com/css?family=Roboto' rel='stylesheet'>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css"/>
<link rel="stylesheet" href="/public/assets/css/toast.css">
<link rel="stylesheet" href="/public/assets/css/header.css">
<link rel="stylesheet" href="/public/assets/css/footer.css">
<title>${param.title}</title>