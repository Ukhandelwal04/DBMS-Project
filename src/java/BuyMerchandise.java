//import java.io.*;
//import jakarta.servlet.*;
//import jakarta.servlet.http.*;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//import java.sql.PreparedStatement;
//
//public class BuyMerchandise extends HttpServlet {
//
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        response.setContentType("text/html");
//        PrintWriter out = response.getWriter();
//
//        
//        String merchId = request.getParameter("merch_id");
//        String cardNumber = request.getParameter("card_number");
//        String size = request.getParameter("size");
//
//        if ( merchId.isBlank() || cardNumber.isBlank() || size.isBlank()) {
//            response.setContentType("text/html");
//            out.println("<script type=\"text/javascript\">");
//            out.println("alert('Please fill all the fields!');");
//            out.println("</script>");
//            RequestDispatcher requestDispatcher = request.getRequestDispatcher("buy.html");
//            requestDispatcher.include(request, response);
//        } else {
//            try {
//                Class.forName("com.mysql.cj.jdbc.Driver");
//                String conURL = "jdbc:mysql://localhost:3306/EventlyDB";
//                String dbUsername = "root";
//                String dbUserPassword = "1234";
//                Connection con = DriverManager.getConnection(conURL, dbUsername, dbUserPassword);
//                con.setAutoCommit(false);
//                
//                
//
//                // Insert into merch_transaction table
//                String transactionQuery = "INSERT INTO merch_transaction (merch_id, Card_Number, size) VALUES (?, ?, ?)";
//                PreparedStatement transactionStatement = con.prepareStatement(transactionQuery);
//                transactionStatement.setString(1, merchId);
//                transactionStatement.setString(2, cardNumber);
//                transactionStatement.setString(3, size);
//                transactionStatement.executeUpdate();
//
//                con.commit();
//                con.close();
//                RequestDispatcher requestDispatcher = request.getRequestDispatcher("Payment.html");
//                requestDispatcher.forward(request, response);
//            } catch (ClassNotFoundException | SQLException e) {
//                System.out.println("Exception Caught: " + e);
//            }
//        }
//    }
//}
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class BuyMerchandise extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String merchId = request.getParameter("merch_id");
        String cardNumber = request.getParameter("cardno");
        String size = request.getParameter("size");

        if ( merchId.isBlank() || cardNumber.isBlank() || size.isBlank()) {
            out.println("<script type=\"text/javascript\">");
            out.println("alert('Please fill all the fields!');");
            out.println("</script>");
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("buy.html");
            requestDispatcher.include(request, response);
        } else {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                String conURL = "jdbc:mysql://localhost:3306/EventlyDB";
                String dbUsername = "root";
                String dbUserPassword = "1234";
                try (Connection con = DriverManager.getConnection(conURL, dbUsername, dbUserPassword)) {
                    con.setAutoCommit(false);

                    // Insert into merch_transaction table
                    String transactionQuery = "INSERT INTO merch_transaction (merch_id, Card_Number, size) VALUES (?, ?, ?)";
                    try (PreparedStatement transactionStatement = con.prepareStatement(transactionQuery)) {
                        transactionStatement.setString(1, merchId);
                        transactionStatement.setString(2, cardNumber);
                        transactionStatement.setString(3, size);
                        transactionStatement.executeUpdate();
                    }
                    con.commit();
                }
                response.sendRedirect("Payment.html");
            } catch (ClassNotFoundException | SQLException e) {
                out.println("<script type=\"text/javascript\">");
                out.println("alert('An error occurred while processing your request. Please try again later.');");
                out.println("</script>");
                e.printStackTrace(); // Print the stack trace for debugging
            }
        }
    }
}
