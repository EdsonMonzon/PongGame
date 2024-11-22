import javax.swing.*;
import java.awt.*;
import java.util.Random;

/**
 * La clase Layout contiene los puntos y el nivel del juego.
 */
public class Layout {
    private int score = 0; // Puntuación del juego
    private int nivel = 0; // Nivel del juego
    private final Game game; // Instancia del juego
    final int CUADROS_POR_PUNTO = 100; // Cuadros por punto
    final int PUNTOS_POR_NIVEL = 20; // Puntos por nivel
    final private Random random = new Random(); // Generador de números aleatorios
    final String[] mensajes = { // Mensajes que se muestran durante el juego
            "El piloto del escudo es nuestra ultima esperanza",
            "Cuanto tiempo dices que nesecita repeler el meteorito",
            "De verdad nuestras vidas dependende de un estudiante de instituto?",
            "Donde se supone que esta rebotando el meteoro??? ",
            "Su pedido de uber ya esta afuera",
            "CIERRAME, NO TE DISTRAIGAS",
            "Que suerte que teniamos un escudo antimeteoritos a mano",
            "La sexta es inevitable",
            "La base de datos de virus ha sido actualizada",
            "Bro, te vienes a la fiesta en el polideportivo?",
            "Toda la tierra confia en ti"
    };

    /**
     * Constructor de la clase Layout.
     * @param game La instancia del juego.
     */
    public Layout(Game game) {
        this.game = game;
    }

    /**
     * Devuelve la puntuación actual del juego.
     * @return La puntuación del juego.
     */
    public int getScore() {
        return this.score;
    }

    /**
     * Devuelve el nivel actual del juego.
     * @return El nivel del juego.
     */
    public int getNivel() {
        return this.nivel;
    }

    /**
     * Establece el nivel del juego.
     * @param nuevoNivel El nuevo nivel del juego.
     */
    public void setNivel(int nuevoNivel) {
        this.nivel = nuevoNivel;
    }

    /**
     * Actualiza los datos del juego.
     */
    public void actualizar() {

        // Comprueba si el jugador ha alcanzado el nivel máximo y la puntuación máxima
        if (nivel == 20 && score == 20) {
            mostrarMensaje("FELICIDADES!! Haz salvado a la tierra");
            game.gameOver();
        }

        // Actualiza la puntuación del juego
        this.score = game.getCuadros() / CUADROS_POR_PUNTO;

        // Actualiza el nivel del juego cada 20 puntos
        if (score % 20 == 0 && game.getCuadros() % 1000 == 0) {
            nivel++;
        }

        // Muestra mensajes aleatorios durante el juego
        if (game.getCuadros() % random.nextInt(1, 1000) == 0  && game.bola.velocidadBolaY < 0 ) {
            mostrarMensaje(mensajes[random.nextInt(11)]);
        }
    }

    /**
     * Dibuja el nivel y la puntuación en la pantalla.
     * @param g El contexto gráfico en el que se dibujarán el nivel y la puntuación.
     */
    public void paint(Graphics2D g) {

        final int CORD_Y_LAYOUT = 50; // Coordenada Y para el layout
        final int CORD_X_LAYOUT = 50; // Coordenada X para el layout

        // Dibuja la puntuación y el nivel en la pantalla si el juego no ha terminado
        if (!game.getFin()) {
            g.setColor(Color.BLACK);
            g.drawString(String.valueOf(score), CORD_X_LAYOUT, CORD_Y_LAYOUT);
            g.drawString(String.valueOf(nivel), Ventana.FRAME_WIDTH - CORD_X_LAYOUT, CORD_Y_LAYOUT);
        }
    }

    /**
     * Muestra un mensaje en una ventana emergente.
     * @param mensaje El mensaje a mostrar.
     */
    private void mostrarMensaje(String mensaje) {
        new Thread(() -> {
            JOptionPane.showMessageDialog(null, mensaje);
        }).start();
    }
}
