package integradorobjetos.vista;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.plaf.LayerUI;

public class Style {
    // Paleta de colores mágicos
    public static final Color COLOR_PANEL = new Color(40, 45, 60);
    public static final Color COLOR_BORDE = new Color(70, 80, 100);
    public static final Color COLOR_TEXTO = new Color(220, 220, 230);
    public static final Color COLOR_DORADO = new Color(255, 215, 0);
    public static final Color COLOR_AZUL_MAGICO = new Color(70, 130, 180);
    public static final Color COLOR_SELECCION = new Color(80, 100, 140);
    public static final Color COLOR_FONDO = new Color(25, 25, 35);
    public static final Color COLOR_ESTRELLA = new Color(255, 215, 0);
    public static final Color COLOR_ESTRELLA_BRILLANTE = Color.WHITE;
    
    // Fuentes (usando fuentes estándar para evitar problemas)
    public static final Font FUENTE_TITULO = new Font("Serif", Font.BOLD, 28);
    public static final Font FUENTE_SUBTITULO = new Font("Serif", Font.BOLD, 20);
    public static final Font FUENTE_NORMAL = new Font("Serif", Font.PLAIN, 16);
    public static final Font FUENTE_PEQUEÑA = new Font("Serif", Font.PLAIN, 14);
 
    // Método para crear el ojo mágico
    public static OjoMagico crearOjoMagico() {
        return new OjoMagico();
    }
    
    public static JLayer<JComponent> aplicarFondoEstrellado(JComponent panelExistente, int velocidadPx, int delayMs) {
        EstrellasUI ui = new EstrellasUI(velocidadPx, delayMs);
        return new JLayer<>(panelExistente, ui);
    }
    
    private static class EstrellasUI extends LayerUI<JComponent> implements ActionListener {
        private final int[][] estrellas; // x0,y,tam
        private int offsetX = 0;
        private final int vel;
        private final Timer timer;
        private JLayer<? extends JComponent> layer;

        EstrellasUI(int velocidadPx, int delayMs) {
            this.vel = Math.max(1, velocidadPx);
            this.timer = new Timer(Math.max(10, delayMs), this);

            // genera un campo de estrellas base
            Random rnd = new Random();
            estrellas = new int[80][3];
            for (int i = 0; i < estrellas.length; i++) {
                estrellas[i][0] = rnd.nextInt(1000);     // x inicial (se normaliza en paint)
                estrellas[i][1] = rnd.nextInt(700);      // y inicial
                estrellas[i][2] = 1 + rnd.nextInt(3);    // tamaño 1..3
            }
        }

        @Override public void installUI(JComponent c) {
            super.installUI(c);
            layer = (JLayer<? extends JComponent>) c;
            timer.start();
        }

        @Override public void uninstallUI(JComponent c) {
            timer.stop();
            layer = null;
            super.uninstallUI(c);
        }

        @Override public void actionPerformed(ActionEvent e) {
            offsetX += vel;
            if (layer != null) layer.repaint();
        }

        @Override public void paint(Graphics g, JComponent c) {
            // pintamos el fondo primero (debajo del contenido)
            Graphics2D g2 = (Graphics2D) g.create();
            int w = c.getWidth();
            int h = c.getHeight();

            g2.setColor(COLOR_FONDO);
            g2.fillRect(0, 0, w, h);

            // estrellas
            for (int i = 0; i < estrellas.length; i++) {
                int x0 = estrellas[i][0] % Math.max(1, w);
                int y  = estrellas[i][1] % Math.max(1, h);
                int t  = estrellas[i][2];

                // desplazamiento hacia la derecha con módulo positivo
                int x = ((x0 + offsetX) % w + w) % w;

                g2.setColor(i % 4 == 0 ? COLOR_ESTRELLA_BRILLANTE : COLOR_ESTRELLA);
                g2.fillOval(x, y, t, t);
            }
            g2.dispose();

            // ahora pintamos el contenido del panel
            super.paint(g, c);
        }
    }
}