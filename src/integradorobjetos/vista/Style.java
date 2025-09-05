package integradorobjetos.vista;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.Random;

public class Style {
    // Paleta de colores mágicos
    public static final Color COLOR_FONDO = new Color(25, 25, 35);
    public static final Color COLOR_PANEL = new Color(40, 45, 60);
    public static final Color COLOR_BORDE = new Color(70, 80, 100);
    public static final Color COLOR_TEXTO = new Color(220, 220, 230);
    public static final Color COLOR_DORADO = new Color(255, 215, 0);
    public static final Color COLOR_AZUL_MAGICO = new Color(70, 130, 180);
    public static final Color COLOR_SELECCION = new Color(80, 100, 140);
    
    // Fuentes (usando fuentes estándar para evitar problemas)
    public static final Font FUENTE_TITULO = new Font("Serif", Font.BOLD, 28);
    public static final Font FUENTE_SUBTITULO = new Font("Serif", Font.BOLD, 20);
    public static final Font FUENTE_NORMAL = new Font("Serif", Font.PLAIN, 16);
    public static final Font FUENTE_PEQUEÑA = new Font("Serif", Font.PLAIN, 14);
    
    // Métodos para aplicar estilos a componentes
    public static void estiloFrame(JFrame frame) {
        frame.setBackground(COLOR_FONDO);
        frame.getContentPane().setBackground(COLOR_FONDO);
        frame.getRootPane().setBorder(BorderFactory.createLineBorder(COLOR_DORADO, 3));
    }
    
    public static void estiloPanel(JPanel... panels) {
        for (JPanel panel : panels) {
            panel.setBackground(COLOR_PANEL);
            panel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        }
    }
    
    public static JLabel crearSubtitulo(String texto) {
        JLabel subtitulo = new JLabel(texto, SwingConstants.CENTER);
        subtitulo.setFont(FUENTE_SUBTITULO);
        subtitulo.setForeground(COLOR_AZUL_MAGICO);
        subtitulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        return subtitulo;
    }
    
    // Método para estilizar botones (tanto JButton como JToggleButton)
    public static void estiloBoton(AbstractButton... botones) {
        for (AbstractButton boton : botones) {
            boton.setBackground(COLOR_AZUL_MAGICO);
            boton.setForeground(COLOR_DORADO);
            boton.setFont(FUENTE_NORMAL);
            boton.setFocusPainted(false);
            boton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_DORADO, 1),
                BorderFactory.createEmptyBorder(8, 20, 8, 20)
            ));
            boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            
            // Efecto hover con brillo mágico
            boton.getModel().addChangeListener(e -> {
                if (boton.getModel().isPressed()) {
                    boton.setBackground(new Color(120, 50, 150)); // Púrpura real
                } else if (boton.getModel().isRollover()) {
                    boton.setBackground(new Color(100, 120, 160)); // Azul hover
                } else {
                    boton.setBackground(COLOR_AZUL_MAGICO);
                }
            });
        }
    }
    
    // Método específico para el botón HOME
    public static void estiloBotonHome(JButton boton) {
        boton.setBackground(new Color(60, 60, 70));
        boton.setForeground(COLOR_DORADO);
        boton.setFont(FUENTE_NORMAL);
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COLOR_DORADO, 2),
            BorderFactory.createEmptyBorder(8, 15, 8, 15)
        ));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.setHorizontalTextPosition(SwingConstants.CENTER);
        boton.setVerticalTextPosition(SwingConstants.BOTTOM);
        
        // Efecto hover dorado
        boton.getModel().addChangeListener(e -> {
            if (boton.getModel().isPressed()) {
                boton.setBackground(COLOR_DORADO);
                boton.setForeground(COLOR_FONDO);
            } else if (boton.getModel().isRollover()) {
                boton.setBackground(new Color(80, 80, 90));
            } else {
                boton.setBackground(new Color(60, 60, 70));
            }
        });
    }
    
    public static void estiloEtiqueta(JLabel... etiquetas) {
        for (JLabel etiqueta : etiquetas) {
            etiqueta.setForeground(COLOR_TEXTO);
            etiqueta.setFont(FUENTE_NORMAL);
        }
    }
    
    public static void estiloTabla(JTable tabla) {
        tabla.setBackground(new Color(35, 40, 50));
        tabla.setForeground(COLOR_TEXTO);
        tabla.setFont(FUENTE_NORMAL);
        tabla.setGridColor(new Color(60, 70, 80));
        tabla.setSelectionBackground(COLOR_SELECCION);
        tabla.setSelectionForeground(COLOR_DORADO);
        tabla.setRowHeight(35);
        
        // Configurar encabezado
        if (tabla.getTableHeader() != null) {
            tabla.getTableHeader().setBackground(COLOR_PANEL);
            tabla.getTableHeader().setForeground(COLOR_DORADO);
            tabla.getTableHeader().setFont(FUENTE_NORMAL);
            tabla.getTableHeader().setBorder(BorderFactory.createLineBorder(COLOR_DORADO, 1));
        }
    }
    
    public static void estiloScrollPane(JScrollPane... scrollPanes) {
        for (JScrollPane scrollPane : scrollPanes) {
            scrollPane.setBackground(COLOR_PANEL);
            scrollPane.setBorder(BorderFactory.createEmptyBorder());
            
            if (scrollPane.getViewport() != null) {
                scrollPane.getViewport().setBackground(new Color(35, 40, 50));
            }
            
            // Configurar barras de scroll
            if (scrollPane.getVerticalScrollBar() != null) {
                scrollPane.getVerticalScrollBar().setBackground(COLOR_PANEL);
            }
            if (scrollPane.getHorizontalScrollBar() != null) {
                scrollPane.getHorizontalScrollBar().setBackground(COLOR_PANEL);
            }
        }
    }
    
    // Métodos adicionales
    public static void estiloCampoTexto(JTextField... campos) {
        for (JTextField campo : campos) {
            campo.setBackground(new Color(35, 40, 50));
            campo.setForeground(COLOR_TEXTO);
            campo.setFont(FUENTE_NORMAL);
            campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_BORDE, 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
            ));
            campo.setCaretColor(COLOR_DORADO);
        }
    }
    
    // Panel con fondo estrellado
    public static JPanel crearPanelEstrellado(JComponent componente) {
        return new JPanelEstrellado(componente);
    }
    
    // Crear panel de botones
    public static JPanel crearPanelBotones(JButton... botones) {
        JPanel panel = new JPanel(new FlowLayout());
        for (JButton boton : botones) {
            panel.add(boton);
        }
        estiloPanel(panel);
        estiloBoton(botones);
        return panel;
    }
    
    // Métodos para mostrar mensajes
    public static void mostrarMensaje(Component parent, String titulo, String mensaje, int tipo) {
        JOptionPane.showMessageDialog(parent, mensaje, titulo, tipo);
    }
    
    public static boolean confirmar(Component parent, String mensaje) {
        return JOptionPane.showConfirmDialog(parent, mensaje, "Confirmar", 
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }
    
    // Panel interno con fondo estrellado
    private static class JPanelEstrellado extends JPanel {
        private JComponent componente;
        
        public JPanelEstrellado(JComponent componente) {
            this.componente = componente;
            setLayout(new BorderLayout());
            add(componente, BorderLayout.CENTER);
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            
            Graphics2D g2d = (Graphics2D) g;
            
            // Fondo oscuro
            g2d.setColor(COLOR_FONDO);
            g2d.fillRect(0, 0, getWidth(), getHeight());
            
            // Estrellas
            g2d.setColor(COLOR_DORADO);
            Random random = new Random();
            for (int i = 0; i < 50; i++) {
                int x = random.nextInt(getWidth());
                int y = random.nextInt(getHeight());
                int tamaño = random.nextInt(3) + 1;
                g2d.fillOval(x, y, tamaño, tamaño);
            }
            
            // Estrellas más brillantes
            g2d.setColor(Color.WHITE);
            for (int i = 0; i < 20; i++) {
                int x = random.nextInt(getWidth());
                int y = random.nextInt(getHeight());
                int tamaño = random.nextInt(2) + 1;
                g2d.fillOval(x, y, tamaño, tamaño);
            }
        }
    }
}