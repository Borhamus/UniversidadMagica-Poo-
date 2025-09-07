/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package integradorobjetos.vista;

import integradorobjetos.modelo.Alumno;
import integradorobjetos.modelo.Carrera;
import integradorobjetos.modelo.Facultad;
import integradorobjetos.vista.Style;
import integradorobjetos.vista.VistaAlumno4;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLayer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Borhamus
 */
public class VistaAlumno1 extends javax.swing.JPanel {

    /**
     * Creates new form VistaAlumno1
     */
    public VistaAlumno1() {
        initComponents();
        
        // Crear el componente del ojo mágico
        OjoMagico ojoMagico = new OjoMagico();
        
        // Configurar el panel Ojo para que contenga nuestro ojo mágico
        Ojo.removeAll(); // Limpiar el panel
        Ojo.setLayout(new BorderLayout()); // Establecer un layout
        Ojo.add(ojoMagico, BorderLayout.CENTER); // Añadir el ojo mágico
        
        // IMPORTANTE: Establecer un tamaño preferido para el panel Ojo
        Ojo.setPreferredSize(new Dimension(433, 100)); // Alto de 100px para que se vea el ojo

        

        
        
        Fondo.setOpaque(false);
        
        // Establecer dimensiones exactas
        setMaximumSize(new java.awt.Dimension(846, 398));
        setMinimumSize(new java.awt.Dimension(846, 398));
        setPreferredSize(new java.awt.Dimension(846, 398));
        
        // Configurar el panel Fondo para que ocupe todo el espacio
        Fondo.setMaximumSize(new java.awt.Dimension(846, 398));
        Fondo.setMinimumSize(new java.awt.Dimension(846, 398));
        Fondo.setPreferredSize(new java.awt.Dimension(846, 398));
        
        JLayer<JComponent> capaEstrellada = Style.aplicarFondoEstrellado(Fondo, 1, 30);
        setLayout(new BorderLayout());
        remove(Fondo);
        add(capaEstrellada, BorderLayout.CENTER);
        
        // Inicializar componentes con datos del modelo
        inicializarComponentes();
        actualizarTabla();
        
        
    }
    
    private void inicializarComponentes() {
    // Configurar modelo de tabla
    DefaultTableModel modeloTabla = new DefaultTableModel();
    modeloTabla.addColumn("Nombre");
    modeloTabla.addColumn("DNI");
    modeloTabla.addColumn("Carrera");
    modeloTabla.addColumn("Estado");
    TablaContenido.setModel(modeloTabla);
    
    // Cargar opciones especiales y carreras en el combo
    Facultad facultad = Facultad.getInstance();
    DefaultComboBoxModel<String> modeloCombo = new DefaultComboBoxModel<>();
    
    // Agregar opciones especiales al principio
    modeloCombo.addElement("Todos");
    modeloCombo.addElement("En ninguna carrera");
    
    // Agregar carreras después de las opciones especiales
    for (Carrera carrera : facultad.getCarreras()) {
        modeloCombo.addElement(carrera.getNombre());
    }
    
    BarraDeCarreras.setModel(modeloCombo);
    
    // Agregar listener para actualizar tabla al cambiar selección
    BarraDeCarreras.addItemListener(new ItemListener() {
        @Override
        public void itemStateChanged(ItemEvent e) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                actualizarTabla();
            }
        }
    });
    
    // Seleccionar "Todos" por defecto
    if (modeloCombo.getSize() > 0) {
        BarraDeCarreras.setSelectedIndex(0);
    }
}

private void actualizarTabla() {
    String seleccion = (String) BarraDeCarreras.getSelectedItem();
    if (seleccion == null) return;
    
    Facultad facultad = Facultad.getInstance();
    List<Alumno> alumnos = facultad.getAlumnos();
    DefaultTableModel modelo = (DefaultTableModel) TablaContenido.getModel();
    modelo.setRowCount(0); // Limpiar tabla
    
    for (Alumno alumno : alumnos) {
        String nombreCarrera = "";
        String estado = "";
        
        // Determinar la carrera y estado del alumno
        if (alumno.getCarreraInscripta() != null) {
            nombreCarrera = alumno.getCarreraInscripta().getNombre();
            estado = "Inscripto";
        } else {
            nombreCarrera = "Ninguna";
            estado = "Sin carrera";
        }
        
        // Filtrar según la selección del combo
        boolean agregarAlumno = false;
        
        if (seleccion.equals("Todos")) {
            agregarAlumno = true;
        } 
        else if (seleccion.equals("En ninguna carrera") && alumno.getCarreraInscripta() == null) {
            agregarAlumno = true;
        } 
        else if (alumno.getCarreraInscripta() != null && 
                 alumno.getCarreraInscripta().getNombre().equals(seleccion)) {
            agregarAlumno = true;
        }
        
        // Agregar fila si cumple el filtro
        if (agregarAlumno) {
            Object[] fila = {
                alumno.getNombre(),
                alumno.getDni(),
                nombreCarrera,
                estado
            };
            modelo.addRow(fila);
        }
    }
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
        BarraDeCarreras = new javax.swing.JComboBox<>();
        ContTabla = new javax.swing.JScrollPane();
        TablaContenido = new javax.swing.JTable();
        Ojo = new javax.swing.JPanel();
        CrearAlumnoBoton = new javax.swing.JButton();
        EliminarAlumnoBoton = new javax.swing.JButton();

        setMaximumSize(new java.awt.Dimension(846, 398));
        setMinimumSize(new java.awt.Dimension(846, 398));
        setPreferredSize(new java.awt.Dimension(846, 398));

        Fondo.setMaximumSize(new java.awt.Dimension(846, 398));
        Fondo.setMinimumSize(new java.awt.Dimension(846, 398));
        Fondo.setPreferredSize(new java.awt.Dimension(846, 398));

        BarraDeCarreras.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Carreras" }));

        TablaContenido.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        ContTabla.setViewportView(TablaContenido);

        Ojo.setBackground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout OjoLayout = new javax.swing.GroupLayout(Ojo);
        Ojo.setLayout(OjoLayout);
        OjoLayout.setHorizontalGroup(
            OjoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 433, Short.MAX_VALUE)
        );
        OjoLayout.setVerticalGroup(
            OjoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        CrearAlumnoBoton.setText("Crear Alumno");
        CrearAlumnoBoton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CrearAlumnoBotonActionPerformed(evt);
            }
        });

        EliminarAlumnoBoton.setText("Eliminar Alumno");
        EliminarAlumnoBoton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EliminarAlumnoBotonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout FondoLayout = new javax.swing.GroupLayout(Fondo);
        Fondo.setLayout(FondoLayout);
        FondoLayout.setHorizontalGroup(
            FondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(FondoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(FondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ContTabla)
                    .addGroup(FondoLayout.createSequentialGroup()
                        .addGroup(FondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(FondoLayout.createSequentialGroup()
                                .addComponent(Ojo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(BarraDeCarreras, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(FondoLayout.createSequentialGroup()
                                .addComponent(CrearAlumnoBoton, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(EliminarAlumnoBoton, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 3, Short.MAX_VALUE)))
                .addContainerGap())
        );
        FondoLayout.setVerticalGroup(
            FondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, FondoLayout.createSequentialGroup()
                .addGroup(FondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(FondoLayout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(Ojo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18))
                    .addGroup(FondoLayout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(BarraDeCarreras, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)))
                .addComponent(ContTabla, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(FondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CrearAlumnoBoton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(EliminarAlumnoBoton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Fondo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Fondo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void CrearAlumnoBotonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CrearAlumnoBotonActionPerformed
        VistaAlumno4 panel = new VistaAlumno4();
        Fondo.removeAll();
        Fondo.setLayout(new BorderLayout());
        Fondo.add(panel, BorderLayout.CENTER);
        Fondo.revalidate();
        Fondo.repaint();
         
    }//GEN-LAST:event_CrearAlumnoBotonActionPerformed

    private void EliminarAlumnoBotonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EliminarAlumnoBotonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_EliminarAlumnoBotonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> BarraDeCarreras;
    private javax.swing.JScrollPane ContTabla;
    private javax.swing.JButton CrearAlumnoBoton;
    private javax.swing.JButton EliminarAlumnoBoton;
    private javax.swing.JPanel Fondo;
    private javax.swing.JPanel Ojo;
    private javax.swing.JTable TablaContenido;
    // End of variables declaration//GEN-END:variables
}
