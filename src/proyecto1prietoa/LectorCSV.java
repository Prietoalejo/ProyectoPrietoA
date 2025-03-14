/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto1prietoa;

/**
 *
 * @author Prietoalejo
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Clase que se encarga de leer y guardar partidas en formato CSV para el juego de Buscaminas.
 */
public class LectorCSV {

    /**
     * Guarda el estado de una partida en un archivo CSV.
     *
     * @param nombreArchivo El nombre del archivo donde se guardará la partida.
     * @param campo El objeto CampoMinas que contiene el estado del juego.
     */
    public void guardarPartidaCSV(String nombreArchivo, CampoMinas campo) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nombreArchivo))) {
            writer.write("Filas,Columnas");
            writer.newLine();
            writer.write(campo.filas + "," + campo.columnas);
            writer.newLine();

            writer.write("ID,Fila,Columna,TieneMina,Mostrada,Marcada,MinasAdyacentes");
            writer.newLine();

            for (int i = 0; i < campo.filas; i++) {
                for (int j = 0; j < campo.columnas; j++) {
                    Casilla casilla = campo.matriz[i][j];
                    writer.write(casilla.id + ","
                            + i + ","
                            + j + ","
                            + casilla.mina + ","
                            + casilla.mostrada + "," // Cambiado de "Revelada" a "Mostrada"
                            + casilla.bandera + ","
                            + campo.contarBombasAlrededor(casilla));
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace(); // Manejo de excepciones de entrada/salida
        }
    }

    /**
     * Lee el estado de una partida desde un archivo CSV y lo carga en un objeto CampoMinas.
     *
     * @param nombreArchivo El nombre del archivo desde el cual se leerá la partida.
     * @return Un objeto CampoMinas que representa el estado del juego.
     */
    public CampoMinas leerPartidaCSV(String nombreArchivo) {
        CampoMinas campo = null;

        try (BufferedReader reader = new BufferedReader(new FileReader(nombreArchivo))) {
            String line;

            line = reader.readLine(); // Leer encabezados
            line = reader.readLine(); // Leer la línea con los valores

            String[] tableroInfo = line.split(",");
            int filas = Integer.parseInt(tableroInfo[0]);
            int columnas = Integer.parseInt(tableroInfo[1]);
            campo = new CampoMinas(filas, columnas); // Inicializa el campo de minas

            reader.readLine(); // Leer encabezados de las casillas

            while ((line = reader.readLine()) != null) {
                String[] datos = line.split(",");

                if (datos.length != 7) {
                    continue; // Asegurarse de que hay suficientes datos
                }

                String id = datos[0];
                int fila = Integer.parseInt(datos[1]);
                int columna = Integer.parseInt(datos[2]);
                boolean tieneMina = Boolean.parseBoolean(datos[3]);
                boolean mostrada = Boolean.parseBoolean(datos[4]); // Cambiado de "Revelada" a "Mostrada"
                boolean marcada = Boolean.parseBoolean(datos[5]);
                int minasAdyacentes = Integer.parseInt(datos[6]);

                campo.matriz[fila][columna] = new Casilla(id);
                campo.matriz[fila][columna].mina = tieneMina;
                campo.matriz[fila][columna].mostrada = mostrada; // Asignar el valor del nuevo atributo
                campo.matriz[fila][columna].bandera = marcada;
                // Aquí puedes agregar lógica para establecer las minas adyacentes si es necesario
            }
        } catch (IOException e) {
            e.printStackTrace(); // Manejo de excepciones de entrada/salida
        } catch (NumberFormatException e) {
            System.err.println("Error al convertir un número: " + e.getMessage()); // Manejo de errores de conversión
        }
        return campo; // Retorna el campo de minas cargado
    }
}
