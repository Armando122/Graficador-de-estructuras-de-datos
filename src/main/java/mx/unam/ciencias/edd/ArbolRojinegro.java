package mx.unam.ciencias.edd;

/**
 * Clase para árboles rojinegros. Un árbol rojinegro cumple las siguientes
 * propiedades:
 *
 * <ol>
 *  <li>Todos los vértices son NEGROS o ROJOS.</li>
 *  <li>La raíz es NEGRA.</li>
 *  <li>Todas las hojas (<code>null</code>) son NEGRAS (al igual que la raíz).</li>
 *  <li>Un vértice ROJO siempre tiene dos hijos NEGROS.</li>
 *  <li>Todo camino de un vértice a alguna de sus hojas descendientes tiene el
 *      mismo número de vértices NEGROS.</li>
 * </ol>
 *
 * Los árboles rojinegros se autobalancean.
 */
public class ArbolRojinegro<T extends Comparable<T>>
    extends ArbolBinarioOrdenado<T> {

    /**
     * Clase interna protegida para vértices.
     */
    protected class VerticeRojinegro extends Vertice {

        /** El color del vértice. */
        public Color color;

        /**
         * Constructor único que recibe un elemento.
         * @param elemento el elemento del vértice.
         */
        public VerticeRojinegro(T elemento) {
            super(elemento);
            color = Color.NINGUNO;
        }

        /**
         * Regresa una representación en cadena del vértice rojinegro.
         * @return una representación en cadena del vértice rojinegro.
         */
        public String toString() {
            if (color == Color.ROJO) {
              return ("R{" + elemento.toString() + "}");
            }
            if (color == Color.NEGRO) {
              return ("N{" + elemento.toString() + "}");
            }
            return "";
        }

        /**
         * Compara el vértice con otro objeto. La comparación es
         * <em>recursiva</em>.
         * @param objeto el objeto con el cual se comparará el vértice.
         * @return <code>true</code> si el objeto es instancia de la clase
         *         {@link VerticeRojinegro}, su elemento es igual al elemento de
         *         éste vértice, los descendientes de ambos son recursivamente
         *         iguales, y los colores son iguales; <code>false</code> en
         *         otro caso.
         */
        @Override public boolean equals(Object objeto) {
            if (objeto == null || getClass() != objeto.getClass())
                return false;
            @SuppressWarnings("unchecked")
                VerticeRojinegro vertice = (VerticeRojinegro)objeto;
            return (color == vertice.color && super.equals(vertice));
        }
    }

    /**
     * Constructor sin parámetros. Para no perder el constructor sin parámetros
     * de {@link ArbolBinarioOrdenado}.
     */
    public ArbolRojinegro() { super(); }

    /**
     * Construye un árbol rojinegro a partir de una colección. El árbol
     * rojinegro tiene los mismos elementos que la colección recibida.
     * @param coleccion la colección a partir de la cual creamos el árbol
     *        rojinegro.
     */
    public ArbolRojinegro(Coleccion<T> coleccion) {
        super(coleccion);
    }

    /**
     * Construye un nuevo vértice, usando una instancia de {@link
     * VerticeRojinegro}.
     * @param elemento el elemento dentro del vértice.
     * @return un nuevo vértice rojinegro con el elemento recibido dentro del mismo.
     */
    @Override protected Vertice nuevoVertice(T elemento) {
        return new VerticeRojinegro(elemento);
    }

    /*
    * Método auxiliar VerticeRojinegro.
    * Que se encarga de hacer las audiciones en un solo
    * lugar.
    * Recibe un vértice de VerticeArbolBinario<T>
    * Regresa el un vertice rojinegro.
    */
    private VerticeRojinegro verticeRojinegro(VerticeArbolBinario<T> vertice) {
        VerticeRojinegro n = (VerticeRojinegro)vertice;
        return n;
    }

    /**
     * Regresa el color del vértice rojinegro.
     * @param vertice el vértice del que queremos el color.
     * @return el color del vértice rojinegro.
     * @throws ClassCastException si el vértice no es instancia de {@link
     *         VerticeRojinegro}.
     */
    public Color getColor(VerticeArbolBinario<T> vertice) {
        return verticeRojinegro(vertice).color;
    }

    /**
     * Agrega un nuevo elemento al árbol. El método invoca al método {@link
     * ArbolBinarioOrdenado#agrega}, y después balancea el árbol recoloreando
     * vértices y girando el árbol como sea necesario.
     * @param elemento el elemento a agregar.
     */
    @Override public void agrega(T elemento) {
        super.agrega(elemento);
        VerticeRojinegro ultimo = verticeRojinegro(ultimoAgregado);
        ultimo.color = Color.ROJO;
        rebalanceaAgrega(ultimo);
    }

    /*
    * Método auxiliar esRojo.
    * Regresa true si el vertice es rojo.
    *         false en otro caso
    */
    private boolean esRojo(VerticeRojinegro vertice) {
        return (vertice != null && vertice.color == Color.ROJO);
    }

    /*
    * Método auxiliar esNegro.
    * Regresa true si el vertice es negro.
    * false en otro caso.
    */
    private boolean esNegro(VerticeRojinegro vertice) {
        return (vertice == null || vertice.color == Color.NEGRO);
    }

    /*
    * Método auxiliar rebalanceaAgrega.
    * Que rebalancea el árbol rojinegro después de agregar
    * un elemento.
    * Recibe un vértice rojinegro de color rojo distinto de vacío.
    */
    private void rebalanceaAgrega(VerticeRojinegro ultimo) {
        VerticeRojinegro tio;
        /* Caso1 */
        if (ultimo.padre == null) {
          ultimo.color = Color.NEGRO;
          return;
        }

        VerticeRojinegro padreA = verticeRojinegro(ultimo.padre());

        /* Caso 2*/
        if (padreA.color == Color.NEGRO)
          return;

        VerticeRojinegro abuelo = verticeRojinegro(padreA.padre);

        /* Caso 3. Recordando no hacer verificaciones innecesarias. */
        if (esIzquierdo(padreA)) {
          tio = verticeRojinegro(abuelo.derecho);
        } else {
          tio = verticeRojinegro(abuelo.izquierdo);
        }
        if (tio != null && (tio.color == Color.ROJO)) {
          tio.color = Color.NEGRO;
          padreA.color = Color.NEGRO;
          abuelo.color = Color.ROJO;
          rebalanceaAgrega(abuelo);
          return;
        }

        /* Caso 4. */
        /* Esto automaticamente verifica su están cruzados. */
        if (esIzquierdo(ultimo) != esIzquierdo(padreA)) {
          if (esIzquierdo(padreA)) {
            super.giraIzquierda(padreA);
          } else {
              super.giraDerecha(padreA);
          }
          VerticeRojinegro hijoProv = padreA;
          padreA = ultimo;
          ultimo = hijoProv;
        }

        /* Caso 5. */
        padreA.color = Color.NEGRO;
        abuelo.color = Color.ROJO;
        if (esIzquierdo(ultimo)) {
          super.giraDerecha(abuelo);
        } else {
          super.giraIzquierda(abuelo);
        }
    }

    /**
    * Elimina un elemento del árbol. El método elimina el vértice que contiene
    * el elemento, y recolorea y gira el árbol como sea necesario para
    * rebalancearlo.
    * @param elemento el elemento a eliminar del árbol.
    */
    @Override public void elimina(T elemento) {
      /* Buscamos el elemento si es null, termina. */
      VerticeRojinegro eliminado = verticeRojinegro(busca(elemento));
      if (eliminado == null) {
        return;
      }
      /* Caso donde tiene dos hijos distintos de null. */
      if (eliminado.hayIzquierdo()) {
        eliminado = verticeRojinegro(intercambiaEliminable(eliminado));
      }

      /* Caso donde no tiene hijos. */
      if (!eliminado.hayDerecho() && !eliminado.hayIzquierdo()) {
        VerticeRojinegro fantasma = verticeRojinegro(nuevoVertice(null));
        fantasma.color = Color.NEGRO;
        fantasma.padre = eliminado;
        eliminado.izquierdo = fantasma;
      }
      VerticeRojinegro h = getHijoV(eliminado);
      eliminaVertice(eliminado);
      if (esNegro(eliminado) && esNegro(h)) {
        rebalanceaNegro(h);
      } else {
        h.color = Color.NEGRO;
      }
      eliminaFantasma(h);
      elementos--;
    }

    /*
    * Método auxiliar getHijoV.
    * Recibe un vértice con un único hijo
    * Regresa su hijo.
    */
    private VerticeRojinegro getHijoV(VerticeRojinegro v) {
      if (v.izquierdo != null) {
        return verticeRojinegro(v.izquierdo);
      }
      return verticeRojinegro(v.derecho);
    }

    /*
    * Método auxiliar eliminaFantasma.
    * Recibe un vértice fantasma que será eliminado.
    */
    private void eliminaFantasma(VerticeRojinegro fantasma) {
        if (fantasma.elemento == null) {
          eliminaAuxiliar(fantasma);
        }
    }

    /*
    * Método auxiliar eliminaAuxiliar.
    * Recibe un vértice que ya se comprobó que es fantasma y se procede
    * a eliminarlo.
    */
    private void eliminaAuxiliar(VerticeRojinegro f) {
        if (raiz == f) {
          raiz = null;
        } else if (esIzquierdo(f)) {
          f.padre.izquierdo = null;
        } else {
          f.padre.derecho = null;
        }
    }

    /*
    * Método auxiliar hermanoDeV.
    * Que nos regresa el hermano del vértice V que recibe.
    */
    private VerticeRojinegro hermanoDeV(VerticeRojinegro v) {
        if (esIzquierdo(v)) {
          return verticeRojinegro(v.padre.derecho);
        } else {
          return verticeRojinegro(v.padre.izquierdo);
        }
    }

    /*
    * Método auxiliar rebalanceaNegro.
    * Que recibe un vértice de color negro distinto de null.
    */
    private void rebalanceaNegro(VerticeRojinegro verticeF) {
        /* Caso 1. */
        if (!verticeF.hayPadre()) {
          raiz = verticeF;
          return;
        }
        VerticeRojinegro padreV = verticeRojinegro(verticeF.padre);
        VerticeRojinegro hermanoV = hermanoDeV(verticeF);

        /* Caso 2, verticeF tiene padre. */
        if (esRojo(hermanoV)) {
          hermanoV.color = Color.NEGRO;
          padreV.color = Color.ROJO;
          if (esIzquierdo(verticeF)) {
            super.giraIzquierda(padreV);
          } else {
            super.giraDerecha(padreV);
          }
          padreV = verticeRojinegro(verticeF.padre);
          hermanoV = hermanoDeV(verticeF);
        }
        VerticeRojinegro hijoIzq = verticeRojinegro(hermanoV.izquierdo);
        VerticeRojinegro hijoDer = verticeRojinegro(hermanoV.derecho);

        /* Caso 3. */
        if (esNegro(hermanoV) && esNegro(hijoIzq) && esNegro(hijoDer)) {
          if (esNegro(padreV)) {
            hermanoV.color = Color.ROJO;
            rebalanceaNegro(padreV);
            return;
          }
           /*
           * Aquí mismo entra en caso 4, ya que no pasó el 3 y están
           * relacionados.
           */
           padreV.color = Color.NEGRO;
           hermanoV.color = Color.ROJO;
           return;
        }

        /* Caso 5, donde las indeterminaciones comienzan. */
        if ((esRojo(hijoIzq) && esNegro(hijoDer) && esIzquierdo(verticeF)) ||
             esRojo(hijoDer) && esNegro(hijoIzq) && esDerecho(verticeF)) {
           if (esRojo(hijoIzq)) {
             hijoIzq.color = Color.NEGRO;
           } else {
             hijoDer.color = Color.NEGRO;
           }
           hermanoV.color = Color.ROJO;
           if (esIzquierdo(verticeF)) {
             super.giraDerecha(hermanoV);
           } else {
             super.giraIzquierda(hermanoV);
           }
           hermanoV = hermanoDeV(verticeF);
           hijoIzq = verticeRojinegro(hermanoV.izquierdo);
           hijoDer = verticeRojinegro(hermanoV.derecho);
        }

        /* Caso 6, es lo único que puede pasar. */
        hermanoV.color = padreV.color;
        padreV.color = Color.NEGRO;
        if (esIzquierdo(verticeF)) {
          hijoDer.color = Color.NEGRO;
        } else {
          hijoIzq.color = Color.NEGRO;
        }
        if (esIzquierdo(verticeF)) {
          super.giraIzquierda(padreV);
        } else {
          super.giraDerecha(padreV);
        }
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
     * Lanza la excepción {@link UnsupportedOperationException}: los árboles
     * rojinegros no pueden ser girados a la izquierda por los usuarios de la
     * clase, porque se desbalancean.
     * @param vertice el vértice sobre el que se quiere girar.
     * @throws UnsupportedOperationException siempre.
     */
    @Override public void giraIzquierda(VerticeArbolBinario<T> vertice) {
        throw new UnsupportedOperationException("Los árboles rojinegros no " +
                                                "pueden girar a la izquierda " +
                                                "por el usuario.");
    }

    /**
     * Lanza la excepción {@link UnsupportedOperationException}: los árboles
     * rojinegros no pueden ser girados a la derecha por los usuarios de la
     * clase, porque se desbalancean.
     * @param vertice el vértice sobre el que se quiere girar.
     * @throws UnsupportedOperationException siempre.
     */
    @Override public void giraDerecha(VerticeArbolBinario<T> vertice) {
        throw new UnsupportedOperationException("Los árboles rojinegros no " +
                                                "pueden girar a la derecha " +
                                                "por el usuario.");
    }
}
