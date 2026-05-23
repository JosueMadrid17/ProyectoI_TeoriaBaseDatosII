package servicios;
import conexion.conexion_bd;

public class servicio_conexion{
    private conexion_bd conexion_actual;

    public boolean iniciar_conexion(String host, String puerto, String base_datos, String usuario, String contrasena){
        conexion_actual = new conexion_bd();
        return conexion_actual.conectar(host, puerto, base_datos, usuario, contrasena);
    }

    public conexion_bd obtener_conexion_actual(){
        return conexion_actual;
    }
}