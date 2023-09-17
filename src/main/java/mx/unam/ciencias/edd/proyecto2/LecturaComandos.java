package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;

/**
 * Clase Lectura que lee el archivo o archivos recibidos.
 * @author Armando Ramírez
 * @version 1.0
 */
public class LecturaComandos {

    /* Variable para obtener la ruta al archivo. */
    private Path path;

    /*
     * Lista en la que se almacenarán las líneas de los archivos de texto.
     */
    private Lista<String> m = new Lista<String>();

    /**
     * Método leerArchivo.
     * Que recibe un arreglo de tipo String con la dirección
     * o direcciones asociadas a los archivos de texto que recibe
     * el programa.
     * @param arreglo de tipo String con la dirección al archivo
     *         de texto que se recibe.
     * @return una lista de tipo String con el contenido del archivo.
     * @throws IOException si el archivo no existe.
     */
    public Lista<String> leerArchivo(String[] archivo) throws IOException {
        path = Paths.get(archivo[0]);
        for (String h : Files.readAllLines(path)) {
          m.agregaFinal(h.trim());
        }
        return m;
    }
}
