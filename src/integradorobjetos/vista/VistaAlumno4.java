/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package integradorobjetos.vista;
import integradorobjetos.modelo.Alumno;
import integradorobjetos.modelo.Carrera;
import integradorobjetos.modelo.Facultad;
import integradorobjetos.vista.OjoMagico;
import integradorobjetos.vista.Style;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JLayer;
import javax.swing.JOptionPane;
import javax.swing.border.EmptyBorder;
import integradorobjetos.vista.VistaAlumno1;
/**
 *
 * @author Borhamus
 */
public class VistaAlumno4 extends javax.swing.JPanel {
    /**
     * Creates new form VistaAlumno4
     */
    public VistaAlumno4() {
        initComponents();
        
        // Crear el componente del ojo mágico
        OjoMagico ojoMagico = new OjoMagico();
        
        // Configurar el panel Ojo para que contenga nuestro ojo mágico
        Ojo.removeAll(); // Limpiar el panel
        Ojo.setLayout(new BorderLayout()); // Establecer un layout
        Ojo.add(ojoMagico, BorderLayout.CENTER); // Añadir el ojo mágico
        
        // IMPORTANTE: Establecer un tamaño preferido para el panel Ojo
        Ojo.setPreferredSize(new Dimension(475, 116)); // Alto de 100px para que se vea el ojo
        
        Fondo.setOpaque(false);
        
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
        
        // Configurar listeners para los campos de texto
        configurarListeners();
    }
    
    private void configurarListeners() {
        // Listener para el campo Nombre
        Nombre.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (Nombre.getText().equals("Nombre:")) {
                    Nombre.setText("");
                }
            }
            
            @Override
            public void focusLost(FocusEvent e) {
                if (Nombre.getText().isEmpty()) {
                    Nombre.setText("Nombre:");
                }
            }
        });
        
        // Listener para el campo DNI
        Dni.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (Dni.getText().equals("DNI:")) {
                    Dni.setText("");
                }
            }
            
            @Override
            public void focusLost(FocusEvent e) {
                if (!Dni.getText().isEmpty()) {
                    validarDNI();
                } else {
                    Dni.setText("DNI:");
                }
            }
        });
        
        // Listener para el botón de inscripción
        InscripcionBoton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validarCampos()) {
                    inscribirAlumno();
                }
            }
        });
    }
    
    private boolean validarDNI() {
        String dni = Dni.getText();
        
        // Validar formato (5 dígitos)
        if (!dni.matches("\\d{5}")) {
            JOptionPane.showMessageDialog(this, 
                "El DNI debe contener exactamente 5 dígitos numéricos", 
                "Error de DNI", JOptionPane.ERROR_MESSAGE);
            Dni.setText("DNI:");
            return false;
        }
        
        // Validar que no exista en la facultad usando el método real
        String dniCompleto = "MAGO-" + dni + "-ARCANA";
        Alumno alumnoExistente = Facultad.getInstance().buscarAlumnoPorDni(dniCompleto);
        
        if (alumnoExistente != null) {
            JOptionPane.showMessageDialog(this, 
                "Este DNI ya está registrado en la facultad\n" +
                "Alumno: " + alumnoExistente.getNombre(), 
                "Error de DNI", JOptionPane.ERROR_MESSAGE);
            Dni.setText("DNI:");
            return false;
        }
        
        return true;
    }
    
    private boolean validarCampos() {
        // Validar nombre
        if (Nombre.getText().isEmpty() || Nombre.getText().equals("Nombre:")) {
            JOptionPane.showMessageDialog(this, 
                "Debe ingresar un nombre válido", 
                "Error de Validación", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        // Validar DNI
        if (Dni.getText().isEmpty() || Dni.getText().equals("DNI:")) {
            JOptionPane.showMessageDialog(this, 
                "Debe ingresar un DNI válido", 
                "Error de Validación", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        return validarDNI();
    }
    
    private void inscribirAlumno() {
    try {
        // Obtener datos del formulario
        String nombre = Nombre.getText();
        String dniIngresado = Dni.getText();
        String dniCompleto = "MAGO-" + dniIngresado + "-ARCANA";
        
        // Crear nuevo alumno usando el método de Facultad
        Facultad.getInstance().crearAlumno(nombre, dniCompleto);
        
        // Mostrar mensaje de éxito
        JOptionPane.showMessageDialog(this, 
            "Alumno creado con éxito\n" +
            "Nombre: " + nombre + "\n" +
            "DNI: " + dniCompleto + "\n" +
            "Estado: Sin carrera asignada", 
            "Creación Exitosa", JOptionPane.INFORMATION_MESSAGE);
        
        // Después de que el usuario presiona OK, redirigir a VistaAlumno1
        redirigirAVistaAlumno1();
        
        // Limpiar campos
        Nombre.setText("Nombre:");
        Dni.setText("DNI:");
        
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, 
            "Error al crear alumno: " + ex.getMessage(), 
            "Error", JOptionPane.ERROR_MESSAGE);
    }
}

    private void redirigirAVistaAlumno1() {
        VistaAlumno1 panel = new VistaAlumno1();
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
        Dni = new javax.swing.JTextField();
        Info = new java.awt.Label();
        InscripcionBoton = new javax.swing.JButton();
        Ojo = new javax.swing.JPanel();

        setMaximumSize(new java.awt.Dimension(846, 398));
        setMinimumSize(new java.awt.Dimension(846, 398));

        Fondo.setMaximumSize(new java.awt.Dimension(846, 398));
        Fondo.setMinimumSize(new java.awt.Dimension(846, 398));

        MarcoDeTexto.setBackground(new java.awt.Color(0, 0, 102));

        Nombre.setText("Nombre:");

        Dni.setText("DNI:");

        Info.setForeground(new java.awt.Color(255, 255, 255));
        Info.setText("Formato: MAGO-XXXXX-ARCANA (solo ingrese los 5 dígitos)");

        javax.swing.GroupLayout MarcoDeTextoLayout = new javax.swing.GroupLayout(MarcoDeTexto);
        MarcoDeTexto.setLayout(MarcoDeTextoLayout);
        MarcoDeTextoLayout.setHorizontalGroup(
            MarcoDeTextoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MarcoDeTextoLayout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(MarcoDeTextoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Info, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Dni, javax.swing.GroupLayout.PREFERRED_SIZE, 399, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Nombre, javax.swing.GroupLayout.PREFERRED_SIZE, 399, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(45, Short.MAX_VALUE))
        );
        MarcoDeTextoLayout.setVerticalGroup(
            MarcoDeTextoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MarcoDeTextoLayout.createSequentialGroup()
                .addContainerGap(24, Short.MAX_VALUE)
                .addComponent(Nombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Info, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(Dni, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16))
        );

        InscripcionBoton.setText("Inscribir a Carrera");

        Ojo.setBackground(new java.awt.Color(0, 0, 0));
        Ojo.setMaximumSize(new java.awt.Dimension(475, 116));
        Ojo.setMinimumSize(new java.awt.Dimension(475, 116));
        Ojo.setPreferredSize(new java.awt.Dimension(475, 116));

        javax.swing.GroupLayout OjoLayout = new javax.swing.GroupLayout(Ojo);
        Ojo.setLayout(OjoLayout);
        OjoLayout.setHorizontalGroup(
            OjoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        OjoLayout.setVerticalGroup(
            OjoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 116, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout FondoLayout = new javax.swing.GroupLayout(Fondo);
        Fondo.setLayout(FondoLayout);
        FondoLayout.setHorizontalGroup(
            FondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, FondoLayout.createSequentialGroup()
                .addGap(0, 188, Short.MAX_VALUE)
                .addGroup(FondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(Ojo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(MarcoDeTexto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(183, 183, 183))
            .addGroup(FondoLayout.createSequentialGroup()
                .addGap(348, 348, 348)
                .addComponent(InscripcionBoton, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        FondoLayout.setVerticalGroup(
            FondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(FondoLayout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(Ojo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(MarcoDeTexto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(48, 48, 48)
                .addComponent(InscripcionBoton, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35))
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


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField Dni;
    private javax.swing.JPanel Fondo;
    private java.awt.Label Info;
    private javax.swing.JButton InscripcionBoton;
    private javax.swing.JPanel MarcoDeTexto;
    private javax.swing.JTextField Nombre;
    private javax.swing.JPanel Ojo;
    // End of variables declaration//GEN-END:variables
}
