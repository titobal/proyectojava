/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.dao;

import java.util.List;
import java.util.Map;
import modelo.bean.Administrador;

/**
 *
 * @author cristobal
 */
public interface AdministradorDAO {
    public List<Map<String, String>> updateAttr(int id, int m);
    
    public List<Map<String, String>> guardaCodigo(String correo, String codigo);
    
    public List<Map<String, String>> newPassword(String code, String pass);
    
    public List<Map<String, String>> compCode(String code);
    
    public boolean iniciaSesion(Administrador a);
    
    public String nuevoAdministrador(Administrador a);
    
    public List<Map<String, String>> getNivel(int id);
    
    public List<Map<String, String>> getEstado(int id);
    
    public List<Map<String, String>> updateUltimaSesion(int id);
    
    public boolean intentaIniciarSesion(String correo, String contrasena);
    
    public int getCountAdministradorPorCorreo(String correo);
    
    public Map<String, String> getAdministrador(String correo);
    
    public Map<String, String> getAdministrador(int id);
    
    public List<Map<String, String>> getAdministradores();
}
