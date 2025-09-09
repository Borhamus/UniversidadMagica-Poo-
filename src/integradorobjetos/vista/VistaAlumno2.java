/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package integradorobjetos.vista;

import integradorobjetos.modelo.Alumno;
import integradorobjetos.modelo.Carrera;
import integradorobjetos.modelo.EstadoInscripcion;
import integradorobjetos.modelo.InscripcionMateria;
import integradorobjetos.modelo.Materia;
import integradorobjetos.vista.Style;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.JLayer;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Borhamus
 */
public class VistaAlumno2 extends javax.swing.JPanel {
    private Alumno alumno;

    
    public VistaAlumno2(Alumno alumno) {
        this.alumno = alumno;
        initComponents();
        Fondo.setOpaque(false);
        
        // Establecer dimensiones exactas
        setMaximumSize(new java.awt.Dimension(846, 398));
        setMinimumSize(new java.awt.Dimension(846, 398));
        setPreferredSize(new java.awt.Dimension(846, 398));
        
        // Configurar el panel Fondo para que ocupe todo el espacio
        Fondo.setMaximumSize(new java.awt.Dimension(846, 398));
        Fondo.setMinimumSize(new java.awt.Dimension(846, 398));
        Fondo.setPreferredSize(new java.awt.Dimension(846, 398));
        
        // Estilo estrellado
        JLayer<JComponent> capaEstrellada = Style.aplicarFondoEstrellado(Fondo, 1, 30);
        setLayout(new BorderLayout());
        remove(Fondo);
        add(capaEstrellada, BorderLayout.CENTER);
        
        // Crear el componente del ojo mágico
        OjoMagico ojoMagico = new OjoMagico();
        
        // Configurar el panel Ojo para que contenga nuestro ojo mágico
        Ojo.removeAll(); // Limpiar el panel
        Ojo.setLayout(new BorderLayout()); // Establecer un layout
        Ojo.add(ojoMagico, BorderLayout.CENTER); // Añadir el ojo mágico
        
        // IMPORTANTE: Establecer un tamaño preferido para el panel Ojo
        Ojo.setPreferredSize(new Dimension(200, 40)); // Alto de 100px para que se vea el ojo
        
        // Cargar datos del alumno
        cargarDatosAlumno();
    }
    
    private void cargarDatosAlumno() {
        // 1. Cargar nombre completo del alumno
        Nombre.setText("Nombre: " + alumno.getNombre());

        // 2. Cargar DNI con formato especial
        Dni.setText("DNI: " + alumno.getDni() );

        // 3. Cargar carreras finalizadas
        StringBuilder carrerasFinalizadas = new StringBuilder("Carreras Finalizadas: ");

        if (alumno.getCarrerasTerminadas().isEmpty()) {
            carrerasFinalizadas.append("Ninguna");
        } else {
            for (Carrera carrera : alumno.getCarrerasTerminadas()) {
                carrerasFinalizadas.append(carrera.getNombre()).append(", ");
            }
            // Eliminar la última coma y espacio
            carrerasFinalizadas.setLength(carrerasFinalizadas.length() - 2);
        }

        CF.setText(carrerasFinalizadas.toString());

        // 4. Cargar carrera cursando actualmente
        String textoCarreraActiva = "Carrera Activa: ";
        if (alumno.getCarreraInscripta() != null) {
            textoCarreraActiva += alumno.getCarreraInscripta().getNombre();
        } else {
            textoCarreraActiva += "Sin Carrera";
        }

        CC.setText(textoCarreraActiva);

        // 5. Cargar tabla de materias
        cargarTablaMaterias();
    }
    
    private void cargarTablaMaterias() {
        // Crear modelo para la tabla de materias
        InscripcionMateriaTableModel modelo = new InscripcionMateriaTableModel(alumno.getInscripciones());
        Materias.setModel(modelo);
        
        // Configurar columnas
        Materias.getColumnModel().getColumn(0).setPreferredWidth(200); // Materias Inscriptas
        Materias.getColumnModel().getColumn(1).setPreferredWidth(100); // Cuatrimestre
        Materias.getColumnModel().getColumn(2).setPreferredWidth(150); // Estado
    }
    
    // Modelo de tabla para las inscripciones a materias
    class InscripcionMateriaTableModel extends AbstractTableModel {
        private List<InscripcionMateria> inscripciones;
        private String[] columnNames = {"Materias Inscriptas", "Cuatrimestre", "Estado"}; // Columnas solicitadas
        
        public InscripcionMateriaTableModel(List<InscripcionMateria> inscripciones) {
            this.inscripciones = new ArrayList<>(inscripciones);
        }
        
        @Override
        public int getRowCount() {
            return inscripciones.size();
        }
        
        @Override
        public int getColumnCount() {
            return columnNames.length;
        }
        
        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            InscripcionMateria inscripcion = inscripciones.get(rowIndex);
            Materia materia = inscripcion.getMateria();
            
            switch (columnIndex) {
                case 0: // Materias Inscriptas
                    return materia.getNombre();
                case 1: // Cuatrimestre
                    return materia.getCuatrimestre() + "º";
                case 2: // Estado
                    return convertirEstadoToString(inscripcion.getEstado());
                default:
                    return null;
            }
        }
        
        @Override
        public String getColumnName(int column) {
            return columnNames[column];
        }
        
        private String convertirEstadoToString(EstadoInscripcion estado) {
            switch (estado) {
                case INSCRIPTO:
                    return "Inscripto";
                case CURSADA_APROBADA:
                    return "Cursada Aprobada";
                case FINAL_APROBADO:
                    return "Final Aprobado";
                case REPROBADO:
                    return "Reprobado";
                default:
                    return "Desconocido";
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
        MarcoDeTexto = new javax.swing.JPanel();
        Nombre = new javax.swing.JTextField();
        Dni = new javax.swing.JTextField();
        CF = new javax.swing.JTextField();
        CC = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        Materias = new javax.swing.JTable();
        InscribirCarreraBoton = new javax.swing.JButton();
        InscribirMateriaBoton = new javax.swing.JButton();
        Ojo = new javax.swing.JPanel();

        setMaximumSize(new java.awt.Dimension(846, 398));
        setMinimumSize(new java.awt.Dimension(846, 398));
        setPreferredSize(new java.awt.Dimension(846, 398));

        Fondo.setMaximumSize(new java.awt.Dimension(846, 398));
        Fondo.setMinimumSize(new java.awt.Dimension(846, 398));

        MarcoDeTexto.setBackground(new java.awt.Color(0, 153, 153));

        Nombre.setText("Nombre:");

        Dni.setText("DNI:");

        CF.setText("Carreras Finalizadas:");

        CC.setText("Carrera en Curso:");

        javax.swing.GroupLayout MarcoDeTextoLayout = new javax.swing.GroupLayout(MarcoDeTexto);
        MarcoDeTexto.setLayout(MarcoDeTextoLayout);
        MarcoDeTextoLayout.setHorizontalGroup(
            MarcoDeTextoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MarcoDeTextoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(MarcoDeTextoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Nombre)
                    .addComponent(Dni, javax.swing.GroupLayout.DEFAULT_SIZE, 822, Short.MAX_VALUE)
                    .addComponent(CF, javax.swing.GroupLayout.DEFAULT_SIZE, 822, Short.MAX_VALUE)
                    .addComponent(CC, javax.swing.GroupLayout.DEFAULT_SIZE, 822, Short.MAX_VALUE))
                .addContainerGap())
        );
        MarcoDeTextoLayout.setVerticalGroup(
            MarcoDeTextoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MarcoDeTextoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Nombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Dni, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(CF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(CC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        Materias.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(Materias);

        InscribirCarreraBoton.setText("Inscribir a Carrera");
        InscribirCarreraBoton.setFocusable(false);
        InscribirCarreraBoton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                InscribirCarreraBotonActionPerformed(evt);
            }
        });

        InscribirMateriaBoton.setText("Inscribir a Materias");
        InscribirMateriaBoton.setFocusable(false);
        InscribirMateriaBoton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                InscribirMateriaBotonActionPerformed(evt);
            }
        });

        Ojo.setBackground(new java.awt.Color(0, 0, 0));
        Ojo.setMaximumSize(new java.awt.Dimension(200, 40));
        Ojo.setMinimumSize(new java.awt.Dimension(200, 40));
        Ojo.setPreferredSize(new java.awt.Dimension(200, 40));

        javax.swing.GroupLayout OjoLayout = new javax.swing.GroupLayout(Ojo);
        Ojo.setLayout(OjoLayout);
        OjoLayout.setHorizontalGroup(
            OjoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 200, Short.MAX_VALUE)
        );
        OjoLayout.setVerticalGroup(
            OjoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout FondoLayout = new javax.swing.GroupLayout(Fondo);
        Fondo.setLayout(FondoLayout);
        FondoLayout.setHorizontalGroup(
            FondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(FondoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(FondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(MarcoDeTexto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1)
                    .addGroup(FondoLayout.createSequentialGroup()
                        .addComponent(InscribirCarreraBoton, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(InscribirMateriaBoton, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(Ojo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(151, 151, 151)))
                .addContainerGap())
        );
        FondoLayout.setVerticalGroup(
            FondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(FondoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(MarcoDeTexto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addGroup(FondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(FondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(InscribirMateriaBoton, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(InscribirCarreraBoton, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(Ojo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16))
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
            .addGap(0, 399, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(Fondo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void InscribirCarreraBotonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_InscribirCarreraBotonActionPerformed
        // Verificar si el alumno ya tiene una carrera activa
        if (alumno.getCarreraInscripta() != null) {
            // Mostrar mensaje de error
            JOptionPane.showMessageDialog(
                this, 
                "No puedes inscribirte a dos carreras a la vez. Ya tienes una carrera activa.", 
                "Error de Inscripción", 
                JOptionPane.ERROR_MESSAGE
            );
            return; // No hacemos nada más
        }
        
        VistaAlumno3 panel = new VistaAlumno3(this.alumno);
        Fondo.removeAll();
        Fondo.setLayout(new BorderLayout());
        Fondo.add(panel, BorderLayout.CENTER);
        Fondo.revalidate();
        Fondo.repaint();
    }//GEN-LAST:event_InscribirCarreraBotonActionPerformed

    private void InscribirMateriaBotonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_InscribirMateriaBotonActionPerformed
        VistaAlumno5 panel = new VistaAlumno5(this.alumno);
        Fondo.removeAll();
        Fondo.setLayout(new BorderLayout());
        Fondo.add(panel, BorderLayout.CENTER);
        Fondo.revalidate();
        Fondo.repaint();
    }//GEN-LAST:event_InscribirMateriaBotonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField CC;
    private javax.swing.JTextField CF;
    private javax.swing.JTextField Dni;
    private javax.swing.JPanel Fondo;
    private javax.swing.JButton InscribirCarreraBoton;
    private javax.swing.JButton InscribirMateriaBoton;
    private javax.swing.JPanel MarcoDeTexto;
    private javax.swing.JTable Materias;
    private javax.swing.JTextField Nombre;
    private javax.swing.JPanel Ojo;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
