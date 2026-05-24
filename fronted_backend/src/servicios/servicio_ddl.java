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
    
    public String obtener_ddl_procedimiento(String nombre_procedimiento){
        String ddl = "";
        try{
            Statement stmt = conexion.createStatement();
            ResultSet rs = stmt.executeQuery(
                "SHOW CREATE PROCEDURE " + nombre_procedimiento
            );
            if(rs.next()){
                ddl = rs.getString("Create Procedure");
            }
        }catch(Exception e){
            ddl = "Error al generar DDL del procedimiento: " + e.getMessage();
        }
        return ddl;
    }

    public String obtener_ddl_funcion(String nombre_funcion){
        String ddl = "";
        try{
            Statement stmt = conexion.createStatement();
            ResultSet rs = stmt.executeQuery(
                "SHOW CREATE FUNCTION " + nombre_funcion
            );
            if(rs.next()){
                ddl = rs.getString("Create Function");
            }
        }catch(Exception e){
            ddl = "Error al generar DDL de la funcion: " + e.getMessage();
        }
        return ddl;
    }

    public String obtener_ddl_trigger(String nombre_trigger){
        String ddl = "";
        try{
            Statement stmt = conexion.createStatement();
            ResultSet rs = stmt.executeQuery(
                "SHOW CREATE TRIGGER " + nombre_trigger
            );
            if(rs.next()){
                ddl = rs.getString("SQL Original Statement");
            }
        }catch(Exception e){

            ddl = "Error al generar DDL del trigger: " + e.getMessage();
        }
        return ddl;
    }
}
