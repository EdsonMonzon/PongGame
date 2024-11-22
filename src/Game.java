import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.sound.sampled.*;
import javax.swing.*;

public class Game extends JPanel {
    private  boolean fin=false;
    private boolean menuFi=false;
    private int cuadros=0;
    private String usuario;
    public String idioma;
    final ArrayList<PaloEnemigo> palosEnemigos=new ArrayList<>();
    Bola bola = new Bola(this);
    Palo palo = new Palo(this);
    Layout layout=new Layout(this);
    PongMenu pongMenu=new PongMenu(this);
    Fondo fondo=new Fondo();

    // Definir una lista para almacenar todos los Clips en reproducción
    private static List<Clip> clipsEnReproduccion = new ArrayList<>();
    // Variable para almacenar el nombre del archivo de música
    private static String nombreArchivoMusica = "src/Sonidos/Musica.wav";
    public boolean getFin(){
        return this.fin;
    }public int getCuadros(){
        return this.cuadros;
    }public void setMenuFi(boolean nuevoMenuFi){
        this.menuFi=nuevoMenuFi;
    }public void setUsuario(String nuevoUsuario){
        this.usuario=nuevoUsuario;
    }
    public Game() {
        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                palo.keyReleased(e);
            }

            @Override
            public void keyPressed(KeyEvent e) {
                palo.keyPressed(e);
            }
        });
        setFocusable(true);
    }

    //Actualiza cada objeto en pantalla
    private void mover(){
        fondo.moverCuadrados();
        bola.moverBola();
        palo.moverPalo();

        int cantidadPalosEnemigos= (layout.getNivel()/10);

        while (palosEnemigos.size() < cantidadPalosEnemigos) {
            palosEnemigos.add(new PaloEnemigo(this, palosEnemigos.size()+1));
        }

        for(PaloEnemigo paloEnemigo:palosEnemigos){
            paloEnemigo.moverPaloEnemigo();
        }

        layout.actualizar();
    }

    //Pinta los objetos en pantalla
    @Override
    public void paint(Graphics g) {
        //Esto limpia la pantalla en cada llamada
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        fondo.paint(g2d);
        try {
            bola.paint(g2d);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        palo.paint(g2d);

        for(PaloEnemigo paloEnemigo:palosEnemigos){
            paloEnemigo.paint(g2d);
        }

        layout.paint(g2d);

        if(this.fin){
            try {
                bola.paint(g2d);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void gameOver() {
        this.fin=true;
        detenerSonido();
    }

    public void reproducirSonido(String nombreArchivo) {
        try {
            File archivoSonido = new File(nombreArchivo);
            if (!archivoSonido.exists()) {
                System.out.println("El archivo de sonido no existe en la ruta proporcionada.");
                return;
            }

            Clip clip = AudioSystem.getClip();
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(archivoSonido);
            clip.open(audioInputStream);

            // Verificar si es el archivo de música antes de agregar el LineListener
            if (nombreArchivo.equals(nombreArchivoMusica)) {
                // Agregar un LineListener solo para la música para detectar cuando termine de reproducirse
                clip.addLineListener(new LineListener() {
                    @Override
                    public void update(LineEvent event) {
                        if (event.getType() == LineEvent.Type.STOP && !fin) {
                            clip.close();
                            clipsEnReproduccion.remove(clip);
                            // Volver a reproducir la música una vez que haya terminado
                            reproducirSonido(nombreArchivo);
                        }
                    }
                });
            }

            clip.start();
            clipsEnReproduccion.add(clip);

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public static void detenerSonido() {
        Iterator<Clip> iterator = clipsEnReproduccion.iterator();
        while (iterator.hasNext()) {
            Clip clip = iterator.next();
            if (clip != null && clip.isOpen() && clip.equals(clipsEnReproduccion.get(0))) {
                clip.stop();
                clip.close();
                iterator.remove();
            }
        }
    }
    public static void main(String[] args) throws InterruptedException {
        do {
            Game game = new Game();
            game.pongMenu.setVisible(true);

            while (!game.menuFi) {
                Thread.sleep(10);
            }

            JFrame frame = new JFrame("Mini Tennis");
            frame.add(game);
            frame.setSize(Ventana.FRAME_WIDTH + 12, Ventana.FRAME_HEIGHT + 5);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            game.reproducirSonido(nombreArchivoMusica);

            while (!game.fin) {
                game.cuadros++;
                game.mover();
                game.repaint();
                Thread.sleep(10);
            }

            Conexio conexio = new Conexio(game);
            conexio.abrirConexio();
            conexio.obtenerTablaConexio();

            LocalDate fechaActual = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String fechaActualString = fechaActual.format(formatter);

            Thread.sleep(5000);
            conexio.insertarConexio(game.usuario, game.layout.getScore(), game.idioma, fechaActualString);
            conexio.obtenerTablaConexio();
            frame.dispose();
            conexio.mostrarDatosConexio();
            conexio.cerrarConexio();
        } while (true);
    }
}