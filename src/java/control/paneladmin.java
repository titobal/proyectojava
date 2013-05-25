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
import modelo.bean.Producto;
import modelo.dao.AdministradorDAO;
import modelo.dao.AdministradorDAOImpl;
import modelo.dao.ProductoDAO;
import modelo.dao.ProductoDAOImpl;

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
                        case 1://Categorias
                            if(request.getParameter("m")!= null){
                                Producto p = new Producto();
                                ProductoDAO pd = new ProductoDAOImpl();
                                switch(Integer.parseInt(request.getParameter("m"))){
                                    case 1:
                                        out.println(gson.toJson(new OutPut("0","ok",pd.getCategorias(true))));
                                    break;
                                    default:
                                        out.println(gson.toJson(msg.peticionErronea()));
                                    break;
                                }
                            }else{
                                out.println(gson.toJson(msg.sinValores())); 
                            }
                        break;
                        case 2://Producto
                            if(request.getParameter("m")!= null){
                                ProductoDAO pd = new ProductoDAOImpl();
                                switch(Integer.parseInt(request.getParameter("m"))){
                                    case 1://NEW PRODUCTO
                                        if(request.getParameter("nombre") != null && request.getParameter("descripcion") != null
                                                && request.getParameter("precio") != null && request.getParameter("stock") != null
                                                && request.getParameter("estado") != null && request.getParameter("categoria") != null){
                                            String nombre = request.getParameter("nombre");
                                            String descripcion = request.getParameter("descripcion");
                                            double precio = Double.parseDouble(request.getParameter("precio"));
                                            int stock = Integer.parseInt(request.getParameter("stock"));
                                            boolean estado = (request.getParameter("estado").equals("1")) ? true : false;
                                            int categoria = Integer.parseInt(request.getParameter("categoria"));
                                            Producto p = new Producto(nombre, descripcion, precio, stock, estado, categoria);
                                            out.println(pd.nuevoProducto(p));
                                        }else{
                                            out.println(gson.toJson(msg.sinValores()));
                                        }
                                    break;
                                    case 2:
                                        out.println(gson.toJson(new OutPut("0","ok",pd.getProductos(true))));
                                    break;
                                    default:
                                        out.println(gson.toJson(msg.peticionErronea()));
                                    break;
                                }
                            }else{
                                out.println(gson.toJson(msg.sinValores()));
                            }
                        break;
                        case 3://ADMINISTRADOR
                            if(request.getParameter("m")!= null){
                                switch(Integer.parseInt(request.getParameter("m"))){
                                    case 1://getAdministradores
                                        out.println(gson.toJson(new OutPut("0","ok",ad.getAdministradores())));
                                    break;
                                    case 2://creaAdministraaor
                                        if(request.getParameter("correo") != null && request.getParameter("nivel") != null && request.getParameter("nombre") != null){
                                            out.println(ad.nuevoAdministrador(
                                                    new Administrador(request.getParameter("correo"), request.getParameter("nombre"), Integer.parseInt(request.getParameter("nivel")))));
                                        }else{
                                            out.println(gson.toJson(msg.sinValores()));
                                        }
                                    break;
                                    case 3://updateAdministrador
                                        if(request.getParameter("correo") != null && request.getParameter("a") != null){
                                            switch(Integer.parseInt(request.getParameter("a"))){
                                                case 1://UPDATE NIVEL
                                                    out.println(ad.updateNivel(request.getParameter("correo"), session.getAttribute("admin").toString()));
                                                break;
                                                case 2://UPDATE ESTADO
                                                    out.println(ad.updateEstado(request.getParameter("correo"), session.getAttribute("admin").toString()));
                                                break;
                                                case 3://DELETE ADMINISTRADOR
                                                    out.println(ad.deleteAdministrador(request.getParameter("correo"), session.getAttribute("admin").toString()));
                                                break;
                                                default:
                                                    out.println(gson.toJson(msg.peticionErronea()));
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
