package integradorobjetos.vista;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.LayerUI;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

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
    
    // Colores para tablas
    public static final Color COLOR_FONDO_TABLA = new Color(30, 35, 50);
    public static final Color COLOR_FONDO_CELDA = new Color(40, 45, 60);
    public static final Color COLOR_FONDO_SELECCION_TABLA = new Color(90, 110, 150);
    public static final Color COLOR_BORDE_TABLA = new Color(60, 70, 90);
    public static final Color COLOR_ENCABEZADO_TABLA = new Color(50, 60, 80);
    
    // Fuentes (usando fuentes estándar para evitar problemas)
    public static final Font FUENTE_TITULO = new Font("Serif", Font.BOLD, 28);
    public static final Font FUENTE_SUBTITULO = new Font("Serif", Font.BOLD, 20);
    public static final Font FUENTE_NORMAL = new Font("Serif", Font.PLAIN, 16);
    public static final Font FUENTE_PEQUEÑA = new Font("Serif", Font.PLAIN, 14);
    public static final Font FUENTE_TABLA = new Font("Serif", Font.PLAIN, 14);
    public static final Font FUENTE_ENCABEZADO_TABLA = new Font("Serif", Font.BOLD, 15);
 
    // Método para crear el ojo mágico
    public static OjoMagico crearOjoMagico() {
        return new OjoMagico();
    }
    
    // Método para aplicar estilo mágico a los botones
    public static void estiloBotonMagico(JButton boton) {
        // Fuente mágica
        boton.setFont(FUENTE_NORMAL);
        
        // Colores base
        boton.setBackground(COLOR_AZUL_MAGICO);
        boton.setForeground(COLOR_TEXTO);
        boton.setFocusPainted(false);
        
        // Borde mágico con brillo
        Border bordeMagico = BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COLOR_DORADO, 2),
            BorderFactory.createEmptyBorder(8, 20, 8, 20)
        );
        boton.setBorder(bordeMagico);
        
        // Efecto de brillo mágico al pasar el mouse
        boton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                // Animación de brillo
                Timer timer = new Timer(30, new ActionListener() {
                    float alpha = 0.3f;
                    
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        alpha += 0.05f;
                        if (alpha >= 1.0f) {
                            ((Timer)e.getSource()).stop();
                            alpha = 1.0f; // Asegurar que no exceda 1.0
                        }
                        
                        // Asegurar que el valor alfa esté en el rango [0, 255]
                        int alphaValue = Math.min(255, Math.max(0, (int)(alpha * 255)));
                        
                        boton.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(new Color(
                                COLOR_DORADO.getRed(),
                                COLOR_DORADO.getGreen(),
                                COLOR_DORADO.getBlue(),
                                alphaValue
                            ), 3),
                            BorderFactory.createEmptyBorder(8, 20, 8, 20)
                        ));
                    }
                });
                timer.start();
                
                boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
            
            public void mouseExited(MouseEvent evt) {
                // Restaurar borde normal
                boton.setBorder(bordeMagico);
                boton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });
        
        // Efecto de magia al presionar
        boton.getModel().addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                if (boton.getModel().isPressed()) {
                    // Efecto de pulsación mágica
                    boton.setBackground(COLOR_DORADO);
                    boton.setForeground(COLOR_FONDO);
                    
                    // Crear partículas mágicas
                    crearParticulasMagicas(boton);
                } else if (boton.getModel().isRollover()) {
                    boton.setBackground(COLOR_AZUL_MAGICO);
                    boton.setForeground(COLOR_TEXTO);
                } else {
                    boton.setBackground(COLOR_AZUL_MAGICO);
                    boton.setForeground(COLOR_TEXTO);
                }
            }
        });
        
        // Efecto de resplandor constante (simplificado para evitar errores)
        boton.addHierarchyListener(new HierarchyListener() {
            @Override
            public void hierarchyChanged(HierarchyEvent e) {
                if ((e.getChangeFlags() & HierarchyEvent.SHOWING_CHANGED) != 0 && boton.isShowing()) {
                    Timer glowTimer = new Timer(100, new ActionListener() {
                        float glow = 0.5f;
                        boolean increasing = true;
                        
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (increasing) {
                                glow += 0.02f;
                                if (glow >= 1.0f) {
                                    glow = 1.0f; // Asegurar que no exceda 1.0
                                    increasing = false;
                                }
                            } else {
                                glow -= 0.02f;
                                if (glow <= 0.5f) {
                                    glow = 0.5f; // Asegurar que no baje de 0.5
                                    increasing = true;
                                }
                            }
                            
                            // Asegurar que el valor alfa esté en el rango [0, 255]
                            int glowValue = Math.min(255, Math.max(0, (int)(glow * 255)));
                            
                            boton.setBorder(BorderFactory.createCompoundBorder(
                                BorderFactory.createLineBorder(new Color(
                                    COLOR_DORADO.getRed(),
                                    COLOR_DORADO.getGreen(),
                                    COLOR_DORADO.getBlue(),
                                    glowValue
                                ), 2),
                                BorderFactory.createEmptyBorder(8, 20, 8, 20)
                            ));
                        }
                    });
                    glowTimer.start();
                }
            }
        });
    }
    
    // Método para crear partículas mágicas al hacer clic
    private static void crearParticulasMagicas(JComponent componente) {
        Timer particleTimer = new Timer(30, new ActionListener() {
            int count = 0;
            final int maxParticles = 5;
            
            @Override
            public void actionPerformed(ActionEvent e) {
                if (count >= maxParticles) {
                    ((Timer)e.getSource()).stop();
                    return;
                }
                
                // Crear una partícula mágica
                JLabel particle = new JLabel("✦");
                particle.setForeground(COLOR_DORADO);
                particle.setFont(new Font("Serif", Font.BOLD, 12 + new Random().nextInt(8)));
                
                // Posición aleatoria dentro del botón
                int x = new Random().nextInt(componente.getWidth() - 20);
                int y = new Random().nextInt(componente.getHeight() - 20);
                particle.setBounds(x, y, 20, 20);
                
                componente.add(particle);
                componente.repaint();
                
                // Animación de la partícula
                Timer animTimer = new Timer(50, new ActionListener() {
                    int life = 0;
                    final int maxLife = 5;
                    
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        life++;
                        if (life >= maxLife) {
                            ((Timer)e.getSource()).stop();
                            componente.remove(particle);
                            componente.repaint();
                            return;
                        }
                        
                        // Movimiento y desvanecimiento
                        int dx = (new Random().nextInt(3) - 1) * 2;
                        int dy = (new Random().nextInt(3) - 1) * 2;
                        particle.setLocation(particle.getX() + dx, particle.getY() + dy);
                        
                        // Calcular el valor alfa y asegurar que esté en el rango [0, 255]
                        float alpha = 1.0f - (float)life / maxLife;
                        int alphaValue = Math.min(255, Math.max(0, (int)(alpha * 255)));
                        
                        particle.setForeground(new Color(
                            COLOR_DORADO.getRed(),
                            COLOR_DORADO.getGreen(),
                            COLOR_DORADO.getBlue(),
                            alphaValue
                        ));
                    }
                });
                animTimer.start();
                
                count++;
            }
        });
        particleTimer.start();
    }
    
    // Método público para crear partículas mágicas
    public static void crearParticulasMagicasPublicas(JComponent componente) {
        crearParticulasMagicas(componente);
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
        
        @Override 
        public void installUI(JComponent c) {
            super.installUI(c);
            layer = (JLayer<? extends JComponent>) c;
            timer.start();
        }
        
        @Override 
        public void uninstallUI(JComponent c) {
            timer.stop();
            layer = null;
            super.uninstallUI(c);
        }
        
        @Override 
        public void actionPerformed(ActionEvent e) {
            offsetX += vel;
            if (layer != null) layer.repaint();
        }
        
        @Override 
        public void paint(Graphics g, JComponent c) {
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
    
    // ===== MÉTODOS PARA ESTILO DE TABLAS =====
    
    /**
     * Aplica estilo mágico a una tabla JTable con opciones personalizables
     * @param tabla La tabla a la que se aplicará el estilo
     * @param configurador Configurador adicional para personalizar la tabla (puede ser null)
     */
    public static void estiloTablaMagico(JTable tabla, TableConfigurador configurador) {
        // Configuración básica
        configurarTablaBasica(tabla);
        
        // Configurar encabezado
        configurarEncabezadoTabla(tabla);
        
        // Configurar renderizadores por defecto
        configurarRenderizadoresTabla(tabla);
        
        // Aplicar configurador personalizado si existe
        if (configurador != null) {
            configurador.configurar(tabla);
        }
    }
    
    /**
     * Versión simplificada del método que aplica el estilo básico
     */
    public static void estiloTablaMagico(JTable tabla) {
        estiloTablaMagico(tabla, null);
    }
    
    /**
     * Configura los aspectos básicos de una tabla
     */
    private static void configurarTablaBasica(JTable tabla) {
        tabla.setFont(FUENTE_TABLA);
        tabla.setForeground(COLOR_TEXTO);
        tabla.setBackground(COLOR_FONDO_TABLA);
        tabla.setGridColor(COLOR_BORDE_TABLA);
        tabla.setSelectionBackground(COLOR_FONDO_SELECCION_TABLA);
        tabla.setSelectionForeground(COLOR_TEXTO);
        tabla.setRowHeight(25);
        tabla.setBorder(BorderFactory.createLineBorder(COLOR_BORDE_TABLA, 1));
    }
    
    /**
     * Configura el encabezado de la tabla
     */
    private static void configurarEncabezadoTabla(JTable tabla) {
        JTableHeader header = tabla.getTableHeader();
        header.setFont(FUENTE_ENCABEZADO_TABLA);
        header.setForeground(COLOR_DORADO);
        header.setBackground(COLOR_ENCABEZADO_TABLA);
        header.setReorderingAllowed(false);
        header.setDefaultRenderer(new EncabezadoTablaMagicoRenderer());
    }
    
    /**
     * Configura los renderizadores y editores de la tabla
     */
    private static void configurarRenderizadoresTabla(JTable tabla) {
        tabla.setDefaultRenderer(Object.class, new CeldaTablaMagicoRenderer());
        tabla.setDefaultRenderer(Boolean.class, new CheckboxTablaMagicoRenderer());
        tabla.setDefaultEditor(Boolean.class, new CheckboxTablaMagicoEditor());
    }
    
    /**
     * Interfaz para configurar tablas de manera personalizada
     */
    public interface TableConfigurador {
        void configurar(JTable tabla);
    }
    
    /**
     * Renderizador personalizado para encabezados de tabla con estilo mágico
     */
    private static class EncabezadoTablaMagicoRenderer implements TableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, 
                boolean isSelected, boolean hasFocus, int row, int column) {
            JLabel label = new JLabel(value != null ? value.toString() : "");
            label.setOpaque(true);
            label.setFont(FUENTE_ENCABEZADO_TABLA);
            label.setForeground(COLOR_DORADO);
            label.setBackground(COLOR_ENCABEZADO_TABLA);
            label.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, COLOR_BORDE_TABLA),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
            ));
            label.setHorizontalAlignment(SwingConstants.CENTER);
            return label;
        }
    }
    
    /**
     * Renderizador personalizado para celdas de tabla con estilo mágico
     */
    private static class CeldaTablaMagicoRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, 
                boolean isSelected, boolean hasFocus, int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            
            // Configurar colores según estado
            if (isSelected) {
                c.setBackground(COLOR_FONDO_SELECCION_TABLA);
                c.setForeground(COLOR_TEXTO);
            } else {
                // Alternar colores de filas para mejor legibilidad
                if (row % 2 == 0) {
                    c.setBackground(COLOR_FONDO_CELDA);
                } else {
                    c.setBackground(COLOR_FONDO_TABLA);
                }
                c.setForeground(COLOR_TEXTO);
            }
            
            // Configurar fuente
            c.setFont(FUENTE_TABLA);
            
            // Configurar borde
            if (c instanceof JComponent) {
                ((JComponent) c).setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
            }
            
            return c;
        }
    }
    
    /**
     * Renderizador personalizado para checkboxes en tablas con estilo mágico
     */
    private static class CheckboxTablaMagicoRenderer extends JCheckBox implements TableCellRenderer {
        public CheckboxTablaMagicoRenderer() {
            setOpaque(true);
            setHorizontalAlignment(SwingConstants.CENTER);
            setBorderPainted(true);
            setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        }
        
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, 
                boolean isSelected, boolean hasFocus, int row, int column) {
            // Configurar selección
            if (isSelected) {
                setBackground(COLOR_FONDO_SELECCION_TABLA);
            } else {
                // Alternar colores de filas
                if (row % 2 == 0) {
                    setBackground(COLOR_FONDO_CELDA);
                } else {
                    setBackground(COLOR_FONDO_TABLA);
                }
            }
            
            // Configurar valor del checkbox
            setSelected(value != null && (Boolean) value);
            
            // Configurar colores
            setForeground(COLOR_TEXTO);
            setFocusPainted(false);
            
            return this;
        }
    }
    
    /**
     * Editor personalizado para checkboxes en tablas con estilo mágico
     */
    private static class CheckboxTablaMagicoEditor extends DefaultCellEditor {
        private JCheckBox checkbox;
        
        public CheckboxTablaMagicoEditor() {
            super(new JCheckBox());
            this.checkbox = (JCheckBox) getComponent();
            checkbox.setOpaque(true);
            checkbox.setHorizontalAlignment(SwingConstants.CENTER);
            checkbox.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            checkbox.setFocusPainted(false);
            
            // Configurar colores
            checkbox.setBackground(COLOR_FONDO_CELDA);
            checkbox.setForeground(COLOR_TEXTO);
            
            // Agregar efecto mágico al cambiar estado
            checkbox.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    if (e.getStateChange() == ItemEvent.SELECTED) {
                        // Efecto de brillo al seleccionar
                        Timer timer = new Timer(30, new ActionListener() {
                            float alpha = 0.3f;
                            
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                alpha += 0.1f;
                                if (alpha >= 1.0f) {
                                    ((Timer)e.getSource()).stop();
                                    alpha = 1.0f;
                                }
                                
                                int alphaValue = Math.min(255, Math.max(0, (int)(alpha * 255)));
                                checkbox.setBorder(BorderFactory.createLineBorder(
                                    new Color(COLOR_DORADO.getRed(), 
                                            COLOR_DORADO.getGreen(), 
                                            COLOR_DORADO.getBlue(), 
                                            alphaValue), 2));
                            }
                        });
                        timer.start();
                        
                        // Crear partículas mágicas
                        crearParticulasMagicasPublicas(checkbox);
                    }
                }
            });
        }
        
        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, 
                boolean isSelected, int row, int column) {
            // Configurar colores según selección
            if (isSelected) {
                checkbox.setBackground(COLOR_FONDO_SELECCION_TABLA);
            } else {
                // Alternar colores de filas
                if (row % 2 == 0) {
                    checkbox.setBackground(COLOR_FONDO_CELDA);
                } else {
                    checkbox.setBackground(COLOR_FONDO_TABLA);
                }
            }
            
            checkbox.setSelected(value != null && (Boolean) value);
            return checkbox;
        }
    }
}