/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto1prietoa;

/**
 *
 * @author Prietoalejo
 */
/**
 * Clase que representa una lista de casillas adyacentes en el juego de Buscaminas.
 */
public class Adyacentes {
    public Casilla primera; // Referencia a la primera casilla en la lista
    public int tamano; // Tamaño de la lista de adyacentes

    /**
     * Constructor de la clase Adyacentes.
     * Inicializa la lista de adyacentes.
     */
    public Adyacentes() {
        primera = null; // Inicializa la primera casilla como null
        tamano = 0; // Inicializa el tamaño de la lista en 0
    }

    /**
     * Inserta una nueva casilla en la lista de adyacentes.
     *
     * @param dato El identificador de la casilla a insertar.
     */
    public void insertar(String dato) {
        Casilla nuevaCasilla = new Casilla(dato); // Crea una nueva casilla con el dato proporcionado
        if (primera == null) {
            primera = nuevaCasilla; // Si la lista está vacía, la nueva casilla se convierte en la primera
        } else {
            Casilla actual = primera; // Comienza desde la primera casilla
            while (actual.siguiente != null) {
                actual = actual.siguiente; // Avanza hasta la última casilla
            }
            actual.siguiente = nuevaCasilla; // Agrega la nueva casilla al final de la lista
        }
        tamano++; // Incrementa el tamaño de la lista
    }

    /**
     * Elimina la primera casilla de la lista de adyacentes.
     */
    public void eliminar() {
        if (primera == null) {
            System.out.println("La lista está vacía."); // Mensaje si la lista está vacía
            return;
        }
        primera = primera.siguiente; // Mueve la referencia de la primera casilla a la siguiente
        tamano--; // Decrementa el tamaño de la lista
    }

    /**
     * Busca una casilla en la lista de adyacentes.
     *
     * @param dato El identificador de la casilla a buscar.
     * @return true si la casilla se encuentra en la lista, false en caso contrario.
     */
    public boolean buscar(String dato) {
        Casilla actual = primera; // Comienza desde la primera casilla
        while (actual != null) {
            if (actual.id.equals(dato)) {
                return true; // Retorna true si se encuentra la casilla
            }
            actual = actual.siguiente; // Avanza a la siguiente casilla
        }
        return false; // Retorna false si no se encuentra la casilla
    }

    /**
     * Imprime los identificadores de las casillas en la lista de adyacentes.
     */
    public void imprimir() {
        Casilla actual = primera; // Comienza desde la primera casilla
        if (actual == null) {
            System.out.println("La lista está vacía."); // Mensaje si la lista está vacía
            return;
        }
        while (actual != null) {
            System.out.print(actual.id + " "); // Imprime el identificador de la casilla
            actual = actual.siguiente; // Avanza a la siguiente casilla
        }
        System.out.println(); // Nueva línea al final de la impresión
    }
}
