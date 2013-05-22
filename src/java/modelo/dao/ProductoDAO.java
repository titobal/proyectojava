/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.dao;

import java.util.List;
import java.util.Map;
import modelo.bean.Producto;

/**
 *
 * @author cristobal
 */
public interface ProductoDAO {
    public List<Map<String, String>> getCategorias();
    
    public String nuevoProducto(Producto p);
    
    public List<Map<String, String>> getProductos();
}
