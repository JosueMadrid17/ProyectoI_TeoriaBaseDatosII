package servicios;
import java.util.ArrayList;
import modelos.conexion_guardada;

public class servicio_conexiones_guardadas{
    private static ArrayList<conexion_guardada> conexiones = new ArrayList<>();

    public static void agregar_conexion(conexion_guardada conexion){
        conexiones.add(conexion);
    }

    public static ArrayList<conexion_guardada> obtener_conexiones(){
        return conexiones;
    }

    public static void eliminar_conexion(conexion_guardada conexion){
        conexiones.remove(conexion);
    }
}