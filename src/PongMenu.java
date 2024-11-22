import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// Clase principal para el menú del Pong
public class PongMenu extends JFrame {

    public final Game game;

    // Variables para almacenar el usuario, el nivel y el idioma
    public static String usuario;
    public static String nivel;
    public static String idioma = "Català";

    // Constructor para el menú del Pong
    private JRadioButton catRadioBoton;
    private JRadioButton espRadioBoton;
    private JButton jugarBoton;
    private JButton salirBoton;
    private JTextField usuarioText;
    private JTextField nivelText;

    private JLabel usuarilabelcat;

    private JLabel nivellabelcat;


    private String idiomaCat = "Català";
    private String idiomaEsp = "Español";

    public PongMenu(Game game) {

        // Título de la ventana
        super("Menú del Pong");
        this.game = game;
        // El programa se cerrará al cerrar la ventana
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Tamaño de la ventana
        setSize(400, 250);
        // Centrar la ventana en la pantalla
        setLocationRelativeTo(null);

        // Crear botones de radio para seleccionar el idioma
        catRadioBoton = new JRadioButton("Català");
        espRadioBoton = new JRadioButton("Español");
        ButtonGroup idiomaButtonGroup = new ButtonGroup();
        idiomaButtonGroup.add(catRadioBoton);
        idiomaButtonGroup.add(espRadioBoton);
        // Catalán seleccionado por defecto
        catRadioBoton.setSelected(true);

        // Botones de jugar y salir
        jugarBoton = new JButton("Jugar");
        salirBoton = new JButton("Sortir");

        // Campos de texto para el usuario y el nivel
        usuarioText = new JTextField(15);
        nivelText = new JTextField(15);

        // ActionListener para los botones de idioma
        ActionListener idiomaListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (catRadioBoton.isSelected()) {
                    idioma = idiomaCat;
                    actualizarIdioma();
                } else if (espRadioBoton.isSelected()) {
                    idioma = idiomaEsp;
                    actualizarIdioma();
                }
            }
        };
        // ActionListener para los botones de idioma
        catRadioBoton.addActionListener(idiomaListener);
        espRadioBoton.addActionListener(idiomaListener);

// ActionListener para el botón Jugar
        jugarBoton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Obtener los valores de los campos de texto de usuario y nivel
                usuario = usuarioText.getText();
                nivel = nivelText.getText();
                int nivelInt;
                try {
                    nivelInt = Integer.parseInt(nivel);
                    if (nivelInt < 1 || nivelInt > 20) {
                        // Mostrar mensaje de error en el idioma seleccionado
                        if (idioma.equals(idiomaCat)) {
                            JOptionPane.showMessageDialog(null, "Només pots introduir enters del 1 al 20");
                        } else if (idioma.equals(idiomaEsp)) {
                            JOptionPane.showMessageDialog(null, "Solo puedes introducir enteros del 1 al 20");
                        }
                    } else {
                        // Mostrar el usuario y el nivel en la consola
                        JOptionPane.showMessageDialog(null, "Nom d'usuari: " + usuario + " Nivell seleccionat: " + nivel);
                        JOptionPane.showMessageDialog(null, "Fes que la pilota reboti a la paret del frontó i torni a ser rebutjada abans que toqui el terra.");
                        game.setUsuario(usuario);
                        game.layout.setNivel(Integer.parseInt(nivel));
                        game.idioma = idioma;
                        game.setMenuFi(true);
                        dispose();
                    }
                } catch (NumberFormatException exception) {
                    // Mostrar mensaje de error en el idioma seleccionado
                    if (idioma.equals(idiomaCat)) {
                        JOptionPane.showMessageDialog(null, "ERROR. Només es poden introduir nombres enters.");
                    } else if (idioma.equals(idiomaEsp)) {
                        JOptionPane.showMessageDialog(null, "ERROR. solo puedes introducir numeros enteros.");
                    }
                    // Mostrar la ventana del menú nuevamente
                    setVisible(true);
                }
            }
        });

        // ActionListener para el botón Salir
        salirBoton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Salir del programa al hacer clic en el botón Salir
                System.exit(0);
            }
        });

        // Configurar el diseño del menú en catalán
        JPanel mainPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.add(new JLabel("Idioma:"));
        mainPanel.add(catRadioBoton);
        mainPanel.add(new JLabel(""));
        mainPanel.add(espRadioBoton);
        usuarilabelcat = new JLabel("Usuari:");
        mainPanel.add(usuarilabelcat);
        mainPanel.add(usuarioText);
        nivellabelcat = new JLabel("Nivell:");
        mainPanel.add(nivellabelcat);
        mainPanel.add(nivelText);
        mainPanel.add(jugarBoton);
        mainPanel.add(salirBoton);

        getContentPane().add(mainPanel, BorderLayout.CENTER);
    }

    // Método para actualizar el idioma de los componentes
// Método para actualizar el idioma de los componentes
    private void actualizarIdioma() {
        // Actualizar los textos de los componentes según el idioma seleccionado
        if (idioma.equals(idiomaCat)) {
            jugarBoton.setText("Jugar");
            salirBoton.setText("Sortir");
            usuarilabelcat.setText("Usuari:");
            nivellabelcat.setText("Nivell:");
            getContentPane().validate();
            getContentPane().repaint();
        } else if (idioma.equals(idiomaEsp)) {
            usuarilabelcat.setText("Usuario");
            nivellabelcat.setText("Nivel");
            jugarBoton.setText("Jugar");
            salirBoton.setText("Salir");
            getContentPane().validate();
            getContentPane().repaint();
        }
    }

}
