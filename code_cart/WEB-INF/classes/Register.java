// Loading required libraries
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
 
public class Register extends HttpServlet{
    
  @Override
  public void doPost(HttpServletRequest request,
                    HttpServletResponse response)
            throws ServletException, IOException
  {
  

      // Set response content type
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();

    request.getRequestDispatcher("link.html").include(request, response);

      try{
         // Register JDBC driver
         Class.forName("com.mysql.jdbc.Driver");

         // Open a connection
         String name=request.getParameter("name");
         String email=request.getParameter("email");
         String pass =request.getParameter("pass");
         Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/user","root","root");

         // Execute SQL query
         Statement stmt = conn.createStatement();
         String sql;
         sql = "insert into user (name,email,pass)values('"+name+"','"+email+"','"+pass+"')";
         stmt.executeUpdate(sql);

  
          out.println("Thank you! , you are successfully registred...");
      

         stmt.close();
         conn.close();
      }catch(SQLException se){
         //Handle errors for JDBC
         out.println("errorrrrrrrrrrr");;
      }catch(Exception e){
         //Handle errors for Class.forName
         out.println("errorrrrrrrrrrr");
      } //end try
   }
} 