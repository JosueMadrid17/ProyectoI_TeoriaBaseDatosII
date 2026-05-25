package modelos;
public class conexion_guardada{
    private String nombre;
    private String host;
    private String puerto;
    private String base_datos;
    private String usuario;

    public conexion_guardada(String nombre,String host,String puerto,String base_datos,String usuario){
        this.nombre = nombre;
        this.host = host;
        this.puerto = puerto;
        this.base_datos = base_datos;
        this.usuario = usuario;
    }

    public String get_nombre(){
        return nombre;
    }

    public String get_host(){
        return host;
    }

    public String get_puerto(){
        return puerto;
    }

    public String get_base_datos(){
        return base_datos;
    }

    public String get_usuario(){
        return usuario;
    }

    @Override
    public String toString(){
        return nombre + " - " + base_datos + "@" + host;
    }
}