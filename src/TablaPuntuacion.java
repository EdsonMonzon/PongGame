import javax.swing.*;
import javax.xml.crypto.Data;
import java.awt.*;

//JPanel que mostrara la puntutacionde los jugadores
public class TablaPuntuacion extends JPanel {
    String[][] registros;
    private final Game game;

    public TablaPuntuacion(Game game){
        this.game=game;
    }

    //Pintara sin borrar cada vez los datos de los jugadores
    @Override
    public void paint(Graphics g) {


        Graphics2D g2d = (Graphics2D) g;
        Font fuente = new Font("Press Start 2P", Font.PLAIN, 12);
        g2d.setFont(fuente);
        Fondo fondo=new Fondo();
        fondo.paint(g2d);


        if(game.idioma.equals("Español")){
            g.drawString("Tabla de Puntuaciones", Ventana.FRAME_WIDTH / 3, 50);
            g.drawString("Nombres", 0, 80);
            g.drawString("Puntaje", 75, 80);
            g.drawString("Fecha", 150, 80);
            g.drawString("Idioma", 227, 80);
        }else{
            g.drawString("Taula de Puntuacions", Ventana.FRAME_WIDTH / 3, 50);
            g.drawString("Noms", 0, 80);
            g.drawString("Puntatge", 75, 80);
            g.drawString("Data", 150, 80);
            g.drawString("Idioma", 227, 80);
        }


        for (int i = 0; i < 10; i++) {
            g.drawString((i+1)+"."+registros[i][0], 0, 110 + i * 30);
            g.drawString(registros[i][1], 75, 110 + i * 30);
            g.drawString(registros[i][2], 150, 110 + i * 30);
            g.drawString(registros[i][3], 227, 110 + i * 30);
        }
    }

    void actualizarDatos(String[][] registros) {
        this.registros = registros;
    }
}
