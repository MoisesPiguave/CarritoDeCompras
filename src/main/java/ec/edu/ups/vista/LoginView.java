package ec.edu.ups.vista;

import javax.swing.*;
import java.awt.*;

public class LoginView extends JFrame {
    private JPanel panelPrincipal;
    private JPanel panelSecundario;
    private JTextField txtUsername;
    private JPasswordField txtContrasenia;
    private JButton btnIniciarSesion;
    private JButton btnRegistrarse;

    public LoginView() {
        initComponents(); // <- IMPORTANTE para evitar el error
        setContentPane(panelPrincipal);
        setTitle("Iniciar Sesión");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        // Panel principal
        panelPrincipal = new JPanel(new BorderLayout());

        // Panel secundario con campos
        panelSecundario = new JPanel(new GridLayout(3, 2, 10, 10));
        panelSecundario.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Componentes
        JLabel lblUsername = new JLabel("Usuario:");
        txtUsername = new JTextField();

        JLabel lblContrasenia = new JLabel("Contraseña:");
        txtContrasenia = new JPasswordField();

        btnIniciarSesion = new JButton("Iniciar Sesión");
        btnRegistrarse = new JButton("Registrarse");

        // Agregar componentes al panel secundario
        panelSecundario.add(lblUsername);
        panelSecundario.add(txtUsername);
        panelSecundario.add(lblContrasenia);
        panelSecundario.add(txtContrasenia);
        panelSecundario.add(btnIniciarSesion);
        panelSecundario.add(btnRegistrarse);

        // Agregar al panel principal
        panelPrincipal.add(panelSecundario, BorderLayout.CENTER);
    }

    // Getters y Setters
    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    public JPanel getPanelSecundario() {
        return panelSecundario;
    }

    public JTextField getTxtUsername() {
        return txtUsername;
    }

    public JPasswordField getTxtContrasenia() {
        return txtContrasenia;
    }

    public JButton getBtnIniciarSesion() {
        return btnIniciarSesion;
    }

    public JButton getBtnRegistrarse() {
        return btnRegistrarse;
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }
}
