/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
@WebServlet(name = "loginadmin", urlPatterns = {"/loginadmin"})
public class loginadmin extends HttpServlet {

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
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
            String correo;String contrasena;
            if(session.getAttribute("admin") != null){                
                out.println(gson.toJson(msg.tieneSesion()));
            }else{
                if(request.getParameter("m") != null){
                    int m = Integer.parseInt(request.getParameter("m"));
                    switch(m){
                        case 1:
                            if(request.getParameter("correo") != null && request.getParameter("pass") != null){
                                correo = request.getParameter("correo").toLowerCase();
                                if(ad.iniciaSesion(new Administrador(correo, request.getParameter("pass")))){
                                    session.setAttribute("admin", correo);
                                    out.println(gson.toJson(msg.ok()));
                                }else{
                                    out.println(gson.toJson(msg.usuarioIncorrecto()));
                                }
                            }else{
                                out.println(gson.toJson(msg.peticionErronea()));
                            }
                        break;
                        case 2:
                            if(request.getParameter("correo") != null){
                                out.println(gson.toJson(ad.guardaCodigo(request.getParameter("correo").toLowerCase(), Tools.genCode(30))));
                            }else{
                                out.println(gson.toJson(msg.peticionErronea()));
                            }
                        break;
                        case 3:
                            if(request.getParameter("c") != null){
                                out.println(gson.toJson(ad.compCode(request.getParameter("c"))));
                            }else{
                                out.println(gson.toJson(msg.peticionErronea()));
                            }
                        break;
                        case 4:
                            if(request.getParameter("code") != null && request.getParameter("pass") != null){
                                out.println(gson.toJson(ad.newPassword(request.getParameter("code"), request.getParameter("pass"))));
                            }else{
                                out.println(gson.toJson(msg.peticionErronea()));
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
        }catch(NumberFormatException e){
            out.println(gson.toJson(msg.peticionErronea()));
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
