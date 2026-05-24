package ui;
import conexion.conexion_bd;
import servicios.servicio_metadata;
import servicios.servicio_sql;
import servicios.servicio_ddl;
import ui.vista_crear_objetos;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JOptionPane;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;
import javax.swing.JFileChooser;
import java.io.FileWriter;

public class vista_principal extends JFrame{
    private JPanel panel_fondo;
    private JTabbedPane panel_pestanas;
    private JLabel lbl_titulo;
    private JTree arbol_objetos;
    private JScrollPane scroll_arbol;
    private JTextArea txt_sql;
    private JScrollPane scroll_sql;
    private JTable tabla_resultados;
    private JScrollPane scroll_tabla;
    private JButton btn_ejecutar;
    private JButton btn_refrescar;
    private JButton btn_ddl;
    private JButton btn_limpiar;
    private JButton btn_exportar;
    private conexion_bd conexion_actual;
    private servicio_metadata metadata;
    private servicio_ddl servicio_ddl;
    private servicio_sql servicio_sql;

    public vista_principal(conexion_bd conexion_actual){
        this.conexion_actual = conexion_actual;
        this.metadata = new servicio_metadata(conexion_actual.obtener_conexion());
        this.servicio_sql = new servicio_sql(conexion_actual.obtener_conexion());
        this.servicio_ddl = new servicio_ddl(conexion_actual.obtener_conexion());
        iniciar_componentes();
        cargar_objetos();
    }

    private void iniciar_componentes(){
        setTitle("Database Manager Tool - Panel Principal");
        setSize(1000, 650);
        setMinimumSize(new java.awt.Dimension(1000,650));
        setLayout(null);
        getContentPane().setBackground(new Color(15,23,42));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel_pestanas = new JTabbedPane();
        panel_pestanas.setBackground(new Color(15,23,42));
        add(panel_pestanas);
        panel_fondo = new JPanel();
        panel_fondo.setLayout(null);
        panel_fondo.setBackground(new Color(15,23,42));
        panel_pestanas.addTab("SQL Editor",panel_fondo);
        panel_pestanas.addTab("Crear Objetos",new vista_crear_objetos(conexion_actual));
        panel_fondo.setBounds(0,0,getWidth(),getHeight());

        lbl_titulo = new JLabel("DATABASE MANAGER TOOL");
        lbl_titulo.setForeground(new Color(240,240,240));
        lbl_titulo.setFont(new Font("Segoe UI",Font.BOLD,22));
        panel_fondo.add(lbl_titulo);

        arbol_objetos = new JTree(new DefaultMutableTreeNode("Base de Datos"));
        arbol_objetos.setBackground(Color.WHITE);
        arbol_objetos.setOpaque(true);
        arbol_objetos.setForeground(Color.BLACK);
        scroll_arbol = new JScrollPane(arbol_objetos);
        scroll_arbol.setBackground(Color.WHITE);
        scroll_arbol.getViewport().setBackground(Color.WHITE);
        panel_fondo.add(scroll_arbol);

        txt_sql = new JTextArea();
        txt_sql.setFont(new Font("Consolas",Font.PLAIN,14));
        txt_sql.setBackground(Color.WHITE);
        txt_sql.setOpaque(true);
        scroll_sql = new JScrollPane(txt_sql);
        scroll_sql.setBackground(Color.WHITE);
        scroll_sql.getViewport().setBackground(Color.WHITE);
        panel_fondo.add(scroll_sql);

        btn_ejecutar = new JButton("Ejecutar SQL");
        btn_ejecutar.setBackground(new Color(37,99,235));
        btn_ejecutar.setForeground(Color.WHITE);
        btn_ejecutar.setFocusPainted(false);
        panel_fondo.add(btn_ejecutar);
        btn_ejecutar.addActionListener(e -> ejecutar_sql());
        
        btn_refrescar = new JButton("Refrescar");
        btn_refrescar.setBackground(new Color(37,99,235));
        btn_refrescar.setForeground(Color.WHITE);
        btn_refrescar.setFocusPainted(false);
        panel_fondo.add(btn_refrescar);
        btn_refrescar.addActionListener(e -> cargar_objetos());
        
        btn_ddl = new JButton("Generar DDL");
        btn_ddl.setBackground(new Color(37,99,235));
        btn_ddl.setForeground(Color.WHITE);
        btn_ddl.setFocusPainted(false);
        panel_fondo.add(btn_ddl);
        btn_ddl.addActionListener(e -> generar_ddl());
        
        btn_limpiar = new JButton("Limpiar");
        btn_limpiar.setBackground(new Color(37,99,235));
        btn_limpiar.setForeground(Color.WHITE);
        btn_limpiar.setFocusPainted(false);
        btn_limpiar.setBorderPainted(false);
        panel_fondo.add(btn_limpiar);
        btn_limpiar.addActionListener(e -> limpiar_editor());
        
        btn_exportar = new JButton("Exportar SQL");
        btn_exportar.setBackground(new Color(37,99,235));
        btn_exportar.setForeground(Color.WHITE);
        btn_exportar.setFocusPainted(false);
        btn_exportar.setBorderPainted(false);
        panel_fondo.add(btn_exportar);
        btn_exportar.addActionListener(e -> exportar_sql());
        
        arbol_objetos.addTreeSelectionListener(new TreeSelectionListener(){
            @Override
            public void valueChanged(TreeSelectionEvent e){
                seleccionar_objeto_arbol();
            }
        });

        tabla_resultados = new JTable();
        tabla_resultados.setBackground(Color.WHITE);
        tabla_resultados.setOpaque(true);
        tabla_resultados.setFillsViewportHeight(true);
        tabla_resultados.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        scroll_tabla = new JScrollPane(tabla_resultados);
        scroll_tabla.setBackground(Color.WHITE);
        scroll_tabla.getViewport().setBackground(Color.WHITE);
        panel_fondo.add(scroll_tabla);

        addComponentListener(new ComponentAdapter(){
            @Override
            public void componentResized(ComponentEvent e){
                ajustar_componentes();
            }
        });
        ajustar_componentes();
    }

    private void ajustar_componentes(){
        int ancho_frame = getContentPane().getWidth();
        int alto_frame = getContentPane().getHeight();

        panel_pestanas.setBounds(0,0,ancho_frame,alto_frame);

        int ancho = panel_pestanas.getWidth();
        int alto = panel_pestanas.getHeight() - 30;
        lbl_titulo.setBounds(20,15,500,30);

        int margen = 20;
        int ancho_arbol = 270;
        int espacio = 20;

        int x_derecha = margen + ancho_arbol + espacio;
        int ancho_derecha = ancho - x_derecha - margen;

        int y_inicio = 70;

        int alto_editor = (int)(alto * 0.38);
        int y_botones = y_inicio + alto_editor + 15;
        int y_tabla = y_botones + 55;
        int alto_tabla = alto - y_tabla - 45;

        scroll_arbol.setBounds(
            margen,
            y_inicio,
            ancho_arbol,
            alto - 110
        );

        scroll_sql.setBounds(
            x_derecha,
            y_inicio,
            ancho_derecha,
            alto_editor
        );

        btn_ejecutar.setBounds(x_derecha,y_botones,150,35);
        btn_refrescar.setBounds(
            x_derecha + 165,
            y_botones,
            140,
            35
        );
        btn_ddl.setBounds(
            x_derecha + 320,
            y_botones,
            160,
            35
        );
        btn_limpiar.setBounds(
            x_derecha + 495,
            y_botones,
            130,
            35
        );
        btn_exportar.setBounds(
            x_derecha + 640,
            y_botones,
            150,
            35
        );
        scroll_tabla.setBounds(
            x_derecha,
            y_tabla,
            ancho_derecha,
            alto_tabla
        );
    }

    private void cargar_objetos(){
        DefaultMutableTreeNode raiz = new DefaultMutableTreeNode("Objetos de Base de Datos");
        DefaultMutableTreeNode nodo_tablas = new DefaultMutableTreeNode("Tablas");
        DefaultMutableTreeNode nodo_vistas = new DefaultMutableTreeNode("Vistas");
        DefaultMutableTreeNode nodo_procedimientos = new DefaultMutableTreeNode("Procedimientos");
        DefaultMutableTreeNode nodo_funciones = new DefaultMutableTreeNode("Funciones");
        DefaultMutableTreeNode nodo_triggers = new DefaultMutableTreeNode("Triggers");
        DefaultMutableTreeNode nodo_indices = new DefaultMutableTreeNode("Indices");
        DefaultMutableTreeNode nodo_usuarios = new DefaultMutableTreeNode("Usuarios");

        ArrayList<String> tablas = metadata.listar_tablas();
        ArrayList<String> vistas = metadata.listar_vistas();
        ArrayList<String> procedimientos = metadata.listar_procedimientos();
        ArrayList<String> funciones = metadata.listar_funciones();
        ArrayList<String> triggers = metadata.listar_triggers();
        ArrayList<String> indices = metadata.listar_indices();
        ArrayList<String> usuarios = metadata.listar_usuarios();

        for(String tabla: tablas){
            nodo_tablas.add(new DefaultMutableTreeNode(tabla));
        }

        for(String vista: vistas){
            nodo_vistas.add(new DefaultMutableTreeNode(vista));
        }

        for(String procedimiento: procedimientos){
            nodo_procedimientos.add(new DefaultMutableTreeNode(procedimiento));
        }

        for(String funcion: funciones){
            nodo_funciones.add(new DefaultMutableTreeNode(funcion));
        }

        for(String trigger: triggers){
            nodo_triggers.add(new DefaultMutableTreeNode(trigger));
        }

        for(String indice: indices){
            nodo_indices.add(new DefaultMutableTreeNode(indice));
        }

        for(String usuario: usuarios){
            nodo_usuarios.add(new DefaultMutableTreeNode(usuario));
        }

        raiz.add(nodo_tablas);
        raiz.add(nodo_vistas);
        raiz.add(nodo_procedimientos);
        raiz.add(nodo_funciones);
        raiz.add(nodo_triggers);
        raiz.add(nodo_indices);
        raiz.add(nodo_usuarios);
        arbol_objetos.setModel(new javax.swing.tree.DefaultTreeModel(raiz));
    }

    private void ejecutar_sql(){
        String sql = txt_sql.getText().trim();

        if(sql.isEmpty()){
            JOptionPane.showMessageDialog(this,"Escriba una sentencia SQL");
            return;
        }
        
        tabla_resultados.setModel(servicio_sql.ejecutar_sql(sql));
        
        for(int i = 0; i < tabla_resultados.getColumnCount(); i++){
            tabla_resultados.getColumnModel().getColumn(i).setPreferredWidth(150);
        }
        JOptionPane.showMessageDialog(this,servicio_sql.obtener_mensaje());
        cargar_objetos();
    }
    
    private void seleccionar_objeto_arbol(){
        TreePath ruta = arbol_objetos.getSelectionPath();

        if(ruta == null){
            return;
        }
        Object[] partes = ruta.getPath();

        if(partes.length < 3){
            return;
        }
        String tipo_objeto = partes[1].toString();
        String nombre_objeto = partes[2].toString();

        if(tipo_objeto.equals("Tablas")){
            txt_sql.setText("show columns from " + nombre_objeto + ";");
            tabla_resultados.setModel(servicio_sql.ejecutar_sql("show columns from " + nombre_objeto));
        }else if(tipo_objeto.equals("Vistas")){
            txt_sql.setText("show columns from " + nombre_objeto + ";");
            tabla_resultados.setModel(servicio_sql.ejecutar_sql("show columns from " + nombre_objeto));
        }else if(tipo_objeto.equals("Indices")){
            JOptionPane.showMessageDialog(this,nombre_objeto);
        }else if(tipo_objeto.equals("Usuarios")){
            txt_sql.setText("select user,host from mysql.user;");
            tabla_resultados.setModel(servicio_sql.ejecutar_sql("select user,host from mysql.user"));
        }
        for(int i = 0; i < tabla_resultados.getColumnCount(); i++){
            tabla_resultados.getColumnModel().getColumn(i).setPreferredWidth(150);
        }
    }
    
    private void generar_ddl(){
        TreePath ruta = arbol_objetos.getSelectionPath();
        
        if(ruta == null){
            JOptionPane.showMessageDialog(this,"Por favor, seleccione una tabla o vista");
            return;
        }
        Object[] partes = ruta.getPath();

        if(partes.length < 3){
            JOptionPane.showMessageDialog(this,"Por favor, seleccione un objeto valido");
            return;
        }

        String tipo_objeto = partes[1].toString();
        String nombre_objeto = partes[2].toString();
        String ddl = "";

        if(tipo_objeto.equals("Tablas")){
            ddl = servicio_ddl.obtener_ddl_tabla(nombre_objeto);
        }else if(tipo_objeto.equals("Vistas")){
            ddl = servicio_ddl.obtener_ddl_vista(nombre_objeto);
        }else{
            JOptionPane.showMessageDialog(this,"DDL disponible solo para tablas y vistas");
            return;
        }
        
        if(ddl.toUpperCase().contains("CREATE ALGORITHM")){
        ddl = ddl.replace(" VIEW ", "\nVIEW ");
        ddl = ddl.replace(" AS select ", "\nAS\nSELECT\n    ");
        ddl = ddl.replace(" from ", "\nFROM ");
        ddl = ddl.replace(",", ",\n    ");
        }
        txt_sql.setText(ddl);
        tabla_resultados.setModel(new javax.swing.table.DefaultTableModel());
    }
    
    private void limpiar_editor(){
        txt_sql.setText("");
    }
    
    private void exportar_sql(){
        String sql = txt_sql.getText().trim();
        if(sql.isEmpty()){
            JOptionPane.showMessageDialog(this,"No hay SQL para exportar");
            return;
        }
        try{
            JFileChooser selector = new JFileChooser();
            selector.setDialogTitle("Guardar archivo SQL");
            int opcion = selector.showSaveDialog(this);
            if(opcion == JFileChooser.APPROVE_OPTION){
                String ruta = selector.getSelectedFile().getAbsolutePath();

                if(!ruta.endsWith(".sql")){
                    ruta += ".sql";
                }
                FileWriter writer = new FileWriter(ruta);
                writer.write(sql);
                writer.close();
                JOptionPane.showMessageDialog(
                    this,
                    "SQL exportado correctamente"
                );
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(
                this,
                "Error al exportar SQL: " + e.getMessage()
            );
        }
    }
}