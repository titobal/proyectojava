/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author cristobal
 */
public class Producto {
    private int id;
    private String nombre;
    private String descripcion;
    private double precio;
    private int stock;
    private boolean estado;
    private int categoria;
    
    public Producto(){}
    
    public Producto(int id, String nombre, String descripcion, double precio, int stock, boolean estado, int categoria){
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stock = stock;
        this.estado = estado;
        this.categoria = categoria;
    }
    
    public Producto(String nombre, String descripcion, double precio, int stock, boolean estado, int categoria){
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stock = stock;
        this.estado = estado;
        this.categoria = categoria;
    }
    
    public List<Map<String, String>> toListMap(){
        Map<String, String> map = new HashMap<String, String>();
        List<Map<String, String>> ret = new ArrayList<Map<String, String>>();
        map.put("Id",String.valueOf(this.id));
        map.put("0",String.valueOf(this.id));
        map.put("Nombre",this.nombre);
        map.put("1",String.valueOf(this.id));
        map.put("Descripcion", this.descripcion);
        map.put("2",String.valueOf(this.id));
        map.put("Precio", String.valueOf(this.precio));
        map.put("3",String.valueOf(this.id));
        map.put("Stock", String.valueOf(this.stock));
        map.put("4",String.valueOf(this.id));
        map.put("Estado", String.valueOf(this.estado));
        map.put("5",String.valueOf(this.id));
        map.put("Categoria", String.valueOf(this.categoria));
        map.put("6",String.valueOf(this.id));
        ret.add(map);
        return ret;
    }

    public static String[] getArrayAttributes(){
        return new String[]{"Id","Nombre","Descripcion","Precio","Stock","Estado","Categoria"};
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public int getCategoria() {
        return categoria;
    }

    public void setCategoria(int categoria) {
        this.categoria = categoria;
    }
}
