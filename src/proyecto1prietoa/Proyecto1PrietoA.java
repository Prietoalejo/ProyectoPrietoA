/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package proyecto1prietoa;

import Ventanas.MenuPrincipal;
import Ventanas.Tablero;
import javax.swing.JOptionPane;

/**
 *
 * @author Prietoalejo
 */
public class Proyecto1PrietoA {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        CampoMinas campo = new CampoMinas(5, 5);
        
        campo.marcarBombas(5);        
        for (int i = 0; i < campo.filas; i++) {
            for (int j = 0; j < campo.columnas; j++) {
                Casilla casilla = campo.matriz[i][j];
                System.out.print(casilla.id + "(M:" + casilla.mina + ", R:" + casilla.mostrada + ", B:" + casilla.bandera + ") ");
            }
            System.out.println();
        }
        LectorCSV partidaCsv = new LectorCSV();
        String nombreArchivo = "partida_buscaminas.csv";
        partidaCsv.guardarPartidaCSV(nombreArchivo, campo);
        System.out.println("Partida guardada en: " + nombreArchivo);        
        CampoMinas nuevoCampo = partidaCsv.leerPartidaCSV(nombreArchivo);
        System.out.println("Partida cargada desde: " + nombreArchivo);        
        System.out.println("Estado del campo cargado:");
        for (int i = 0; i < nuevoCampo.filas; i++) {
            for (int j = 0; j < nuevoCampo.columnas; j++) {
                Casilla casilla = nuevoCampo.matriz[i][j];
                System.out.print(casilla.id + "(M:" + casilla.mina + ", R:" + casilla.mostrada + ", B:" + casilla.bandera + ") ");
            }
            System.out.println();
        }
        MenuPrincipal p = new MenuPrincipal();
//        Tablero t = new Tablero();
        
}
    
    
}
