package mx.unam.ciencias.edd;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * <p>Clase genérica para listas doblemente ligadas.</p>
 *
 * <p>Las listas nos permiten agregar elementos al inicio o final de la lista,
 * eliminar elementos de la lista, comprobar si un elemento está o no en la
 * lista, y otras operaciones básicas.</p>
 *
 * <p>Las listas no aceptan a <code>null</code> como elemento.</p>
 *
 * @param <T> El tipo de los elementos de la lista.
 */
public class Lista<T> implements Coleccion<T> {

    /* Clase interna privada para nodos. */
    private class Nodo {
        /* El elemento del nodo. */
        public T elemento;
        /* El nodo anterior. */
        public Nodo anterior;
        /* El nodo siguiente. */
        public Nodo siguiente;

        /* Construye un nodo con un elemento. */
        public Nodo(T elemento) {
            this.elemento = elemento;
        }
    }

    /* Clase interna privada para iteradores. */
    private class Iterador implements IteradorLista<T> {
        /* El nodo anterior. */
        public Nodo anterior;
        /* El nodo siguiente. */
        public Nodo siguiente;

        /* Construye un nuevo iterador. */
        public Iterador() {
            start();
        }

        /* Nos dice si hay un elemento siguiente. */
        @Override public boolean hasNext() {
            return siguiente != null;
        }

        /* Nos da el elemento siguiente. */
        @Override public T next() {
          if (siguiente == null) {
            throw new NoSuchElementException("No hay elemento siguiente");
          } else {
            T s = siguiente.elemento;
            anterior = siguiente;
            siguiente = siguiente.siguiente;
            return s;
          }
        }

        /* Nos dice si hay un elemento anterior. */
        @Override public boolean hasPrevious() {
            return anterior != null;
        }

        /* Nos da el elemento anterior. */
        @Override public T previous() {
          if (anterior == null) {
            throw new NoSuchElementException("No hay elemento anterior");
          }
          siguiente = anterior;
          anterior = anterior.anterior;
          return siguiente.elemento;
        }

        /* Mueve el iterador al inicio de la lista. */
        @Override public void start() {
          anterior = null;
          siguiente = cabeza;
        }

        /* Mueve el iterador al final de la lista. */
        @Override public void end() {
            anterior = rabo;
            siguiente = null;
        }
    }

    /* Primer elemento de la lista. */
    private Nodo cabeza;
    /* Último elemento de la lista. */
    private Nodo rabo;
    /* Número de elementos en la lista. */
    private int longitud;

    /**
     * Regresa la longitud de la lista. El método es idéntico a {@link
     * #getElementos}.
     * @return la longitud de la lista, el número de elementos que contiene.
     */
    public int getLongitud() {
        return longitud;
    }

    /**
     * Regresa el número elementos en la lista. El método es idéntico a {@link
     * #getLongitud}.
     * @return el número elementos en la lista.
     */
    @Override public int getElementos() {
        return longitud;
    }

    /**
     * Nos dice si la lista es vacía.
     * @return <code>true</code> si la lista es vacía, <code>false</code> en
     *         otro caso.
     */
    @Override public boolean esVacia() {
        return (longitud == 0)?true:false;
    }

    /**
     * Agrega un elemento a la lista. Si la lista no tiene elementos, el
     * elemento a agregar será el primero y último. El método es idéntico a
     * {@link #agregaFinal}.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    @Override public void agrega(T elemento) {
        agregaFinal(elemento);
    }

    /**
     * Agrega un elemento al final de la lista. Si la lista no tiene elementos,
     * el elemento a agregar será el primero y último.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    public void agregaFinal(T elemento) {
      if (elemento == null){
        throw new IllegalArgumentException("El elemento debe ser"
        + "distinto de null");
      }
      Nodo n = new Nodo(elemento);
      if (longitud == 0) {
        cabeza = rabo = n;
      } else {
        Nodo r = rabo;
        r.siguiente = n;
        n.anterior = r;
        rabo = n;
      }
      longitud++;
    }

    /**
     * Agrega un elemento al inicio de la lista. Si la lista no tiene elementos,
     * el elemento a agregar será el primero y último.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    public void agregaInicio(T elemento) {
      if (elemento == null){
        throw new IllegalArgumentException("El elemento debe ser"
        + "distinto de null");
      }
      Nodo n = new Nodo(elemento);
      if (longitud == 0) {
        cabeza = rabo = n;
      } else {
        Nodo c = cabeza;
        c.anterior = n;
        n.siguiente = c;
        cabeza = n;
      }
      longitud++;
    }

    /**
     * Inserta un elemento en un índice explícito.
     *
     * Si el índice es menor o igual que cero, el elemento se agrega al inicio
     * de la lista. Si el índice es mayor o igual que el número de elementos en
     * la lista, el elemento se agrega al fina de la misma. En otro caso,
     * después de mandar llamar el método, el elemento tendrá el índice que se
     * especifica en la lista.
     * @param i el índice dónde insertar el elemento. Si es menor que 0 el
     *          elemento se agrega al inicio de la lista, y si es mayor o igual
     *          que el número de elementos en la lista se agrega al final.
     * @param elemento el elemento a insertar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    public void inserta(int i, T elemento) {
      if (elemento == null) {
        throw new IllegalArgumentException("El elemento no es válido");
      }
      if (i <= 0) {
        agregaInicio(elemento);
        return;
      }
      if (i >= longitud) {
        agregaFinal(elemento);
        return;
      } else {
        Nodo n = new Nodo(elemento);
        int j = 0;
        longitud++;
        Nodo m = cabeza;
        while (j++ < i) {
          m = m.siguiente;
        }
        n.siguiente = m;
        n.anterior = m.anterior;
        m.anterior.siguiente = n;
        m.anterior = n;
      }
    }

    /*
    * Método auxiliar para buscar el elemento en una lista.
    * Recibe como parametro el elemento que se va a buscar.
    */
    private Nodo buscaElemento(T elemento){
      Nodo m = cabeza;
      while (m != null) {
        if (elemento.equals(m.elemento)) {
          return m;
        }
        m = m.siguiente;
      }
      return null;
    }

    /**
     * Elimina un elemento de la lista. Si el elemento no está contenido en la
     * lista, el método no la modifica.
     * @param elemento el elemento a eliminar.
     */
    @Override public void elimina(T elemento) {
      if (elemento == null) {
        return;
      }
      Nodo e = buscaElemento(elemento);
      if (e == null) {
        return;
      }
      if (longitud == 1) {
        limpia();
        return;
      }
      if (e == cabeza) {
        eliminaPrimero();
        return;
      }
      if (e == rabo) {
        eliminaUltimo();
        return;
      }
      e.anterior.siguiente = e.siguiente;
      e.siguiente.anterior = e.anterior;
      longitud--;
      return;
    }

    /**
     * Elimina el primer elemento de la lista y lo regresa.
     * @return el primer elemento de la lista antes de eliminarlo.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T eliminaPrimero() {
      if (rabo == null) {
        throw new NoSuchElementException("Lista vacía");
      }
      T e = cabeza.elemento;
      if (longitud == 1) {
        cabeza = rabo = null;
      } else {
        cabeza = cabeza.siguiente;
        cabeza.anterior = null;
      }
      longitud--;
      return e;
    }

    /**
     * Elimina el último elemento de la lista y lo regresa.
     * @return el último elemento de la lista antes de eliminarlo.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T eliminaUltimo() {
      if (rabo == null) {
        throw new NoSuchElementException("La lista es vacía");
      }
      T e = rabo.elemento;
      if (longitud == 1) {
        cabeza = rabo = null;
      } else {
        rabo = rabo.anterior;
        rabo.siguiente = null;
      }
      longitud--;
      return e;
    }

    /**
     * Nos dice si un elemento está en la lista.
     * @param elemento el elemento que queremos saber si está en la lista.
     * @return <code>true</code> si <code>elemento</code> está en la lista,
     *         <code>false</code> en otro caso.
     */
    @Override public boolean contiene(T elemento) {
        return buscaElemento(elemento) != null;
    }

    /**
     * Regresa la reversa de la lista.
     * @return una nueva lista que es la reversa la que manda llamar el método.
     */
    public Lista<T> reversa() {
      Lista<T> lista = new Lista<T>();
      Nodo m = cabeza;
      while (m != null) {
        lista.agregaInicio(m.elemento);
        m = m.siguiente;
      }
      return lista;
    }

    /**
     * Regresa una copia de la lista. La copia tiene los mismos elementos que la
     * lista que manda llamar el método, en el mismo orden.
     * @return una copiad de la lista.
     */
    public Lista<T> copia() {
      Lista<T> lista = new Lista<>();
      Nodo m = cabeza;
      while (m != null) {
        lista.agregaFinal(m.elemento);
        m = m.siguiente;
      }
      return lista;
    }

    /**
     * Limpia la lista de elementos, dejándola vacía.
     */
    @Override public void limpia() {
      cabeza = rabo = null;
      longitud = 0;
    }

    /**
     * Regresa el primer elemento de la lista.
     * @return el primer elemento de la lista.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T getPrimero() {
      if (rabo == null) {
        throw new NoSuchElementException("La lista no debe ser vacía");
      }
      return cabeza.elemento;
    }

    /**
     * Regresa el último elemento de la lista.
     * @return el primer elemento de la lista.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T getUltimo() {
      if (longitud == 0) {
        throw new NoSuchElementException("La lista no debe ser vacía");
      }
      return rabo.elemento;
    }

    /**
     * Regresa el <em>i</em>-ésimo elemento de la lista.
     * @param i el índice del elemento que queremos.
     * @return el <em>i</em>-ésimo elemento de la lista.
     * @throws ExcepcionIndiceInvalido si <em>i</em> es menor que cero o mayor o
     *         igual que el número de elementos en la lista.
     */
    public T get(int i) {
      if (i < 0 || i >= longitud) {
        throw new ExcepcionIndiceInvalido("Índice no valido");
      } else {
        int j = 0;
        Nodo m = cabeza;
        while (j++ < i) {
          m = m.siguiente;
        }
        return m.elemento;
      }
    }

    /**
     * Regresa el índice del elemento recibido en la lista.
     * @param elemento el elemento del que se busca el índice.
     * @return el índice del elemento recibido en la lista, o -1 si el elemento
     *         no está contenido en la lista.
     */
    public int indiceDe(T elemento) {
      int j = 0;
      Nodo m = cabeza;
      while (m != null) {
        if (m.elemento.equals(elemento)) {
          return j;
        }
        m = m.siguiente;
        j++;
      }
      return -1;
    }

    /**
     * Regresa una representación en cadena de la lista.
     * @return una representación en cadena de la lista.
     */
    @Override public String toString() {
        Nodo m = cabeza;
        String n = "[";
        while (m != null) {
          n += m.elemento;
          m = m.siguiente;
          if (m != null) {
            n += ", ";
          }
        }
        return n + "]";
    }

    /**
     * Nos dice si la lista es igual al objeto recibido.
     * @param objeto el objeto con el que hay que comparar.
     * @return <code>true</code> si la lista es igual al objeto recibido;
     *         <code>false</code> en otro caso.
     */
    @Override public boolean equals(Object objeto) {
        if (objeto == null || getClass() != objeto.getClass())
            return false;
        @SuppressWarnings("unchecked") Lista<T> lista = (Lista<T>)objeto;
        Nodo m1 = cabeza;
        Nodo m2 = lista.cabeza;
        if ((longitud == 0) && (lista.longitud == 0)) {
          return true;
        }
        if ((lista.longitud == 0) && (longitud != 0)) {
          return false;
        }
        if ((lista.longitud != 0) && (longitud == 0)) {
          return false;
        }
        if (lista.longitud != longitud){
          return false;
        }
        while (m1 != null && m2 != null) {
          if (!m1.elemento.equals(m2.elemento)) {
            return false;
          }
          m1 = m1.siguiente;
          m2 = m2.siguiente;
        }
        return true;
    }

    /**
     * Regresa un iterador para recorrer la lista en una dirección.
     * @return un iterador para recorrer la lista en una dirección.
     */
    @Override public Iterator<T> iterator() {
        return new Iterador();
    }

    /**
     * Regresa un iterador para recorrer la lista en ambas direcciones.
     * @return un iterador para recorrer la lista en ambas direcciones.
     */
    public IteradorLista<T> iteradorLista() {
        return new Iterador();
    }

    /*
    * Métod auxiliar mezcla.
    * Recibe dos listas ordenadas y regresa una lista ordenada
    * que contiene los elementos de las listas de entrada.
    * @param c un comparador que hace el ordenamiento en la lista.
    */
    private Lista<T> mezcla(Comparator<T> c, Lista<T> lista1, Lista<T> lista2){
      Lista<T>.Nodo l1 = lista1.cabeza;
      Lista<T>.Nodo l2 = lista2.cabeza;
      Lista<T> n = new Lista<>();
      while (l1 != null && l2 != null) {
        if (c.compare(l1.elemento, l2.elemento) <= 0) {
          n.agrega(l1.elemento);
          l1 = l1.siguiente;
        } else {
          n.agrega(l2.elemento);
          l2 = l2.siguiente;
        }
      }
      while (l1 != null && l2 == null) {
        n.agrega(l1.elemento);
        l1 = l1.siguiente;
      }
      while (l1 == null && l2 != null) {
        n.agrega(l2.elemento);
        l2 = l2.siguiente;
      }
      return n;
    }

    /**
     * Regresa una copia de la lista, pero ordenada. Para poder hacer el
     * ordenamiento, el método necesita una instancia de {@link Comparator} para
     * poder comparar los elementos de la lista.
     * @param comparador el comparador que la lista usará para hacer el
     *                   ordenamiento.
     * @return una copia de la lista, pero ordenada.
     */
    public Lista<T> mergeSort(Comparator<T> comparador) {
        return mergeSort(copia(), comparador);
    }

    /*
    * Método auxiliar mergeSort que ejecuta el algoritmo completo.
    * Recibe una lista.
    * comparador el comparador para odernar la lista
    */
    private Lista<T> mergeSort(Lista<T> lista, Comparator<T> comparador){
      Lista<T> lista1 = new Lista<>();
      Lista<T> lista2;
      if (lista.getLongitud() == 1 || lista.esVacia()) {
        return lista.copia();
      }
      int l = lista.getLongitud()/2;
      while (lista.getLongitud() != l) {
        lista1.agrega(lista.getPrimero());
        if (lista.longitud != 0) {
          lista.eliminaPrimero();
        }
      }
      lista2 = lista.copia();
      return (mezcla(comparador,mergeSort(lista1,comparador),mergeSort(lista2,comparador)));
    }

    /**
     * Regresa una copia de la lista recibida, pero ordenada. La lista recibida
     * tiene que contener nada más elementos que implementan la interfaz {@link
     * Comparable}.
     * @param <T> tipo del que puede ser la lista.
     * @param lista la lista que se ordenará.
     * @return una copia de la lista recibida, pero ordenada.
     */
    public static <T extends Comparable<T>> Lista<T> mergeSort(Lista<T> lista) {
        return lista.mergeSort((a, b) -> a.compareTo(b));
    }

    /**
     * Busca un elemento en la lista ordenada, usando el comparador recibido. El
     * método supone que la lista está ordenada usando el mismo comparador.
     * @param elemento el elemento a buscar.
     * @param comparador el comparador con el que la lista está ordenada.
     * @return <code>true</code> si el elemento está contenido en la lista,
     *         <code>false</code> en otro caso.
     */
    public boolean busquedaLineal(T elemento, Comparator<T> comparador) {
        Nodo m = cabeza;
        while (m != null) {
          if (comparador.compare(m.elemento, elemento) == 0) {
            return true;
          }
          m = m.siguiente;
        }
        return false;
    }

    /**
     * Busca un elemento en una lista ordenada. La lista recibida tiene que
     * contener nada más elementos que implementan la interfaz {@link
     * Comparable}, y se da por hecho que está ordenada.
     * @param <T> tipo del que puede ser la lista.
     * @param lista la lista donde se buscará.
     * @param elemento el elemento a buscar.
     * @return <code>true</code> si el elemento está contenido en la lista,
     *         <code>false</code> en otro caso.
     */
    public static <T extends Comparable<T>>
    boolean busquedaLineal(Lista<T> lista, T elemento) {
        return lista.busquedaLineal(elemento, (a, b) -> a.compareTo(b));
    }
}
