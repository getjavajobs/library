<%@ page import="com.getjavajobs.library.model.Publisher" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Publishers page</title>
    </head>
    <body>

        <jsp:useBean id="publishersServlet" class="com.getjavajobs.library.webui.PublisherServlet" scope="session" />

        <script type="text/javascript">

            function deletePublisher(idValue) {
                <%

                    request.setAttribute("commandType", "delete");
                    publishersServlet.service(request,response);
                %>
            }

        </script>


        <%
            List<Publisher> publishersList = publishersServlet.getAllPublishers();
            int num = 0;
        %>
    
        <%@ include file="/html/strelHeader.html"%>

        <h1>Publishers table</h1>

        <table>
            <tr>
                <th>ID</th>
                <th>Publisher name</th>
                <th>City</th>
                <th>Phone number</th>
                <th>Email</th>
                <th>Site address</th>
                <th colspan="2">Actions</th>
            </tr>

            <%
                if (publishersList != null) {
                    for (Publisher anotherPublisher : publishersList) { %>

            <tr>
                <td><%=++num%></td>
                <td><%=anotherPublisher.getName()%></td>
                <td><%=anotherPublisher.getCity()%></td>
                <td><%=anotherPublisher.getPhoneNumber()%></td>
                <td><%=anotherPublisher.getEmail()%></td>
                <td><%=anotherPublisher.getSiteAddress()%></td>

                <%-- Buttons for 'update' and 'delete' --%>
                <td>
                    <form action="publishersChangingPage.jsp" method="POST">
                        <input type="hidden" name="id" value="<%=anotherPublisher.getId()%>">
                        <input type="hidden" name="commandType" value="update">
                        <input type="submit" value="Update">
                    </form>
                </td>
                <td>
                    <form action="publishersChangingPage.jsp" method="POST">
                        <input type="hidden" name="id" value="<%=anotherPublisher.getId()%>">
                        <input type="hidden" name="commandType" value="delete">
                        <input type="submit" value="Delete">
                    </form>
                </td>
            </tr>

            <%        }
                }
            %>

        </table>

        <br>

        <form action="publishersChangingPage.jsp">
            <input type="hidden" name="id" value="<%=0%>">
            <input type="hidden" name="commandType" value="add">
            <input type="submit" value="Add publisher">
        </form>


        <%@ include file="/html/strelFooter.html"%>

    </body>
</html>
