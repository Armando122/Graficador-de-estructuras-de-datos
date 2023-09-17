package mx.unam.ciencias.edd;

/**
 * Clase para colas genéricas.
 */
public class Cola<T> extends MeteSaca<T> {

    /**
     * Regresa una representación en cadena de la cola.
     * @return una representación en cadena de la cola.
     */
    @Override public String toString() {
        if (esVacia()) {
          return "";
        }
        String cola = "";
        Nodo c = cabeza;
        while (c != null){
          cola += c.elemento + ",";
          c = c.siguiente;
        }
        return cola;
    }

    /**
     * Agrega un elemento al final de la cola.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    @Override public void mete(T elemento) {
        if (elemento == null) {
          throw new IllegalArgumentException("El elemento es vacío");
        }
        Nodo n = new Nodo(elemento);
        if (cabeza == null) {
          cabeza = n;
          rabo = n;
          return;
        } else {
          rabo.siguiente = n;
          rabo = n;
        }
    }
}
