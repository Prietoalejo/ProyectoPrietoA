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
 * Clase que representa una casilla en el juego de Buscaminas.
 */
public class Casilla {
    public Casilla siguiente; // Referencia a la siguiente casilla en la lista de adyacentes
    public boolean mina; // Indica si la casilla contiene una mina
    public boolean bandera; // Indica si la casilla está marcada con una bandera
    public String id; // Identificador único de la casilla
    public Adyacentes adyacentes; // Lista de casillas adyacentes
    public boolean mostrada; // Indica si la casilla ha sido revelada

    /**
     * Constructor de la clase Casilla.
     *
     * @param id El identificador único de la casilla.
     */
    public Casilla(String id) {
        this.id = id; // Asigna el identificador a la casilla
        siguiente = null; // Inicializa la referencia a la siguiente casilla como null
        bandera = mina = mostrada = false; // Inicializa los estados de mina, bandera y mostrada como false
        adyacentes = new Adyacentes(); // Inicializa la lista de adyacentes
    }
}
