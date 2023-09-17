package mx.unam.ciencias.edd;

/**
 * Clase para pilas genéricas.
 */
public class Pila<T> extends MeteSaca<T> {

    /**
     * Regresa una representación en cadena de la pila.
     * @return una representación en cadena de la pila.
     */
    @Override public String toString() {
        if (esVacia()) {
          return "";
        }
        Nodo c = cabeza;
        String pila = "";
        while (c != null) {
          pila += c.elemento + "\n";
          c = c.siguiente;
        }
        return pila;
    }

    /**
     * Agrega un elemento al tope de la pila.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    @Override public void mete(T elemento) {
        if (elemento == null) {
          throw new IllegalArgumentException("No acepta elementos vacíos");
        }
        Nodo m = new Nodo(elemento);
        Nodo c = cabeza;
        if (c == null) {
          cabeza = m;
          rabo = m;
        } else {
          m.siguiente = c;
          cabeza = m;
        }
    }
}
