<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.getjavajobs.library.model.Genre"%>
<%@page import="java.util.List" %>
<%@page import="com.getjavajobs.library.services.GenreService" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        
        <!-- copied and changed from publishers.jsp !--> 
             <%
            List<Genre> genreList = genreServlet.getAllGenre(); 
            int num = 0;
        %>
         <h1>Genre table</h1>

        <table>
            <tr>
                <th>ID</th>
                <th>Genre name</th>                  
            </tr>

            <%
                if (genreList != null) {
                    for (Genre anotherGenre : genreList) { %>

            <tr>
                <td><%=++num%></td>
                <td><%=anotherGenre.getGenreType()%></td>               

                <%-- Buttons for 'update' and 'delete' --%>
                <td>
                    <form action="publishersChangingPage.jsp" method="POST">
                        <input type="hidden" name="id" value="<%=anotherGenre.getId()%>">
                        <input type="hidden" name="commandType" value="update">
                        <input type="submit" value="Update">
                    </form>
                </td>
                <td>
                    <form action="publishersChangingPage.jsp" method="POST">
                        <input type="hidden" name="id" value="<%=anotherGenre.getId()%>">
                        <input type="hidden" name="commandType" value="delete">
                        <input type="submit" value="Delete">
                    </form>
                </td>
            </tr>

            <%        }
                }
            %>

        </table>

        
    </body>
</html>
