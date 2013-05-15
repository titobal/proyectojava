/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author cristobal
 */
public class Mensaje {
    Map<String, String> map = new HashMap<String, String>();;
    List<Map<String, String>> ret = new ArrayList<Map<String, String>>();
    
    public Mensaje(){}
    
    public List<Map<String, String>> tieneSesion(){
        map.put("err", "2");
        map.put("session", "ok");
        ret.add(map);
        return ret;
    }
    
    public List<Map<String, String>> sinValores(){
        map.put("err", "1");
        map.put("msg", "No se han establecido los valores necesarios.");
        ret.add(map);
        return ret;
    }
    
    public List<Map<String, String>> peticionErronea(){
        map.put("err", "1");
        map.put("msg", "Petición incorrecta.");
        ret.add(map);
        return ret;
    }
    
    public List<Map<String, String>> usuarioIncorrecto(){
        map.put("err", "1");
        map.put("msg", "Usuairo o contrase&ntilde;a incorrectos.");
        ret.add(map);
        return ret;
    }
    
    public List<Map<String, String>> ok(){
        map.put("err", "0");
        map.put("msg", "ok.");
        ret.add(map);
        return ret;
    }
    
    public List<Map<String, String>> errorInesperado(){
        map.put("err", "1");
        map.put("msg", "Ha ocurrido un error inesperado.");
        ret.add(map);
        return ret;
    }
    
    public List<Map<String, String>> msg(String msg){
        map.put("err", "1");
        map.put("msg", msg);
        ret.add(map);
        return ret;
    }
}
