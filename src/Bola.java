import java.awt.*;
import java.util.Random;
import java.util.ArrayList;

/**
 * Clase que representa la bola en el juego.
 */
public class Bola {
    final int ANCHO_BOLA = 15;
    final int ALTO_BOLA = 15;
    private final Game game;
    final int BOLA_X_INICI = (Ventana.FRAME_WIDTH / 2) - (ANCHO_BOLA);
    final int BOLA_Y_INICI = (Ventana.FRAME_HEIGHT / 2) - (ALTO_BOLA);
    final int velocidadBolaInicial = 1;
    double velocidadBolaX = -1;
    double velocidadBolaY = -1;
    int bolaX = BOLA_X_INICI;
    int bolaY = BOLA_Y_INICI;
    int nuevoNivel = 0;

    private ArrayList<Particula> particulas;

    /**
     * Constructor de la clase Bola.
     *
     * @param game Instancia del juego.
     */
    public Bola(Game game) {
        this.game = game;
        this.particulas = new ArrayList<>();
    }

    /**
     * Mueve la bola y gestiona las colisiones.
     */
    void moverBola() {
        chocaPared();
        chocaPalo();
        chocaPaloEnemigo();
        acelerarBola();

        bolaX += velocidadBolaX;
        bolaY += velocidadBolaY;

        for (Particula particula : particulas) {
            particula.actualizar();
        }

        // Genera nuevas partículas de fuego
        generarParticulas();
    }

    /**
     * Genera partículas de fuego alrededor de la bola.
     */
    private void generarParticulas() {
        Random random = new Random();

        // Genera nuevas partículas de fuego cerca de la posición de la bola
        for (int i = 0; i < 5; i++) { // Genera 5 partículas en cada iteración
            int x = bolaX + random.nextInt(ANCHO_BOLA); // Posición x aleatoria cerca de la bola
            int y = bolaY + random.nextInt(ALTO_BOLA); // Posición y aleatoria cerca de la bola
            int velocidadX = random.nextInt(3) - 1; // Velocidad x aleatoria entre -1 y 1
            int velocidadY = random.nextInt(3) - 1; // Velocidad y aleatoria entre -1 y 1
            Color color = new Color(random.nextInt(256), random.nextInt(256), 0); // Color aleatorio (tonos de rojo)

            Particula particula = new Particula(x, y, velocidadX, velocidadY);
            particulas.add(particula);
        }

        // Elimina las partículas más antiguas para evitar que la lista crezca indefinidamente
        while (particulas.size() > 100) {
            particulas.remove(0);
        }
    }

    /**
     * Acelera la bola en función del nivel actual del juego.
     */
    void acelerarBola() {
        if (nuevoNivel != game.layout.getNivel()) {
            if (velocidadBolaX > 0) {
                velocidadBolaX = velocidadBolaInicial + game.layout.getNivel() * (velocidadBolaInicial * 0.1);
            }
            if (velocidadBolaX < 0) {
                velocidadBolaX = (velocidadBolaInicial + game.layout.getNivel() * (velocidadBolaInicial * 0.1)) * -1;
            }
            if (velocidadBolaY > 0) {
                velocidadBolaY = velocidadBolaInicial + game.layout.getNivel() * (velocidadBolaInicial * 0.1);
            }
            if (velocidadBolaY < 0) {
                velocidadBolaY = (velocidadBolaInicial + game.layout.getNivel() * (velocidadBolaInicial * 0.1)) * -1;
            }
            System.out.println("se aumento la velocidad a " + velocidadBolaX + " x " + velocidadBolaY + " y");
            nuevoNivel = game.layout.getNivel();
        }
    }

    /**
     * Comprueba si la bola choca con una pared y ajusta su velocidad.
     */
    void chocaPared() {
        if (bolaX < 0 || bolaX + ANCHO_BOLA > Ventana.FRAME_WIDTH) {
            // Chocaste con una pared
            velocidadBolaX *= -1;
            game.reproducirSonido("src/Sonidos/Bola.wav");
            System.out.println("Chocaste con una pared");
        }

        if (bolaY < 0 || bolaY + ALTO_BOLA * 4 >= Ventana.FRAME_HEIGHT) {
            if (bolaY < 0) {
                // Chocaste con el techo
                velocidadBolaY *= -1;
                System.out.println("Chocaste con el techo");
            } else {
                // Chocaste con el suelo
                game.reproducirSonido("src/Sonidos/Bola.wav");
                game.gameOver();
            }
        }
    }

    /**
     * Comprueba si la bola choca con el palo del jugador y ajusta su velocidad.
     */
    void chocaPalo() {
        Palo objetoPalo = game.palo;

        if (bolaY + ALTO_BOLA > objetoPalo.paloY &&
                bolaX + ANCHO_BOLA > objetoPalo.paloX &&
                bolaX < objetoPalo.paloX + objetoPalo.ANCHO_PALO) {

            velocidadBolaY *= -1;

            if (bolaX + ANCHO_BOLA / 2 < objetoPalo.paloX + objetoPalo.ANCHO_PALO && bolaX + ANCHO_BOLA / 2 < objetoPalo.paloX + objetoPalo.ANCHO_PALO) {
                velocidadBolaX *= 1; // Dirección hacia la izquierda
            } else {
                velocidadBolaX *= -1; // Dirección hacia la derecha
            }
            game.reproducirSonido("src/Sonidos/Bola.wav");
            System.out.println("Chocaste con la barra");
        }
    }

    /**
     * Comprueba si la bola choca con el palo del enemigo y ajusta su velocidad.
     */
    void chocaPaloEnemigo() {

        for (PaloEnemigo paloEnemigo : game.palosEnemigos) {

            if (bolaY + ALTO_BOLA > paloEnemigo.getPaloEnemigoY() &&
                    bolaX + ANCHO_BOLA > paloEnemigo.getPaloEnemigoX() &&
                    bolaX < paloEnemigo.getPaloEnemigoX() + paloEnemigo.getANCHO_PALO_ENEMIGO() &&
                    bolaY < paloEnemigo.getPaloEnemigoY() + paloEnemigo.getALTO_PALO_ENEMIGO()) {

                velocidadBolaY *= -1;

                if (bolaX + ANCHO_BOLA / 2 < paloEnemigo.getPaloEnemigoX() + paloEnemigo.getANCHO_PALO_ENEMIGO() && bolaX + ANCHO_BOLA / 2 < paloEnemigo.getPaloEnemigoX() + paloEnemigo.getANCHO_PALO_ENEMIGO()) {

                    velocidadBolaX *= 1; // Dirección hacia la izquierda

                } else {
                    velocidadBolaX *= -1; // Dirección hacia la derecha
                }
                game.reproducirSonido("src/Sonidos/Bola.wav");
                System.out.println("Chocaste con la barra");
            }
        }
    }

    /**
     * Dibuja la bola y las partículas asociadas.
     *
     * @param g El contexto gráfico en el que se dibujará la bola.
     * @throws InterruptedException Excepción lanzada en caso de interrupción durante el dibujo.
     */
    public void paint(Graphics2D g) throws InterruptedException {
        Font fuente = new Font("Press Start 2P", Font.BOLD, 25);
        g.setFont(fuente);

        FontMetrics metrics = g.getFontMetrics(fuente);
        int anchoTexto = metrics.stringWidth("GAME OVER");
        int altoTexto = metrics.getHeight();

        int gameOverX = (Ventana.FRAME_WIDTH - anchoTexto) / 2;
        int gameOverY = (Ventana.FRAME_HEIGHT - altoTexto) / 2;

        if (game.getFin()) {
            g.setColor(Color.RED);
            g.drawString("GAME OVER", gameOverX, gameOverY);
            game.reproducirSonido("src/Sonidos/GameOver.wav");
        } else {
            g.setColor(Color.BLACK);
            g.fillOval(bolaX, bolaY, ANCHO_BOLA, ALTO_BOLA);
            g.setColor(Color.RED);
            g.fillOval(bolaX + 2, bolaY + 2, ANCHO_BOLA - 4, ALTO_BOLA - 4);
            g.setColor(Color.YELLOW);
            g.fillOval(bolaX + 3, bolaY + 3, ANCHO_BOLA - 6, ALTO_BOLA - 6);

        }
        for (Particula particula : particulas) {
            particula.paint(g);
        }
    }
}