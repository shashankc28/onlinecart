

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.*;

public class LoginServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		request.getRequestDispatcher("link.html").include(request, response);
		String id=request.getParameter("id");
		String pass=request.getParameter("password");
		
		
		try{

         Class.forName("com.mysql.jdbc.Driver");

      
         Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/user","root","root");

        
         Statement stmt = conn.createStatement();
         String sql;
         sql = "SELECT * from user where email='"+id+"' and pass='"+pass+"'";
         ResultSet rs = stmt.executeQuery(sql);

         // Extract data from result set
         if(rs.next())
         {

            int id1=rs.getInt("id");
            String id2=id1+"";
            HttpSession session=request.getSession();

            session.setAttribute("id",id2);
            
            int attempt=rs.getInt("attempt");
            
            String name=rs.getString("name");
            
            int score=rs.getInt("score");


         if(attempt==1)
         {
            out.print("Welcome, "+name); 
            out.print("  You have given the test . To know the score go to your profile<br>");
            out.print("To know the score of other participents go to leaderboard.<br> ");
         }
         else
         {
            out.print("Welcome, "+name+"<br>"); 
            out.print("You have not given the test . To start the test click <a href='Quiz'>here</a> <br>");
         }
           
      
		}
		else{
			out.print("Sorry, username or password error!<br>");
			request.getRequestDispatcher("login.html").include(request, response);
		}
		
         rs.close();
         stmt.close();
         conn.close();
      }catch(SQLException se){
         //Handle errors for JDBC
         out.println("errorr");;
      }catch(Exception e){
         //Handle errors for Class.forName
         out.println("errorr");
      } //end try
	}

}
