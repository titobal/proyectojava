/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 *
 * @author cristobal
 */
public class Tools {
    public Tools(){}
    
    public static String genCode(int l){
        char[] chars = "1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < l; i++) {
            char c = chars[random.nextInt(chars.length)];
            sb.append(c);
        }
        String output = sb.toString();
        return output;
    }
    
    /*public static List<List<Map<String, String>>> returnOkPlusObject(List<Map<String, String>> obj){
        List<Map<String, String>> ok = new ArrayList<Map<String, String>>();
        List<List<Map<String, String>>> ret = new ArrayList<List<Map<String, String>>>();
        Map<String, String> map = new HashMap<String, String>();
        map.put("err","0");
        ok.add(map);
        ret.add(ok);
        ret.add(obj);
        return ret;
    }*/
    
    /*public static List<Map<String, String>> returnOkPlusObject(List<Map<String, String>> o){
        /*List<Map<String, String>> ret = new ArrayList<Map<String, String>>();
        Map<String, String> obj = new HashMap<String, String>();
        Map<String, String> ok = new HashMap<String, String>();
        obj.putAll(o.get(0));
        ok.put("err", "0");
        ret.add(ok);
        ret.add(obj);
        return ret;*/
    //}
}
