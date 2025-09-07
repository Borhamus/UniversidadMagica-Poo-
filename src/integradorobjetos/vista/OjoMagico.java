package integradorobjetos.vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.*;
import java.util.ArrayList;
import java.util.Random;

public class OjoMagico extends JComponent {
    private static final int TIEMPO_PARPADEO = 200; // ms
    private static final int TICK = 50; // ms
    
    private boolean parpadeando = false;
    private double progresoParpadeo = 0.0; // 0: abierto, 1: cerrado
    private Timer timerParpadeo;
    private Timer timerMouse;
    private Point2D posMouse; // Posición del mouse en la pantalla
    private long tiempoSiguienteParpadeo;
    private final Random random = new Random();
    private final ArrayList<Point2D> particulasFuego = new ArrayList<>();
    
    public OjoMagico() {
        // Hacemos el componente transparente
        setOpaque(false);
        
        // Inicializar partículas de fuego
        for (int i = 0; i < 50; i++) {
            particulasFuego.add(new Point2D.Double(
                random.nextDouble() * 100 - 50,
                random.nextDouble() * 100 - 50
            ));
        }
        
        // Timer para el parpadeo
        timerParpadeo = new Timer(TICK, e -> {
            if (parpadeando) {
                progresoParpadeo += 1.0 / (TIEMPO_PARPADEO / TICK);
                if (progresoParpadeo >= 1.0) {
                    progresoParpadeo = 1.0;
                    parpadeando = false;
                    // Programar el siguiente parpadeo
                    tiempoSiguienteParpadeo = System.currentTimeMillis() + 2000 + (long)(Math.random() * 3000);
                }
            } else {
                // Verificar si es hora de parpadear
                if (System.currentTimeMillis() >= tiempoSiguienteParpadeo) {
                    parpadeando = true;
                    progresoParpadeo = 0.0;
                }
            }
            repaint();
        });
        timerParpadeo.start();
        
        // Timer para seguir el mouse globalmente
        timerMouse = new Timer(50, e -> {
            Point mouseLocation = MouseInfo.getPointerInfo().getLocation();
            posMouse = new Point2D.Double(mouseLocation.x, mouseLocation.y);
            repaint();
        });
        timerMouse.start();
        
        // Inicializar la posición del mouse
        Point mouseLocation = MouseInfo.getPointerInfo().getLocation();
        posMouse = new Point2D.Double(mouseLocation.x, mouseLocation.y);
        
        // Inicializar el tiempo para el primer parpadeo
        tiempoSiguienteParpadeo = System.currentTimeMillis() + 2000 + (long)(Math.random() * 3000);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        // No llamamos a super.paintComponent para mantener el fondo transparente
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        
        // Calcular el radio del ojo basado en el tamaño del panel
        int radioOjo = Math.min(getWidth(), getHeight()) / 2 - 5;
        int radioPupila = Math.max(radioOjo / 3, 5); // Mínimo 5px para que se vea
        
        if (parpadeando) {
            // Dibujar el ojo cerrado: una línea que se va haciendo más gruesa
            int grosor = (int) (radioOjo * 2 * progresoParpadeo);
            g2d.setColor(new Color(255, 69, 0, 200)); // Naranja con transparencia
            g2d.fillRect(centerX - radioOjo, centerY - grosor/2, radioOjo * 2, grosor);
        } else {
            // Dibujar el ojo como fuego
            
            // 1. Dibujar la base del fuego (forma irregular)
            Path2D formaFuego = new Path2D.Double();
            int puntos = 20;
            double angulo = 0;
            double variacion = 0;
            
            for (int i = 0; i <= puntos; i++) {
                angulo = 2 * Math.PI * i / puntos;
                // Variación aleatoria para hacer la forma irregular
                variacion = 0.8 + 0.4 * Math.sin(angulo * 3 + System.currentTimeMillis() / 500.0);
                double radioVariado = radioOjo * variacion;
                
                double x = centerX + Math.cos(angulo) * radioVariado;
                double y = centerY + Math.sin(angulo) * radioVariado;
                
                if (i == 0) {
                    formaFuego.moveTo(x, y);
                } else {
                    formaFuego.lineTo(x, y);
                }
            }
            formaFuego.closePath();
            
            // Relleno con gradiente radial de fuego
            Point2D center = new Point2D.Float(centerX, centerY);
            float[] dist = {0.0f, 0.7f, 1.0f};
            Color[] colors = {
                new Color(255, 255, 200, 220), // Amarillo claro en el centro
                new Color(255, 100, 0, 180),   // Naranja
                new Color(200, 0, 0, 100)      // Rojo oscuro en el borde
            };
            
            RadialGradientPaint paint = new RadialGradientPaint(
                center, radioOjo, dist, colors
            );
            g2d.setPaint(paint);
            g2d.fill(formaFuego);
            
            // 2. Dibujar llamas alrededor del ojo
            for (int i = 0; i < 8; i++) {
                double anguloLlama = 2 * Math.PI * i / 8;
                double alturaLlama = radioOjo * (0.7 + 0.3 * Math.sin(System.currentTimeMillis() / 200.0 + i));
                
                Point2D inicio = new Point2D.Double(
                    centerX + Math.cos(anguloLlama) * radioOjo * 0.8,
                    centerY + Math.sin(anguloLlama) * radioOjo * 0.8
                );
                
                Point2D control = new Point2D.Double(
                    centerX + Math.cos(anguloLlama) * (radioOjo + alturaLlama * 0.7),
                    centerY + Math.sin(anguloLlama) * (radioOjo + alturaLlama * 0.7)
                );
                
                Point2D fin = new Point2D.Double(
                    centerX + Math.cos(anguloLlama) * (radioOjo + alturaLlama),
                    centerY + Math.sin(anguloLlama) * (radioOjo + alturaLlama)
                );
                
                // Crear forma de llama
                Path2D llama = new Path2D.Double();
                llama.moveTo(inicio.getX(), inicio.getY());
                llama.quadTo(control.getX(), control.getY(), fin.getX(), fin.getY());
                
                // Color de la llama (de amarillo a rojo)
                Color colorLlama = new Color(
                    255, 
                    100 + (int)(155 * Math.random()), 
                    0, 
                    150 + (int)(105 * Math.random())
                );
                
                g2d.setColor(colorLlama);
                
                // CORRECCIÓN: Convertir anchoLlama a float y limitarlo a un rango razonable
                float anchoLlama = (float) Math.max(1, Math.min(radioOjo * 0.3, 10));
                g2d.setStroke(new BasicStroke(anchoLlama, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                g2d.draw(llama);
            }
            
            // 3. Calcular la posición de la pupila
            Point puntoPanelEnPantalla = getLocationOnScreen();
            double panelX = puntoPanelEnPantalla.x;
            double panelY = puntoPanelEnPantalla.y;
            
            // Vector desde el centro del ojo hasta el mouse (en coordenadas de pantalla)
            double dx = posMouse.getX() - (panelX + centerX);
            double dy = posMouse.getY() - (panelY + centerY);
            double distancia = Math.sqrt(dx*dx + dy*dy);
            double maxDist = radioOjo - radioPupila;
            if (distancia > maxDist) {
                // Escalar el vector para que no exceda maxDist
                dx = dx * maxDist / distancia;
                dy = dy * maxDist / distancia;
            }
            
            // 4. Dibujar la pupila (forma irregular de fuego)
            Path2D formaPupila = new Path2D.Double();
            int puntosPupila = 8;
            for (int i = 0; i <= puntosPupila; i++) {
                double anguloPupila = 2 * Math.PI * i / puntosPupila;
                double radioVariado = radioPupila * (0.8 + 0.2 * Math.sin(anguloPupila * 4));
                
                double x = centerX + dx + Math.cos(anguloPupila) * radioVariado;
                double y = centerY + dy + Math.sin(anguloPupila) * radioVariado;
                
                if (i == 0) {
                    formaPupila.moveTo(x, y);
                } else {
                    formaPupila.lineTo(x, y);
                }
            }
            formaPupila.closePath();
            
            // Relleno de la pupila con gradiente
            Point2D centerPupila = new Point2D.Float((float)(centerX + dx), (float)(centerY + dy));
            float[] distPupila = {0.0f, 1.0f};
            Color[] colorsPupila = {
                new Color(50, 0, 0, 255), // Rojo muy oscuro en el centro
                new Color(0, 0, 0, 255)   // Negro en el borde
            };
            RadialGradientPaint paintPupila = new RadialGradientPaint(
                centerPupila, radioPupila, distPupila, colorsPupila
            );
            g2d.setPaint(paintPupila);
            g2d.fill(formaPupila);
            
            // 5. Añadir brillo a la pupila
            g2d.setColor(new Color(255, 200, 0, 200)); // Amarillo anaranjado
            g2d.fillOval(
                (int)(centerX + dx - radioPupila/3), 
                (int)(centerY + dy - radioPupila/3), 
                radioPupila/2, 
                radioPupila/2
            );
            
            // 6. Añadir partículas de fuego alrededor del ojo
            for (int i = 0; i < particulasFuego.size(); i++) {
                Point2D p = particulasFuego.get(i);
                double distanciaParticula = p.distance(0, 0);
                if (distanciaParticula > radioOjo * 1.5) {
                    // Reiniciar partícula si está muy lejos
                    particulasFuego.set(i, new Point2D.Double(
                        random.nextDouble() * 20 - 10,
                        random.nextDouble() * 20 - 10
                    ));
                    p = particulasFuego.get(i);
                }
                
                // Mover partícula hacia afuera
                double anguloParticula = Math.atan2(p.getY(), p.getX());
                double nuevaDistancia = distanciaParticula + random.nextDouble() * 2;
                double nuevaX = Math.cos(anguloParticula) * nuevaDistancia;
                double nuevaY = Math.sin(anguloParticula) * nuevaDistancia;
                particulasFuego.set(i, new Point2D.Double(nuevaX, nuevaY));
                
                // Dibujar partícula
                int tamano = 1 + random.nextInt(3);
                int alpha = 100 + random.nextInt(155);
                
                // Color de partícula (amarillo, naranja o rojo)
                Color colorParticula;
                int tipoColor = random.nextInt(3);
                if (tipoColor == 0) {
                    colorParticula = new Color(255, 255, 0, alpha); // Amarillo
                } else if (tipoColor == 1) {
                    colorParticula = new Color(255, 100, 0, alpha); // Naranja
                } else {
                    colorParticula = new Color(255, 0, 0, alpha); // Rojo
                }
                
                g2d.setColor(colorParticula);
                g2d.fillRect(
                    centerX + (int)p.getX() - tamano/2,
                    centerY + (int)p.getY() - tamano/2,
                    tamano, tamano
                );
            }
            
            // 7. Añadir efecto de brillo general
            int radioBrillo = Math.min(radioOjo * 2, Math.min(getWidth(), getHeight()) / 2);
            Point2D centerBrillo = new Point2D.Float(centerX, centerY);
            float[] distBrillo = {0.0f, 0.5f, 1.0f};
            Color[] colorsBrillo = {
                new Color(255, 255, 200, 50), // Amarillo muy transparente
                new Color(255, 100, 0, 30),   // Naranja transparente
                new Color(255, 0, 0, 0)       // Rojo completamente transparente
            };
            RadialGradientPaint paintBrillo = new RadialGradientPaint(
                centerBrillo, radioBrillo, distBrillo, colorsBrillo
            );
            g2d.setPaint(paintBrillo);
            g2d.fillOval(centerX - radioBrillo, centerY - radioBrillo, 
                        radioBrillo * 2, radioBrillo * 2);
        }
        
        g2d.dispose();
    }
    
    // Sobreescribimos estos métodos para que el componente se adapte al contenedor
    @Override
    public Dimension getPreferredSize() {
        if (getParent() != null) {
            return getParent().getSize();
        }
        return new Dimension(100, 100); // Tamaño por defecto si no tiene padre
    }
    
    @Override
    public Dimension getMinimumSize() {
        return new Dimension(50, 50); // Tamaño mínimo para que siempre se vea
    }
    
    @Override
    public Dimension getMaximumSize() {
        return new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE); // Sin límite por defecto
    }
}