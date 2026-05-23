package servicios;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import javax.swing.table.DefaultTableModel;

public class servicio_sql{
    private Connection conexion;
    private String mensaje;

    public servicio_sql(Connection conexion){
        this.conexion = conexion;
    }

    public DefaultTableModel ejecutar_sql(String sql){
        DefaultTableModel modelo = new DefaultTableModel();
        mensaje = "";

        try{
            Statement stmt = conexion.createStatement();
            boolean tiene_resultados = stmt.execute(sql);
            if(tiene_resultados){

                ResultSet rs = stmt.getResultSet();
                ResultSetMetaData metadata = rs.getMetaData();

                int columnas = metadata.getColumnCount();

                for(int i = 1; i <= columnas; i++){
                    modelo.addColumn(metadata.getColumnName(i));
                }

                while(rs.next()){
                    Object[] fila = new Object[columnas];
                    for(int i = 1; i <= columnas; i++){
                        fila[i - 1] = rs.getObject(i);
                    }
                    modelo.addRow(fila);
                }
                mensaje = "Consulta ejecutada correctamente";
            }else{
                int filas_afectadas = stmt.getUpdateCount();
                mensaje = "Sentencia ejecutada correctamente. Filas afectadas: " + filas_afectadas;
            }
        }catch(Exception e){
            mensaje = "Error al ejecutar SQL: " + e.getMessage();
            System.out.println(mensaje);
        }
        return modelo;
    }

    public String obtener_mensaje(){
        return mensaje;
    }
}