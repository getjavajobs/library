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
		<input id="readers" type="text" value="" />
		<table>
			<c:forEach var="reader" items="${requestScope.readersList}">
				<tr>
					<td>
						${reader.firstName}
					</td>
					<td>
						${reader.secondName}
					</td>
				</tr>
			</c:forEach>
		</table>
        <%@ include file="strelFooter.jsp"%>
	</body>
	<script>
	$(function() {
		$('#readers').autocomplete({
    		source: function (request, response) {
        		jQuery.get(
        			"${pageContext.request.contextPath}/readers/filter",
        			{ query: request.term },
        			function (data) {
		  				var suggestions = jQuery.map(data, function(item) {
 		      				return { label: item.firstName, data: item.ReaderId };
		   				});
          				response(suggestions);
        		});
    		},
    		minLength: 1
		});
	});
	</script>
</html>
