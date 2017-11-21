<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="user" class="ua.nure.kn156.ostapenko.User" scope="session"/>

<html>
    <head>
        <title>User management. Details</title>
    </head>
    <body>
        First name: ${user.firstName}<br>
        Last name: ${user.lastName}<br>
        Date of birth: <fmt:formatDate value="${user.dateOfBirthd}" type="date" dateStyle="medium"/><br>
        <form action="<%=request.getContextPath()%>/details" method="get">
            <input type="submit" value="Ok"  name="okButton" />
        </form>
        <c:if test="${requestScope.error != null}">
            <script>
            alert('${requestScope.error}');
            </script>
        </c:if>
    </body>
</html>