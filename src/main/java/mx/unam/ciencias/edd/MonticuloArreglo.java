package mx.unam.ciencias.edd;

import java.util.NoSuchElementException;

/**
 * Clase para montículos de Dijkstra con arreglos.
 */
public class MonticuloArreglo<T extends ComparableIndexable<T>>
    implements MonticuloDijkstra<T> {

    /* Número de elementos en el arreglo. */
    private int elementos;
    /* Usamos un truco para poder utilizar arreglos genéricos. */
    private T[] arreglo;

    /* Truco para crear arreglos genéricos. Es necesario hacerlo así por cómo
       Java implementa sus genéricos; de otra forma obtenemos advertencias del
       compilador. */
    @SuppressWarnings("unchecked") private T[] nuevoArreglo(int n) {
        return (T[])(new ComparableIndexable[n]);
    }

    /**
     * Constructor para montículo de Dijkstra con un arreglo a partir de una
     * colección.
     * @param coleccion la colección a partir de la cuál queremos construir el
     *                  montículo.
     */
    public MonticuloArreglo(Coleccion<T> coleccion) {
        this(coleccion, coleccion.getElementos());
    }

    /**
     * Construye un nuevo para montículo de Dijkstra con arreglo a partir de un
     * iterable.
     * @param iterable el iterable a partir de la cual construir el montículo.
     * @param n el número de elementos en el iterable.
     */
    public MonticuloArreglo(Iterable<T> iterable, int n) {
        arreglo = nuevoArreglo(n);
        int i = 0;
        for (T a : iterable) {
          arreglo[i] = a;
          a.setIndice(i);
          i++;
        }
        elementos = n;
    }

    /**
     * Elimina el elemento mínimo del montículo.
     * @return el elemento mínimo del montículo.
     * @throws IllegalStateException si el montículo es vacío.
     */
    @Override public T elimina() {
        if (elementos == 0) {
          throw new IllegalStateException("El montículo es vacío.");
        }
        elementos--;
        return buscaMinimo();
    }

    /*
     * Método auxiliar buscaMinimo.
     * Que recibe un arreglo génerico, anula la entrada del elemento
     * mínimo y define su indice como -1.
     * Devuelve el elemento eliminado.
     */
    private T buscaMinimo() {
        /* Obtenemos el elemento mínimo. */
        T min = null;
        for (int i = 0; i<arreglo.length; i++) {
          if (arreglo[i] != null) {
            min = arreglo[i];
          }
        }

        /* Obtenemos el indice del elemento mínimo. */
        int ind = min.getIndice();
        for (int j = 0; j<arreglo.length; j++) {
          if (arreglo[j] != null) {
            if (min.compareTo(arreglo[j]) > 0) {
              min = arreglo[j];
              ind = arreglo[j].getIndice();
            }
          }
        }
        arreglo[ind].setIndice(-1);
        arreglo[ind] = null;
        return min;
    }

    /**
     * Regresa el <i>i</i>-ésimo elemento del arreglo.
     * @param i el índice del elemento que queremos.
     * @return el <i>i</i>-ésimo elemento del arreglo.
     * @throws NoSuchElementException si i es menor que cero, o mayor o igual
     *         que el número de elementos.
     */
    @Override public T get(int i) {
        if (i < 0 || i >= elementos) {
          throw new NoSuchElementException("Indice invalido.");
        }
        return arreglo[i];
    }

    /**
     * Nos dice si el montículo es vacío.
     * @return <code>true</code> si ya no hay elementos en el montículo,
     *         <code>false</code> en otro caso.
     */
    @Override public boolean esVacia() {
        return elementos == 0;
    }

    /**
     * Regresa el número de elementos en el montículo.
     * @return el número de elementos en el montículo.
     */
    @Override public int getElementos() {
        return elementos;
    }
}
