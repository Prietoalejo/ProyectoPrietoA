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
        // TODO code application logic here

        CampoMinas campo = new CampoMinas(5, 5); // Crear un campo de 5x5
    campo.marcarBombas(5); // Marcar 5 bombas
    campo.dibujarGrafo(); // Dibujar el grafo

    // Inicializar la matriz de visitadas
    boolean[][] visitadas = new boolean[5][5];

    // Simular un clic en la casilla A1
    Adyacentes recorrido = campo.dfs(campo.matriz[0][0], visitadas); // Asumiendo que A1 es la casilla en (0, 0)

    // Mostrar el recorrido
    System.out.println("Casillas visitadas:");
    recorrido.imprimir();
}
    
    
}
