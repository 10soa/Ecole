package servlet;
import promotion.*;
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
// import jakarta.servlet.*;
// import jakarta.servlet.http.*;

public class PromotionServlet extends HttpServlet {
        protected void doGet(HttpServletRequest req, HttpServletResponse res)
        throws ServletException, IOException {
        res.setContentType("text/plain");
        
        try {
            
            Promotion prom = new Promotion();
            ArrayList<Promotion> list = prom.getprom();
            req.setAttribute("list_prom",list);
                } catch (Exception e) {
            //TODO: handle exception
        }
            RequestDispatcher dispat = req.getRequestDispatcher("/index.jsp");
            dispat.forward(req,res);           
    
 
    }

        public void doPost(HttpServletRequest req, HttpServletResponse res) 
        throws ServletException, IOException {
            
        }
}