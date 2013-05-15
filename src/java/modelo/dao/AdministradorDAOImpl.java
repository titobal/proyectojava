/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.dao;

import control.Mensaje;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import modelo.bean.Administrador;

/**
 *
 * @author cristobal
 */
public class AdministradorDAOImpl implements AdministradorDAO{
    Map<String, String> map = new HashMap<String, String>();
    List<Map<String, String>> ret = new ArrayList<Map<String, String>>();
    Conexion q = new Conexion();
    Mensaje m = new Mensaje();
    
    @Override
    public List<Map<String, String>> updateAttr(int id, int m) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Map<String, String>> guardaCodigo(String correo, String codigo) {
        if(this.getCountAdministradorPorCorreo(correo) == 1){
            ret = this.q.q("SELECT * FROM Administrador WHERE Correo = '"+correo+"';", Administrador.getArrayAttributes(), false);
            if(ret.size() == 1){
                this.q.s("DELETE FROM CodigoRecuperacion WHERE Objeto = 0 AND Id = "+ret.get(0).get("Id")+";", false);
                this.q.s("INSERT INTO CodigoRecuperacion VALUES ('"+codigo+"',sysdate(),"+ret.get(0).get("Id")+",0);", true);
                return m.ok();
            }else{
                return m.errorInesperado();
            }
        }else{
            return m.msg("Parece que el correo no est&aacute; registrado en la base de datos."); 
        }
    }

    @Override
    public List<Map<String, String>> newPassword(String code, String pass) {
        ret = this.q.q("SELECT * FROM CodigoRecuperacion WHERE Codigo = '"+code+"' AND Objeto = 0;", new String[]{"Codigo","Fecha","Id","Objeto"}, false);
        if(ret.size() == 1){
            this.q.s("UPDATE Administrador SET Contrasena = '"+pass+"' WHERE Id = "+ret.get(0).get("Id")+";", true);
            return this.m.ok();
        }else{
            this.q.cierra();
            return m.msg("Al parecer el c&oacute;digo no es v&aacute;lido.");
        }
    }

    @Override
    public List<Map<String, String>> compCode(String code) {
        if(Integer.parseInt(this.q.q("SELECT COUNT(*) FROM CodigoRecuperacion WHERE Codigo = '"+code+"';", new String[]{"c"}, false).get(0).get("c")) == 1){
            map.put("err","0");
            map.put("msg","ok");
            map.put("form","<fieldset><div class=\"control-group\"><label class=\"control-label\">Nueva Contraseña</label><div class=\"controls\"><input type=\"password\" name=\"pass1\" class=\"input-xlarge\" pattern=\"(?=^.{8,}$)((?=.*\\d)|(?=.*\\W+))(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*$\" required placeholder=\"8 caracteres mínimo\" title=\"Letras mayusculas y minusculas, un número o simbolo, 8 caracteres minimo.\"/></div></div><div class=\"control-group\"><label class=\"control-label\">Confirmar Contraseña</label><div class=\"controls\"><input type=\"password\" name=\"pass2\" class=\"input-xlarge\" pattern=\"(?=^.{8,}$)((?=.*\\d)|(?=.*\\W+))(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*$\" required placeholder=\"8 caracteres mínimo\" title=\"Letras mayusculas y minusculas, un número o simbolo, 8 caracteres minimo.\"/></div></div><p><small>Contraseña segura, debe ser una combinación de letras mayusculas, letras minusculas, con números o algún simbolo, y de largo mínimo de 8 caracteres.</small></p></fieldset>");
            ret.add(map);
            return ret;
        }else{
            this.q.cierra();
            return m.msg("El c&oacute;digo ingresado no es v&aacute;lido, puede solicitar uno nuevo");
        }      
    }

    @Override
    public boolean iniciaSesion(Administrador a) {
        if(this.intentaIniciarSesion(a.getCorreo(), a.getContrasena())){
            this.q.cierra();
            return true;
        }else{
            this.q.cierra();
            return false;
        }
    }

    @Override
    public List<Map<String, String>> nuevoAdministrador(Administrador a) {
        if(this.getCountAdministradorPorCorreo(a.getCorreo()) == 0){
            this.q.s("INSERT INTO Administrador VALUES (NULL, 1, "+String.valueOf(a.getNivel())+", '"+a.getCorreo()+"', NULL, 'no tiene');", false);
            return this.getAdministrador(this.q.getLastId()).toListMap();
        }else{
            return m.msg("Ya existe un administrador registrador con este correo.");
        }
    }

    @Override
    public List<Map<String, String>> getNivel(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Map<String, String>> getEstado(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Map<String, String>> updateUltimaSesion(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean intentaIniciarSesion(String correo, String contrasena) {
        ret = this.q.q("SELECT Id FROM Administrador WHERE Correo = '"+correo+"' AND Contrasena = '"+contrasena+"';",  new String[]{"Id"}, false);
        if(ret.size() == 1){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public int getCountAdministradorPorCorreo(String correo) {
        return Integer.parseInt(this.q.q("SELECT COUNT(*) FROM Administrador WHERE Correo = '"+correo+"';", new String[]{"COUNT"}, false).get(0).get("COUNT"));
    }

    @Override
    public Administrador getAdministrador(String correo) {
        map.putAll(this.q.q("SELECT * FROM Administrador WHERE Correo = '"+correo+"';", Administrador.getArrayAttributes(), true).get(0));
        return new Administrador(Integer.parseInt(map.get("Id")),map.get("Correo"),map.get("UltimaSesion"),(map.get("Estado")=="1"?true:false),Integer.parseInt(map.get("Nivel")));
    }
    
    @Override
    public Administrador getAdministrador(int id){
        map.putAll(this.q.q("SELECT * FROM Administrador WHERE Id = '"+String.valueOf(id)+"';", Administrador.getArrayAttributes(), true).get(0));
        return new Administrador(Integer.parseInt(map.get("Id")),map.get("Correo"),map.get("UltimaSesion"),(map.get("Estado")=="1"?true:false),Integer.parseInt(map.get("Nivel")));
    }       

    @Override
    public List<Map<String, String>> getAdministradores() {
        return this.q.q("SELECT Id, Estado, Nivel, Correo, UltimaSesion FROM Administrador;",new String[]{"Id", "Estado", "Nivel", "Correo", "UltimaSesion"}, true);
    }
}
