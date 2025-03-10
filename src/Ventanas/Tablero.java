/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Ventanas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import proyecto1prietoa.Adyacentes;
import proyecto1prietoa.CampoMinas;
import proyecto1prietoa.Casilla;
import proyecto1prietoa.LectorCSV;

/**
 *
 * @author Prietoalejo
 */
public class Tablero extends javax.swing.JFrame {

    public static CampoMinas campo;
    boolean bandera;
    public static boolean DFS;

    /**
     * Creates new form Tablero
     */
    public Tablero(CampoMinas campo, boolean DFS) {
        initComponents();
        this.campo = campo;
        this.DFS = DFS;
        bandera = false;
        setTitle("Ventana con Tablero");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panelFondo = new JPanel();
        panelFondo.setBackground(Color.LIGHT_GRAY);
        add(panelFondo, BorderLayout.CENTER); 

        JPanel panelSuperior = new JPanel();
        panelSuperior.setLayout(new BoxLayout(panelSuperior, BoxLayout.Y_AXIS)); 
        String modo;
        if(this.DFS){
          modo = "(DFS)";  
        }else{
            modo = "(BFS)";
        }
        JLabel titulo = new JLabel("Juego de Buscaminas");
        titulo.setFont(new Font("STHupo", Font.BOLD, 48)); 
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT); 
        panelSuperior.add(titulo);


        panelSuperior.add(Box.createRigidArea(new Dimension(0, 40))); 

   
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new FlowLayout(FlowLayout.CENTER));

        JButton btnGuardar = new JButton("Guardar Partida");
        btnGuardar.setFont(new Font("STHupo", Font.PLAIN, 24)); 
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                LectorCSV l = new LectorCSV();
                l.guardarPartidaCSV(buscarArchivo(), campo);
                System.out.println("Partida guardada.");
            }
        });
        panelBotones.add(btnGuardar);
        panelBotones.add(Box.createRigidArea(new Dimension(10, 0))); 


        JButton btnSalir = new JButton("Salir");
        btnSalir.setFont(new Font("STHupo", Font.PLAIN, 24)); 
        btnSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MenuPrincipal m = new MenuPrincipal();
                salir();
            }
        });
        panelBotones.add(btnSalir);
        panelBotones.add(Box.createRigidArea(new Dimension(10, 0))); 

     
        JToggleButton btnBandera = new JToggleButton("🏴");
        btnBandera.setFont(new Font("DEFAULT", Font.PLAIN, 20)); 

        btnBandera.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bandera = !bandera; 

            }
        });
        panelBotones.add(btnBandera);

  
        panelSuperior.add(panelBotones);


        panelFondo.setLayout(new BorderLayout());
        panelFondo.add(panelSuperior, BorderLayout.NORTH);

        
        JPanel tablero = crearTablero(campo.filas, campo.columnas);

     
        JPanel panelInferior = new JPanel();
        panelInferior.setLayout(new FlowLayout(FlowLayout.CENTER)); 

    
        panelInferior.setPreferredSize(new Dimension(0, (int) (Toolkit.getDefaultToolkit().getScreenSize().height * 0.7)));

        panelInferior.add(tablero);

        panelFondo.add(panelInferior, BorderLayout.CENTER); 

        setSize(Toolkit.getDefaultToolkit().getScreenSize());
        setVisible(true);
    }

    public void salir() {
        this.dispose();
    }

    private JPanel crearTablero(int filas, int columnas) {
        JPanel panelTablero = new JPanel();
        panelTablero.setLayout(new GridLayout(filas, columnas));

        int size = (int) (Toolkit.getDefaultToolkit().getScreenSize().height * 0.6 / Math.max(filas, columnas));
        panelTablero.setPreferredSize(new Dimension(size * columnas, size * filas)); 

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                String nombreCasilla = String.valueOf((char) ('A' + i)) + (j + 1);
                JToggleButton boton = new JToggleButton(nombreCasilla);
                boton.setName(nombreCasilla);
                boton.setForeground(Color.black);
                boton.setBackground(Color.LIGHT_GRAY); 
                boton.setOpaque(true);
                boton.setPreferredSize(new Dimension(size, size));
                marcarCasilla(boton, panelTablero);
                boton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (boton.isSelected()) {
                            boton.setForeground(Color.RED);
                            Casilla casilla = campo.buscarID(boton.getName());
                            if (bandera && !casilla.bandera) {
                                boton.setText("🏴");
                                boton.setForeground(Color.red);

                                casilla.bandera = true;
                                return;
                            } else if (bandera && casilla.bandera) {
                                boton.setText(boton.getName());
                                boton.setForeground(Color.black);

                                casilla.bandera = false;
                                return;
                            } else if (!bandera && casilla.bandera) {
                                return;
                            }
                            if (casilla.mina) {
                                JOptionPane.showMessageDialog(rootPane, "HAS PERDIDO");
                            } else {
                                Adyacentes lista;
                                if (DFS) {
                                    lista = campo.dfs(casilla, new boolean[campo.filas][campo.columnas]);
                                } else {
                                    lista = campo.bfs(casilla, new boolean[campo.filas][campo.columnas]);
                                }

                                Casilla actual = lista.primera;
                                while (actual != null) {
   
                                    for (Component comp : panelTablero.getComponents()) {
                                        if (comp instanceof JToggleButton) {
                                            JToggleButton botonCasilla = (JToggleButton) comp;
                                            if (botonCasilla.getText().equals(actual.id)) {
                                                int minasAlrededor = campo.contarBombasAlrededor(actual);
                                                campo.buscarID(actual.id).mostrada = true;

                                                if (minasAlrededor == 0) {
                                                    botonCasilla.setText(String.valueOf(""));

                                                } else {
                                                    botonCasilla.setText(String.valueOf(minasAlrededor));

                                                }

                                                botonCasilla.setSelected(true);

                                                botonCasilla.setBackground(Color.BLACK);
                                                botonCasilla.setEnabled(false);

                                                break;
                                            }
                                        }
                                    }
                                    actual = actual.siguiente;
                                }
                            }

                        } else {
                            Casilla casilla = campo.buscarID(boton.getName());

                            if (bandera && !casilla.bandera) {
                                boton.setText("🏴");
                                casilla.bandera = true;
//                                boton.setEnabled(false);
                                return;
                            } else if (bandera && casilla.bandera) {
                                boton.setText(boton.getName());
                                boton.setForeground(Color.black);

                                casilla.bandera = false;
                                return;
                            } else if (!bandera && casilla.bandera) {
                                return;
                            }
                        }
                    }
                });
                panelTablero.add(boton);
            }
        }

        return panelTablero;
    }

    public void marcarCasilla(JToggleButton boton, JPanel panelTablero) {
        Casilla casilla = campo.buscarID(boton.getName());
        if (casilla.bandera) {
            boton.setText("🏴");
            boton.setForeground(Color.RED);

            boton.setSelected(true);
            return;
        }
        if (casilla.mostrada) {
            int minasAlrededor = campo.contarBombasAlrededor(casilla);
            if (minasAlrededor == 0) {
                boton.setText(String.valueOf(""));

            } else {
                boton.setText(String.valueOf(minasAlrededor));

            }

            boton.setSelected(true);

            boton.setBackground(Color.BLACK);
            boton.setEnabled(false); 
        }
    }

    public String buscarArchivo() {
        JFileChooser fileChooser = new JFileChooser();
        String rutaArchivo = null;

            fileChooser.setDialogTitle("Guardar archivo CSV");
            fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("CSV Files", "csv"));
            int resultado = fileChooser.showSaveDialog(null);
            if (resultado == JFileChooser.APPROVE_OPTION) {
                File archivoSeleccionado = fileChooser.getSelectedFile();
                // Asegurarse de que el archivo tenga la extensión .csv
                if (!archivoSeleccionado.getName().endsWith(".csv")) {
                    archivoSeleccionado = new File(archivoSeleccionado.getAbsolutePath() + ".csv");
                }
                rutaArchivo = archivoSeleccionado.getAbsolutePath();
            }
        
        return rutaArchivo;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(new java.awt.BorderLayout());
        getContentPane().add(jPanel1, java.awt.BorderLayout.PAGE_START);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Tablero.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Tablero.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Tablero.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Tablero.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Tablero(campo, DFS).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
