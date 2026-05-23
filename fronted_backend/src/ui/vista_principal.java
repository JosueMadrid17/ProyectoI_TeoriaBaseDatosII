package ui;
import conexion.conexion_bd;
import servicios.servicio_metadata;
import servicios.servicio_sql;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JOptionPane;
import javax.swing.tree.DefaultMutableTreeNode;

public class vista_principal extends JFrame{
    private JPanel panel_fondo;
    private JLabel lbl_titulo;
    private JTree arbol_objetos;
    private JScrollPane scroll_arbol;
    private JTextArea txt_sql;
    private JScrollPane scroll_sql;
    private JTable tabla_resultados;
    private JScrollPane scroll_tabla;
    private JButton btn_ejecutar;
    private JButton btn_refrescar;
    private conexion_bd conexion_actual;
    private servicio_metadata metadata;
    private servicio_sql servicio_sql;

    public vista_principal(conexion_bd conexion_actual){
        this.conexion_actual = conexion_actual;
        this.metadata = new servicio_metadata(conexion_actual.obtener_conexion());
        this.servicio_sql = new servicio_sql(conexion_actual.obtener_conexion());
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

        panel_fondo = new JPanel();
        panel_fondo.setLayout(null);
        panel_fondo.setBackground(new Color(15,23,42));
        add(panel_fondo);

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
        btn_ejecutar.setBackground(new Color(33,150,243));
        btn_ejecutar.setForeground(Color.WHITE);
        btn_ejecutar.setFocusPainted(false);
        panel_fondo.add(btn_ejecutar);
        btn_ejecutar.addActionListener(e -> ejecutar_sql());
        
        btn_refrescar = new JButton("Refrescar");
        btn_refrescar.setBackground(new Color(16,185,129));
        btn_refrescar.setForeground(Color.WHITE);
        btn_refrescar.setFocusPainted(false);
        panel_fondo.add(btn_refrescar);

        btn_refrescar.addActionListener(e -> cargar_objetos());

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
        int ancho = getContentPane().getWidth();
        int alto = getContentPane().getHeight();

        panel_fondo.setBounds(0,0,ancho,alto);
        lbl_titulo.setBounds(20,15,500,30);

        int margen = 20;
        int ancho_arbol = 270;
        int espacio = 20;
        int x_derecha = margen + ancho_arbol + espacio;
        int ancho_derecha = ancho - x_derecha - margen;
        int alto_editor = 180;
        int y_inicio = 60;

        scroll_arbol.setBounds(margen,y_inicio,ancho_arbol,alto - 120);
        scroll_sql.setBounds(x_derecha,y_inicio,ancho_derecha,alto_editor);
        btn_ejecutar.setBounds(x_derecha,y_inicio + alto_editor + 12,150,35);
        btn_refrescar.setBounds(x_derecha + 165,y_inicio + alto_editor + 12,130,35);
        scroll_tabla.setBounds(x_derecha,y_inicio + alto_editor + 60,ancho_derecha,alto - (y_inicio + alto_editor + 120));
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
}