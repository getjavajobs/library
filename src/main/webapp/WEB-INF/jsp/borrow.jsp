<!doctype html/>
<%@ page import="com.getjavajobs.library.model.Borrow" %>
<%@ page import="com.getjavajobs.library.services.BorrowService" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>

<html>
    <head>
        <title> Borrow </title>
    </head>

    <body>
    <table>
    <tr><th>BorrowID</th>
        <th>Book</th>
        <th>Reader</th>
        <th>DateOfBorrow</th>
        <th>DateOfReturn</th>
        <th>Employee</th> </tr>


        <% BorrowService bs = new BorrowService();
            List<Borrow> listOfBorrow = bs.getAll();
        for(Borrow borrow : listOfBorrow){ %>

            <tr><td><%= borrow.getBorrowId() %></td>
                <td><%= borrow.getBook().getName()%></td>
                <td><%= borrow.getReader().getFirstName() + " " + borrow.getReader().getSecondName()%>/td>
                <td><%= borrow.getDateOfBorrow().toString()%></td>
                <td><%= borrow.getDateOfReturn().toString()%></td>
                <td><%= borrow.getEmployee().getSurname()%></td>
            </tr>


        <%}

        %>
        Borrow list<br/>


             <input type="button" value="ADD" onClick="document.location='url'">

        </table>

    </body>

</html>