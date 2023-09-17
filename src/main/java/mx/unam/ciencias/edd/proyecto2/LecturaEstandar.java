package mx.unam.ciencias.edd.proyecto2;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import mx.unam.ciencias.edd.*;

/**
 * Clase LecturaEstandar que procesa los datos
 * de tipo String que recibe mediante la entrada estándar
 * en la terminal.
 * @author Armando Ramírez González.
 * @version 1.0
 */
public class LecturaEstandar {

    /**
     * Método estático lecturaEstandar.
     * Que lee un archivo de texto en la entrada estandar y guarda
     * los elementos en una lista de tipo String.
     * @throws IOException si no se recibe nada.
     * @return una lista de tipo String.
     */
    public static Lista<String> lecturaEstandar() throws IOException {
      BufferedReader lector = new BufferedReader(new InputStreamReader(System.in));
      String linea;
      Lista<String> archivo = new Lista<String>();
      while ((linea = lector.readLine()) != null) {
        archivo.agregaFinal(linea.trim());
      }
      lector.close();
      return archivo;
    }
}
