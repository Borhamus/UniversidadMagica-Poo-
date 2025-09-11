/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package integradorobjetos.vista;

import integradorobjetos.modelo.Carrera;
import integradorobjetos.modelo.Facultad;
import integradorobjetos.modelo.Plan;
import integradorobjetos.modelo.Planes.PlanA;
import integradorobjetos.modelo.Planes.PlanB;
import integradorobjetos.modelo.Planes.PlanC;
import integradorobjetos.modelo.Planes.PlanD;
import integradorobjetos.modelo.Planes.PlanE;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComponent;
import javax.swing.JLayer;
import javax.swing.JOptionPane;

/**
 *
 * @author Borhamus
 */
public class VistaCarrera2 extends javax.swing.JPanel {

    /**
     * Creates new form VistaCarrera2
     */
    public VistaCarrera2() {
        initComponents();
        
        // Aplicar estilo mágico a los botones
        Style.estiloBotonMagico2(ConfirmarBoton);

        // CAMBIAR LAS FUENTES DE LOS BOTONES
        ConfirmarBoton.setFont(Style.FUENTE_NORMAL);
        
        
        Fondo.setOpaque(false);
        
        // Crear el componente del ojo mágico
        OjoMagico ojoMagico = new OjoMagico();
        
        // Configurar el panel Ojo para que contenga nuestro ojo mágico
        Ojo.removeAll(); // Limpiar el panel
        Ojo.setLayout(new BorderLayout()); // Establecer un layout
        Ojo.add(ojoMagico, BorderLayout.CENTER); // Añadir el ojo mágico
        
        // IMPORTANTE: Establecer un tamaño preferido para el panel Ojo
        Ojo.setPreferredSize(new Dimension(547, 119)); // Alto de 100px para que se vea el ojo
        
        // Establecer dimensiones exactas
        setMaximumSize(new java.awt.Dimension(846, 398));
        setMinimumSize(new java.awt.Dimension(846, 398));
        setPreferredSize(new java.awt.Dimension(846, 398));
        
        // Configurar el panel Fondo para que ocupe todo el espacio
        Fondo.setMaximumSize(new java.awt.Dimension(846, 398));
        Fondo.setMinimumSize(new java.awt.Dimension(846, 398));
        Fondo.setPreferredSize(new java.awt.Dimension(846, 398));
        
        //Estilo estrellado
        JLayer<JComponent> capaEstrellada = Style.aplicarFondoEstrellado(Fondo, 1, 30);
        setLayout(new BorderLayout());
        remove(Fondo);
        add(capaEstrellada, BorderLayout.CENTER);
        
        // Llenar el combo box con los planes de estudio reales
        PlanDeEstudio.setModel(new DefaultComboBoxModel<>(new String[]{"PlanA", "PlanB", "PlanC", "PlanD", "PlanE"}));
        
        // Configurar listeners para los campos de texto
        configurarListeners();
    }

        private void configurarListeners() {
        // Listener para el campo Nombre
        Nombre.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (Nombre.getText().equals("Nombre de Carrera:")) {
                    Nombre.setText("");
                }
            }
            
            @Override
            public void focusLost(FocusEvent e) {
                if (Nombre.getText().isEmpty()) {
                    Nombre.setText("Nombre de Carrera:");
                }
            }
        });
        
        // Listener para el botón de creación - CORREGIDO: usar ConfirmarBoton en lugar de CrearCarreraBoton
        ConfirmarBoton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validarCampos()) {
                    crearCarrera();
                }
            }
        });
    }
    
    private boolean validarCampos() {
        // Validar nombre
        if (Nombre.getText().isEmpty() || Nombre.getText().equals("Nombre de Carrera:")) {
            JOptionPane.showMessageDialog(this, 
                "Debe ingresar un nombre válido para la carrera", 
                "Error de Validación", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        // Validar plan de estudio
        if (PlanDeEstudio.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(this, 
                "Debe seleccionar un plan de estudio", 
                "Error de Validación", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        return true;
    }
    
    private void crearCarrera() {
        try {
            // Obtener datos del formulario
            String nombre = Nombre.getText();
            String planSeleccionado = (String) PlanDeEstudio.getSelectedItem();
            
            // Validar que no exista una carrera con el mismo nombre
            Carrera carreraExistente = Facultad.getInstance().buscarCarrera(nombre);
            if (carreraExistente != null) {
                JOptionPane.showMessageDialog(this, 
                    "Ya existe una carrera con ese nombre", 
                    "Error de Validación", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Crear nueva carrera usando el método de Facultad (con optativas=0 por defecto)
            Carrera nuevaCarrera = Facultad.getInstance().crearCarrera(nombre, 0);
            
            // Crear el plan de estudio seleccionado
            Plan plan = crearPlanDeEstudio(planSeleccionado);
            
            // Asignar el plan a la carrera
            nuevaCarrera.seleccionarPlanDeEstudio(plan);
            
            // Mostrar mensaje de éxito
            JOptionPane.showMessageDialog(this, 
                "Carrera creada con éxito\n" +
                "Nombre: " + nombre + "\n" +
                "Plan de Estudio: " + planSeleccionado, 
                "Creación Exitosa", JOptionPane.INFORMATION_MESSAGE);
            
            // Después de que el usuario presiona OK, redirigir a VistaCarrera1
            redirigirAVistaCarrera1();
            
            // Limpiar campos
            Nombre.setText("Nombre de Carrera:");
            PlanDeEstudio.setSelectedIndex(-1);
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                "Error al crear carrera: " + ex.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private Plan crearPlanDeEstudio(String nombrePlan) {
        switch (nombrePlan) {
            case "PlanA":
                return new PlanA();
            case "PlanB":
                return new PlanB();
            case "PlanC":
                return new PlanC();
            case "PlanD":
                return new PlanD();
            case "PlanE":
                return new PlanE();
            default:
                throw new IllegalArgumentException("Plan de estudio no válido: " + nombrePlan);
        }
    }
    
    private void redirigirAVistaCarrera1() {
        VistaCarrera1 panel = new VistaCarrera1();
            Fondo.removeAll();
            Fondo.setLayout(new BorderLayout());
            Fondo.add(panel, BorderLayout.CENTER);
            Fondo.revalidate();
            Fondo.repaint();
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
        MarcoDeTexto = new javax.swing.JPanel();
        Nombre = new javax.swing.JTextField();
        PlanDeEstudio = new javax.swing.JComboBox<>();
        ConfirmarBoton = new javax.swing.JButton();
        Ojo = new javax.swing.JPanel();

        setMaximumSize(new java.awt.Dimension(846, 398));
        setMinimumSize(new java.awt.Dimension(846, 398));

        Fondo.setMaximumSize(new java.awt.Dimension(846, 398));
        Fondo.setMinimumSize(new java.awt.Dimension(846, 398));

        MarcoDeTexto.setBackground(new java.awt.Color(0, 153, 153));

        Nombre.setText("Nombre de Carrera:");
        Nombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NombreActionPerformed(evt);
            }
        });

        PlanDeEstudio.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout MarcoDeTextoLayout = new javax.swing.GroupLayout(MarcoDeTexto);
        MarcoDeTexto.setLayout(MarcoDeTextoLayout);
        MarcoDeTextoLayout.setHorizontalGroup(
            MarcoDeTextoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MarcoDeTextoLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(MarcoDeTextoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(PlanDeEstudio, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Nombre, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(31, Short.MAX_VALUE))
        );
        MarcoDeTextoLayout.setVerticalGroup(
            MarcoDeTextoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, MarcoDeTextoLayout.createSequentialGroup()
                .addContainerGap(29, Short.MAX_VALUE)
                .addComponent(Nombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(PlanDeEstudio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27))
        );

        ConfirmarBoton.setText("Confirmar");
        ConfirmarBoton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ConfirmarBotonActionPerformed(evt);
            }
        });

        Ojo.setBackground(new java.awt.Color(0, 0, 0));
        Ojo.setMaximumSize(new java.awt.Dimension(547, 119));
        Ojo.setMinimumSize(new java.awt.Dimension(547, 119));
        Ojo.setPreferredSize(new java.awt.Dimension(547, 119));

        javax.swing.GroupLayout OjoLayout = new javax.swing.GroupLayout(Ojo);
        Ojo.setLayout(OjoLayout);
        OjoLayout.setHorizontalGroup(
            OjoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        OjoLayout.setVerticalGroup(
            OjoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 119, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout FondoLayout = new javax.swing.GroupLayout(Fondo);
        Fondo.setLayout(FondoLayout);
        FondoLayout.setHorizontalGroup(
            FondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(FondoLayout.createSequentialGroup()
                .addGroup(FondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(FondoLayout.createSequentialGroup()
                        .addGap(349, 349, 349)
                        .addComponent(ConfirmarBoton, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(FondoLayout.createSequentialGroup()
                        .addGap(146, 146, 146)
                        .addGroup(FondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(MarcoDeTexto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(Ojo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(153, Short.MAX_VALUE))
        );
        FondoLayout.setVerticalGroup(
            FondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(FondoLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(Ojo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(MarcoDeTexto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(ConfirmarBoton, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(40, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 846, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(Fondo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 398, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(Fondo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void NombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_NombreActionPerformed

    private void ConfirmarBotonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ConfirmarBotonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ConfirmarBotonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ConfirmarBoton;
    private javax.swing.JPanel Fondo;
    private javax.swing.JPanel MarcoDeTexto;
    private javax.swing.JTextField Nombre;
    private javax.swing.JPanel Ojo;
    private javax.swing.JComboBox<String> PlanDeEstudio;
    // End of variables declaration//GEN-END:variables
}
