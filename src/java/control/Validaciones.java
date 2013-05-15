/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

/**
 *
 * @author cristobal
 */
public class Validaciones {
    public Validaciones(){}
    
    public boolean isNumeric(String n){
        try {
            Integer.parseInt(n);
            return true;
	} catch (NumberFormatException nfe){
            return false;
	}
    }
    
    public boolean isNumeric(String n, int min, int max){
        try {
            Integer.parseInt(n);
            if(n.length() >= min && n.length() <= max){
                return true;
            } else {
                return false;
            }
	} catch (NumberFormatException nfe){
            return false;
	}
    }
    
    public boolean isEmail(String correo){
        if (correo.matches("^([0-9a-zA-Z]([_.w]*[0-9a-zA-Z])*@([0-9a-zA-Z][-w]*[0-9a-zA-Z].)+([a-zA-Z]{2,9}.)+[a-zA-Z]{2,3})$")) {
            return true;
        } else {
            return false;
        }
    }
    
    public boolean isAlfaNumeric(String text, int min, int max){
        if (text.matches("[\\d\\w\\s]*") && text.length() >= min && text.length() <= max) {
            return true;
        } else {
            return false;
        }
    }
    
    public boolean isAlfaNumericCU(String text, int min, int max){
        if (text.matches("[\\d\\w\\sáéíóúÁÉÍÓÚñÑ]*") && text.length() >= min && text.length() <= max) {
            return true;
        } else {
            return false;
        }
    }
    
    public String returnSinUnicode(String texto){
        try{
            byte[] converttoBytes = texto.getBytes("UTF-8");
            texto = new String(converttoBytes, "UTF-8");
        } catch (Exception e) {
            return "//--";
        } finally {
            return texto;
        }
    }
}
