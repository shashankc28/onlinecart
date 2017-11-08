

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.*;

public class Quiz extends HttpServlet {
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

        Statement ck = conn.createStatement();
         String sql;
         String id7=(String)session.getAttribute("id");
         sql = "SELECT * from user where id="+id7;
         ResultSet ck1 = ck.executeQuery(sql);
         ck1.next();
         int attempt=ck1.getInt("attempt");
            
            


         if(attempt==0)
         { 
            sql="update user set attempt =1 where id="+id7;
            ck.executeUpdate(sql);
         out.print("All the questions are necessary , there is no negative marking..if you leave the page without submitting the answers ...you will be straight away given a 0.<br><br>");
         Statement stmt = conn.createStatement();
         String id1=(String)session.getAttribute("id");
         sql = "SELECT COUNT(*) as cnt FROM questions where userid=0"; 
         ResultSet rs = stmt.executeQuery(sql);
         rs.next();
         int cnt=rs.getInt("cnt");
         out.println("number of questions left in database ="+cnt);
         if(cnt >= 10)
         {
                rs.close();
                stmt.close();
             sql = "SELECT * FROM questions where userid=0 limit 10";
             Statement stmt1 = conn.createStatement();
              ResultSet rs1 = stmt1.executeQuery(sql);
             while(rs1.next())
             {
                int qid=rs1.getInt("id");
                sql = "update questions set userid="+session.getAttribute("id")+" where id="+qid;
                Statement stmt3 = conn.createStatement();
                stmt3.executeUpdate(sql);
             }
                rs1.close();

                stmt1.close();

                sql = "SELECT * from questions where userid="+session.getAttribute("id");
                Statement stmt2 = conn.createStatement();
                ResultSet rs2 = stmt2.executeQuery(sql);
                int cc=1;
                out.print("<form action='Calculate' method='post'>");
                while(rs2.next())
             {
                String question=rs2.getString("question");
                String op1=rs2.getString("op1");
                String op2=rs2.getString("op2");
                String op3=rs2.getString("op3");
                String qn="q"+cc;

                out.println("<p> "+qn+": "+question+" </p>");
                out.println("<ul>");
                out.println("<input type='radio' name='"+qn+"' value='1' id='"+qn+"'><label for='"+qn+"'>"+op1+"</label><br/>");
                out.println("<input type='radio' name='"+qn+"' value='2' id='"+qn+"'><label for='"+qn+"'>"+op2+"</label><br/>");
                out.println("<input type='radio' name='"+qn+"' value='3' id='"+qn+"'><label for='"+qn+"'>"+op3+"</label><br/>");
                out.println("</ul>");
                cc++;
                  
             }
             out.println("<input type='submit' value='Finish'>");
             out.println("</form>");
             rs2.close();
             stmt2.close();


         }
         else
         {
            out.print("sorry we dont have enough questions for you at the moment. please try again later.");
         }
        
         





		}
        else
        {
            out.println("you have already given the quiz");
        }
         
         conn.close();
      }catch(SQLException se){
         //Handle errors for JDBC
         out.println(se);
      }catch(Exception e){
         //Handle errors for Class.forName
         out.println(e);
      }


		
		}
		out.close();
	}
}
