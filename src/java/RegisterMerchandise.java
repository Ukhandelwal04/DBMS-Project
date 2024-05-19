import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class RegisterMerchandise extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String merchandiseName = request.getParameter("merchandise_name");
        String price = request.getParameter("price");
        String merchId = request.getParameter("merch_id");

        if (merchandiseName.isBlank() || price.isBlank() || merchId.isBlank()) {
            response.setContentType("text/html");
            out.println("<script type=\"text/javascript\">");
            out.println("alert('Please fill all the fields!');");
            out.println("</script>");
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("Registration.html");
            requestDispatcher.include(request, response);
        } else {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                String conURL = "jdbc:mysql://localhost:3306/EventlyDB";
                String dbUsername = "root";
                String dbUserPassword = "1234";
                Connection con = DriverManager.getConnection(conURL, dbUsername, dbUserPassword);
                con.setAutoCommit(false);
                Statement statement = con.createStatement();
                String mysqlQuery = "INSERT INTO merchandise (merchandise_name, price, merch_id) VALUES ('" + merchandiseName + "','" + price + "','" + merchId + "')";
                statement.executeUpdate(mysqlQuery);
                con.commit();
                con.close();
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("Payment.html");
                requestDispatcher.forward(request, response);
            } catch (ClassNotFoundException | SQLException e) {
                System.out.println("Exception Caught: " + e);
            }
        }
    }
}
