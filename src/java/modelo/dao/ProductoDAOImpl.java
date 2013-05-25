/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.dao;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import control.ItemCarro;
import control.Mensaje;
import control.OutPut;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import modelo.bean.EnviarCorreo;
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
    public List<Map<String, String>> getCategorias(boolean oculto) {
        if(oculto){
            return this.q.q("SELECT * FROM Categoria;", new String[]{"Id","Nombre","Categoria","Estado"}, true);
        }else{
            return this.q.q("SELECT * FROM Categoria WHERE Estado = 1;", new String[]{"Id","Nombre","Categoria","Estado"}, true);
        }
    }
    
    @Override
    public String nuevoProducto(Producto p){
        this.q.s("INSERT INTO Producto VALUES (NULL, '"+p.getNombre().toUpperCase()+"', '"+p.getDescripcion().toUpperCase()+"', "+
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
    public List<Map<String, String>> getProductos(boolean oculto){
        if(oculto){
            return this.q.q("SELECT * FROM Producto;", Producto.getArrayAttributes(), true);
        }else{
            return this.q.q("SELECT * FROM Producto WHERE Estado = 1;", Producto.getArrayAttributes(), true);
        }
    }
    
    @Override
    public List<Map<String, String>> getComunas(){
        return this.q.q("SELECT * FROM Comuna;", new String[]{"Id","Nombre","Ciudad"}, true);
    }
    
    @Override
    public String newVenta(String nombre, String apellidoP, String apellidoM, String correo, int comuna,
            String calle, int numero, String carro){
        this.q.s("INSERT INTO Venta VALUES(NULL, sysdate(), '"+correo+"', "+String.valueOf(comuna)+", '"+calle+"', "+String.valueOf(numero)+
                ", '"+nombre+"', '"+apellidoP+"', '"+apellidoM+"');", false);
        String codigo = String.valueOf(this.q.getLastId());
        if(this.q.q("SELECT Codigo FROM Venta WHERE Codigo = "+codigo+";",
                new String[]{"Codigo"}, false).size() == 1){
            String[] items = carro.split("}");
            String query = "INSERT INTO ProductoDeVenta (Venta, Producto, Cantidad, Valor) VALUES ";
            int largo = (items.length) -1;
            for(int i = 0; i < largo; i++){
                int a = items[i].indexOf(":")+1;
                int b = 0;
                if(i == 0){
                    b = items[i].indexOf(",");
                }else{
                    b = items[i].indexOf(",",1);
                }
                int id = Integer.parseInt(items[i].substring(a, a+(b-a)));
                int cantidad = Integer.parseInt(items[0].substring(items[0].indexOf(":", a)+1));
                ret = new ArrayList<Map<String, String>>();
                ret = this.q.q("SELECT Precio FROM Producto WHERE Id = "+String.valueOf(id)+";" ,new String[]{"Precio"}, false);
                query += "("+codigo+","+String.valueOf(id)+","+String.valueOf(cantidad)+","+ret.get(0).get("Precio")+"),";
            }
            this.q.s(query.substring(0, (query.length()-1)), true);
            ret = new ArrayList<Map<String, String>>();
            map = new HashMap<String, String>();
            String venta = "La compra se ha ingresado correctamente.<br/>Puede ver el detalle de su compra haciendo click"+
                    " <a href='venta?id="+codigo+"' target='_blank'>aqu&iacute;</a>";
            map.put("msg", venta);
            EnviarCorreo.enviarCorreo(correo, "Detalle de compra", "Usted ha comprado en elproyectojava, para ver el "+
                    "detalle de su compra vaya al siguiente enlace: http://localhost:8080/proyectojava/venta?id=" + codigo);
            ret.add(map);
            return gson.toJson(new OutPut("0","ok",ret));
        }else{
            return gson.toJson(new OutPut("1","No se ha logrado insertar el producto."));
        }
    }
    
    @Override
    public Map<String, String> getVenta(String id){
        map = new HashMap<String, String>();
        map.putAll(this.q.q("SELECT v.Codigo, v.Fecha, v.Correo, c.Nombre as Comuna, v.Calle, v.Numero, v.Nombre, v.ApellidoP, v.ApellidoM"+
                " FROM Venta v, Comuna c WHERE Codigo = "+id+";",
                new String[]{"Codigo", "Fecha", "Correo", "Comuna", "Calle","Numero","Nombre","ApellidoP","ApellidoM"}, false).get(0));
        return map;
    }
    
    @Override
    public List<Map<String, String>> getCarro(String id){
        return this.q.q("SELECT p.Nombre, pd.Cantidad, pd.Valor, pd.Cantidad * pd.Valor FROM ProductoDeVenta pd, Producto p "+
                "WHERE pd.Producto = p.Id AND pd.Venta = "+id+";", new String[]{"Nombre","Cantidad","Valor","Total"},false);
    }
    
    @Override
    public String getTotalVenta(String id){
        ret = this.q.q("SELECT Cantidad * Valor as total FROM ProductoDeVenta WHERE Venta = "+id+";", new String[]{"total"}, true);
        int l = ret.size();
        double total = 0;
        for(int x = 0; x < l; x++){
            total += Double.parseDouble(ret.get(0).get("total"));
        }
        return String.valueOf(total);
    }
}
