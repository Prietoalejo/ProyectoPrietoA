/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto1prietoa;

import java.util.Random;

/**
 *
 * @author Prietoalejo
 */
public class CampoMinas {
    public Casilla[][] matriz;
    public int filas;
    public int columnas;

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

public void marcarBombas(int numBombas) {
        Random random = new Random();
        int bombasMarcadas = 0;

        while (bombasMarcadas < numBombas) {
            int i = random.nextInt(filas);
            int j = random.nextInt(columnas);
            if (!matriz[i][j].mina) {
                matriz[i][j].mina = true;
                bombasMarcadas++;
            }
        }
    }


public int contarBombasAlrededor(Casilla casilla) {
    int contador = 0;
    int fila = casilla.id.charAt(0) - 'A';
    int columna = Integer.parseInt(casilla.id.substring(1)) - 1;
    for (int x = -1; x <= 1; x++) {
        for (int y = -1; y <= 1; y++) {
            if (x == 0 && y == 0) continue;
            int nuevoI = fila + x;
            int nuevoJ = columna + y;
            if (nuevoI >= 0 && nuevoI < filas && nuevoJ >= 0 && nuevoJ < columnas) {
                if (matriz[nuevoI][nuevoJ].mina) {
                    contador++;
                }
            }
        }
    }
    return contador;
}

public Adyacentes dfs(Casilla casilla, boolean[][] visitadas) {
    Adyacentes casillasVisitadas = new Adyacentes();
    if (casilla.mina || visitadas[casilla.id.charAt(0) - 'A'][Integer.parseInt(casilla.id.substring(1)) - 1]) {
        return casillasVisitadas;
    }
    visitadas[casilla.id.charAt(0) - 'A'][Integer.parseInt(casilla.id.substring(1)) - 1] = true;
    casillasVisitadas.insertar(casilla.id);
    System.out.println("Revelando casilla: " + casilla.id);
    int bombasAlrededor = contarBombasAlrededor(casilla);
    if (bombasAlrededor == 0) {
        Casilla actual = casilla.adyacentes.primera;
        while (actual != null) {
            Adyacentes adyacentesVisitados = dfs(matriz[actual.id.charAt(0) - 'A'][Integer.parseInt(actual.id.substring(1)) - 1], visitadas);
            Casilla adyacenteActual = adyacentesVisitados.primera;
            while (adyacenteActual != null) {
                casillasVisitadas.insertar(adyacenteActual.id);
                adyacenteActual = adyacenteActual.siguiente;
            }
            actual = actual.siguiente;
        }
    } else {
        System.out.println("Bombas alrededor de " + casilla.id + ": " + bombasAlrededor);
    }

    return casillasVisitadas;
}

public Adyacentes bfs(Casilla casilla, boolean[][] visitadas) {
    Adyacentes casillasVisitadas = new Adyacentes();
    Adyacentes cola = new Adyacentes(); 
    if (casilla.mina || visitadas[casilla.id.charAt(0) - 'A'][Integer.parseInt(casilla.id.substring(1)) - 1]) {
        return casillasVisitadas;
    }
    visitadas[casilla.id.charAt(0) - 'A'][Integer.parseInt(casilla.id.substring(1)) - 1] = true;
    casillasVisitadas.insertar(casilla.id);
    System.out.println("Revelando casilla: " + casilla.id);
    int bombasAlrededor = contarBombasAlrededor(casilla);
    if (bombasAlrededor == 0) {
        cola.insertar(casilla.id);

        while (cola.primera != null) {
            Casilla actual = matriz[cola.primera.id.charAt(0) - 'A'][Integer.parseInt(cola.primera.id.substring(1)) - 1];
            cola.eliminar();
            Casilla adyacente = actual.adyacentes.primera;
            while (adyacente != null) {
                int fila = adyacente.id.charAt(0) - 'A';
                int columna = Integer.parseInt(adyacente.id.substring(1)) - 1;
                if (!visitadas[fila][columna] && !matriz[fila][columna].mina) {
                    visitadas[fila][columna] = true;
                    casillasVisitadas.insertar(matriz[fila][columna].id);
                    System.out.println("Revelando casilla: " + matriz[fila][columna].id);
                    int bombasAlrededorAdyacente = contarBombasAlrededor(matriz[fila][columna]);
                    if (bombasAlrededorAdyacente == 0) {
                        cola.insertar(matriz[fila][columna].id);
                    }
                }
                adyacente = adyacente.siguiente;
            }
        }
    } else {
        System.out.println("Bombas alrededor de " + casilla.id + ": " + bombasAlrededor);
    }

    return casillasVisitadas;
}

}