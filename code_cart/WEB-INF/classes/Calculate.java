

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.*;

public class Calculate extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		request.getRequestDispatcher("link.html").include(request, response);
		HttpSession session=request.getSession(false);
		
		try{

         Class.forName("com.mysql.jdbc.Driver");

      
         Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/user","root","root");

        
         Statement stmt = conn.createStatement();
         String sql;
         sql = "SELECT * from questions where userid="+session.getAttribute("id");
         ResultSet rs = stmt.executeQuery(sql);
         int cc=1;
         int score=0;
       while(rs.next())
       {
         int ans=rs.getInt("answer");
         String a=ans+"";
         String pp="q"+cc;
         String ans1=request.getParameter(pp);
         if(ans1.equals(a))
         {
            score++;
         }
         cc++;
       }
       out.println("<br>You scored : "+score);
		sql= "update user set score="+score+" where id="+session.getAttribute("id");
      stmt.executeUpdate(sql);
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
