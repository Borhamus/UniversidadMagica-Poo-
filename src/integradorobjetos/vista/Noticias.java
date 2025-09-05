/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package integradorobjetos.vista;

import static integradorobjetos.vista.Style.crearPanelEstrellado;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Borhamus
 */
public class Noticias extends javax.swing.JPanel {

    private Timer timer;
    private int indiceActual = 0;
    private List<ImageIcon> imagenes;
    private List<String> textos;
    private JLabel lblImagen;
    private JLabel lblTexto;
    
    public Noticias() {
        initComponents();
        
        // Establecer dimensiones exactas
        setMaximumSize(new java.awt.Dimension(846, 398));
        setMinimumSize(new java.awt.Dimension(846, 398));
        setPreferredSize(new java.awt.Dimension(846, 398));
        
        // Configurar el panel Fondo para que ocupe todo el espacio
        Fondo.setMaximumSize(new java.awt.Dimension(846, 398));
        Fondo.setMinimumSize(new java.awt.Dimension(846, 398));
        Fondo.setPreferredSize(new java.awt.Dimension(846, 398));
        
        aplicarFondoEstrellado();
        configurarPaneles();
        cargarImagenesYTextos();
        configurarCarrusel();
        iniciarTimer();
    }
    
    private void aplicarFondoEstrellado() {
        // Hacemos el panel Fondo transparente para que se vea el fondo estrellado
        Fondo.setOpaque(false);
        
        // Sobrescribimos el método paintComponent para dibujar el fondo estrellado
        Fondo = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                
                // Fondo oscuro
                g2d.setColor(Style.COLOR_FONDO);
                g2d.fillRect(0, 0, getWidth(), getHeight());
                
                // Estrellas doradas
                g2d.setColor(Style.COLOR_DORADO);
                java.util.Random random = new java.util.Random();
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
                
                super.paintComponent(g);
            }
        };
        
        // Configurar los componentes existentes para que sean transparentes
        Fotos.setOpaque(false);
        PanelNoticia.setOpaque(false);
        Pasadas.setOpaque(false);
    }
    
    private void configurarPaneles() {
        // Configurar el panel Fotos para la imagen
        Fotos.setLayout(new BorderLayout());
        lblImagen = new JLabel();
        lblImagen.setHorizontalAlignment(SwingConstants.CENTER);
        lblImagen.setVerticalAlignment(SwingConstants.CENTER);
        Fotos.add(lblImagen, BorderLayout.CENTER);
        
        // Configurar el panel PanelNoticia para el texto
        PanelNoticia.setLayout(new BorderLayout());
        lblTexto = new JLabel();
        lblTexto.setHorizontalAlignment(SwingConstants.CENTER);
        lblTexto.setVerticalAlignment(SwingConstants.CENTER);
        PanelNoticia.add(lblTexto, BorderLayout.CENTER);
        
        // Configurar los checkboxes para que parezcan indicadores
        P1.setOpaque(false);
        P2.setOpaque(false);
        P3.setOpaque(false);
        P4.setOpaque(false);
        
        P1.setFocusPainted(false);
        P2.setFocusPainted(false);
        P3.setFocusPainted(false);
        P4.setFocusPainted(false);
        
        P1.setBorderPainted(true);
        P2.setBorderPainted(true);
        P3.setBorderPainted(true);
        P4.setBorderPainted(true);
        
        P1.setBorder(BorderFactory.createLineBorder(Style.COLOR_DORADO, 1));
        P2.setBorder(BorderFactory.createLineBorder(Style.COLOR_DORADO, 1));
        P3.setBorder(BorderFactory.createLineBorder(Style.COLOR_DORADO, 1));
        P4.setBorder(BorderFactory.createLineBorder(Style.COLOR_DORADO, 1));
    }
    
    
    private void cargarImagenesYTextos() {
        imagenes = new ArrayList<>();
        textos = new ArrayList<>();
        
        // Cargar imágenes
        try {
            imagenes.add(new ImageIcon(getClass().getResource("/integradorobjetos/vista/Img/Ghroth.jpeg")));
            imagenes.add(new ImageIcon(getClass().getResource("/integradorobjetos/vista/Img/Undercity.jpeg")));
            imagenes.add(new ImageIcon(getClass().getResource("/integradorobjetos/vista/Img/Elf.jpeg")));
            imagenes.add(new ImageIcon(getClass().getResource("/integradorobjetos/vista/Img/Elf2.jpeg")));
        } catch (Exception e) {
            System.err.println("Error al cargar las imágenes: " + e.getMessage());
            // Si no se pueden cargar las imágenes, mostrar texto alternativo
            for (int i = 0; i < 4; i++) {
                imagenes.add(null);
            }
        }
        
        // Textos fantásticos para cada imagen
        textos.add("<html><div style='width: 400px; padding: 15px; background-color: rgba(245, 235, 220, 0.9); border: 2px solid #D4AF37; border-radius: 10px;'>"
                + "<h2 style='color: #5C4033; text-align: center;'>El Despertar de Ghroth</h2>"
                + "<p style='color: #5C4033; text-align: justify;'>Cuando las estrellas se alinean, el antiguo dios Ghroth emerge de su sueño milenario para impartir sabiduría arcana.</p>"
                + "<p style='color: #5C4033; text-align: justify;'>Su presencia hace que las runas antiguas brillen con poder inimaginable.</p>"
                + "</div></html>");
        
        textos.add("<html><div style='width: 400px; padding: 15px; background-color: rgba(245, 235, 220, 0.9); border: 2px solid #D4AF37; border-radius: 10px;'>"
                + "<h2 style='color: #5C4033; text-align: center;'>Los Secretos de Undercity</h2>"
                + "<p style='color: #5C4033; text-align: justify;'>Bajo las calles de Undercity yacen conocimientos olvidados que esperan ser redescubiertos.</p>"
                + "<p style='color: #5C4033; text-align: justify;'>Solo los más valientes osan adentrarse en sus profundidades.</p>"
                + "</div></html>");
        
        textos.add("<html><div style='width: 400px; padding: 15px; background-color: rgba(245, 235, 220, 0.9); border: 2px solid #D4AF37; border-radius: 10px;'>"
                + "<h2 style='color: #5C4033; text-align: center;'>La Sabiduría Élfica</h2>"
                + "<p style='color: #5C4033; text-align: justify;'>Los elfos antiguos guardan secretos de magia que han perdurado a través de los milenios.</p>"
                + "<p style='color: #5C4033; text-align: justify;'>Su conocimiento de las artes arcanas es incomparable en el reino.</p>"
                + "</div></html>");
        
        textos.add("<html><div style='width: 400px; padding: 15px; background-color: rgba(245, 235, 220, 0.9); border: 2px solid #D4AF37; border-radius: 10px;'>"
                + "<h2 style='color: #5C4033; text-align: center;'>El Reino de los Elfos Estelares</h2>"
                + "<p style='color: #5C4033; text-align: justify;'>En el reino de los elfos estelares, la magia fluye como ríos de luz cristalina.</p>"
                + "<p style='color: #5C4033; text-align: justify;'>Cada estrella en el cielo corresponde a un hechizo antiguo.</p>"
                + "</div></html>");
    }
    
    private void configurarCarrusel() {
        // Configurar los eventos de los checkboxes para navegación manual
        P1.addActionListener(e -> {
            indiceActual = 0;
            actualizarCarrusel();
            reiniciarTimer();
        });
        
        P2.addActionListener(e -> {
            indiceActual = 1;
            actualizarCarrusel();
            reiniciarTimer();
        });
        
        P3.addActionListener(e -> {
            indiceActual = 2;
            actualizarCarrusel();
            reiniciarTimer();
        });
        
        P4.addActionListener(e -> {
            indiceActual = 3;
            actualizarCarrusel();
            reiniciarTimer();
        });
    }
    
    private void iniciarTimer() {
        // Crear timer para cambiar la imagen cada 5 segundos
        timer = new Timer(5000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                indiceActual = (indiceActual + 1) % imagenes.size();
                actualizarCarrusel();
            }
        });
        timer.start();
        
        // Mostrar la primera imagen
        actualizarCarrusel();
    }
    
    
    
    private void reiniciarTimer() {
        if (timer != null) {
            timer.stop();
            timer.start();
        }
    }
    
    @Override
    public void removeNotify() {
        super.removeNotify();
        if (timer != null) {
            timer.stop();
        }
    }


    private void actualizarCarrusel() {
        // Actualizar la imagen en el panel Fotos
        if (imagenes.get(indiceActual) != null) {
            ImageIcon icono = imagenes.get(indiceActual);
            Image imagen = icono.getImage();
            // Escalar la imagen al tamaño del panel Fotos (253x262 según tu diseño)
            Image escalada = imagen.getScaledInstance(253, 262, Image.SCALE_SMOOTH);
            lblImagen.setIcon(new ImageIcon(escalada));
        } else {
            // Si no se pudo cargar la imagen, mostrar texto alternativo
            lblImagen.setText("Imagen no disponible");
            lblImagen.setForeground(Style.COLOR_DORADO);
            lblImagen.setFont(Style.FUENTE_NORMAL);
        }
        
        // Actualizar el texto en el panel PanelNoticia
        lblTexto.setText(textos.get(indiceActual));
        
        // Actualizar los indicadores
        P1.setSelected(indiceActual == 0);
        P2.setSelected(indiceActual == 1);
        P3.setSelected(indiceActual == 2);
        P4.setSelected(indiceActual == 3);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Fondo = new javax.swing.JPanel();
        Fotos = new javax.swing.JPanel();
        PanelNoticia = new javax.swing.JPanel();
        Pasadas = new javax.swing.JPanel();
        P1 = new javax.swing.JCheckBox();
        P2 = new javax.swing.JCheckBox();
        P3 = new javax.swing.JCheckBox();
        P4 = new javax.swing.JCheckBox();

        setMaximumSize(new java.awt.Dimension(846, 398));
        setMinimumSize(new java.awt.Dimension(846, 398));
        setPreferredSize(new java.awt.Dimension(846, 398));

        Fondo.setMaximumSize(new java.awt.Dimension(846, 398));
        Fondo.setMinimumSize(new java.awt.Dimension(846, 398));

        Fotos.setBackground(new java.awt.Color(102, 102, 255));

        javax.swing.GroupLayout FotosLayout = new javax.swing.GroupLayout(Fotos);
        Fotos.setLayout(FotosLayout);
        FotosLayout.setHorizontalGroup(
            FotosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 253, Short.MAX_VALUE)
        );
        FotosLayout.setVerticalGroup(
            FotosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 262, Short.MAX_VALUE)
        );

        PanelNoticia.setBackground(new java.awt.Color(204, 255, 204));

        javax.swing.GroupLayout PanelNoticiaLayout = new javax.swing.GroupLayout(PanelNoticia);
        PanelNoticia.setLayout(PanelNoticiaLayout);
        PanelNoticiaLayout.setHorizontalGroup(
            PanelNoticiaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 444, Short.MAX_VALUE)
        );
        PanelNoticiaLayout.setVerticalGroup(
            PanelNoticiaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 123, Short.MAX_VALUE)
        );

        Pasadas.setBackground(new java.awt.Color(51, 51, 51));

        P1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                P1ActionPerformed(evt);
            }
        });

        P2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                P2ActionPerformed(evt);
            }
        });

        P3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                P3ActionPerformed(evt);
            }
        });

        P4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                P4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PasadasLayout = new javax.swing.GroupLayout(Pasadas);
        Pasadas.setLayout(PasadasLayout);
        PasadasLayout.setHorizontalGroup(
            PasadasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PasadasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(P1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(P2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(P3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(P4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        PasadasLayout.setVerticalGroup(
            PasadasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PasadasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PasadasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(P1)
                    .addComponent(P2)
                    .addComponent(P3)
                    .addComponent(P4))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout FondoLayout = new javax.swing.GroupLayout(Fondo);
        Fondo.setLayout(FondoLayout);
        FondoLayout.setHorizontalGroup(
            FondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(FondoLayout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(Fotos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 73, Short.MAX_VALUE)
                .addGroup(FondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(PanelNoticia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Pasadas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(45, 45, 45))
        );
        FondoLayout.setVerticalGroup(
            FondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(FondoLayout.createSequentialGroup()
                .addGroup(FondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(FondoLayout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addComponent(Fotos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(FondoLayout.createSequentialGroup()
                        .addGap(122, 122, 122)
                        .addComponent(PanelNoticia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
                .addComponent(Pasadas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(Fondo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Fondo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void P1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_P1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_P1ActionPerformed

    private void P2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_P2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_P2ActionPerformed

    private void P4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_P4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_P4ActionPerformed

    private void P3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_P3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_P3ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Fondo;
    private javax.swing.JPanel Fotos;
    private javax.swing.JCheckBox P1;
    private javax.swing.JCheckBox P2;
    private javax.swing.JCheckBox P3;
    private javax.swing.JCheckBox P4;
    private javax.swing.JPanel PanelNoticia;
    private javax.swing.JPanel Pasadas;
    // End of variables declaration//GEN-END:variables
}
