<%@ page import="com.getjavajobs.library.model.Publisher" %>
<%@ page import="com.getjavajobs.library.services.PublisherService" %>
<%@ page import="com.getjavajobs.library.dao.PublisherDao" %>
<%@ page import="org.springframework.context.ApplicationContext" %>
<%@ page import="org.springframework.web.context.support.WebApplicationContextUtils" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <%@ include file="head_content.jsp"%>
        <title>
            <%
                String publisherIdString = request.getParameter("publisherId");
                Integer publisherId = (publisherIdString == null? null: new Integer(publisherIdString));
                if (publisherId != null) {%>
                    Update publishing
            <%} else {%>
                    Add new publishing
            <%}%>
        </title>
    </head>
    <body>

        <%
            ApplicationContext context =  WebApplicationContextUtils.getWebApplicationContext(getServletContext());
            PublisherService publisherService = context.getBean(PublisherService.class);
            Publisher publisher = (publisherId == null)? null: publisherService.get(publisherId);
        %>

        <%@ include file="strelHeader.jsp"%>

        <form method="post" action="publisherChange">
            <input type="hidden" name="commandType" value="<%=(publisherId == null)? "add": "update"%>">
            <input type="hidden" name="publisherId" value="<%=publisherId%>">
            <p>
                Publisher name: <input type="text" name="publisherName" required value="<%=(publisherId == null)? "": publisher.getName()%>">
            </p>
            <p>
                City: <input type="text" name="publisherCity" required value="<%=(publisherId == null)? "": publisher.getCity()%>">
            </p>
            <p>
                Phone number: <input type="text" name="publisherPhoneNumber" required value="<%=(publisherId == null)? "": publisher.getPhoneNumber()%>">
            </p>
            <p>
                Email: <input type="text" name="publisherEmail" required value="<%=(publisherId == null)? "": publisher.getEmail()%>">
            </p>
            <p>
                Site address: <input type="text" name="publisherSiteAddress" required value="<%=(publisherId == null)? "": publisher.getSiteAddress()%>">
            </p>
            <p>
                <input type="submit" value="<%=(publisherId == null)? "Add": "Update"%>">
                <input type="reset">
            </p>
        </form>

        <%@ include file="strelFooter.jsp"%>

    </body>
</html>
