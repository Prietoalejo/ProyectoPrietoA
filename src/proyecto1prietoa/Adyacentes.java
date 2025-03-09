/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto1prietoa;

/**
 *
 * @author Prietoalejo
 */
public class Adyacentes {
  Casilla primera;
  int tamano;
  
  public Adyacentes(){
      primera = null;
      tamano = 0;
  }
  
    public void insertar(String dato) {
        Casilla nuevaCasilla = new Casilla(dato);
        if (primera == null) {
            primera = nuevaCasilla;
        } else {
            Casilla actual = primera;
            while (actual.siguiente != null) {
                actual = actual.siguiente;
            }
            actual.siguiente = nuevaCasilla;
        }
        tamano++;
    }

    public void eliminar() {
        if (primera == null) {
            System.out.println("La lista está vacía.");
            return;
        }
        primera = primera.siguiente;
        tamano--;
    }

    public boolean buscar(String dato) {
        Casilla actual = primera;
        while (actual != null) {
            if (actual.id.equals(dato)) {
                return true;
            }
            actual = actual.siguiente;
        }
        return false;
    }

    public void imprimir() {
        Casilla actual = primera;
        if (actual == null) {
            System.out.println("La lista está vacía.");
            return;
        }
        while (actual != null) {
            System.out.print(actual.id + " ");
            actual = actual.siguiente;
        }
        System.out.println();
    }
}
