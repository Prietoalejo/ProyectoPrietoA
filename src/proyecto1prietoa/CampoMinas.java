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
   
    
    private int filas;
    private int columnas;
    private Adyacentes[][] cuadrícula;

    public CampoMinas(int filas, int columnas) {
        this.filas = filas;
        this.columnas = columnas;
        cuadrícula = new Adyacentes[filas][columnas];
        crearCuadrícula();
    }


    private void crearCuadrícula() {
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                cuadrícula[i][j] = new Adyacentes(); 
                String id = "C" + i + "_" + j; 
                insertarCasilla(i, j, id);
            }
        }

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                crearAristas(i, j);
            }
        }
    }


    private void insertarCasilla(int fila, int columna, String id) {
        cuadrícula[fila][columna].insertar(id);
    }


    private void crearAristas(int fila, int columna) {

        int[][] direcciones = {
            {-1, -1}, {-1, 0}, {-1, 1},
            {0, -1},           {0, 1},
            {1, -1}, {1, 0}, {1, 1}
        };


        String idActual = "C" + fila + "_" + columna;

        for (int[] dir : direcciones) {
            int nuevaFila = fila + dir[0];
            int nuevaColumna = columna + dir[1];

         
            if (nuevaFila >= 0 && nuevaFila < filas && nuevaColumna >= 0 && nuevaColumna < columnas) {
                String idAdyacente = "C" + nuevaFila + "_" + nuevaColumna;
                cuadrícula[fila][columna].insertar(idAdyacente); 
            }
        }
    }

    public void imprimirCuadrícula() {
        for (int i = 0; i < filas; i++) {
            System.out.print("Fila " + i + ": ");
            cuadrícula[i][0].imprimir(); 
        }
    }
}
