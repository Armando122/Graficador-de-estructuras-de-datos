package mx.unam.ciencias.edd;

import java.util.Comparator;

/**
 * Clase para ordenar y buscar arreglos genéricos.
 */
public class Arreglos {

    /* Constructor privado para evitar instanciación. */
    private Arreglos() {}

    /**
     * Ordena el arreglo recibido usando QickSort.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo el arreglo a ordenar.
     * @param comparador el comparador para ordenar el arreglo.
     */
    public static <T> void quickSort(T[] arreglo, Comparator<T> comparador) {
        quickSort(arreglo, comparador, 0, arreglo.length-1);
    }

    /*
    * Método auxiliar quickSort que ejecuta el algoritmo completo.
    * Recibe: arreglo el arreglo a ordenar.
    * comparador el comparador para ordenar el arreglo.
    * Entero a posición del pivote del arreglo.
    * Entero b.
    */
    private static <T> void quickSort(T[] arreglo, Comparator<T> comparador, int a, int b){
      if (b<=a){
        return;
      }
      int i = a+1;
      int j = b;
      while(i<j){
        if ((comparador.compare(arreglo[i], arreglo[a]) > 0)
        && comparador.compare(arreglo[j], arreglo[a]) <= 0){
          intercambia(arreglo, i, j);
          i++;
          j--;
        } else if (comparador.compare(arreglo[j],arreglo[a])<=0){
          i++;
        } else {
          j--;
        }
      }
      if (comparador.compare(arreglo[i], arreglo[a])>0){
        i--;
      }
      intercambia(arreglo, a, i);
      quickSort(arreglo, comparador, a, i-1);
      quickSort(arreglo, comparador, i+1, b);
    }

    /**
     * Ordena el arreglo recibido usando QickSort.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo un arreglo cuyos elementos son comparables.
     */
    public static <T extends Comparable<T>> void
    quickSort(T[] arreglo) {
        quickSort(arreglo, (a, b) -> a.compareTo(b));
    }

    /**
     * Ordena el arreglo recibido usando SelectionSort.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo el arreglo a ordenar.
     * @param comparador el comparador para ordernar el arreglo.
     */
    public static <T> void
    selectionSort(T[] arreglo, Comparator<T> comparador) {
      for (int i = 0; i < arreglo.length; i++){
        int m = i;
        for (int j = i + 1; j<arreglo.length; j++)
          if (comparador.compare(arreglo[j], arreglo[m]) < 0)
            m = j;
          intercambia(arreglo, i, m);
      }
    }

    /*
    * Método privado intercambia que
    * modifica dos elementos de una arreglo que recibe:
    * Un arreglo
    * Un entero que será la posición a intercambiar con
    * Un entero
    */
    private static <T> void intercambia(T[] arreglo, int i, int m){
      T nuevo = arreglo[i];
      arreglo[i] = arreglo[m];
      arreglo[m] = nuevo;
    }

    /**
     * Ordena el arreglo recibido usando SelectionSort.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo un arreglo cuyos elementos son comparables.
     */
    public static <T extends Comparable<T>> void
    selectionSort(T[] arreglo) {
        selectionSort(arreglo, (a, b) -> a.compareTo(b));
    }

    /**
     * Hace una búsqueda binaria del elemento en el arreglo. Regresa el índice
     * del elemento en el arreglo, o -1 si no se encuentra.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo el arreglo dónde buscar.
     * @param elemento el elemento a buscar.
     * @param comparador el comparador para hacer la búsqueda.
     * @return el índice del elemento en el arreglo, o -1 si no se encuentra.
     */
    public static <T> int busquedaBinaria(T[] arreglo, T elemento, Comparator<T> comparador) {
        return busquedaBinaria(comparador, arreglo, 0, arreglo.length-1, elemento);
    }

    /*
    * Método auxiliar busquedaBinaria que ejecuta el algoritmo completo.
    * comparador el comparador para buscar en el arreglo.
    * arreglo el subarreglo en el que se hará la busqueda.
    * Entero a posición inicial del arreglo.
    * Entero b posición final del arreglo.
    */
    private static <T> int
    busquedaBinaria(Comparator<T> comparador, T[] arreglo, int a, int b, T elemento){
      if (a > b){
        return -1;
      }
      int m = (a+b)/2;
      if (comparador.compare(elemento, arreglo[m]) == 0) {
        return m;
      }
      if (comparador.compare(elemento, arreglo[m]) < 0) {
        return busquedaBinaria(comparador, arreglo, a, m-1, elemento);
      }
      if (comparador.compare(elemento, arreglo[m]) > 0) {
        return busquedaBinaria(comparador, arreglo, m+1, b, elemento);
      }
      return -1;
    }

    /**
     * Hace una búsqueda binaria del elemento en el arreglo. Regresa el índice
     * del elemento en el arreglo, o -1 si no se encuentra.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo un arreglo cuyos elementos son comparables.
     * @param elemento el elemento a buscar.
     * @return el índice del elemento en el arreglo, o -1 si no se encuentra.
     */
    public static <T extends Comparable<T>> int
    busquedaBinaria(T[] arreglo, T elemento) {
        return busquedaBinaria(arreglo, elemento, (a, b) -> a.compareTo(b));
    }
}
