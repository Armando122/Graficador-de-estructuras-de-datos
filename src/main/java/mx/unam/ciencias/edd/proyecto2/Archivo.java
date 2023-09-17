package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.*;

/**
 * Clase Archivo.
 * @author Armando Ramírez González.
 * @version 1.0.0.
 */
public class Archivo {

    /* Lista para guardar los elementos de la estructura. */
    Lista<String> arch = new Lista<String>();

    /**
     * Método organiza.
     * Que recibe una lista de tipo String.
     * Regresa la lista con el nombre de la clase y los elementos a graficar.
     */
    public Lista<String> organiza(Lista<String> archivo) {
        for (String a : archivo) {
          if (!a.startsWith("#")) {
            arch.agrega(a);
          }
        }
        arch = separa(arch);
        return arch;
    }

    /*
     * Método auxiliar separa.
     * Que recibe una lista de tipo String y regresa una lista de
     * tipo String con sus elementos separados.
     */
    private Lista<String> separa(Lista<String> lista) {
        Lista<String> nueva = new Lista<String>();
        for (String d : lista) {
          String[] nodos = d.split(" ");
          for (int i = 0; i<nodos.length; i++) {
            nueva.agrega(nodos[i]);
          }
        }
        return nueva;
    }
}
