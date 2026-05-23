package servicios;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import javax.swing.table.DefaultTableModel;

public class servicio_sql{
    private Connection conexion;

    public servicio_sql(Connection conexion){
        this.conexion = conexion;
    }

    public DefaultTableModel ejecutar_select(String sql){
        DefaultTableModel modelo = new DefaultTableModel();

        try{
            Statement stmt = conexion.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
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

        }catch(Exception e){
            System.out.println("Error al ejecutar SELECT");
            System.out.println(e.getMessage());
        }

        return modelo;
    }
}