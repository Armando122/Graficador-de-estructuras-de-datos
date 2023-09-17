package mx.unam.ciencias.edd;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Clase para gráficas. Una gráfica es un conjunto de vértices y aristas, tales
 * que las aristas son un subconjunto del producto cruz de los vértices.
 */
public class Grafica<T> implements Coleccion<T> {

    /* Clase interna privada para iteradores. */
    private class Iterador implements Iterator<T> {

        /* Iterador auxiliar. */
        private Iterator<Vertice> iterador;

        /* Construye un nuevo iterador, auxiliándose de la lista de vértices. */
        public Iterador() {
            this.iterador = vertices.iterator();
        }

        /* Nos dice si hay un siguiente elemento. */
        @Override public boolean hasNext() {
            return iterador.hasNext();
        }

        /* Regresa el siguiente elemento. */
        @Override public T next() {
            return iterador.next().elemento;
        }
    }

    /* Clase interna privada para vértices. */
    private class Vertice implements VerticeGrafica<T> {

        /* El elemento del vértice. */
        public T elemento;
        /* El color del vértice. */
        public Color color;
        /* La lista de vecinos del vértice. */
        public Lista<Vertice> vecinos;

        /* Crea un nuevo vértice a partir de un elemento. */
        public Vertice(T elemento) {
            this.elemento = elemento;
            color = Color.NINGUNO;
            vecinos = new Lista<Vertice>();
        }

        /* Regresa el elemento del vértice. */
        @Override public T get() {
            return elemento;
        }

        /* Regresa el grado del vértice. */
        @Override public int getGrado() {
            return vecinos.getLongitud();
        }

        /* Regresa el color del vértice. */
        @Override public Color getColor() {
            return color;
        }

        /* Regresa un iterable para los vecinos. */
        @Override public Iterable<? extends VerticeGrafica<T>> vecinos() {
            return vecinos;
        }
    }

    /* Vértices. */
    private Lista<Vertice> vertices;
    /* Número de aristas. */
    private int aristas;

    /**
     * Constructor único.
     */
    public Grafica() {
        this.vertices = new Lista<Vertice>();
    }

    /**
     * Regresa el número de elementos en la gráfica. El número de elementos es
     * igual al número de vértices.
     * @return el número de elementos en la gráfica.
     */
    @Override public int getElementos() {
        return vertices.getLongitud();
    }

    /**
     * Regresa el número de aristas.
     * @return el número de aristas.
     */
    public int getAristas() {
        return aristas;
    }

    /**
     * Agrega un nuevo elemento a la gráfica.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si el elemento es <code>null</code> o ya
     *         había sido agregado a la gráfica.
     */
    @Override public void agrega(T elemento) {
        if (elemento == null || this.contiene(elemento)) {
          throw new IllegalArgumentException("No se puede agregar.");
        }
        Vertice n = new Vertice(elemento);
        vertices.agrega(n);
    }

    /*
     * Método auxiliar buscaVertice.
     * Que recibe un elemento y regresa el vértice
     * correspondiente al elemento si existe, regresa null si no
     * se encuentra.
     */
    private Vertice buscaVertice(T elemento) {
        for (Vertice b : vertices) {
          if (b.elemento.equals(elemento)) {
            return b;
          }
        }
        return null;
    }

    /**
     * Conecta dos elementos de la gráfica. Los elementos deben estar en la
     * gráfica. El peso de la arista que conecte a los elementos será 1.
     * @param a el primer elemento a conectar.
     * @param b el segundo elemento a conectar.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     * @throws IllegalArgumentException si a o b ya están conectados, o si a es
     *         igual a b.
     */
    public void conecta(T a, T b) {
        if (!this.contiene(a) || !this.contiene(b)) {
          throw new NoSuchElementException("No hay elementos suficientes.");
        }
        if (a == b || sonVecinos(a,b)) {
          throw new IllegalArgumentException();
        }
        Vertice aN = buscaVertice(a);
        Vertice bN = buscaVertice(b);
        aN.vecinos.agrega(bN);
        bN.vecinos.agrega(aN);
        aristas++;
    }

    /**
     * Desconecta dos elementos de la gráfica. Los elementos deben estar en la
     * gráfica y estar conectados entre ellos.
     * @param a el primer elemento a desconectar.
     * @param b el segundo elemento a desconectar.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     * @throws IllegalArgumentException si a o b no están conectados.
     */
    public void desconecta(T a, T b) {
      if (!this.contiene(a) || !this.contiene(b)) {
        throw new NoSuchElementException("No hay elementos suficientes.");
      }
      if (!sonVecinos(a,b)) {
        throw new IllegalArgumentException("No hay conexión.");
      }
      Vertice aN = buscaVertice(a);
      Vertice bN = buscaVertice(b);
      aN.vecinos.elimina(bN);
      bN.vecinos.elimina(aN);
      aristas--;
    }

    /**
     * Nos dice si el elemento está contenido en la gráfica.
     * @return <code>true</code> si el elemento está contenido en la gráfica,
     *         <code>false</code> en otro caso.
     */
    @Override public boolean contiene(T elemento) {
        return buscaVertice(elemento) != null;
    }

    /**
     * Elimina un elemento de la gráfica. El elemento tiene que estar contenido
     * en la gráfica.
     * @param elemento el elemento a eliminar.
     * @throws NoSuchElementException si el elemento no está contenido en la
     *         gráfica.
     */
    @Override public void elimina(T elemento) {
        if (!this.contiene(elemento)) {
          throw new NoSuchElementException("No hay elemento.");
        }
        Vertice eliminado = buscaVertice(elemento);
        for (Vertice vertice : vertices) {
          for (Vertice t : vertice.vecinos) {
            if (t.equals(eliminado)) {
              vertice.vecinos.elimina(eliminado);
              aristas--;
            }
          }
        }
        vertices.elimina(eliminado);
    }

    /**
     * Nos dice si dos elementos de la gráfica están conectados. Los elementos
     * deben estar en la gráfica.
     * @param a el primer elemento.
     * @param b el segundo elemento.
     * @return <code>true</code> si a y b son vecinos, <code>false</code> en otro caso.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     */
    public boolean sonVecinos(T a, T b) {
      Vertice primer = buscaVertice(a);
      Vertice segundo = buscaVertice(b);
      if (!this.contiene(a) || !this.contiene(b)) {
        throw new NoSuchElementException("No hay elementos.");
      }
      return primer.vecinos.contiene(segundo) && segundo.vecinos.contiene(primer);
    }

    /**
     * Regresa el vértice correspondiente el elemento recibido.
     * @param elemento el elemento del que queremos el vértice.
     * @throws NoSuchElementException si elemento no es elemento de la gráfica.
     * @return el vértice correspondiente el elemento recibido.
     */
    public VerticeGrafica<T> vertice(T elemento) {
        if (!this.contiene(elemento)) {
          throw new NoSuchElementException("No está en la gráfica.");
        }
        return buscaVertice(elemento);
    }

    /**
     * Define el color del vértice recibido.
     * @param vertice el vértice al que queremos definirle el color.
     * @param color el nuevo color del vértice.
     * @throws IllegalArgumentException si el vértice no es válido.
     */
    public void setColor(VerticeGrafica<T> vertice, Color color) {
        if (vertice == null || vertice.getClass() != Vertice.class) {
          throw new IllegalArgumentException("Vértice no válido.");
        }
        Vertice b = (Vertice)vertice;
        b.color = color;
    }

    /**
     * Nos dice si la gráfica es conexa.
     * @return <code>true</code> si la gráfica es conexa, <code>false</code> en
     *         otro caso.
     */
    public boolean esConexa() {
        Vertice n = vertices.getPrimero();
        for (Vertice t : vertices) {
          t.color = Color.ROJO;
        }
        Cola<Vertice> conexa = new Cola<Vertice>();
        n.color = Color.ROJO;
        conexa.mete(n);
        while (!conexa.esVacia()) {
          Vertice u = conexa.saca();
          for (Vertice s : u.vecinos) {
            if (s.color == Color.ROJO) {
              s.color = Color.NEGRO;
              conexa.mete(s);
            }
          }
        }
        for (Vertice m : vertices) {
          if (m.color == Color.ROJO) {
            return false;
          }
        }
        return true;
    }

    /**
     * Realiza la acción recibida en cada uno de los vértices de la gráfica, en
     * el orden en que fueron agregados.
     * @param accion la acción a realizar.
     */
    public void paraCadaVertice(AccionVerticeGrafica<T> accion) {
        for (Vertice s : vertices) {
          accion.actua(s);
        }
    }

    /**
     * Realiza la acción recibida en todos los vértices de la gráfica, en el
     * orden determinado por BFS, comenzando por el vértice correspondiente al
     * elemento recibido. Al terminar el método, todos los vértices tendrán
     * color {@link Color#NINGUNO}.
     * @param elemento el elemento sobre cuyo vértice queremos comenzar el
     *        recorrido.
     * @param accion la acción a realizar.
     * @throws NoSuchElementException si el elemento no está en la gráfica.
     */
    public void bfs(T elemento, AccionVerticeGrafica<T> accion) {
        if (!this.contiene(elemento)) {
          throw new NoSuchElementException("No hay elemento.");
        }
        /* En otro caso si existe el elemento.*/
        Cola<Vertice> bfs = new Cola<Vertice>();
        Vertice inicio = buscaVertice(elemento);
        recorrido(inicio, accion, bfs);
    }

    /**
     * Realiza la acción recibida en todos los vértices de la gráfica, en el
     * orden determinado por DFS, comenzando por el vértice correspondiente al
     * elemento recibido. Al terminar el método, todos los vértices tendrán
     * color {@link Color#NINGUNO}.
     * @param elemento el elemento sobre cuyo vértice queremos comenzar el
     *        recorrido.
     * @param accion la acción a realizar.
     * @throws NoSuchElementException si el elemento no está en la gráfica.
     */
    public void dfs(T elemento, AccionVerticeGrafica<T> accion) {
        if (!this.contiene(elemento)) {
          throw new NoSuchElementException("No hay elemento.");
        }
        /* En otro caso si existe el elemento.*/
        Pila<Vertice> dfs = new Pila<Vertice>();
        Vertice inicio = buscaVertice(elemento);
        recorrido(inicio, accion, dfs);
    }

    /*
    * Método auxiliar recorrido.
    * Que recibe una instancia de la clase MeteSaca<T>, un elemento y
    * una acción a realizar.
    */
    private void recorrido(Vertice v, AccionVerticeGrafica<T> accion,
    MeteSaca<Vertice> estructura) {
      for (Vertice s : vertices) {
        s.color = Color.ROJO;
      }
      estructura.mete(v);
      v.color = Color.NEGRO;
      while (!estructura.esVacia()) {
        Vertice u = estructura.saca();
        accion.actua(u);
        for (Vertice n : u.vecinos) {
          if (n.color == Color.ROJO) {
            estructura.mete(n);
            n.color = Color.NEGRO;
          }
        }
      }
      for (Vertice t : vertices) {
        t.color = Color.NINGUNO;
      }
    }

    /**
     * Nos dice si la gráfica es vacía.
     * @return <code>true</code> si la gráfica es vacía, <code>false</code> en
     *         otro caso.
     */
    @Override public boolean esVacia() {
        return vertices.esVacia();
    }

    /**
     * Limpia la gráfica de vértices y aristas, dejándola vacía.
     */
    @Override public void limpia() {
        vertices.limpia();
        aristas = 0;
    }

    /**
     * Regresa una representación en cadena de la gráfica.
     * @return una representación en cadena de la gráfica.
     */
    @Override public String toString() {
        String elementosG = "{";
        /*Lista auxiliar para guardar los elementos ya impresos. */
        Lista<Vertice> recorrido = new Lista<Vertice>();
        for (Vertice s : vertices) {
          elementosG += s.elemento.toString();
          elementosG += ", ";
        }
        elementosG += "}, {";
        for (Vertice j : vertices) {
          for (Vertice l : vertices) {
            if (sonVecinos(j.elemento, l.elemento) && !recorrido.contiene(l)) {
              elementosG += "(";
              elementosG += j.elemento.toString();
              elementosG += ", ";
              elementosG += l.elemento.toString();
              elementosG += "), ";
            }
            recorrido.agrega(j);
          }
        }
        return elementosG + "}";
    }

    /**
     * Nos dice si la gráfica es igual al objeto recibido.
     * @param objeto el objeto con el que hay que comparar.
     * @return <code>true</code> si la gráfica es igual al objeto recibido;
     *         <code>false</code> en otro caso.
     */
    @Override public boolean equals(Object objeto) {
        if (objeto == null || getClass() != objeto.getClass())
            return false;
        @SuppressWarnings("unchecked") Grafica<T> grafica = (Grafica<T>)objeto;
        if (vertices.getLongitud() != grafica.vertices.getLongitud() ||
        aristas != grafica.aristas || equals(vertices, grafica.vertices)) {
          return false;
        }
        for (Vertice r : vertices) {
          for (Vertice p : vertices) {
            if (!r.elemento.equals(p.elemento) &&
            sonVecinos(r.elemento, p.elemento) &&
            !grafica.sonVecinos(r.elemento, p.elemento)) {
              return false;
            }
          }
        }
        return true;
    }

    /*
     * Método auxiliar equals.
     * Que recibe dos listas de vertices, recorre la primera
     * y revisa si los vertices de las listas son iguales.
     * Regresa true si las listas son iguales, false en otro caso.
     */
    private boolean equals(Lista<Vertice> prim, Lista<Vertice> seg) {
        for (Vertice m : prim) {
          if (!seg.contiene(m)) {
            return false;
          }
        }
        return true;
    }

    /**
     * Regresa un iterador para iterar la gráfica. La gráfica se itera en el
     * orden en que fueron agregados sus elementos.
     * @return un iterador para iterar la gráfica.
     */
    @Override public Iterator<T> iterator() {
        return new Iterador();
    }
}
