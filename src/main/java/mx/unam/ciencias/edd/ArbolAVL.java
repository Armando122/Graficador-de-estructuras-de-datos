package mx.unam.ciencias.edd;

/**
 * <p>Clase para árboles AVL.</p>
 *
 * <p>Un árbol AVL cumple que para cada uno de sus vértices, la diferencia entre
 * la áltura de sus subárboles izquierdo y derecho está entre -1 y 1.</p>
 */
public class ArbolAVL<T extends Comparable<T>>
    extends ArbolBinarioOrdenado<T> {

    /**
     * Clase interna protegida para vértices.
     */
    protected class VerticeAVL extends Vertice {

        /** La altura del vértice. */
        public int altura;

        /**
         * Constructor único que recibe un elemento.
         * @param elemento el elemento del vértice.
         */
        public VerticeAVL(T elemento) {
            super(elemento);
        }

        /**
         * Regresa la altura del vértice.
         * @return la altura del vértice.
         */
        @Override public int altura() {
            return altura;
        }

        /**
         * Regresa una representación en cadena del vértice AVL.
         * @return una representación en cadena del vértice AVL.
         */
        @Override public String toString() {
            /* Balance de la forma vertice h/b. */
            return (elemento.toString() + " " + altura + "/" + balance(this));
        }

        /**
         * Compara el vértice con otro objeto. La comparación es
         * <em>recursiva</em>.
         * @param objeto el objeto con el cual se comparará el vértice.
         * @return <code>true</code> si el objeto es instancia de la clase
         *         {@link VerticeAVL}, su elemento es igual al elemento de éste
         *         vértice, los descendientes de ambos son recursivamente
         *         iguales, y las alturas son iguales; <code>false</code> en
         *         otro caso.
         */
        @Override public boolean equals(Object objeto) {
            if (objeto == null || getClass() != objeto.getClass())
                return false;
            @SuppressWarnings("unchecked") VerticeAVL vertice = (VerticeAVL)objeto;
            return (altura == vertice.altura && super.equals(objeto));
        }
    }

    /**
     * Constructor sin parámetros. Para no perder el constructor sin parámetros
     * de {@link ArbolBinarioOrdenado}.
     */
    public ArbolAVL() { super(); }

    /**
     * Construye un árbol AVL a partir de una colección. El árbol AVL tiene los
     * mismos elementos que la colección recibida.
     * @param coleccion la colección a partir de la cual creamos el árbol AVL.
     */
    public ArbolAVL(Coleccion<T> coleccion) {
        super(coleccion);
    }

    /**
     * Construye un nuevo vértice, usando una instancia de {@link VerticeAVL}.
     * @param elemento el elemento dentro del vértice.
     * @return un nuevo vértice con el elemento recibido dentro del mismo.
     */
    @Override protected Vertice nuevoVertice(T elemento) {
        return new VerticeAVL(elemento);
    }

    /*
    * Método auxiliar VerticeAVL.
    * Que se encarga de hacer las audiciones en un solo
    * lugar.
    * Recibe un vértice de VerticeArbolBinario<T>.
    * Regresa el vértice AVL.
    */
    private VerticeAVL verticeAVL(VerticeArbolBinario<T> vertice) {
        return (VerticeAVL)vertice;
    }

    /**
     * Agrega un nuevo elemento al árbol. El método invoca al método {@link
     * ArbolBinarioOrdenado#agrega}, y después balancea el árbol girándolo como
     * sea necesario.
     * @param elemento el elemento a agregar.
     */
    @Override public void agrega(T elemento) {
        super.agrega(elemento);
        /* Se sabe que el último vértice agregado existe, funciona. */
        rebalancea(verticeAVL(ultimoAgregado.padre));
    }

    /**
     * Elimina un elemento del árbol. El método elimina el vértice que contiene
     * el elemento, y gira el árbol como sea necesario para rebalancearlo.
     * @param elemento el elemento a eliminar del árbol.
     */
    @Override public void elimina(T elemento) {
        VerticeAVL eliminado = verticeAVL(busca(elemento));
        /* Terminamos si el elemento no está. */
        if (eliminado == null) {
          return;
        }
        /* En otro caso si existe. Verificamos el número de hijos. */
        if (eliminado.hayIzquierdo() && eliminado.hayDerecho()) {
          eliminado = verticeAVL(intercambiaEliminable(eliminado));
        }
        if (!eliminado.hayIzquierdo() && !eliminado.hayDerecho()) {
          eliminaSinHijos(eliminado);
        } else {
          eliminaUnico(eliminado);
        }

        /* Rebalanceamos sobre su padre. */
        rebalancea(verticeAVL(eliminado.padre));
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

    /*
    * Método auxiliar eliminaSinHijos.
    * Se encarga de eliminar un vértice que por definición
    * es una hoja. (No tiene hijos izquierdo ni derecho).
    * Recibe un vertice AVL que será eliminado.
    */
    private void eliminaSinHijos(VerticeAVL v) {
        if (raiz == v) {
          raiz = null;
        } else if (esIzquierdo(v)) {
          v.padre.izquierdo = null;
        } else {
          v.padre.derecho = null;
        }
        elementos--;
    }

    /*
    * Método auxiliar eliminaUnico.
    * Que se encarga de eliminar un vértice
    * que tiene a lo más un único hijo.
    * Recibe el vértice a eliminar de tipo AVL.
    * Utiliza el algoritmo de la clase ArbolBinarioOrdenado
    * eliminaVertice.
    */
    private void eliminaUnico(VerticeAVL v) {
        eliminaVertice(v);
        elementos--;
    }

    /*
    * Método auxiliar recursivo rebalancea.
    * Que se encarga de rebalancear árboles AVL en función
    * de su altura y su balance.
    * Recibe un vertice de la clase VerticeAVL.
    */
    private void rebalancea(VerticeAVL vertice) {
        /* Clausula de escape. */
        if (vertice == null) {
          return;
        }
        setAltura(vertice);

        /* Casos extremo: que el balance del vértice sea -2 o 2. */
        if (balance(vertice) == -2) {
          if (balance(verticeAVL(vertice.derecho)) == 1) {
            VerticeAVL aux = verticeAVL(vertice.derecho);
            super.giraDerecha(aux);
            /* Actualizamos las alturas después de girar. */
            setAltura(aux);
            setAltura(verticeAVL(aux.padre));
          }
          super.giraIzquierda(vertice);
          setAltura(vertice);

        } else if (balance(vertice) == 2) {
          if (balance(verticeAVL(vertice.izquierdo)) == -1) {
            VerticeAVL aux1 = verticeAVL(vertice.izquierdo);
            super.giraIzquierda(aux1);
            setAltura(aux1);
            setAltura(verticeAVL(aux1.padre));
          }
          super.giraDerecha(vertice);
          setAltura(vertice);
        }

        /* En caso de que el vértice no tenga balance -2 ó 2.
        *  Hacemos recursión sobre el padre del vértice. */
        rebalancea(verticeAVL(vertice.padre));
    }

    /*
    * Método auxiliar balanceVertice.
    * Que calcula el balance de un vértice como
    * la diferencia de las alturas de sus sub árboles.
    * Recibe el vértice.
    * Regresa el balance del vértice
    */
    private int balance(VerticeAVL vertice) {
        if (vertice == null)
          return 0;
        return auxAltura(vertice.izquierdo) - auxAltura(vertice.derecho);
    }

    /*
    * Método auxiliar setAltura.
    * Que calcula la altura de un vértice dado.
    * Recibe un vérticeAVL del que se obtendrá su altura.
    */
    private void setAltura(VerticeAVL vertice) {
        if (vertice == null) {
          return;
        }
        vertice.altura = 1 + Math.max(auxAltura(vertice.izquierdo),
                                      auxAltura(vertice.derecho));
    }

    /*
    * Método auxiliar auxAltura que permitirá implementar un
    * método para obtener el balance de un vértice de forma más
    * eficiente.
    * Que se encargaŕa de calcular la altura de un vértice
    * utilizando el atributo altura de los vértices AVL.
    * Recibe un vértice de VerticeArbolBinario<T>.
    * Regresa -1 si es vacío, en otro caso regresa la altura
    * asociada al vértice.
    */
    private int auxAltura(VerticeArbolBinario<T> v) {
        if (v == null) {
          return -1;
        }
        return verticeAVL(v).altura;
    }

    /**
     * Lanza la excepción {@link UnsupportedOperationException}: los árboles AVL
     * no pueden ser girados a la derecha por los usuarios de la clase, porque
     * se desbalancean.
     * @param vertice el vértice sobre el que se quiere girar.
     * @throws UnsupportedOperationException siempre.
     */
    @Override public void giraDerecha(VerticeArbolBinario<T> vertice) {
        throw new UnsupportedOperationException("Los árboles AVL no  pueden " +
                                                "girar a la izquierda por el " +
                                                "usuario.");
    }

    /**
     * Lanza la excepción {@link UnsupportedOperationException}: los árboles AVL
     * no pueden ser girados a la izquierda por los usuarios de la clase, porque
     * se desbalancean.
     * @param vertice el vértice sobre el que se quiere girar.
     * @throws UnsupportedOperationException siempre.
     */
    @Override public void giraIzquierda(VerticeArbolBinario<T> vertice) {
        throw new UnsupportedOperationException("Los árboles AVL no  pueden " +
                                                "girar a la derecha por el " +
                                                "usuario.");
    }
}
