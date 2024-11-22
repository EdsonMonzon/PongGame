import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Fondo {
    private final List<Cuadrado> CUADRADOS;

    public Fondo() {
        this.CUADRADOS = new ArrayList<>();
        generarCuadrados();
    }

    private void generarCuadrados() {
        Random random = new Random();
        int numCuadrados = 20; // Número de cuadrados a generar

        for (int i = 0; i < numCuadrados; i++) {
            int x = random.nextInt(Ventana.FRAME_WIDTH); // Coordenada x aleatoria
            int y = random.nextInt(Ventana.FRAME_HEIGHT); // Coordenada y aleatoria
            int lado = random.nextInt(50) + 10; // Tamaño del lado aleatorio (entre 10 y 59)
            int velocidad = random.nextInt(5) + 1; // Velocidad aleatoria (entre 1 y 5)

            Cuadrado cuadrado = new Cuadrado(x, y, lado, velocidad);
            CUADRADOS.add(cuadrado);
        }
    }

    public void paint(Graphics2D g2d) {
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Pinta el fondo negro
        g2d.setColor(Color.CYAN);
        g2d.fillRect(0, 0, Ventana.FRAME_WIDTH, Ventana.FRAME_HEIGHT);

        // Pinta los cuadrados
        for (Cuadrado cuadrado : CUADRADOS) {
            cuadrado.paint(g2d);
        }
    }

    public void moverCuadrados() {
        for (Cuadrado cuadrado : CUADRADOS) {
            cuadrado.mover();
        }
    }
}

class Cuadrado {
    private int x;
    private int y;
    private int lado;
    private int velocidad;

    public Cuadrado(int x, int y, int lado, int velocidad) {
        this.x = x;
        this.y = y;
        this.lado = lado;
        this.velocidad = velocidad;
    }

    public void paint(Graphics2D g2d) {
        g2d.setColor(Color.WHITE);
        g2d.fillRect(x, y, lado, lado);
    }

    public void mover() {
        x += velocidad;
        y += velocidad;

        // Si el cuadrado sale de los límites de la ventana, lo reposiciona
        if (x > Ventana.FRAME_WIDTH) {
            x = -lado;
        }
        if (y > Ventana.FRAME_HEIGHT) {
            y = -lado;
        }
    }
}
