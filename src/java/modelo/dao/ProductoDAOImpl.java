/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.dao;

import com.google.gson.Gson;
import control.Mensaje;
import control.OutPut;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import modelo.bean.Producto;

/**
 *
 * @author cristobal
 */
public class ProductoDAOImpl implements ProductoDAO{
    Map<String, String> map = new HashMap<String, String>();
    List<Map<String, String>> ret = new ArrayList<Map<String, String>>();
    Conexion q = new Conexion();
    Mensaje m = new Mensaje();
    Gson gson = new Gson();
    
    @Override
    public List<Map<String, String>> getCategorias() {
        return this.q.q("SELECT * FROM Categoria;", new String[]{"Id","Nombre","Categoria","Estado"}, true);
    }
    
    @Override
    public String nuevoProducto(Producto p){
        this.q.s("INSERT INTO Producto VALUES (NULL, '"+p.getNombre()+"', '"+p.getDescripcion()+"', "+
                String.valueOf(p.getPrecio())+", "+String.valueOf(p.getStock())+", "+(p.isEstado()?"1":"0")+
                ","+String.valueOf(p.getCategoria())+");", false);
        if(this.q.q("SELECT * FROM Producto WHERE Id = "+String.valueOf(this.q.getLastId())+";",
                Producto.getArrayAttributes(), true).size() == 1){
            return gson.toJson(new OutPut("0","El producto se ha ingresado correctamente"));
        }else{
            return gson.toJson(new OutPut("1","No se ha logrado insertar el producto."));
        }
    }
    
    @Override
    public List<Map<String, String>> getProductos(){
         return this.q.q("SELECT * FROM Producto;", Producto.getArrayAttributes(), true);
    }
}
