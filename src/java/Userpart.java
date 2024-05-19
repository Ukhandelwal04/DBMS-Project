import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Userpart extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       response.setContentType("text/html");
       PrintWriter out = response.getWriter();
       
       // Fetching user input
       String userName = request.getParameter("user_name");

       out.println("<!DOCTYPE html>");
       out.println("<html>");
       out.println("<head>");
       out.println("<h1 style=\"text-align: center\">Welcome To Evently ... An Event Management Portal!</h1>");
       out.println("<title>User Transaction Details</title>");
       out.println("<link rel=\"stylesheet\" href=\"total.css\">");
       out.println("<link href=\"https://fonts.googleapis.com/css2?family=Balsamiq+Sans&display=swap\" rel=\"stylesheet\">");
       out.println("</head>");
       out.println("<body>");
       
       try {
           // Establish database connection
           Class.forName("com.mysql.cj.jdbc.Driver");
           String conURL = "jdbc:mysql://localhost:3306/EventlyDB";
           String dbusername = "root";
           String dbuserpassword = "1234";
           Connection con = DriverManager.getConnection(conURL , dbusername, dbuserpassword);
           con.setAutoCommit(false); 
           Statement statement = con.createStatement();
           
           // Execute SQL query to fetch transaction details for the user
           String query = "SELECT pd.User_Name, pd.Participant_Name, pd.Semester, e.Event_Number, e.Event_Name " +
                          "FROM plogindetails pd " +
                          "INNER JOIN registered r ON pd.User_Name = r.User_Name " +
                          "INNER JOIN Event e ON r.Event_Number = e.Event_Number " +
                          "WHERE pd.User_Name = '" + userName + "'";
           ResultSet resultSet = statement.executeQuery(query);
           
           // Display transaction details in HTML table format
           out.println("<center><h1>Transaction Details for User: " + userName + "</h1></center>");
           out.println("<div>");
           out.println("<center>");
           out.println("<table border='1'>");
           out.println("<tr><th>User Name</th><th>Participant Name</th><th>Semester</th><th>Event Number</th><th>Event Name</th></tr>");
           
           while(resultSet.next()) {
               String user = resultSet.getString("User_Name");
               String participantName = resultSet.getString("Participant_Name");
               int semester = resultSet.getInt("Semester");
               int eventNumber = resultSet.getInt("Event_Number");
               String eventName = resultSet.getString("Event_Name");
               out.println("<tr><td>" + user + "</td><td>" + participantName + "</td><td>" + semester + "</td><td>" + eventNumber + "</td><td>" + eventName + "</td></tr>");
           }
           
           out.println("</table>");
           out.println("</center>");
           out.println("</div>");
           out.println("<div><label class=\"topnav-right\"> © 1999-2022 Evently. All rights reserved. </label></div>");
           out.println("</body>");
           out.println("</html>");
           
           // Close connections
           resultSet.close();
           statement.close();
           con.close(); 
           
       } catch(ClassNotFoundException | SQLException e) {
           System.out.println("Exception Caught: " + e);
       }
    }
}