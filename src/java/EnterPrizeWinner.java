import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EnterPrizeWinner extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String eventNumber = request.getParameter("eventId");
        String prize1Winner = request.getParameter("prize1Winner");
        String prize2Winner = request.getParameter("prize2Winner");
        String prize3Winner = request.getParameter("prize3Winner");

        if (eventNumber.isBlank() || prize1Winner.isBlank() || prize2Winner.isBlank() || prize3Winner.isBlank()) {
            out.println("<script type=\"text/javascript\">");
            out.println("alert('Please enter all prize winners');");
            out.println("window.location='EnterPrizeWinners.html';");
            out.println("</script>");
        } else {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                String conURL = "jdbc:mysql://localhost:3306/EventlyDB";
                String dbusername = "root";
                String dbuserpassword = "1234";
                Connection con = DriverManager.getConnection(conURL, dbusername, dbuserpassword);

                String insertQuery = "INSERT INTO prizes (Event_Number, Prize_1_Winner, Prize_2_Winner, Prize_3_Winner) VALUES (?, ?, ?, ?)";
                PreparedStatement preparedStatement = con.prepareStatement(insertQuery);
                preparedStatement.setInt(1, Integer.parseInt(eventNumber));
                preparedStatement.setString(2, prize1Winner);
                preparedStatement.setString(3, prize2Winner);
                preparedStatement.setString(4, prize3Winner);

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    out.println("<script type=\"text/javascript\">");
                    out.println("alert('Prize winners entered successfully');");
                    out.println("window.location='EventDetails.html';");
                    out.println("</script>");
                } else {
                    out.println("<script type=\"text/javascript\">");
                    out.println("alert('Failed to enter prize winners');");
                    out.println("window.location='EventDetails.html';");
                    out.println("</script>");
                }

                preparedStatement.close();
                con.close();
            } catch (ClassNotFoundException | SQLException e) {
                out.println("<script type=\"text/javascript\">");
                out.println("alert('An error occurred while processing your request');");
                out.println("window.location='EnterPrizeWinners.html';");
                out.println("</script>");
                e.printStackTrace();
            }
        }
    }
}