/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

/**
 *
 * @author cristobal
 */
public class ItemCarro {
    private int id;
    private int cantidad;
    
    public ItemCarro(){
    }
    
    public ItemCarro(int id, int cantidad){
        this.id = id;
        this.id = cantidad;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
