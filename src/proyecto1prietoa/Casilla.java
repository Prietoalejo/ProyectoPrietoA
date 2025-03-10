/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto1prietoa;

/**
 *
 * @author Prietoalejo
 */
public class Casilla {
    public Casilla siguiente;
    public boolean mina;
    public boolean bandera;
    public String id;
    public Adyacentes adyacentes;
    public boolean mostrada;

    public Casilla(String id) {
        this.id = id;
        siguiente = null;
        bandera = mina = mostrada = false;
        adyacentes = new Adyacentes();
        
    }
    
}
