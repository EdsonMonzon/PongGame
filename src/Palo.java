import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * La clase Palo representa la barra controlada por el jugador en el juego.
 */
public class Palo {
    private final Game game;
    final int ANCHO_PALO = 60; // Ancho de la barra
    final int ALTO_PALO = 10; // Alto de la barra
    final int ESPACIO_VENTANA_ABAJO_PALO = 50; // Espacio entre la barra y el borde inferior de la ventana
    final int PALO_X_INICI = (Ventana.FRAME_WIDTH / 2) - (ANCHO_PALO / 2); // Posición inicial X de la barra
    final int PALO_Y_INICI = Ventana.FRAME_HEIGHT - ESPACIO_VENTANA_ABAJO_PALO; // Posición inicial Y de la barra
    int velocidadPalo = 6; // Velocidad de movimiento de la barra
    int paloX = PALO_X_INICI; // Posición actual X de la barra
    int paloY = PALO_Y_INICI; // Posición actual Y de la barra

    /**
     * Constructor de la clase Palo.
     * @param game La instancia de Game asociada al palo.
     */
    public Palo(Game game) {
        this.game = game;
    }

    /**
     * Mueve la barra horizontalmente según las teclas presionadas.
     */
    void moverPalo() {
        // Controla los límites de movimiento de la barra
        if (paloX + velocidadPalo == 0) {
            paloX = 0;
            paloY = Ventana.FRAME_HEIGHT - ESPACIO_VENTANA_ABAJO_PALO;
        } else if (paloX + velocidadPalo > Ventana.FRAME_WIDTH - ANCHO_PALO) {
            paloX = Ventana.FRAME_WIDTH - ANCHO_PALO;
            paloY = Ventana.FRAME_HEIGHT - ESPACIO_VENTANA_ABAJO_PALO;
        }
    }

    /**
     * Dibuja la barra en el contexto gráfico especificado.
     * @param g El contexto gráfico en el que se dibujará la barra.
     */
    public void paint(Graphics2D g) {
        if (!game.getFin()) {
            g.setColor(Color.BLACK);
            g.fillRect(paloX, paloY, ANCHO_PALO, ALTO_PALO); // Dibuja la barra
            g.setColor(Color.YELLOW);
            g.fillRect(paloX + 3, paloY + 3, ANCHO_PALO - 6, ALTO_PALO - 6); // Dibuja un borde interior de la barra
        }
    }

    /**
     * Maneja el evento de tecla presionada.
     * @param e El evento de tecla presionada.
     */
    public void keyPressed(KeyEvent e) {
        // Mueve la barra hacia la izquierda cuando se presiona la tecla de flecha izquierda
        if (e.getKeyCode() == KeyEvent.VK_LEFT)
            paloX -= velocidadPalo;
        // Mueve la barra hacia la derecha cuando se presiona la tecla de flecha derecha
        if (e.getKeyCode() == KeyEvent.VK_RIGHT)
            paloX += velocidadPalo;
    }

    /**
     * Maneja el evento de tecla liberada (no se utiliza actualmente en esta implementación).
     * @param e El evento de tecla liberada.
     */
    public void keyReleased(KeyEvent e) {
        // No se realiza ninguna acción específica cuando se libera una tecla
    }
}
