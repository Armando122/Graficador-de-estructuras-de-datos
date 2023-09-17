package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.NumberFormat;

/**
 * Proyecto 2 de Estrucutas de Datos.
 * Implementación de un graficador en SVG que grafica
 * las estrucutas de datos vistas en el curso
 * desde Listas hasta Montículos Mínimos.
 * @author Armando Ramírez González.
 * @version 1.0.0.
 */
public class Proyecto2 {

    /* Imprime el uso del programa y lo termina. */
    private static void uso() {
      System.err.println("El archivo de entrada debe especificarse como:\n"
                        + "  java -jar proyecto2.jar archivo.txt\n"
                        + "      ó\n"
                        + "  cat archivo.txt | java -jar proyecto2.jar");
      System.exit(1);
    }

    /* Imprime condicion de longitud para gráficas y termina el programa. */
    private static void usoLong() {
      System.err.println("El número de elementos debe ser par.");
      System.exit(1);
    }

    /* Imprime condición de elementos y termina el programa. */
    private static void usoEnteros() {
      System.err.println("Los elementos deben ser enteros\n"
                        + "y el archivo solo debe contener una clase.");
      System.exit(1);
    }

    public static void main(String[] args) {
        if (args.length > 1) {
          uso();
        }

        /* Constantes que delimitan el lienzo. */
        final String inicio = "<svg width='10000' height='10000'>";
        final String cierre = "</svg>";
        /* Variable en la cual se guardará la salida del programa. */
        String estructuraG = "";
        /* Objeto para graficar estrucutras de la clase Graficador. */
        Graficador graf = new Graficador();
        /* Objeto para lectura de línea de comandos. */
        LecturaComandos leCo = new LecturaComandos();
        /* Objeto para lectura de entrada estándar. */
        LecturaEstandar leSe = new LecturaEstandar();
        /* Objeto para organizar la lista recibida*/
        Archivo arc = new Archivo();
        /* Identificador para Graficadores. */
        Identificador h = new Identificador();
        /* Lista auxiliar de tipo String para trabajar con los archivos. */
        Lista<String> lista = new Lista<>();

        /* Entrada estándar, no recibe archivos. */
        if (args.length == 0) {
          try {
            lista = leSe.lecturaEstandar();
            lista = arc.organiza(lista);
            String est = lista.eliminaPrimero();
            if (est.equals("Grafica") && (lista.getLongitud()%2 != 0)) {
              usoLong();
            }
            graf = h.define(est);
            Lista<Integer> aux = new Lista<Integer>();
            try {
              int n;
              for (String s : lista) {
                n = Integer.parseInt(s);
                aux.agrega(n);
              }
            } catch(NumberFormatException t) {
              usoEnteros();
            }
            estructuraG = graf.grafica(aux);
            System.out.println(inicio);
            System.out.println(estructuraG);
            System.out.println(cierre);
          } catch(IOException e) {
            System.err.println("Archivo" + e.getMessage() + "no encontrado o inexistente.");
          }
        }

        /* Línea de comandos. */
        try {
          if (args.length == 1) {
            lista = leCo.leerArchivo(args);
            lista = arc.organiza(lista);
            String est = lista.eliminaPrimero();
            if (est.equals("Grafica") && (lista.getLongitud()%2 != 0)) {
              usoLong();
            }
            graf = h.define(est);
            Lista<Integer> auxi = new Lista<>();
            try {
              int n;
              for (String s : lista) {
                n = Integer.parseInt(s);
                auxi.agrega(n);
              }
            } catch(NumberFormatException t) {
              usoEnteros();
            }
            estructuraG = graf.grafica(auxi);
            System.out.println(inicio);
            System.out.println(estructuraG);
            System.out.println(cierre);
          }
        } catch(Exception o) {
          System.err.println("El archivo: " + o.getMessage()
                            + " no se encontró en la dirección especificada");
        }
    }
}
