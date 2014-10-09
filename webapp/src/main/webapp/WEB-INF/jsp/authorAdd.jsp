<%@ page import="com.getjavajobs.library.model.Author" %>
<%@ page import="java.util.List" %>
<%@ page import="com.getjavajobs.library.services.AuthorService" %>
<%@ page import="com.getjavajobs.library.dao.AuthorDao" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
    <head>
        <%@ include file="head_content.jsp" %>
        <title>
            <%
                String authorIdString = request.getParameter("authorId");
                Integer authorId = (authorIdString == null? null: new Integer(authorIdString));
                if (authorId != null) {%>
            Update author
            <%} else {%>
            Add new author
            <%}%>
        </title>
    </head>
    <body>
         <%
        AuthorService authorService = new AuthorService(new AuthorDao());
        Author author = (authorId == null)? null: authorService.get(authorId);
        %>

    <%@ include file="strelHeader.jsp"%>
         <form method="post" action="AuthorServlet">
             <input type="hidden" name="commandType" value="<%=(authorId == null)? "add": "update"%>">
             <input type="hidden" name="publisherId" value="<%=authorId%>">
             <p>
                 Name: <input type="text" name="authorName" required value="<%=(authorId == null)? "": author.getName()%>">
             </p>
             <p>
                 Surname: <input type="text" name="publisherCity" required value="<%=(authorId == null)? "": author.getSurname()%>">
             </p>
             <p>
                 Patronymic: <input type="text" name="publisherPhoneNumber" required value="<%=(authorId == null)? "": author.getPatronymic()%>">
             </p>
             <p>
                 BirthDay: <input type="text" name="publisherEmail" required value="<%=(authorId == null)? "": author.getBirthDate()%>">
             </p>
             <p>
                 BirthPlace: <input type="text" name="publisherSiteAddress" required value="<%=(authorId == null)? "": author.getBirthPlace()%>">
             </p>
             <p>
                 <input type="submit" value="<%=(authorId == null)? "Add": "Update"%>">
                 <input type="reset">
             </p>
         </form>

         <%@ include file="strelFooter.jsp"%>

    </body>
</html>



