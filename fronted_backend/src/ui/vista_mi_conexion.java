package ui;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import modelos.conexion_guardada;
import servicios.servicio_conexiones_guardadas;

public class vista_mi_conexion extends JPanel{
    private JLabel lbl_titulo;
    private JLabel lbl_nombre;
    private JLabel lbl_host;
    private JLabel lbl_puerto;
    private JLabel lbl_bd;
    private JLabel lbl_usuario;
    private JButton btn_eliminar;
    private JButton btn_cerrar_sesion;
    private conexion_guardada conexion_actual;
    private vista_principal ventana_principal;

    public vista_mi_conexion(conexion_guardada conexion_actual,vista_principal ventana_principal){
        this.conexion_actual = conexion_actual;
        this.ventana_principal = ventana_principal;
        iniciar_componentes();
    }

    private void iniciar_componentes(){
        setLayout(null);
        setBackground(new Color(15,23,42));

        lbl_titulo = new JLabel("MI CONEXION ACTUAL");
        lbl_titulo.setBounds(25,25,400,30);
        lbl_titulo.setForeground(Color.WHITE);
        lbl_titulo.setFont(new Font("Segoe UI",Font.BOLD,22));
        add(lbl_titulo);

        lbl_nombre = new JLabel("Nombre: " + conexion_actual.get_nombre());
        lbl_nombre.setBounds(35,90,500,25);
        preparar_label(lbl_nombre);
        add(lbl_nombre);

        lbl_host = new JLabel("Host: " + conexion_actual.get_host());
        lbl_host.setBounds(35,125,500,25);
        preparar_label(lbl_host);
        add(lbl_host);

        lbl_puerto = new JLabel("Puerto: " + conexion_actual.get_puerto());
        lbl_puerto.setBounds(35,160,500,25);
        preparar_label(lbl_puerto);
        add(lbl_puerto);

        lbl_bd = new JLabel("Base de Datos: " + conexion_actual.get_base_datos());
        lbl_bd.setBounds(35,195,500,25);
        preparar_label(lbl_bd);
        add(lbl_bd);

        lbl_usuario = new JLabel("Usuario: " + conexion_actual.get_usuario());
        lbl_usuario.setBounds(35,230,500,25);
        preparar_label(lbl_usuario);
        add(lbl_usuario);

        btn_eliminar = new JButton("Eliminar conexion actual");
        btn_eliminar.setBounds(35,290,220,35);
        btn_eliminar.setBackground(new Color(37,99,235));
        btn_eliminar.setForeground(Color.WHITE);
        btn_eliminar.setFocusPainted(false);
        btn_eliminar.setBorderPainted(false);
        add(btn_eliminar);
        btn_eliminar.addActionListener(e -> eliminar_conexion_actual());
        
        btn_cerrar_sesion = new JButton("Cerrar sesion");
        btn_cerrar_sesion.setBounds(275,290,180,35);
        btn_cerrar_sesion.setBackground(new Color(37,99,235));
        btn_cerrar_sesion.setForeground(Color.WHITE);
        btn_cerrar_sesion.setFocusPainted(false);
        btn_cerrar_sesion.setBorderPainted(false);
        add(btn_cerrar_sesion);
        btn_cerrar_sesion.addActionListener(e -> cerrar_sesion());
    }

    private void preparar_label(JLabel label){
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Segoe UI",Font.PLAIN,15));
    }

    private void eliminar_conexion_actual(){
        int opcion = JOptionPane.showConfirmDialog(
            this,
            "¿Desea eliminar esta conexion guardada?",
            "Confirmar",
            JOptionPane.YES_NO_OPTION
        );

        if(opcion == JOptionPane.YES_OPTION){
            servicio_conexiones_guardadas.eliminar_conexion(conexion_actual);
            JOptionPane.showMessageDialog(this,"Conexion eliminada correctamente");
            ventana_principal.dispose();
            vista_login login = new vista_login();
            login.setVisible(true);
        }
    }
    
    private void cerrar_sesion(){
        ventana_principal.dispose();

        vista_login login = new vista_login();
        login.setVisible(true);
    }
}