import java.awt.*;
import java.util.Random;

/**
 * La clase PaloEnemigo representa la barra controlada por la computadora en el juego.
 */
public class PaloEnemigo {
    final private Game game;
    final private int ANCHO_PALO_ENEMIGO = 60; // Ancho de la barra del enemigo
    final private int ALTO_PALO_ENEMIGO = 10; // Alto de la barra del enemigo
    private int paloEnemigoX; // Posición X de la barra del enemigo
    private int paloEnemigoY; // Posición Y de la barra del enemigo
    int velocidadPaloEnemigo = 3; // Velocidad de movimiento de la barra del enemigo

    /**
     * Constructor de la clase PaloEnemigo.
     * @param game La instancia de Game asociada al palo enemigo.
     * @param numPalo El número de la barra enemiga.
     */
    public PaloEnemigo(Game game, int numPalo) {
        Random random = new Random();

        this.game = game;
        this.paloEnemigoY = numPalo * 50; // Ajusta la posición Y según el número de barra
        this.paloEnemigoX = random.nextInt(10, Ventana.FRAME_WIDTH - ANCHO_PALO_ENEMIGO - 10); // Posición X aleatoria dentro de los límites de la ventana
    }

    /**
     * Obtiene la posición Y de la barra enemiga.
     * @return La posición Y de la barra enemiga.
     */
    public int getPaloEnemigoY() {
        return paloEnemigoY;
    }

    /**
     * Obtiene la posición X de la barra enemiga.
     * @return La posición X de la barra enemiga.
     */
    public int getPaloEnemigoX() {
        return paloEnemigoX;
    }

    /**
     * Obtiene el ancho de la barra enemiga.
     * @return El ancho de la barra enemiga.
     */
    public int getANCHO_PALO_ENEMIGO() {
        return ANCHO_PALO_ENEMIGO;
    }

    /**
     * Obtiene el alto de la barra enemiga.
     * @return El alto de la barra enemiga.
     */
    public int getALTO_PALO_ENEMIGO() {
        return ALTO_PALO_ENEMIGO;
    }

    /**
     * Mueve la barra enemiga horizontalmente y controla las colisiones con las paredes.
     */
    void moverPaloEnemigo() {
        chocaPared();
        paloEnemigoX += velocidadPaloEnemigo;
    }

    /**
     * Verifica si la barra enemiga choca con las paredes y cambia la dirección de movimiento si es necesario.
     */
    void chocaPared() {
        if (paloEnemigoX < 0 || paloEnemigoX + ANCHO_PALO_ENEMIGO > Ventana.FRAME_WIDTH) {
            velocidadPaloEnemigo *= -1; // Invierte la dirección de movimiento cuando choca con una pared
            System.out.println("El palo choco con una pared");
        }
    }

    /**
     * Dibuja la barra enemiga en el contexto gráfico especificado.
     * @param g El contexto gráfico en el que se dibujará la barra enemiga.
     */
    public void paint(Graphics2D g) {
        if (!game.getFin()) {
            g.setColor(Color.BLACK);
            g.fillRect(paloEnemigoX, paloEnemigoY, ANCHO_PALO_ENEMIGO, ALTO_PALO_ENEMIGO); // Dibuja la barra enemiga
            g.setColor(Color.GRAY);
            g.fillRect(paloEnemigoX + 3, paloEnemigoY + 3, ANCHO_PALO_ENEMIGO - 6, ALTO_PALO_ENEMIGO - 6); // Dibuja un borde interior de la barra enemiga
        }
    }
}
