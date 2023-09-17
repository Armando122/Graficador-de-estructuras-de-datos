package mx.unam.ciencias.edd;

import java.util.Iterator;

/**
 * <p>Clase para árboles binarios completos.</p>
 *
 * <p>Un árbol binario completo agrega y elimina elementos de tal forma que el
 * árbol siempre es lo más cercano posible a estar lleno.</p>
 */
public class ArbolBinarioCompleto<T> extends ArbolBinario<T> {

    /* Clase interna privada para iteradores. */
    private class Iterador implements Iterator<T> {

        /* Cola para recorrer los vértices en BFS. */
        private Cola<Vertice> cola;

        /* Inicializa al iterador. */
        public Iterador() {
            cola = new Cola<>();
            if (raiz != null) {
              cola.mete(raiz);
            }
        }

        /* Nos dice si hay un elemento siguiente. */
        @Override public boolean hasNext() {
            return !cola.esVacia();
        }

        /* Regresa el siguiente elemento en orden BFS. */
        @Override public T next() {
            Vertice c = cola.saca();
            if(c.hayIzquierdo()){
              cola.mete(c.izquierdo);
            }
            if (c.hayDerecho()) {
              cola.mete(c.derecho);
            }
            return c.elemento;
        }
    }

    /**
     * Constructor sin parámetros. Para no perder el constructor sin parámetros
     * de {@link ArbolBinario}.
     */
    public ArbolBinarioCompleto() { super(); }

    /**
     * Construye un árbol binario completo a partir de una colección. El árbol
     * binario completo tiene los mismos elementos que la colección recibida.
     * @param coleccion la colección a partir de la cual creamos el árbol
     *        binario completo.
     */
    public ArbolBinarioCompleto(Coleccion<T> coleccion) {
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
     * Agrega un elemento al árbol binario completo. El nuevo elemento se coloca
     * a la derecha del último nivel, o a la izquierda de un nuevo nivel.
     * @param elemento el elemento a agregar al árbol.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    @Override public void agrega(T elemento) {
        if (elemento == null) {
          throw new IllegalArgumentException("No hay elemento a agregar");
        }
        Vertice v = nuevoVertice(elemento);
        if (esVacia()) {
          raiz = v;
          elementos++;
          return;
        }
        Cola<Vertice> bfs = new Cola<Vertice>();
        bfs.mete(raiz);
        Vertice n = raiz;
        while (!bfs.esVacia()) {
          n = bfs.saca();
          if (n.izquierdo == null) {
            elementos++;
            n.izquierdo = v;
            v.padre = n;
            return;
          }
          if (n.derecho == null) {
            elementos++;
            n.derecho = v;
            v.padre = n;
            return;
          }
          bfs.mete(n.izquierdo);
          bfs.mete(n.derecho);
        }
    }

    /**
     * Elimina un elemento del árbol. El elemento a eliminar cambia lugares con
     * el último elemento del árbol al recorrerlo por BFS, y entonces es
     * eliminado.
     * @param elemento el elemento a eliminar.
     */
    @Override public void elimina(T elemento) {
        Vertice e = vertice(busca(elemento));
        if (e == null) {
          return;
        }
        elementos -= 1;
        if (raiz.izquierdo == null && raiz.derecho == null) {
          raiz = null;
          return;
        }
        Vertice u = ultimoCola();
        intercambia(e,u);
        if (esIzquierdo(u)){
          u.padre.izquierdo = null;
          u.padre = null;
          return;
        } else {
          u.padre.derecho = null;
          u.padre = null;
          return;
        }
    }

    /*
    * Método auxiliar ultimoCola
    * Recorre el árbol en BFS y regresa el ultimo vertice
    */
    private Vertice ultimoCola(){
      Cola<Vertice> bfs = new Cola<Vertice>();
      Vertice s = raiz;
      bfs.mete(raiz);
      while (!bfs.esVacia()) {
        s = bfs.saca();
        if (s.hayIzquierdo())
          bfs.mete(s.izquierdo);
        if (s.hayDerecho())
          bfs.mete(s.derecho);
      }
      return s;
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
     * Regresa la altura del árbol. La altura de un árbol binario completo
     * siempre es ⌊log<sub>2</sub><em>n</em>⌋.
     * @return la altura del árbol.
     */
    @Override public int altura() {
        if (raiz == null) {
          return -1;
        }
        double a = (Math.log(elementos)/Math.log(2));
        return (int)(Math.floor(a));
    }

    /**
     * Realiza un recorrido BFS en el árbol, ejecutando la acción recibida en
     * cada elemento del árbol.
     * @param accion la acción a realizar en cada elemento del árbol.
     */
    public void bfs(AccionVerticeArbolBinario<T> accion) {
        if (esVacia()) {
          return;
        }
        Cola<Vertice> bfs = new Cola<Vertice>();
        bfs.mete(raiz);
        while (!bfs.esVacia()) {
          Vertice v = bfs.saca();
          accion.actua(v);
          if (v.izquierdo != null)
            bfs.mete(v.izquierdo);
          if (v.derecho != null)
            bfs.mete(v.derecho);
        }
    }

    /**
     * Regresa un iterador para iterar el árbol. El árbol se itera en orden BFS.
     * @return un iterador para iterar el árbol.
     */
    @Override public Iterator<T> iterator() {
        return new Iterador();
    }
}
