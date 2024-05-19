import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Z extends HttpServlet {
    
    
    @Override
protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    response.setContentType("text/html");
    PrintWriter out = response.getWriter();

    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("<title>Prizes Page</title>");
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

        try (Connection con = DriverManager.getConnection(conURL , dbusername, dbuserpassword);
             Statement statement = con.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM prizes")) {

            out.println("<center><h1>Prize Details</h1></center>");
            out.println("<div>");
            out.println("<left><p><a href=\"TransactionDetails.html\"><button>Event Details Page</button></a></p></left>");
            out.println("<center>");
            out.println("<table border=1 width=70%>");  
            out.println("<tr><th>Event Number</th><th>Prize 1 Winner</th><th>Prize 2 Winner</th><th>Prize 3 Winner</th></tr>"); 

            while(resultSet.next()){
                int eventNumber = resultSet.getInt("Event_Number");
                String prize1Winner = resultSet.getString("Prize_1_Winner");
                String prize2Winner = resultSet.getString("Prize_2_Winner");
                String prize3Winner = resultSet.getString("Prize_3_Winner");
                out.println("<tr><td>" + eventNumber + "</td><td>" + prize1Winner + "</td><td>" + prize2Winner + "</td><td>" + prize3Winner + "</td></tr>");   
            }

            out.println("</table>"); 
            out.println("</center>");

        } catch(SQLException e){
            out.println("An error occurred while processing your request: " + e.getMessage());
        }

        out.println("<div><label class=\"topnav-right\">© 1999-2022 Evently. All rights reserved.</label></div>");
        out.println("</body>");
        out.println("</html>");

    } catch(ClassNotFoundException e) {
        out.println("An error occurred while processing your request: " + e.getMessage());
    }
}

       
    }

