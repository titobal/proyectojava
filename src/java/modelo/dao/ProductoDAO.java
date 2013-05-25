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
    public List<Map<String, String>> getCategorias(boolean oculto);
    
    public String nuevoProducto(Producto p);
    
    public List<Map<String, String>> getProductos(boolean oculto);
    
    public List<Map<String, String>> getComunas();
    
    public String newVenta(String nombre, String apellidoP, String apellidoM, String correo, int comuna,
            String calle, int numero, String carro);
    
    public Map<String, String> getVenta(String id);
    
    public List<Map<String, String>> getCarro(String id);
    
    public String getTotalVenta(String id);
}
