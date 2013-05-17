/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelo.bean.Administrador;
import modelo.dao.AdministradorDAO;
import modelo.dao.AdministradorDAOImpl;

/**
 *
 * @author cristobal
 */
@WebServlet(name = "paneladmin", urlPatterns = {"/paneladmin"})
public class paneladmin extends HttpServlet {
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        Mensaje msg = new Mensaje();
        Gson gson = new Gson();
        try {
            HttpSession session = request.getSession();
            Administrador a = new Administrador();
            AdministradorDAO ad = new AdministradorDAOImpl();
            if(session.getAttribute("admin") == null){                
                response.sendRedirect("loginadmin.jsp");
            }else{
                if(request.getParameter("o") != null){
                    switch(Integer.parseInt(request.getParameter("o"))){
                        case 3:
                            if(request.getParameter("m")!= null){
                                switch(Integer.parseInt(request.getParameter("m"))){
                                    case 1:
                                        out.println(gson.toJson(new OutPut("0","ok",ad.getAdministradores())));
                                    break;
                                    case 2:
                                        if(request.getParameter("correo") != null && request.getParameter("nivel") != null){
                                            out.println(ad.nuevoAdministrador(
                                                    new Administrador(request.getParameter("correo"), Integer.parseInt(request.getParameter("nivel")))));
                                        }else{
                                            out.println(gson.toJson(msg.sinValores()));
                                        }
                                    break;
                                    default:
                                        out.println(gson.toJson(msg.sinValores()));
                                    break;
                                }
                            }else{
                                out.println(gson.toJson(msg.sinValores())); 
                            }
                        break;
                        default:
                            out.println(gson.toJson(msg.peticionErronea()));
                        break;
                    }
                }else{
                    out.println(gson.toJson(msg.sinValores()));
                }
            }
        } finally {            
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
