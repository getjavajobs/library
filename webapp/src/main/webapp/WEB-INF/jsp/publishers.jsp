<%@ page import="com.getjavajobs.library.model.Publisher" %>
<%@ page import="java.util.List" %>
<%@ page import="com.getjavajobs.library.exceptions.ServiceException" %>
<%@ page import="com.getjavajobs.library.services.PublisherService" %>
<%@ page import="org.springframework.context.ApplicationContext" %>
<%@ page import="org.springframework.web.context.support.WebApplicationContextUtils" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Publishers page</title>
        <%@ include file="head_content.jsp"%>
    </head>
    <body>

        <%@ include file="strelHeader.jsp"%>

        <%
            ApplicationContext context =  WebApplicationContextUtils.getWebApplicationContext(getServletContext());
            PublisherService publisherService = context.getBean(PublisherService.class);
            List<Publisher> publishersList = null;
            int num = 0;
            try {
                publishersList = publisherService.getAll();
            } catch (ServiceException e) {
                // redirect to 'error' page?..
            }
        %>

        <h1>Publishers table</h1>

        <table border="1px solid black">
            <tr>
                <th>ID</th>
                <th>Publisher name</th>
                <th>City</th>
                <th>Phone number</th>
                <th>Email</th>
                <th>Site address</th>
                <th colspan="2">Actions</th>
            </tr>
            <% if (publishersList != null) {
                for (Publisher anotherPublisher: publishersList) { %>
            <tr>
                <td><%=++num%></td>
                <td><%=anotherPublisher.getName()%></td>
                <td><%=anotherPublisher.getCity()%></td>
                <td><%=anotherPublisher.getPhoneNumber()%></td>
                <td><%=anotherPublisher.getEmail()%></td>
                <td><%=anotherPublisher.getSiteAddress()%></td>

                <%-- Buttons for 'update' and 'delete' --%>
                <td>
                    <form action="changePublishers" method="post">
                        <input type="hidden" name="publisherId" value=<%=anotherPublisher.getId()%>>
                        <input type="submit" value="Update">
                    </form>
                </td>
                <td>
                    <form action="publisherChange" method="post">
                        <input type="hidden" name="commandType" value="delete">
                        <input type="hidden" name="publisherId" value=<%=anotherPublisher.getId()%>>
                        <input type="submit" value="Delete">
                    </form>
                </td>
            </tr>
            <%    }
            }
            %>
        </table>

        <br>

        <input type="button" value="Add new publishing" onClick="document.location='changePublishers'">


        <%@ include file="strelFooter.jsp"%>

    </body>
</html>