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

    <form method="post" action="authorServlet">
        <input type="hidden" name="commandType" value="${(authorId==null)? "add": "update"}">
        <input type="hidden" name="authorId" value="${authorId}">

        <p>
            Name: <input type="text" name="authorName" required value="${(authorId!=null)? author.getName():""}"/>
        </p>

        <p>
           Surname: <input type="text" name="authorSurname" required value="${(authorId!=null)? author.getSurname():""}"/>
        </p>

        <p>
            Patronymic: <input type="text" name="authorPatronimic" required value="${(authorId!=null)? author.getPatronymic():""}"/>
        </p>

        <p>
            BirthDay: <input type="text" name="authorBirthDay" required value="${(authorId!=null)? author.getBirthDate():""}"/>
        </p>

        <p>
            BirthPlace: <input type="text" name="authorBirthPlace" required value="${(authorId!=null)? author.getBirthPlace():""}"/>
        </p>

        <p>
            <input type="submit" value="${(authorId== null)? "Add": "Update"}">
            <input type="reset">
        </p>
    </form>

    <%@ include file="strelFooter.jsp" %>

</body>
</html>
