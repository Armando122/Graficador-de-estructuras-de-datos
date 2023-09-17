package mx.unam.ciencias.edd;

import java.util.Iterator;

/**
 * <p>Clase para árboles binarios ordenados. Los árboles son genéricos, pero
 * acotados a la interfaz {@link Comparable}.</p>
 *
 * <p>Un árbol instancia de esta clase siempre cumple que:</p>
 * <ul>
 *   <li>Cualquier elemento en el árbol es mayor o igual que todos sus
 *       descendientes por la izquierda.</li>
 *   <li>Cualquier elemento en el árbol es menor o igual que todos sus
 *       descendientes por la derecha.</li>
 * </ul>
 */
public class ArbolBinarioOrdenado<T extends Comparable<T>>
    extends ArbolBinario<T> {

    /* Clase interna privada para iteradores. */
    private class Iterador implements Iterator<T> {

        /* Pila para recorrer los vértices en DFS in-order. */
        private Pila<Vertice> pila;

        /* Inicializa al iterador. */
        public Iterador() {
            pila = new Pila<>();
            if (esVacia()) {
              return;
            }
            Vertice r = raiz;
            while (r != null) {
              pila.mete(r);
              r = r.izquierdo;
            }
        }

        /* Nos dice si hay un elemento siguiente. */
        @Override public boolean hasNext() {
            return !pila.esVacia();
        }

        /* Regresa el siguiente elemento en orden DFS in-order. */
        @Override public T next() {
            Vertice n = pila.saca();
            if (n.derecho != null) {
              Vertice d = n.derecho;
              while (d != null) {
                pila.mete(d);
                d = d.izquierdo;
              }
            }
            return n.elemento;
        }
    }

    /**
     * El vértice del último elemento agegado. Este vértice sólo se puede
     * garantizar que existe <em>inmediatamente</em> después de haber agregado
     * un elemento al árbol. Si cualquier operación distinta a agregar sobre el
     * árbol se ejecuta después de haber agregado un elemento, el estado de esta
     * variable es indefinido.
     */
    protected Vertice ultimoAgregado;

    /**
     * Constructor sin parámetros. Para no perder el constructor sin parámetros
     * de {@link ArbolBinario}.
     */
    public ArbolBinarioOrdenado() { super(); }

    /**
     * Construye un árbol binario ordenado a partir de una colección. El árbol
     * binario ordenado tiene los mismos elementos que la colección recibida.
     * @param coleccion la colección a partir de la cual creamos el árbol
     *        binario ordenado.
     */
    public ArbolBinarioOrdenado(Coleccion<T> coleccion) {
        super(coleccion);
    }

    /*
    * Método auxiliar esDerecho para saber si un vértice es
    * hijo derecho.
    * Recibe un vértice sobre el cual se hace la pregunta.
    */
    private boolean esDerecho(Vertice i) {
        if (!i.hayPadre()) {
          return false;
        }
        return i.padre.derecho == i;
    }

    /*
    * Método auxiliar esIzquierdo para saber si un vértice es
    * hijo derecho.
    * Recibe un vértice sobre el cual se hace la pregunta.
    */
    private boolean esIzquierdo(Vertice d) {
      if (!d.hayPadre()) {
        return false;
      }
      return d.padre.izquierdo == d;
    }

    /**
     * Agrega un nuevo elemento al árbol. El árbol conserva su orden in-order.
     * @param elemento el elemento a agregar.
     */
    @Override public void agrega(T elemento) {
        if (elemento == null) {
          throw new IllegalArgumentException();
        }
        Vertice nuevo = nuevoVertice(elemento);
        elementos++;
        ultimoAgregado = nuevo;
        if (raiz == null) {
          raiz = nuevo;
          return;
        }
        agrega(raiz,nuevo);
    }

    /*
    * Método auxiliar recursivo agrega.
    * Recibe un vértice distinto de null y el nuevo vértice a agregar.
    */
    private void agrega(Vertice v, Vertice nuevo){
      if (nuevo.elemento.compareTo(v.elemento) <= 0) {
        if (v.izquierdo == null) {
          v.izquierdo = nuevo;
          nuevo.padre = v;
          return;
        }
        agrega(v.izquierdo, nuevo);
      } else {
        if (v.derecho == null) {
          v.derecho = nuevo;
          nuevo.padre = v;
          return;
        }
        agrega(v.derecho,nuevo);
      }
    }

    /**
     * Elimina un elemento. Si el elemento no está en el árbol, no hace nada; si
     * está varias veces, elimina el primero que encuentre (in-order). El árbol
     * conserva su orden in-order.
     * @param elemento el elemento a eliminar.
     */
    @Override public void elimina(T elemento) {
        if (elemento == null) {
          return;
        }
        Vertice e = vertice(busca(elemento));
        if (e == null) {
          return;
        }
        elementos--;
        if (elementos == 0) {
          raiz = null;
          return;
        }
        if (e.hayIzquierdo() && e.hayDerecho()) {
          e = intercambiaEliminable(e);
        }
        eliminaVertice(e);
    }

    /*
    * Método auxiliar recursivo maximoEnSubarbol
    * Recibe un vértice
    * Regresa un vértice menor o igual que el recibido
    */
    private Vertice maximoEnSubarbol(Vertice v){
        if (v.derecho == null) {
          return v;
        }
        return maximoEnSubarbol(v.derecho);
    }

    /*
    * Método auxiliar intercambia
    * Que cambia la posición de dos vértices
    * Recibe dos Vértices u,v
    */
    private void intercambia(Vertice u, Vertice v){
      T i = u.elemento;
      u.elemento = v.elemento;
      v.elemento = i;
    }

    /**
     * Intercambia el elemento de un vértice con dos hijos distintos de
     * <code>null</code> con el elemento de un descendiente que tenga a lo más
     * un hijo.
     * @param vertice un vértice con dos hijos distintos de <code>null</code>.
     * @return el vértice descendiente con el que vértice recibido se
     *         intercambió. El vértice regresado tiene a lo más un hijo distinto
     *         de <code>null</code>.
     */
    protected Vertice intercambiaEliminable(Vertice vertice) {
        Vertice maximo = maximoEnSubarbol(vertice.izquierdo);
        T i = maximo.elemento;
        maximo.elemento = vertice.elemento;
        vertice.elemento = i;
        return maximo;
    }

    /**
     * Elimina un vértice que a lo más tiene un hijo distinto de
     * <code>null</code> subiendo ese hijo (si existe).
     * @param vertice el vértice a eliminar; debe tener a lo más un hijo
     *                distinto de <code>null</code>.
     */
    protected void eliminaVertice(Vertice vertice) {
        if (vertice.hayPadre()) {
          if (esIzquierdo(vertice)) {
            if (vertice.izquierdo != null) {
              vertice.padre.izquierdo = vertice.izquierdo;
              vertice.izquierdo.padre = vertice.padre;
              return;
            }
            if (vertice.derecho != null) {
              vertice.padre.izquierdo = vertice.derecho;
              vertice.derecho.padre = vertice.padre;
              return;
            }
            vertice.padre.izquierdo = null;
          } else {
            if (vertice.izquierdo != null) {
              vertice.padre.derecho = vertice.izquierdo;
              vertice.izquierdo.padre = vertice.padre;
              return;
            }
            if (vertice.derecho != null) {
              vertice.padre.derecho = vertice.derecho;
              vertice.derecho.padre = vertice.padre;
              return;
            }
            vertice.padre.derecho = null;
          }
        } else {
          if (vertice.izquierdo != null) {
            raiz = vertice.izquierdo;
            vertice.izquierdo.padre = null;
            return;
          } else {
            raiz = vertice.derecho;
            vertice.derecho.padre = null;
            return;
          }
        }
    }

    /**
     * Busca un elemento en el árbol recorriéndolo in-order. Si lo encuentra,
     * regresa el vértice que lo contiene; si no, regresa <code>null</code>.
     * @param elemento el elemento a buscar.
     * @return un vértice que contiene al elemento buscado si lo
     *         encuentra; <code>null</code> en otro caso.
     */
    @Override public VerticeArbolBinario<T> busca(T elemento) {
        if (raiz == null || elemento == null) {
          return null;
        }
        return busca(raiz,elemento);
    }

    /*
    * Métdo auxiliar recursivo que mejora el algoritmo de búsqueda
    * en árboles ordenaodos.
    * Recibe un vértice sobre el cual se hará la recursión.
    * Recibe el elemento a buscar.
    */
    private VerticeArbolBinario<T> busca(Vertice v, T elemento){
        if (v.elemento.equals(elemento)) {
          return (VerticeArbolBinario<T>)v;
        }
        if (v.elemento.compareTo(elemento) < 0) {
          if (v.derecho != null) {
            return busca(v.derecho,elemento);
          }
          return null;
        } else {
          if (v.izquierdo != null) {
            return busca(v.izquierdo,elemento);
          }
          return null;
        }
    }

    /**
     * Regresa el vértice que contiene el último elemento agregado al
     * árbol. Este método sólo se puede garantizar que funcione
     * <em>inmediatamente</em> después de haber invocado al método {@link
     * agrega}. Si cualquier operación distinta a agregar sobre el árbol se
     * ejecuta después de haber agregado un elemento, el comportamiento de este
     * método es indefinido.
     * @return el vértice que contiene el último elemento agregado al árbol, si
     *         el método es invocado inmediatamente después de agregar un
     *         elemento al árbol.
     */
    public VerticeArbolBinario<T> getUltimoVerticeAgregado() {
        return ultimoAgregado;
    }

    /**
     * Gira el árbol a la derecha sobre el vértice recibido. Si el vértice no
     * tiene hijo izquierdo, el método no hace nada.
     * @param vertice el vértice sobre el que vamos a girar.
     */
    public void giraDerecha(VerticeArbolBinario<T> vertice) {
        if (vertice.hayIzquierdo()) {
          Vertice p = vertice(vertice);
          Vertice q = p.izquierdo;
          p.izquierdo = q.derecho;
          if (q.derecho != null) {
            q.derecho.padre = p;
          }
          q.padre = p.padre;
          if (p.padre == null) {
            raiz = q;
          } else {
            if (p.padre.derecho == p) {
              p.padre.derecho = q;
            } else {
              p.padre.izquierdo = q;
            }
          }
          q.derecho = p;
          p.padre = q;
        }
        return;
    }

    /**
     * Gira el árbol a la izquierda sobre el vértice recibido. Si el vértice no
     * tiene hijo derecho, el método no hace nada.
     * @param vertice el vértice sobre el que vamos a girar.
     */
    public void giraIzquierda(VerticeArbolBinario<T> vertice) {
        if (vertice.hayDerecho()) {
          Vertice p = vertice(vertice);
          Vertice q = p.derecho;
          p.derecho = q.izquierdo;
          if(q.izquierdo != null){
            q.izquierdo.padre = p;
          }
          q.padre = p.padre;
          if(p.padre == null){
            raiz = q;
          } else {
            if(esIzquierdo(p)){
              p.padre.izquierdo = q;
            } else {
              p.padre.derecho = q;
            }
          }
          q.izquierdo = p;
          p.padre = q;
        }
        return;
    }

    /**
     * Realiza un recorrido DFS <em>pre-order</em> en el árbol, ejecutando la
     * acción recibida en cada elemento del árbol.
     * @param accion la acción a realizar en cada elemento del árbol.
     */
    public void dfsPreOrder(AccionVerticeArbolBinario<T> accion) {
        if (esVacia()) {
          return;
        } else {
          dfsPreOrder(accion,raiz);
        }
    }

    /*
    * Método auxiliar dfsPreOrder para hacer recorrido en bfs
    * Recibe la accion a realizar en cada elemento del árbol
    * Recibe un vértice
    */
    private void dfsPreOrder(AccionVerticeArbolBinario<T> accion, Vertice v) {
      accion.actua(v);
      if (v.hayIzquierdo()) {
        dfsPreOrder(accion,v.izquierdo);
      }
      if (v.hayDerecho()) {
        dfsPreOrder(accion,v.derecho);
      }
    }

    /**
     * Realiza un recorrido DFS <em>in-order</em> en el árbol, ejecutando la
     * acción recibida en cada elemento del árbol.
     * @param accion la acción a realizar en cada elemento del árbol.
     */
    public void dfsInOrder(AccionVerticeArbolBinario<T> accion) {
        if (raiz == null) {
          return;
        } else {
          dfsInOrder(accion,raiz);
        }
    }

    /*
    * Método auxiliar dfsInOrder para hacer recorrido en bfs
    * Recibe la accion a realizar en cada elemento del árbol
    * Recibe un vértice
    */
    private void dfsInOrder(AccionVerticeArbolBinario<T> accion, Vertice v) {
      if (v.hayIzquierdo()) {
        dfsInOrder(accion,v.izquierdo);
      }
      accion.actua(v);
      if (v.hayDerecho()) {
        dfsInOrder(accion,v.derecho);
      }
    }

    /**
     * Realiza un recorrido DFS <em>post-order</em> en el árbol, ejecutando la
     * acción recibida en cada elemento del árbol.
     * @param accion la acción a realizar en cada elemento del árbol.
     */
    public void dfsPostOrder(AccionVerticeArbolBinario<T> accion) {
        if (raiz == null) {
          return;
        } else {
          dfsPostOrder(accion,raiz);
        }
    }

    /*
    * Método auxiliar dfsPostOrder para hacer recorrido en bfs
    * Recibe la accion a realizar en cada elemento del árbol
    * Recibe un vértice
    */
    private void dfsPostOrder(AccionVerticeArbolBinario<T> accion, Vertice v) {
      if (v.hayIzquierdo()) {
        dfsPostOrder(accion,v.izquierdo);
      }
      if (v.hayDerecho()) {
        dfsPostOrder(accion,v.derecho);
      }
      accion.actua(v);
    }

    /**
     * Regresa un iterador para iterar el árbol. El árbol se itera en orden.
     * @return un iterador para iterar el árbol.
     */
    @Override public Iterator<T> iterator() {
        return new Iterador();
    }
}
