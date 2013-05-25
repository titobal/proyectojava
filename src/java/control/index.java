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
import modelo.bean.Producto;
import modelo.dao.ProductoDAO;
import modelo.dao.ProductoDAOImpl;

/**
 *
 * @author cristobal
 */
@WebServlet(name = "index", urlPatterns = {"/index"})
public class index extends HttpServlet {
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        Mensaje msg = new Mensaje();
        Gson gson = new Gson();
        try {
            if(request.getParameter("m") != null){
                Producto p = new Producto();
                ProductoDAO pd = new ProductoDAOImpl();
                switch(Integer.parseInt(request.getParameter("m"))){
                    case 1:
                        out.println(gson.toJson(new OutPut("0","ok",pd.getCategorias(false))));
                    break;
                    case 2:
                        out.println(gson.toJson(new OutPut("0","ok",pd.getProductos(false))));
                    break;
                    case 3:
                        out.println(gson.toJson(new OutPut("0","ok",pd.getComunas())));
                    break;
                    case 4:
                        if(request.getParameter("nombre") != null && request.getParameter("apellidoP") != null && request.getParameter("apellidoM") != null &&
                                request.getParameter("correo") != null && request.getParameter("comuna") != null &&
                                request.getParameter("calle") != null && request.getParameter("numero") != null &&
                                request.getParameter("carro") != null){
                            String nombre = request.getParameter("nombre");
                            String apellidoP = request.getParameter("apellidoP");
                            String apellidoM = request.getParameter("apellidoM");
                            String correo = request.getParameter("correo");
                            int comuna = Integer.parseInt(request.getParameter("comuna"));
                            String calle = request.getParameter("calle");
                            int numero = Integer.parseInt(request.getParameter("numero"));
                            String carro = request.getParameter("carro");
                            out.println(pd.newVenta(nombre, apellidoP, apellidoM, correo, comuna, calle, numero, carro));
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
