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
import modelo.bean.Administrador;
import modelo.bean.EnviarCorreo;

/**
 *
 * @author cristobal
 */
public class AdministradorDAOImpl implements AdministradorDAO{
    Map<String, String> map = new HashMap<String, String>();
    List<Map<String, String>> ret = new ArrayList<Map<String, String>>();
    Conexion q = new Conexion();
    Mensaje m = new Mensaje();
    Gson gson = new Gson();
    
    @Override
    public String deleteAdministrador(String correo, String admin){
        List<Map<String, String>> retu = new ArrayList<Map<String, String>>();
        if(!correo.equals(admin)){
            if(this.compruebaPrivilegios(admin)){
                retu.add(this.getAdministrador(correo, false));
                this.q.s("DELETE FROM Administrador WHERE Correo = '"+correo+"';", true);
                return gson.toJson(new OutPut("0","ok",retu));
            }else{
                return gson.toJson(new OutPut("1","No tiene los permisos suficientes para realizar esta acción."));
            }
        }else{
            return gson.toJson(new OutPut("1","No puede modificar sus propios atributos."));
        }
    }
    
    @Override
    public String updateNivel(String correo, String admin) {
        List<Map<String, String>> retu = new ArrayList<Map<String, String>>();
        if(!correo.equals(admin)){
            if(this.compruebaPrivilegios(admin)){
                this.q.s("UPDATE Administrador SET Nivel = CASE WHEN Nivel = 1 THEN 0 ELSE 1 END WHERE Correo = '"+correo+"';", false);
                retu.add(this.getAdministrador(correo));
                return gson.toJson(new OutPut("0","ok",retu));
            }else{
                return gson.toJson(new OutPut("1","No tiene los permisos suficientes para realizar esta acción."));
            }
        }else{
            return gson.toJson(new OutPut("1","No puede modificar sus propios atributos."));
        }
    }
    
    @Override
    public String updateEstado(String correo, String admin){
        List<Map<String, String>> retu = new ArrayList<Map<String, String>>();
        if(!correo.equals(admin)){
            if(this.compruebaPrivilegios(admin)){
                this.q.s("UPDATE Administrador SET Estado = CASE WHEN Estado = 1 THEN 0 ELSE 1 END WHERE Correo = '"+correo+"';", false);
                retu.add(this.getAdministrador(correo));
                return gson.toJson(new OutPut("0","ok",retu));
            }else{
                return gson.toJson(new OutPut("1","No tiene los permisos suficientes para realizar esta acción."));
            }
        }else{
            return gson.toJson(new OutPut("1","No puede modificar sus propios atributos."));
        }
    }

    @Override
    public List<Map<String, String>> guardaCodigo(String correo, String codigo) {
        if(this.getCountAdministradorPorCorreo(correo) == 1){
            ret = this.q.q("SELECT * FROM Administrador WHERE Correo = '"+correo+"';", Administrador.getArrayAttributes(), false);
            if(ret.size() == 1){
                this.q.s("DELETE FROM CodigoRecuperacion WHERE Administrador = '"+ret.get(0).get("Correo")+"';", false);
                this.q.s("INSERT INTO CodigoRecuperacion VALUES ('"+codigo+"',sysdate(),'"+ret.get(0).get("Correo")+"');", true);
                ret = new ArrayList<Map<String, String>>();
                EnviarCorreo.enviarCorreo(correo, "Recuperacion de contraseña", "el link para recuperar la contraseña es: "+
                        "http://localhost:8080/proyectojava/loginadmin.jsp#c="+codigo);
                ret.add(m.msg("Se le ha enviado un correo con el c&oacute;digo de recuperaci&oacute;n."));
                return ret;
            }else{
                ret.add(m.errorInesperado());
                return ret;
            }
        }else{
            ret.add(m.msg("Parece que el correo no est&aacute; registrado en la base de datos."));
            return ret;
        }
    }

    @Override
    public List<Map<String, String>> newPassword(String code, String pass) {
        ret = this.q.q("SELECT * FROM CodigoRecuperacion WHERE Codigo = '"+code+"';", new String[]{"Codigo","Fecha","Administrador"}, false);
        if(ret.size() == 1){
            this.q.s("UPDATE Administrador SET Contrasena = '"+pass+"' WHERE Correo = '"+ret.get(0).get("Administrador")+"';", false);
            this.q.s("DELETE FROM CodigoRecuperacion WHERE Administrador = '"+ret.get(0).get("Administrador")+"';", true);
            ret = new ArrayList<Map<String, String>>();
            ret.add(this.m.ok());
            return ret;
        }else{
            this.q.cierra();
            ret = new ArrayList<Map<String, String>>();
            ret.add(m.msg("Al parecer el c&oacute;digo no es v&aacute;lido."));
            return ret;
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
            ret.add(m.msg("El c&oacute;digo ingresado no es v&aacute;lido, puede solicitar uno nuevo"));
            return ret;
        }      
    }

    @Override
    public boolean iniciaSesion(Administrador a) {
        if(this.intentaIniciarSesion(a.getCorreo(), a.getContrasena())){
            this.updateUltimaSesion(a.getCorreo());
            return true;
        }else{
            this.q.cierra();
            return false;
        }
    }

    @Override
    public String nuevoAdministrador(Administrador a) {
        if(this.getCountAdministradorPorCorreo(a.getCorreo()) == 0){
            this.q.s("INSERT INTO Administrador VALUES ('"+a.getCorreo()+"', '"+a.getNombre().toUpperCase()+"','no tiene', 1, "+
                    String.valueOf(a.getNivel())+", NULL);", false);
            ret.add(this.getAdministrador(a.getCorreo()));
            return gson.toJson(new OutPut("0","ok",ret));
        }else{
            return gson.toJson(new OutPut("1","Ya existe un administrador registrado con este correo."));
        }
    }

    @Override
    public boolean compruebaPrivilegios(String correo){
        ret = this.q.q("SELECT Nivel, Estado FROM Administrador WHERE Correo = '"+correo+"';", new String[]{"Nivel","Estado"},false);
        if(Integer.parseInt(ret.get(0).get("Nivel")) == 0 && Integer.parseInt(ret.get(0).get("Estado")) == 1){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public void updateUltimaSesion(String correo) {
        this.q.s("UPDATE Administrador SET UltimaSesion = sysdate() WHERE Correo = '"+correo+"';", true);
    }

    @Override
    public boolean intentaIniciarSesion(String correo, String contrasena) {
        ret = this.q.q("SELECT Correo FROM Administrador WHERE Correo = '"+correo+"' AND Contrasena = '"+contrasena+"';",  new String[]{"Correo"}, false);
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
    public Map<String, String> getAdministrador(String correo) {
        map.putAll(this.q.q("SELECT * FROM Administrador WHERE Correo = '"+correo+"';", Administrador.getArrayAttributes(), true).get(0));
        return map;
    }
    
    public Map<String, String> getAdministrador(String correo, boolean cierra){
        map.putAll(this.q.q("SELECT * FROM Administrador WHERE Correo = '"+correo+"';", Administrador.getArrayAttributes(), cierra).get(0));
        return map;
    }

    @Override
    public List<Map<String, String>> getAdministradores() {
        return this.q.q("SELECT Estado, Nivel, Correo, UltimaSesion FROM Administrador;",new String[]{"Estado", "Nivel", "Correo", "UltimaSesion"}, true);
    }
}
