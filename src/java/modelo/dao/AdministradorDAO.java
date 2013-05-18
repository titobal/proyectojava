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
    public String updateNivel(String correo, String admin);
    
    public String updateEstado(String correo, String admin);
    
    public List<Map<String, String>> guardaCodigo(String correo, String codigo);
    
    public List<Map<String, String>> newPassword(String code, String pass);
    
    public List<Map<String, String>> compCode(String code);
    
    public boolean iniciaSesion(Administrador a);
    
    public String nuevoAdministrador(Administrador a);
    
    public boolean compruebaPrivilegios(String correo);
    
    public void updateUltimaSesion(String correo);
    
    public boolean intentaIniciarSesion(String correo, String contrasena);
    
    public int getCountAdministradorPorCorreo(String correo);
    
    public Map<String, String> getAdministrador(String correo);
        
    public List<Map<String, String>> getAdministradores();
    
    public String deleteAdministrador(String correo, String administrador);
}
