package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.*;

/**
 * Clase auxiliar Ordenado.
 * Para graficar árboles binarios ordenados en svg.
 * @see Graficador para mayor referencia.
 * @author Armando Ramírez González.
 * @version 1.0.0
 */
public class Ordenado {

    /**
     * Método graficadorOrdenado.
     * @param coleccion una lista de tipo String.
     * @return String el árbol binario ordenado en svg.
     */
    public String graficadorOrdenado(Lista<Integer> coleccion) {
      String svg = "";
      /* Construimos el árbol. */
      ArbolBinarioOrdenado<Integer> arbolO = new ArbolBinarioOrdenado<>(coleccion);
      /* Altura del árbol. */
      int alturaArbol = arbolO.altura();
      VerticeArbolBinario<Integer> raiz = arbolO.raiz();
      int v = (int)(Math.pow(2,alturaArbol));
      int p = v/2;
      svg += ordenadoSVG(raiz, v, 0,v);
      return svg;
    }

    /*
     * Método auxiliar ordenadoSVG.
     * Que recibe un vértice de VerticeArbolBinario, su posicion espacial,
     * el nivel del vértice y su posicion auxiliar.
     * Regresa un String con el formato SVG del árbol.
     */
    private String ordenadoSVG(VerticeArbolBinario<Integer> n, int posicion, int nivel, int aux) {
        String m = "";
        int pos = posicion*40;
        int pvp = aux/2;
        Circulo circ = new Circulo(pos-20,40+(100*nivel),20,"black","white");
        Texto texO = new Texto(n.get().toString(),pos-30,40+(100*nivel),"black");
        m += circ.toSVG() + "\n";
        m += texO.toSVG() + "\n";
        if (n.hayIzquierdo()) {
          Linea linI = new Linea(pos-20, 60+(100*nivel), pos-(pvp*40)-20, 20+(100*(nivel+1)));
          m += linI.toSVG() + "\n";
          m += ordenadoSVG(n.izquierdo(), posicion-pvp, nivel+1, pvp);
        }
        if (n.hayDerecho()) {
          Linea linD = new Linea(pos-20, 60+(100*nivel), pos+(pvp*40)-20, 20+(100*(nivel+1)));
          m += linD.toSVG() + "\n";
          m += ordenadoSVG(n.derecho(), posicion+pvp, nivel+1,pvp);
        }
        return m;
    }
}
