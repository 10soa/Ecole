package servlet;
import direction.*;
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
// import jakarta.servlet.*;
// import jakarta.servlet.http.*;

public class EcolageNonPayeServlet extends HttpServlet {
        protected void doGet(HttpServletRequest req, HttpServletResponse res)
        throws ServletException, IOException {
        res.setContentType("text/plain");
        
        try {
            
            EcolageNomPayeEtudiant enp = new EcolageNomPayeEtudiant();
            ArrayList<EcolageNomPayeEtudiant> list = enp.listeMoisActuel();
            req.setAttribute("list_ecolage",list);
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