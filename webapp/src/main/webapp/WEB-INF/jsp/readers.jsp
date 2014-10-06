<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="com.getjavajobs.library.model.Reader"%>
<%@page import="java.util.List" %>
<%@page import="com.getjavajobs.library.services.ReaderService" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
	<head>
		<title>Readers</title>
		<%@ include file="head_content.jsp"%>
	</head>
	<body>
        <%@ include file="strelHeader.jsp"%>
		READERS<br/>
		<table>
			<%
				ApplicationContext context =  WebApplicationContextUtils.getWebApplicationContext(getServletContext());
				ReaderService readerService = context.getBean(ReaderService.class);
				for (Reader reader : readerService.getAll()) {
			%>
				<tr>
					<td>
						<%=reader.getFirstName()%>
					</td>
					<td>
						<%=reader.getSecondName()%>
					</td>
				</tr>
			<% } %>
		</table>
        <%@ include file="strelFooter.jsp"%>
	</body>
</html>
