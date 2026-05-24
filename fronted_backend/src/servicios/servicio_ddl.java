package servicios;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;

public class servicio_ddl{
    private Connection conexion;

    public servicio_ddl(Connection conexion){
        this.conexion = conexion;
    }

    public String obtener_ddl_tabla(String nombre_tabla){
        String ddl = "";
        try{
            Statement stmt = conexion.createStatement();
            ResultSet rs = stmt.executeQuery("SHOW CREATE TABLE " + nombre_tabla);
            if(rs.next()){
                ddl = rs.getString(2);
            }
        }catch(Exception e){
            ddl = "Error al generar DDL: " + e.getMessage();
        }
        return ddl;
    }

    public String obtener_ddl_vista(String nombre_vista){
        String ddl = "";
        try{
            Statement stmt = conexion.createStatement();
            ResultSet rs = stmt.executeQuery("SHOW CREATE VIEW " + nombre_vista);
            if(rs.next()){
                ddl = rs.getString(2);
            }
        }catch(Exception e){
            ddl = "Error al generar DDL: " + e.getMessage();
        }
        return ddl;
    }
}
