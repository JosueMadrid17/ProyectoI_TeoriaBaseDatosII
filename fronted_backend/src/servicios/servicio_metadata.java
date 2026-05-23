package servicios;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class servicio_metadata{
    private Connection conexion;

    public servicio_metadata(Connection conexion){
        this.conexion = conexion;
    }

    public ArrayList<String> listar_tablas(){
        ArrayList<String> tablas = new ArrayList<>();

        try{
            Statement stmt = conexion.createStatement();
            ResultSet rs = stmt.executeQuery("SHOW FULL TABLES WHERE Table_type = 'BASE TABLE'");

            while(rs.next()){
                tablas.add(rs.getString(1));
            }
        }catch(Exception e){
            System.out.println("Error al listar tablas");
            System.out.println(e.getMessage());
        }
        return tablas;
    }

    public ArrayList<String> listar_vistas(){
        ArrayList<String> vistas = new ArrayList<>();

        try{
            Statement stmt = conexion.createStatement();
            ResultSet rs = stmt.executeQuery("SHOW FULL TABLES WHERE Table_type = 'VIEW'");

            while(rs.next()){
                vistas.add(rs.getString(1));
            }
        }catch(Exception e){
            System.out.println("Error al listar vistas");
            System.out.println(e.getMessage());
        }
        return vistas;
    }

    public ArrayList<String> listar_procedimientos(){
        ArrayList<String> procedimientos = new ArrayList<>();

        try{
            Statement stmt = conexion.createStatement();
            ResultSet rs = stmt.executeQuery("SHOW PROCEDURE STATUS WHERE Db = DATABASE()");

            while(rs.next()){
                procedimientos.add(rs.getString("Name"));
            }
        }catch(Exception e){
            System.out.println("Error al listar procedimientos");
            System.out.println(e.getMessage());
        }
        return procedimientos;
    }

    public ArrayList<String> listar_funciones(){
        ArrayList<String> funciones = new ArrayList<>();

        try{
            Statement stmt = conexion.createStatement();
            ResultSet rs = stmt.executeQuery("SHOW FUNCTION STATUS WHERE Db = DATABASE()");

            while(rs.next()){
                funciones.add(rs.getString("Name"));
            }
        }catch(Exception e){
            System.out.println("Error al listar funciones");
            System.out.println(e.getMessage());
        }
        return funciones;
    }

    public ArrayList<String> listar_triggers(){
        ArrayList<String> triggers = new ArrayList<>();

        try{
            Statement stmt = conexion.createStatement();
            ResultSet rs = stmt.executeQuery("SHOW TRIGGERS");

            while(rs.next()){
                triggers.add(rs.getString("Trigger"));
            }
        }catch(Exception e){
            System.out.println("Error al listar triggers");
            System.out.println(e.getMessage());
        }
        return triggers;
    }

    public ArrayList<String> listar_indices(){
        ArrayList<String> indices = new ArrayList<>();
        ArrayList<String> tablas = listar_tablas();

        try{
            Statement stmt = conexion.createStatement();

            for(String tabla: tablas){
                ResultSet rs = stmt.executeQuery("SHOW INDEX FROM " + tabla);

                while(rs.next()){
                    String nombre_indice = rs.getString("Key_name");
                    String columna = rs.getString("Column_name");
                    indices.add(tabla + " -> " + nombre_indice + " (" + columna + ")");
                }
            }
        }catch(Exception e){
            System.out.println("Error al listar indices");
            System.out.println(e.getMessage());
        }
        return indices;
    }

    public ArrayList<String> listar_usuarios(){
        ArrayList<String> usuarios = new ArrayList<>();

        try{
            Statement stmt = conexion.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT user,host FROM mysql.user");

            while(rs.next()){
                usuarios.add(rs.getString("user") + "@" + rs.getString("host"));
            }
        }catch(Exception e){
            System.out.println("Error al listar usuarios");
            System.out.println(e.getMessage());
        }
        return usuarios;
    }
}