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
			<jsp:useBean id="readerService" class="com.getjavajobs.library.services.ReaderService" />
			<c:set var="readers" value="${readerService.getAll()}"/>
			<c:forEach var="reader" items="${readers}">
				<tr>
					<td>
						<c:out value="${reader.getFirstName()}" />
					</td>
					<td>
						<c:out value="${reader.getSecondName()}" />
					</td>
				</tr>
			</c:forEach>
		</table>
        <%@ include file="strelFooter.jsp"%>
	</body>
</html>
