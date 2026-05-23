package conexion;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class conexion_bd{
    private Connection conexion;

    public boolean conectar(String host, String puerto, String base_datos, String usuario, String contrasena){
        try{
            String url = "jdbc:mysql://" + host + ":" + puerto + "/" + base_datos;
            conexion = DriverManager.getConnection(url, usuario, contrasena);
            return true;
        }catch(SQLException e){
            System.out.println("Error de conexion");
            System.out.println(e.getMessage());
            return false;
        }
    }

    public Connection obtener_conexion(){
        return conexion;
    }

    public void cerrar_conexion(){
        try{
            if(conexion != null && !conexion.isClosed()){
                conexion.close();
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
}