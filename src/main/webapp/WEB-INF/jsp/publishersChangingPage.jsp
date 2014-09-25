<%@ page import="com.getjavajobs.library.model.Publisher" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Publishers changing page</title>
    </head>
    <body>

        <jsp:useBean id="publishersServlet" class="com.getjavajobs.library.webui.PublisherServlet" scope="session" />

        <!-- вот тут надо получить переменную Publisher из сервлета - но мне кажется, я неправ -->
        <%
            int id = Integer.parseInt(request.getParameter("id"));
            Publisher publisher = publishersServlet.getById(id);
        %>

        <form>

            <!-- а тут в каждом - надо проверять переданный извне id-шник -->
            Publisher name <input type="text" value="<%=(id == 0)? publisher.getName()%>"> <br>
            City <input type="text" value="<%=(id == 0)? publisher.getCity()%>"> <br>
            Phone number <input type="text" value="<%= (id == 0)? publisher.getPhoneNumber()%>"> <br>
            Email <input type="text" value="<%=(id == 0)? publisher.getEmail()%>"> <br>
            Site address <input type="text" value="<%=(id == 0)? publisher.getSiteAddress()%>"> <br>

            <%-- В зависимости от ID-шника -> разная кнопка --%>
            <% if (id == 0) {%>
                <input type="button" value="Add">
            <% } else {%>
                <input type="button" value="Update information">
            <%} %>

        </form>

    </body>
</html>
