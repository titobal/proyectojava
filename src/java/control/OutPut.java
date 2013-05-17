/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author cristobal
 */
public class OutPut {
    private String err = new String();
    private String msg = new String();
    private List<Map<String, String>> obj = new ArrayList<Map<String, String>>();
    
    public OutPut(String err, String msg, List<Map<String, String>> obj){
        this.err = err;
        this.msg = msg;
        this.obj = obj;
    }
    
    public OutPut(String err, String msg){
        this.err = err;
        this.msg = msg;
    }

    public String getErr() {
        return err;
    }

    public void setErr(String err) {
        this.err = err;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<Map<String, String>> getObj() {
        return obj;
    }

    public void setObj(List<Map<String, String>> obj) {
        this.obj = obj;
    }
}
