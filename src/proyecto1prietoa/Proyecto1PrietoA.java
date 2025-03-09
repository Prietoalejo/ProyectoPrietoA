/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package proyecto1prietoa;

/**
 *
 * @author Prietoalejo
 */
public class Proyecto1PrietoA {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
    // Crear un campo de minas de 5x5
    CampoMinas campo = new CampoMinas(5, 5);
    
    // Marcar 5 bombas en el campo
    campo.marcarBombas(5);
    
    // Dibujar el estado inicial del campo
    campo.dibujarGrafo();
    
    // Inicializar la matriz de visitadas
    boolean[][] visitadas = new boolean[5][5];
    
    // Simular un clic en la casilla A1 (que corresponde a matriz[0][0])
    Adyacentes recorrido = campo.bfs(campo.matriz[0][0], visitadas);
    
    // Mostrar las casillas visitadas
    System.out.println("Casillas visitadas:");
    recorrido.imprimir();
}
    
    
}
