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
/**
 * Clase que representa el campo de minas en el juego de Buscaminas.
 */
public class CampoMinas {
    public Casilla[][] matriz; // Matriz que representa el campo de minas
    public int filas; // Número de filas en el campo
    public int columnas; // Número de columnas en el campo

    /**
     * Constructor de la clase CampoMinas.
     *
     * @param m Número de filas en el campo.
     * @param n Número de columnas en el campo.
     */
    public CampoMinas(int m, int n) {
        this.filas = m;
        this.columnas = n;
        this.matriz = new Casilla[m][n];
        crearCuadricula(); // Inicializa la cuadrícula de casillas
    }

    /**
     * Crea la cuadrícula de casillas y las inicializa con identificadores únicos.
     */
    private void crearCuadricula() {
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                String id = String.valueOf((char) ('A' + i)) + (j + 1);
                matriz[i][j] = new Casilla(id); // Crea una nueva casilla con un identificador
            }
        }
        conectarCasillas(); // Conecta las casillas adyacentes
    }

    /**
     * Conecta cada casilla con sus casillas adyacentes.
     */
    private void conectarCasillas() {
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                for (int x = -1; x <= 1; x++) {
                    for (int y = -1; y <= 1; y++) {
                        if (x == 0 && y == 0) continue; // Ignora la casilla actual
                        int nuevoI = i + x;
                        int nuevoJ = j + y;
                        // Verifica que la nueva posición esté dentro de los límites
                        if (nuevoI >= 0 && nuevoI < filas && nuevoJ >= 0 && nuevoJ < columnas) {
                            matriz[i][j].adyacentes.insertar(matriz[nuevoI][nuevoJ].id); // Conecta la casilla
                        }
                    }
                }
            }
        }
    }

    /**
     * Muestra el grafo de casillas y sus adyacencias en la consola.
     */
    public void mostrarGrafo() {
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                System.out.print("Casilla " + matriz[i][j].id + ": ");
                matriz[i][j].adyacentes.imprimir(); // Imprime las adyacencias de la casilla
            }
        }
    }

    /**
     * Dibuja el grafo de casillas en la consola, mostrando las conexiones.
     */
    public void dibujarGrafo() {
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                System.out.print(matriz[i][j].id + " ");
                if (j < columnas - 1) {
                    System.out.print(" - "); // Dibuja una línea entre las casillas
                }
            }
            System.out.println();

            if (i < filas - 1) {
                for (int j = 0; j < columnas; j++) {
                    System.out.print(" | "); // Dibuja una línea vertical
                    if (j < columnas - 1) {
                        System.out.print("   "); // Espacio entre las casillas
                    }
                }
                System.out.println(); 
            }
        }
        
        System.out.println("\nAdyacencias:");
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                System.out.print("Casilla " + matriz[i][j].id + ": ");
                matriz[i][j].adyacentes.imprimir(); // Imprime las adyacencias de la casilla
            }
        }
    }

    /**
     * Marca un número específico de bombas en el campo de minas de manera aleatoria.
     *
     * @param numBombas Número de bombas a marcar en el campo.
     */
    public void marcarBombas(int numBombas) {
        Random random = new Random();
        int bombasMarcadas = 0;

        while (bombasMarcadas < numBombas) {
            int i = random.nextInt(filas);
            int j = random.nextInt(columnas);
            if (!matriz[i][j].mina) {
                matriz[i][j].mina = true; // Marca la casilla como mina
                bombasMarcadas++;
            }
        }
    }

    /**
     * Cuenta el número de bombas adyacentes a una casilla específica.
     *
     * @param casilla La casilla para la cual se cuentan las bombas adyacentes.
     * @return El número de bombas adyacentes a la casilla.
     */
    public int contarBombasAlrededor(Casilla casilla) {
        int contador = 0;
        int fila = casilla.id.charAt(0) - 'A'; // Convierte el identificador a índice de fila
        int columna = Integer.parseInt(casilla.id.substring(1)) - 1; // Convierte el identificador a índice de columna
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                if (x == 0 && y == 0) continue; // Ignora la casilla actual
                int nuevoI = fila + x;
                int nuevoJ = columna + y;
                // Verifica que la nueva posición esté dentro de los límites
                if (nuevoI >= 0 && nuevoI < filas && nuevoJ >= 0 && nuevoJ < columnas) {
                    if (matriz[nuevoI][nuevoJ].mina) {
                        contador++; // Incrementa el contador si hay una mina
                    }
                }
            }
        }
        return contador; // Retorna el número de bombas adyacentes
    }

    /**
     * Realiza una búsqueda en profundidad (DFS) a partir de una casilla.
     *
     * @param casilla La casilla desde la cual iniciar la búsqueda.
     * @param visitadas Matriz que indica si una casilla ha sido visitada.
     * @return Una lista de casillas visitadas durante la búsqueda.
     */
    public Adyacentes dfs(Casilla casilla, boolean[][] visitadas) {
        Adyacentes casillasVisitadas = new Adyacentes();
        if (casilla.mina || visitadas[casilla.id.charAt(0) - 'A'][Integer.parseInt(casilla.id.substring(1)) - 1]) {
            return casillasVisitadas; // Retorna si la casilla es una mina o ya ha sido visitada
        }
        visitadas[casilla.id.charAt(0) - 'A'][Integer.parseInt(casilla.id.substring(1)) - 1] = true; // Marca la casilla como visitada
        casillasVisitadas.insertar(casilla.id); // Agrega la casilla a la lista de visitadas
        System.out.println("Revelando casilla: " + casilla.id);
        int bombasAlrededor = contarBombasAlrededor(casilla); // Cuenta las bombas alrededor de la casilla
        if (bombasAlrededor == 0) {
            Casilla actual = casilla.adyacentes.primera; // Comienza a explorar las casillas adyacentes
            while (actual != null) {
                Adyacentes adyacentesVisitados = dfs(matriz[actual.id.charAt(0) - 'A'][Integer.parseInt(actual.id.substring(1)) - 1], visitadas);
                Casilla adyacenteActual = adyacentesVisitados.primera;
                while (adyacenteActual != null) {
                    casillasVisitadas.insertar(adyacenteActual.id); // Agrega las casillas visitadas a la lista
                    adyacenteActual = adyacenteActual.siguiente;
                }
                actual = actual.siguiente; // Avanza a la siguiente casilla adyacente
            }
        } else {
            System.out.println("Bombas alrededor de " + casilla.id + ": " + bombasAlrededor);
        }

        return casillasVisitadas; // Retorna la lista de casillas visitadas
    }

    /**
     * Realiza una búsqueda en amplitud (BFS) a partir de una casilla.
     *
     * @param casilla La casilla desde la cual iniciar la búsqueda.
     * @param visitadas Matriz que indica si una casilla ha sido visitada.
     * @return Una lista de casillas visitadas durante la búsqueda.
     */
    public Adyacentes bfs(Casilla casilla, boolean[][] visitadas) {
        Adyacentes casillasVisitadas = new Adyacentes();
        Adyacentes cola = new Adyacentes(); 
        if (casilla.mina || visitadas[casilla.id.charAt(0) - 'A'][Integer.parseInt(casilla.id.substring(1)) - 1]) {
            return casillasVisitadas; // Retorna si la casilla es una mina o ya ha sido visitada
        }
        visitadas[casilla.id.charAt(0) - 'A'][Integer.parseInt(casilla.id.substring(1)) - 1] = true; // Marca la casilla como visitada
        casillasVisitadas.insertar(casilla.id); // Agrega la casilla a la lista de visitadas
        System.out.println("Revelando casilla: " + casilla.id);
        int bombasAlrededor = contarBombasAlrededor(casilla); // Cuenta las bombas alrededor de la casilla
        if (bombasAlrededor == 0) {
            cola.insertar(casilla.id); // Agrega la casilla a la cola para explorar

            while (cola.primera != null) {
                Casilla actual = matriz[cola.primera.id.charAt(0) - 'A'][Integer.parseInt(cola.primera.id.substring(1)) - 1];
                cola.eliminar(); // Elimina la casilla de la cola
                Casilla adyacente = actual.adyacentes.primera; // Comienza a explorar las casillas adyacentes
                while (adyacente != null) {
                    int fila = adyacente.id.charAt(0) - 'A';
                    int columna = Integer.parseInt(adyacente.id.substring(1)) - 1;
                    if (!visitadas[fila][columna] && !matriz[fila][columna].mina) {
                        visitadas[fila][columna] = true; // Marca la casilla adyacente como visitada
                        casillasVisitadas.insertar(matriz[fila][columna].id); // Agrega la casilla a la lista de visitadas
                        System.out.println("Revelando casilla: " + matriz[fila][columna].id);
                        int bombasAlrededorAdyacente = contarBombasAlrededor(matriz[fila][columna]); // Cuenta las bombas alrededor de la casilla adyacente
                        if (bombasAlrededorAdyacente == 0) {
                            cola.insertar(matriz[fila][columna].id); // Agrega la casilla adyacente a la cola si no hay bombas
                        }
                    }
                    adyacente = adyacente.siguiente; }
            }
        } else {
            System.out.println("Bombas alrededor de " + casilla.id + ": " + bombasAlrededor);
        }

        return casillasVisitadas; // Retorna la lista de casillas visitadas
    }

    /**
     * Busca una casilla en el campo de minas por su identificador.
     *
     * @param id El identificador de la casilla a buscar.
     * @return La casilla correspondiente al identificador, o null si no se encuentra.
     */
    public Casilla buscarID(String id) {
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                if (matriz[i][j] != null && matriz[i][j].id.equals(id)) {
                    return matriz[i][j]; // Retorna la casilla si se encuentra
                }
            }
        }
        return null; // Retorna null si no se encuentra la casilla
    }
}
