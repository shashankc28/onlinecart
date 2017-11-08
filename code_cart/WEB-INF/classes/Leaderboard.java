
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.*;

public class Leaderboard extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		request.getRequestDispatcher("link.html").include(request, response);
		
		HttpSession session=request.getSession(false);
		if(session == null)
                {
                    out.print("Please login first");
                    request.getRequestDispatcher("login.html").include(request, response);
                }
                else{


                	try{

         Class.forName("com.mysql.jdbc.Driver");

      
         Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/user","root","root");

        
         Statement stmt = conn.createStatement();
         String sql;
         String id1=(String)session.getAttribute("id");
         sql = "SELECT name,score from user where attempt = 1 ORDER BY score DESC";
         ResultSet rs = stmt.executeQuery(sql);
         
         out.println("<table><tr><th>name</th><th>score</th></tr>");

         while(rs.next())
         {
            String name=rs.getString("name");
            int score=rs.getInt("score");
            out.println("<tr><td>"+name+"</td><td>"+score+"</td></tr>");

         }
        
         out.println("</table>");
		
         rs.close();
         stmt.close();
         conn.close();
      }catch(SQLException se){
         //Handle errors for JDBC
         out.println(se);;
      }catch(Exception e){
         //Handle errors for Class.forName
         out.println(e);
      }


		
		}
		out.close();
	}
}