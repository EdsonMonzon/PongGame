import java.awt.*;

public class Particula {
    private int x;
    private int y;
    private int velocidadX;
    private int velocidadY;

    public Particula(int x, int y, int velocidadX, int velocidadY) {
        this.x = x;
        this.y = y;
        this.velocidadX = velocidadX;
        this.velocidadY = velocidadY;
    }

    public void actualizar() {
        x += velocidadX;
        y += velocidadY;
    }

    public void paint(Graphics2D g) {
        g.setColor(Color.RED);
        g.fillRect(x, y, 2, 2); // Dibuja un pequeño cuadrado como partícula de fuego
    }
}