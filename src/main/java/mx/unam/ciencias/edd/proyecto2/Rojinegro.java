package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.*;

/**
 * Clase auxiliar Rojinegro.
 * Para graficar árboles binarios rojinegros en svg.
 * @see Graficador para mayor referencia.
 * @author Armando Ramírez González.
 * @version 1.0.0
 */
public class Rojinegro {

    /**
     * Método graficadorRojinegro.
     * @param coleccion una lista de tipo String.
     * @return String el árbol binario ordenado en svg.
     */
    public String graficadorRojinegro(Lista<Integer> coleccion) {
      String svg = "";
      /* Construimos el árbol. */
      ArbolRojinegro<Integer> arbolR = new ArbolRojinegro<>(coleccion);
      /* Altura del árbol. */
      int alturaArbol = arbolR.altura();
      VerticeArbolBinario<Integer> raiz = arbolR.raiz();
      int v = (int)(Math.pow(2,alturaArbol));
      int p = v/2;
      svg += rojinegroSVG(arbolR,raiz, v, 0, v);
      return svg;
    }

    /*
     * Método auxiliar rojinegroSVG.
     * Que recibe un árbol rojinegro de tipo String,
     * un vértice de VerticeArbolBinario, su posicion espacial,
     * el nivel del vértice y su posicion auxiliar.
     * Regresa un String con el formato SVG del árbol.
     */
    private String rojinegroSVG(ArbolRojinegro<Integer> r, VerticeArbolBinario<Integer> n, int posicion, int nivel, int aux) {
        String m = "";
        int pos = posicion*40;
        int pvp = aux/2;
        if (r.getColor(n) == Color.NEGRO) {
          Circulo circ = new Circulo(pos-20,40+(100*nivel),20,"black","black");
          m += circ.toSVG() + "\n";
        } else {
          Circulo circ = new Circulo(pos-20,40+(100*nivel),20,"red","red");
          m += circ.toSVG() + "\n";
        }
        Texto texO = new Texto(n.get().toString(),pos-30,40+(100*nivel),"white");
        m += texO.toSVG() + "\n";
        if (n.hayIzquierdo()) {
          Linea linI = new Linea(pos-20, 60+(100*nivel), pos-(pvp*40)-20, 20+(100*(nivel+1)));
          m += linI.toSVG() + "\n";
          m += rojinegroSVG(r,n.izquierdo(), posicion-pvp, nivel+1, pvp);
        }
        if (n.hayDerecho()) {
          Linea linD = new Linea(pos-20, 60+(100*nivel), pos+(pvp*40)-20, 20+(100*(nivel+1)));
          m += linD.toSVG() + "\n";
          m += rojinegroSVG(r,n.derecho(), posicion+pvp, nivel+1,pvp);
        }
        return m;
    }
}
