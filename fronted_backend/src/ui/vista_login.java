package ui;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import servicios.servicio_conexion;

public class vista_login extends JFrame{
    private JPanel panel_fondo;
    private JPanel panel_titulo;
    private JPanel panel_login;
    private JLabel lbl_titulo;
    private JLabel lbl_subtitulo;
    private JLabel lbl_host;
    private JLabel lbl_puerto;
    private JLabel lbl_bd;
    private JLabel lbl_usuario;
    private JLabel lbl_contrasena;
    private JTextField txt_host;
    private JTextField txt_puerto;
    private JTextField txt_bd;
    private JTextField txt_usuario;
    private JPasswordField txt_contrasena;
    private JButton btn_ver_contrasena;
    private JButton btn_conectar;
    private servicio_conexion servicio;

    public vista_login(){
        servicio = new servicio_conexion();
        iniciar_componentes();
    }

    private void iniciar_componentes(){
        setTitle("Database Manager Tool - Inicio de Sesion");
        setSize(520, 455);
        setLayout(null);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel_fondo = new JPanel();
        panel_fondo.setLayout(null);
        panel_fondo.setBackground(new Color(22, 34, 57));
        panel_fondo.setBounds(0, 0, 520, 455);
        add(panel_fondo);

        panel_titulo = new JPanel();
        panel_titulo.setLayout(null);
        panel_titulo.setBackground(Color.WHITE);
        panel_titulo.setBounds(55, 30, 410, 70);
        panel_fondo.add(panel_titulo);

        lbl_titulo = new JLabel("DATABASE MANAGER TOOL");
        lbl_titulo.setBounds(0, 10, 410, 30);
        lbl_titulo.setHorizontalAlignment(JLabel.CENTER);
        lbl_titulo.setForeground(new Color(22, 34, 57));
        lbl_titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        panel_titulo.add(lbl_titulo);

        lbl_subtitulo = new JLabel("Sistema Administrador de Bases de Datos");
        lbl_subtitulo.setBounds(0, 40, 410, 20);
        lbl_subtitulo.setHorizontalAlignment(JLabel.CENTER);
        lbl_subtitulo.setForeground(new Color(90, 90, 90));
        lbl_subtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        panel_titulo.add(lbl_subtitulo);

        panel_login = new JPanel();
        panel_login.setLayout(null);
        panel_login.setBackground(new Color(245, 247, 250));
        panel_login.setBounds(55, 120, 410, 260);
        panel_fondo.add(panel_login);

        lbl_host = new JLabel("Host:");
        lbl_host.setBounds(35, 25, 120, 25);
        lbl_host.setFont(new Font("Segoe UI", Font.BOLD, 13));
        panel_login.add(lbl_host);

        txt_host = new JTextField("localhost");
        txt_host.setBounds(155, 25, 185, 28);
        panel_login.add(txt_host);

        lbl_puerto = new JLabel("Puerto:");
        lbl_puerto.setBounds(35, 62, 120, 25);
        lbl_puerto.setFont(new Font("Segoe UI", Font.BOLD, 13));
        panel_login.add(lbl_puerto);

        txt_puerto = new JTextField("3306");
        txt_puerto.setBounds(155, 62, 185, 28);
        panel_login.add(txt_puerto);

        lbl_bd = new JLabel("Base de Datos:");
        lbl_bd.setBounds(35, 99, 120, 25);
        lbl_bd.setFont(new Font("Segoe UI", Font.BOLD, 13));
        panel_login.add(lbl_bd);

        txt_bd = new JTextField("presupuesto_personal");
        txt_bd.setBounds(155, 99, 185, 28);
        panel_login.add(txt_bd);

        lbl_usuario = new JLabel("Usuario:");
        lbl_usuario.setBounds(35, 136, 120, 25);
        lbl_usuario.setFont(new Font("Segoe UI", Font.BOLD, 13));
        panel_login.add(lbl_usuario);

        txt_usuario = new JTextField("root");
        txt_usuario.setBounds(155, 136, 185, 28);
        panel_login.add(txt_usuario);

        lbl_contrasena = new JLabel("Contraseña:");
        lbl_contrasena.setBounds(35, 173, 120, 25);
        lbl_contrasena.setFont(new Font("Segoe UI", Font.BOLD, 13));
        panel_login.add(lbl_contrasena);

        txt_contrasena = new JPasswordField();
        txt_contrasena.setBounds(155, 173, 185, 28);
        panel_login.add(txt_contrasena);
        
        btn_ver_contrasena = new JButton("Ver");
        btn_ver_contrasena.setBounds(345, 173, 60, 28);
        btn_ver_contrasena.setBackground(new Color(120, 120, 120));
        btn_ver_contrasena.setForeground(Color.WHITE);
        btn_ver_contrasena.setFocusPainted(false);
        panel_login.add(btn_ver_contrasena);
        btn_ver_contrasena.addActionListener(e -> {
            if(txt_contrasena.getEchoChar() == '\u0000'){
                txt_contrasena.setEchoChar('•');
                btn_ver_contrasena.setText("Ver");
            }else{
                txt_contrasena.setEchoChar('\u0000');
                btn_ver_contrasena.setText("Ocultar");
            }
        });

        btn_conectar = new JButton("Conectar");
        btn_conectar.setBounds(155, 215, 185, 32);
        btn_conectar.setBackground(new Color(33, 150, 243));
        btn_conectar.setForeground(Color.WHITE);
        btn_conectar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn_conectar.setFocusPainted(false);
        panel_login.add(btn_conectar);

        btn_conectar.addActionListener(e -> conectar_bd());
    }

    private void conectar_bd(){
        String host = txt_host.getText();
        String puerto = txt_puerto.getText();
        String base_datos = txt_bd.getText();
        String usuario = txt_usuario.getText();
        String contrasena = new String(txt_contrasena.getPassword());
        boolean conectado = servicio.iniciar_conexion(host,puerto,base_datos,usuario,contrasena);

        if(conectado){
            JOptionPane.showMessageDialog(this,"Conexion exitosa con MySQL");
             vista_principal principal = new vista_principal(servicio.obtener_conexion_actual());
            principal.setVisible(true);
            this.dispose();
        }else{
            JOptionPane.showMessageDialog(this,"No se pudo conectar a MySQL");
        }
    }
}