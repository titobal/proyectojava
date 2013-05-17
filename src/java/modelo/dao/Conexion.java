/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author cristobal
 */
public class Conexion {
    private static final Logger logger = Logger.getLogger(Conexion.class.getName());
    private Connection con;
    private static String url;
    private static String user;
    private static String pass;
    private String driver = "com.mysql.jdbc.Driver";
    private int lastId=0;
    private PreparedStatement ps;
    
    public Conexion() {
        try {
            Class.forName(driver); //Declara el dirver de mysql
        } catch (ClassNotFoundException ex) {
            logger.log(Level.CONFIG, "No se puede cargar el Driver: {0}", ex.getMessage());
        }
        url = "jdbc:mysql://localhost/admin"; //Nombre y ubicación de la base de datos
        user = "root"; //Usuario de conexión a la BD
        pass = ""; //Contraseña del usuario de conesión a la BD
        try {
            con = DriverManager.getConnection(url, user, pass);
        } catch (SQLException ex) {
            logger.log(Level.CONFIG, "Error al establecer la conexion: {0}", ex.getMessage());
        }
    }
    
    public List<Map<String, String>> q(String query, String[] columns, boolean cierra){
        Map<String, String> map;
        List<Map<String, String>> ret = new ArrayList<Map<String, String>>();
        ResultSet rs = null;
        try{
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            int largo = columns.length;
            if (rs.next()) {
                do{
                    map = new HashMap<String, String>();
                    for(int x = 0; x < largo; x++){
                        map.put(String.valueOf(x), rs.getString(x+1));
                        map.put(columns[x], rs.getString(x+1));
                        if(rs.wasNull()){
                            map.put(String.valueOf(x), "NULL");
                            map.put(columns[x], "NULL");
                        }
                    }
                    ret.add(map);
                } while (rs.next());
            }
        }
        catch(SQLException ex){
            ret = this.mapError("Error al ejecutar la consulta", ex.getMessage());
        }
        finally{
            if(cierra){
                try {
                    con.close();
                    ps.close();
                } catch (SQLException ex) {
                    ret = this.mapError("Error al Intentar cerrar la conexion", ex.getMessage());
                }
            }
            return ret;
        }
    }
    
    public void s(String query, boolean cierra){
        this.lastId = 0;
        try{
            ps = con.prepareStatement(query, ps.RETURN_GENERATED_KEYS);
            this.lastId = ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()){
                this.lastId = rs.getInt(1);
            }
        }
        catch(SQLException ex){
            logger.log(Level.CONFIG, "Insert, sql erronea: {0}", ex.getMessage());
        }
        finally{
            if(cierra){
                try {
                    con.close();
                    ps.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ex.getMessage()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    public void cierra(){
        try {
            con.close();
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(ex.getMessage()).log(Level.SEVERE, null, ex);
        }
    }
    
    private List<Map<String, String>> mapError(String mensaje, String exmsg){
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        Map map = new HashMap<String, String>();
        map.put("err", "1");
        map.put("msg", mensaje);
        map.put("exmsg", exmsg);
        list.add(map);
        return list;
    }
    
    public int getLastId(){
        return this.lastId;
    }
}