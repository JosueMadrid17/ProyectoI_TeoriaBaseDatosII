package ui;
import conexion.conexion_bd;
import servicios.servicio_sql;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class vista_crear_objetos extends JPanel{
    private JLabel lbl_titulo;
    private JLabel lbl_tipo;
    private JLabel lbl_nombre;
    private JLabel lbl_columnas;
    private JLabel lbl_sql;
    private JComboBox<String> cmb_tipo;
    private JTextField txt_nombre;
    private JTable tabla_columnas;
    private DefaultTableModel modelo_columnas;
    private JScrollPane scroll_columnas;
    private JTextArea txt_sql_generado;
    private JScrollPane scroll_sql;
    private JButton btn_agregar_columna;
    private JButton btn_eliminar_columna;
    private JButton btn_generar_sql;
    private JButton btn_ejecutar_sql;
    private JButton btn_limpiar;
    private conexion_bd conexion_actual;
    private servicio_sql servicio_sql;

    public vista_crear_objetos(conexion_bd conexion_actual){
        this.conexion_actual = conexion_actual;
        this.servicio_sql = new servicio_sql(conexion_actual.obtener_conexion());
        iniciar_componentes();
    }

    private void iniciar_componentes(){
        setLayout(null);
        setBackground(new Color(15,23,42));

        lbl_titulo = new JLabel("CREACION VISUAL DE OBJETOS");
        lbl_titulo.setBounds(25,20,500,30);
        lbl_titulo.setForeground(new Color(240,240,240));
        lbl_titulo.setFont(new Font("Segoe UI",Font.BOLD,22));
        add(lbl_titulo);

        lbl_tipo = new JLabel("Tipo de objeto:");
        lbl_tipo.setBounds(25,75,150,25);
        lbl_tipo.setForeground(Color.WHITE);
        lbl_tipo.setFont(new Font("Segoe UI",Font.BOLD,13));
        add(lbl_tipo);

        cmb_tipo = new JComboBox<>();
        cmb_tipo.addItem("Tabla");
        cmb_tipo.addItem("Vista");
        cmb_tipo.setBounds(150,75,180,28);
        add(cmb_tipo);

        lbl_nombre = new JLabel("Nombre:");
        lbl_nombre.setBounds(360,75,100,25);
        lbl_nombre.setForeground(Color.WHITE);
        lbl_nombre.setFont(new Font("Segoe UI",Font.BOLD,13));
        add(lbl_nombre);

        txt_nombre = new JTextField();
        txt_nombre.setBounds(430,75,260,28);
        add(txt_nombre);

        lbl_columnas = new JLabel("Columnas / Definicion Visual");
        lbl_columnas.setBounds(25,125,300,25);
        lbl_columnas.setForeground(Color.WHITE);
        lbl_columnas.setFont(new Font("Segoe UI",Font.BOLD,14));
        add(lbl_columnas);

        modelo_columnas = new DefaultTableModel();
        modelo_columnas.addColumn("Nombre Columna");
        modelo_columnas.addColumn("Tipo Dato");
        modelo_columnas.addColumn("PK");
        modelo_columnas.addColumn("AI");
        modelo_columnas.addColumn("NOT NULL");

        tabla_columnas = new JTable(modelo_columnas);
        tabla_columnas.setFillsViewportHeight(true);
        tabla_columnas.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        scroll_columnas = new JScrollPane(tabla_columnas);
        scroll_columnas.setBounds(25,155,760,180);
        scroll_columnas.getViewport().setBackground(Color.WHITE);
        add(scroll_columnas);

        btn_agregar_columna = new JButton("Agregar Columna");
        btn_agregar_columna.setBounds(805,155,160,35);
        configurar_boton(btn_agregar_columna);
        add(btn_agregar_columna);

        btn_eliminar_columna = new JButton("Eliminar Columna");
        btn_eliminar_columna.setBounds(805,205,160,35);
        configurar_boton(btn_eliminar_columna);
        add(btn_eliminar_columna);

        btn_generar_sql = new JButton("Generar SQL");
        btn_generar_sql.setBounds(805,255,160,35);
        configurar_boton(btn_generar_sql);
        add(btn_generar_sql);

        btn_ejecutar_sql = new JButton("Ejecutar SQL");
        btn_ejecutar_sql.setBounds(805,305,160,35);
        configurar_boton(btn_ejecutar_sql);
        add(btn_ejecutar_sql);

        lbl_sql = new JLabel("SQL Generado");
        lbl_sql.setBounds(25,360,200,25);
        lbl_sql.setForeground(Color.WHITE);
        lbl_sql.setFont(new Font("Segoe UI",Font.BOLD,14));
        add(lbl_sql);

        txt_sql_generado = new JTextArea();
        txt_sql_generado.setFont(new Font("Consolas",Font.PLAIN,14));

        scroll_sql = new JScrollPane(txt_sql_generado);
        scroll_sql.setBounds(25,390,940,210);
        scroll_sql.getViewport().setBackground(Color.WHITE);
        add(scroll_sql);

        btn_limpiar = new JButton("Limpiar");
        btn_limpiar.setBounds(25,620,150,35);
        configurar_boton(btn_limpiar);
        add(btn_limpiar);

        btn_agregar_columna.addActionListener(e -> agregar_columna());
        btn_eliminar_columna.addActionListener(e -> eliminar_columna());
        btn_generar_sql.addActionListener(e -> generar_sql());
        btn_ejecutar_sql.addActionListener(e -> ejecutar_sql());
        btn_limpiar.addActionListener(e -> limpiar());
    }

    private void configurar_boton(JButton boton){
        boton.setBackground(new Color(37,99,235));
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
        boton.setFont(new Font("Segoe UI",Font.BOLD,13));
    }

    private void agregar_columna(){
        modelo_columnas.addRow(new Object[]{"nombre_columna","varchar(100)","NO","NO","NO"});
    }

    private void eliminar_columna(){
        detener_edicion_tabla();
        int fila = tabla_columnas.getSelectedRow();
        if(fila == -1){
            JOptionPane.showMessageDialog(this,"Seleccione una columna para eliminar");
            return;
        }
        modelo_columnas.removeRow(fila);
    }

    private void generar_sql(){
        detener_edicion_tabla();
        String tipo = cmb_tipo.getSelectedItem().toString();
        String nombre = txt_nombre.getText().trim();

        if(nombre.isEmpty()){
            JOptionPane.showMessageDialog(this,"Ingrese el nombre del objeto");
            return;
        }

        if(tipo.equals("Tabla")){
            generar_create_table(nombre);
        }else{
            generar_create_view(nombre);
        }
    }

    private void generar_create_table(String nombre_tabla){
        if(modelo_columnas.getRowCount() == 0){
            JOptionPane.showMessageDialog(this,"Agregue al menos una columna");
            return;
        }

        StringBuilder sql = new StringBuilder();
        sql.append("CREATE TABLE ").append(nombre_tabla).append(" (\n");

        for(int i = 0; i < modelo_columnas.getRowCount(); i++){
            String nombre_columna = modelo_columnas.getValueAt(i,0).toString();
            String tipo_dato = modelo_columnas.getValueAt(i,1).toString();
            String pk = modelo_columnas.getValueAt(i,2).toString();
            String ai = modelo_columnas.getValueAt(i,3).toString();
            String not_null = modelo_columnas.getValueAt(i,4).toString();

            sql.append("    ").append(nombre_columna).append(" ").append(tipo_dato);

            if(not_null.equalsIgnoreCase("SI")){
                sql.append(" NOT NULL");
            }

            if(ai.equalsIgnoreCase("SI")){
                sql.append(" AUTO_INCREMENT");
            }

            if(pk.equalsIgnoreCase("SI")){
                sql.append(" PRIMARY KEY");
            }

            if(i < modelo_columnas.getRowCount() - 1){
                sql.append(",");
            }

            sql.append("\n");
        }
        sql.append(");");
        txt_sql_generado.setText(sql.toString());
    }

    private void generar_create_view(String nombre_vista){
        String sql = "";
        sql += "CREATE VIEW " + nombre_vista + " AS\n";
        sql += "SELECT columna1, columna2\n";
        sql += "FROM nombre_tabla;";

        txt_sql_generado.setText(sql);
        JOptionPane.showMessageDialog(this,"Edite el SELECT generado antes de ejecutar la vista");
    }

    private void ejecutar_sql(){
        detener_edicion_tabla();
        String sql = txt_sql_generado.getText().trim();

        if(sql.isEmpty()){
            JOptionPane.showMessageDialog(this,"Primero genere o escriba el SQL");
            return;
        }
        servicio_sql.ejecutar_sql(sql);
        JOptionPane.showMessageDialog(this,servicio_sql.obtener_mensaje());
    }

    private void limpiar(){
        detener_edicion_tabla();
        txt_nombre.setText("");
        txt_sql_generado.setText("");
        modelo_columnas.setRowCount(0);
    }
    
    private void detener_edicion_tabla(){
        if(tabla_columnas.isEditing()){
            tabla_columnas.getCellEditor().stopCellEditing();
        }
    }
}