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
public class Administrador {
    private String correo;
    private String nombre;
    private String contrasena;
    private String ultimaSesion;
    private boolean estado;
    private int nivel;
    
    public Administrador(){}
    
    public Administrador(String correo, int nivel){
        this.correo = correo;
        this.nivel = nivel;
    }
    
    public Administrador(String correo, String nombre, int nivel){
        this.correo = correo;
        this.nombre = nombre;
        this.nivel = nivel;
    }
    
    public Administrador(String correo, String ultimaSesion, boolean estado, int nivel){
        this.correo = correo;
        this.ultimaSesion = ultimaSesion;
        this.estado = estado;
        this.nivel = nivel;
    }
    
    public Administrador(String correo, String nombre, String ultimaSesion, boolean estado, int nivel, boolean dif){
        this.correo = correo;
        this.nombre = nombre;
        this.ultimaSesion = ultimaSesion;
        this.estado = estado;
        this.nivel = nivel;
    }
    
    public Administrador(String correo, String contrasena, String ultimaSesion, boolean estado, int nivel){
        this.correo = correo;
        this.contrasena = contrasena;
        this.ultimaSesion = ultimaSesion;
        this.estado = estado;
        this.nivel = nivel;
    }
    
    public List<Map<String, String>> toListMap(){
        Map<String, String> map = new HashMap<String, String>();
        List<Map<String, String>> ret = new ArrayList<Map<String, String>>();
        map.put("correo",this.correo);
        map.put("1",this.correo);
        map.put("Nombre",this.nombre);
        map.put("2",this.ultimaSesion);
        map.put("ultimaSesion",this.ultimaSesion);
        map.put("3",this.ultimaSesion);
        map.put("estado", (estado) ? "true" : "false");
        map.put("4", (estado) ? "true" : "false");
        map.put("nivel", String.valueOf(this.nivel));
        map.put("5", String.valueOf(this.nivel));
        ret.add(map);
        return ret;
    }
    
    public static String[] getArrayAttributes(){
        return new String[]{"Correo","Nombre","Contrasena","Estado","Nivel","UltimaSesion"};
    }
    
    public Administrador(String correo, String contrasena){
        this.correo = correo;
        this.contrasena = contrasena;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getUltimaSesion() {
        return ultimaSesion;
    }

    public void setUltimaSesion(String ultimaSesion) {
        this.ultimaSesion = ultimaSesion;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }  
}
