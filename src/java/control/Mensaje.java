/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author cristobal
 */
public class Mensaje {
    Map<String, String> map = new HashMap<String, String>();
    
    public Mensaje(){}
    
    public Map<String, String> tieneSesion(){
        map.put("err", "2");
        map.put("session", "ok");
        return map;
    }
    
    public Map<String, String> sinValores(){
        map.put("err", "1");
        map.put("msg", "No se han establecido los valores necesarios.");
        return map;
    }
    
    public Map<String, String> peticionErronea(){
        map.put("err", "1");
        map.put("msg", "Petición incorrecta.");
        return map;
    }
    
    public Map<String, String> usuarioIncorrecto(){
        map.put("err", "1");
        map.put("msg", "Usuario o contrase&ntilde;a incorrectos.");
        return map;
    }
    
    public Map<String, String> ok(){
        map.put("err", "0");
        map.put("msg", "ok.");
        return map;
    }
    
    public Map<String, String> errorInesperado(){
        map.put("err", "1");
        map.put("msg", "Ha ocurrido un error inesperado.");
        return map;
    }
    
    public Map<String, String> msg(String msg){
        map.put("err", "1");
        map.put("msg", msg);
        return map;
    }
}