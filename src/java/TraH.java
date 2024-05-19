import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TraH extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Transactions Page</title>");
        out.println("<link rel=\"stylesheet\" href=\"total.css\">");
        out.println("<link href=\"https://fonts.googleapis.com/css2?family=Balsamiq+Sans&display=swap\" rel=\"stylesheet\">");
        out.println("</head>");
        out.println("<body>");

        out.println("<h1 style=\"text-align: center\">Welcome To Evently ... An Event Management Portal!</h1>");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String conURL = "jdbc:mysql://localhost:3306/EventlyDB";
            String dbusername = "root";
            String dbuserpassword = "1234";

            try (Connection con = DriverManager.getConnection(conURL, dbusername, dbuserpassword);
                 PreparedStatement statement = con.prepareStatement("SELECT c.Event_Number, c.Card_Name, c.Expiry_Date, e.Event_Name FROM card AS c INNER JOIN Event AS e ON c.Event_Number = e.Event_Number;");
                 ResultSet resultSet = statement.executeQuery()) {

                out.println("<center><h1>Transaction Details</h1></center>");
                out.println("<div>");
                out.println("<left><p><a href=\"TransactionDetails.html\"><button>Event Details Page</button></a></p></left>");
                out.println("<center>");
                out.println("<table border=1 width=50% height=50%>");
                out.println("<tr><th>Event Number</th><th>Event Name</th><th>Card Name</th><th>Expiry Date</th></tr>");
                
                
              

                while (resultSet.next()) {
                    String eventNumber = resultSet.getString("Event_Number");
                    String re = resultSet.getString("Event_Name");
                    String cardName = resultSet.getString("Card_Name");
                    String expiryDate = resultSet.getString("Expiry_Date");
                    out.println("<tr><td>" + eventNumber + "</td><td>" + re + "</td><td>" + cardName + "</td><td>" + expiryDate + "</td></tr>");
                }

                out.println("</table>");
                out.println("</center>");

            } catch (SQLException e) {
                out.println("An error occurred while processing your request: " + e.getMessage());
            }

            out.println("<div><label class=\"topnav-right\">© 1999-2022 Evently. All rights reserved.</label></div>");
            out.println("</body>");
            out.println("</html>");

        } catch (ClassNotFoundException e) {
            out.println("An error occurred while processing your request: " + e.getMessage());
        }

    }
}

