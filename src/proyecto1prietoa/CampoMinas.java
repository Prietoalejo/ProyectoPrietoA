/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto1prietoa;

/**
 *
 * @author Prietoalejo
 */
public class CampoMinas {
    private Casilla[][] matriz;
    private int filas;
    private int columnas;

    public CampoMinas(int m, int n) {
        this.filas = m;
        this.columnas = n;
        this.matriz = new Casilla[m][n];
        crearCuadricula();
    }

    private void crearCuadricula() {
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                String id = String.valueOf((char) ('A' + i)) + (j + 1);
                matriz[i][j] = new Casilla(id);
            }
        }
        conectarCasillas();
    }

    private void conectarCasillas() {
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                for (int x = -1; x <= 1; x++) {
                    for (int y = -1; y <= 1; y++) {
                        if (x == 0 && y == 0) continue; 
                        int nuevoI = i + x;
                        int nuevoJ = j + y;
                        if (nuevoI >= 0 && nuevoI < filas && nuevoJ >= 0 && nuevoJ < columnas) {
                            matriz[i][j].adyacentes.insertar(matriz[nuevoI][nuevoJ].id);
                        }
                    }
                }
            }
        }
    }

    public void mostrarGrafo() {
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                System.out.print("Casilla " + matriz[i][j].id + ": ");
                matriz[i][j].adyacentes.imprimir();
            }
        }
    }
public void dibujarGrafo() {
    // Dibujar la cuadrícula
    for (int i = 0; i < filas; i++) {
        for (int j = 0; j < columnas; j++) {
            System.out.print(matriz[i][j].id + " ");
            if (j < columnas - 1) {
                System.out.print(" - ");
            }
        }
        System.out.println();

        if (i < filas - 1) {
            for (int j = 0; j < columnas; j++) {

                System.out.print(" | ");
                if (j < columnas - 1) {
                    System.out.print("   ");
                }
            }
            System.out.println(); 
        }
    }
    
    System.out.println("\nAdyacencias:");
    for (int i = 0; i < filas; i++) {
        for (int j = 0; j < columnas; j++) {
            System.out.print("Casilla " + matriz[i][j].id + ": ");
            matriz[i][j].adyacentes.imprimir();
        }
    }
}

}