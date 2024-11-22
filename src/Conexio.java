import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public class Conexio {
    Connection con;
    Statement st;
    ResultSet rs;
    private final Game game;

    public Conexio(Game game) {
        this.game = game;
    }

    public void abrirConexio() {
        try {
            String userName = "root";
            String password = "root";
            String url = "jdbc:mysql://localhost/pong";
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            con = DriverManager.getConnection(url, userName, password);
            System.out.println("Conexión a la BD");
        } catch (Exception e) {
            System.out.println("Error en conexión ");
        }
    }

    public void obtenerTablaConexio() {
        try {
            st = con.createStatement();
            rs = st.executeQuery("SELECT * FROM pong.puntuacion ORDER BY SCORE DESC");
            System.out.println("Tabla abierta");
        } catch (Exception e) {
            System.out.println("Error al Abrir tabla ");
        }
    }

    public void insertarConexio(String nombre, int score, String idioma, String fecha) {
        try {
            Statement s1 = con.createStatement();
            s1.executeUpdate(
                    "INSERT INTO puntuacion (id, nom, score, idioma, data) values " +
                            "(" + "DEFAULT" + ", '" + nombre + "', '" + score + "', '" + idioma + "', '" + fecha + "'" + ")");
            System.out.println("Elemento insertado");
        } catch (Exception o) {
            System.out.println("Error al insertar ");
        }
    }

    public void mostrarDatosConexio() throws InterruptedException {

        int contador = 0;
        JFrame frame2 = new JFrame("Tablero de Puntuaciones");
        String registros[][]=new String[10][4];

        try {
            while (contador<10 && rs.next()) {
                String Nom = rs.getString("nom");
                int Score = rs.getInt("score");
                String Idioma = rs.getString("idioma");
                Date data = rs.getDate("data");

                System.out.println(Nom+" "+Score+" "+Idioma+" "+data);
                registros[contador][0]=Nom;
                registros[contador][1]=String.valueOf(Score);
                registros[contador][2]=Idioma;
                registros[contador][3]=String.valueOf(data);
                contador++;
            }

            //Crea una nueva ventana y un JPanel llamado TablaPuntos
            TablaPuntuacion tablaPuntos = new TablaPuntuacion(game);
            frame2.add(tablaPuntos);
            frame2.setSize(Ventana.FRAME_WIDTH + 12, Ventana.FRAME_HEIGHT + 5);
            frame2.setVisible(true);
            frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            //Le pasa los datos y repinta cada vez
            tablaPuntos.actualizarDatos(registros);
            tablaPuntos.repaint();

        } catch (Exception e) {
            System.out.println("Error al visualizar datos");
        }
        //Descansa 10 segundos y se cierra
        Thread.sleep(10000);
        frame2.dispose();
    }

    //Para cerrar la conexión una vez terminadas las consultas
    public void cerrarConexio() {
        try {
            con.close();
            System.out.println("Conexión cerrada");
        } catch (Exception e) {
            System.out.println("Error al cerrar conexión");
        }
    }
}
