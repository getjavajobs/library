<%@ page import="com.getjavajobs.library.model.Author" %>
<%@ page import="java.util.List" %>
<%@ page import="com.getjavajobs.library.services.AuthorService" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <%@ include file="head_content.jsp" %>
</head>
<body>
<table>
    <jsp:useBean id="authorServlet" class="com.getjavajobs.library.webui.AuthorServlet" scope="session"/>
    <c:set var="authorId" scope="page" value="${Integer.parseInt(requestScope.authorId)}"/>
    <c:set var="author" value="${authorServlet.get(authorId)}" scope="page"/>

    <%@ include file="strelHeader.jsp" %>

    <form method="post" action="AuthorChange">
        <input type="hidden" name="commandType" value="${(authorId==null)? "add": "update"}">
        <input type="hidden" name="authorId" value="${authorId}">

        <p>
            <input type="text" name="authorName" required value="${(authorId>0)? author.getName():""}"/>
        </p>

        <p>
            <input type="text" name="authorSurname" required value="${(authorId>0)? author.getSurname():""}"/>
        </p>

        <p>
            <input type="text" name="authorPatronimic" required value="${(authorId>0)? author.getPatronymic():""}"/>
        </p>

        <p>
            <input type="text" name="authorBirthDay" required value="${(authorId>0)? author.getBirthDate():""}"/>
        </p>

        <p>
            <input type="text" name="authorBirthDay" required value="${(authorId>0)? author.getBirthPlace():""}"/>
        </p>

        <p>
            <input type="submit" value="${(authorId== null)? "Add": "Update"}>">
            <input type="reset">
        </p>
    </form>

    <%@ include file="strelFooter.jsp" %>

</body>
</html>
