package servlet;
import mpianatra.*;
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
// import jakarta.servlet.*;
// import jakarta.servlet.http.*;

public class EtudiantServlet extends HttpServlet {
        protected void doGet(HttpServletRequest req, HttpServletResponse res)
        throws ServletException, IOException {
        res.setContentType("text/plain");
        
        try {
            
            Etudiant etudiant = new Etudiant();
            ArrayList<Etudiant> list = etudiant.getEtudiant();
            req.setAttribute("list_etudiant",list);
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