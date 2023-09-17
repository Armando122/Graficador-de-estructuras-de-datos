package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.*;

/**
 * Clase Graficador para graficar las estructuras
 * en svg.
 * @author Armando Ramírez González.
 * @version 1.0.0.
 */
public class Graficador {

    /* El tipo de estrucutra a graficar. */
    private Estructura est;
    /* Variable de tipo string para la estructura. */
    private String svg;
    private int j;
    private int alt;

    /**
     * Constructor, que crea un objeto
     * sin estructura definida.
     */
    public Graficador() {
        est = Estructura.NINGUNO;
        this.svg = "";
    }

    /**
     * Método estructura.
     * Que define el tipo de estructura a graficar.
     * @param Estructura el nombre de la estructura.
     */
    public void estructura(Estructura nombre) {
        this.est = nombre;
    }

    /**
     * Método grafica.
     * Que devuelve la estructura graficada en SVG.
     * @param coleccion una lista.
     * @return String el código SVG de la estructura.
     */
    public String grafica(Lista<Integer> coleccion) {
        /* switch para saber la estructura a graficar. */
        switch (est) {
          case LISTA:
            svg = graficaLista(coleccion);
          break;

          case PILA:
            svg = graficaPila(coleccion);
          break;

          case COLA:
            svg = graficaCola(coleccion);
          break;

          case ARBOLCOMPLETO:
            svg = graficaArbolCompleto(coleccion);
          break;

          case ARBOLORDENADO:
            svg = graficaArbolOrdenado(coleccion);
          break;

          case ARBOLROJINEGRO:
            svg = graficaRojinegro(coleccion);
          break;

          case ARBOLAVL:
            svg = graficaArbolAVL(coleccion);
          break;

          case GRAFICA:
            svg = graficaGrafo(coleccion);
          break;

          case MONTICULOMINIMO:
            svg = graficaMonticulo(coleccion);
          break;

          default:
            svg = "No hay estructura";
          break;
        }
        return ("<g>\n" + svg + "</g>");
    }

    /*
     * Método auxiliar graficaLista.
     * Que recibe una colección y regresa un String con el formato svg
     * de una lista.
     */
    private String graficaLista(Lista<Integer> coleccion) {
        IteradorLista<Integer> i = coleccion.iteradorLista();
        int j = 0;
        int x = 0;
        Flecha f = new Flecha();
        while (i.hasNext()) {
          /* Construímos los rectángulos. */
          Rectangulo rect = new Rectangulo(20.0+(80*j),20.0,40.0,20.0);
          svg += rect.toSVG() + "\n";
          x = 20 + (80*j);
          if (i.hasPrevious()) {
            svg += f.flechaBidireccional(x-40,30,x,30) + "\n";
          }
          Texto tex = new Texto(i.next().toString(),30+(80*j),37,"black");
          svg += tex.toSVG() + "\n";
          j++;
        }
        return svg;
    }

    /*
     * Método auxiliar graficaPila.
     * Que recibe una Lista y regresa un String con el formato svg
     * de una pila.
     */
    private String graficaPila(Lista<Integer> coleccion) {
        /* Construimos la pila. */
        Pila<Integer> pila = new Pila<>();
        for (Integer e : coleccion) {
          pila.mete(e);
        }
        int j = 0;
        while (!pila.esVacia()) {
          Integer elemento = pila.saca();
          Linea izq = new Linea(20,20+(40*j),20,60+(40*j));
          Linea der = new Linea(60,20+(40*j),60,60+(40*j));
          Texto tex = new Texto(elemento.toString(),30,40+(40*j),"black");
          svg += izq.toSVG() + "\n";
          svg += der.toSVG() + "\n";
          svg += tex.toSVG() + "\n";
          j++;
        }
        Linea fin = new Linea(20,20+(40*j),60,20+(40*j));
        svg += fin.toSVG() + "\n";
        return svg;
    }

    /*
     * Método auxiliar graficaCola.
     * Que recibe una colección y regresa un String con el formato svg
     * de una cola.
     */
    private String graficaCola(Lista<Integer> coleccion) {
      /* Construimos la cola. */
      Cola<Integer> cola = new Cola<>();
      int m = coleccion.getElementos();
      for (Integer e : coleccion) {
        cola.mete(e);
      }
      int j = 1;
      Linea iniArriba = new Linea(20,20,60,20);
      Linea iniAbajo = new Linea(20,60,60,60);
      Linea finArriba = new Linea((40*m)+60,20,(40*m)+100,20);
      Linea finAbajo = new Linea((40*m)+60,60,(40*m)+100,60);
      svg += iniArriba.toSVG() + "\n";
      svg += iniAbajo.toSVG() + "\n";
      svg += finArriba.toSVG() + "\n";
      svg += finAbajo.toSVG() + "\n";
      while (!cola.esVacia()) {
        Integer elemento = cola.saca();
        Linea arriba = new Linea((40*m)+20,20,(40*m)+60,20);
        Linea abajo = new Linea((40*m)+20,60,(40*m)+60,60);
        Texto tex = new Texto(elemento.toString(),(40*m)+40,45,"black");
        svg += arriba.toSVG() + "\n";
        svg += abajo.toSVG() + "\n";
        svg += tex.toSVG() + "\n";
        m--;
      }
      return svg;
    }

    /*
     * Método auxiliar graficaArbolCompleto.
     * Que recibe una colección y regresa un String con el formato svg
     * de un árbol binario completo.
     */
    private String graficaArbolCompleto(Lista<Integer> coleccion) {
        /* Construimos el árbol. */
        ArbolBinarioCompleto<Integer> arbolC = new ArbolBinarioCompleto<>(coleccion);
        int altG = arbolC.altura();
        VerticeArbolBinario<Integer> r = arbolC.raiz();
        arbolC.bfs((v) -> {
          if (v.equals(r)) {
            alt = altG;
          }
          int aux = (int)(Math.pow(2,alt));
          int dibuj = aux-1;
          int i = v.profundidad();
          int nivel = (int)(Math.pow(2,i));
          if (v.hayPadre()) {
            int mx = (int)(Math.pow(2,alt+1));
            int hijo = (mx-1);
            int nx = (int)(Math.pow(2,(alt-1)));
            int lin1 = nx-1;
            Circulo h = new Circulo((40*j*(hijo))+(40*dibuj)+40*j+20, 40+(100*i), 20, "black", "white");
            Texto tex1 = new Texto(v.get().toString(), (40*j)*(hijo+1)+(40*dibuj)+10,40+(100*i), "black");
            if (v.hayIzquierdo()) {
              Linea lin = new Linea((40*j)*(hijo+1)+(40*dibuj)+20, 60+(100*i),
                                    ((40*j)*(hijo+1)+(40*dibuj)+20)-(lin1*40)-40, 20+(100*(i+1)));
              svg += lin.toSVG() + "\n";
            }
            if (v.hayDerecho()) {
              Linea lin = new Linea((40*j)*(hijo+1)+(40*dibuj)+20, 60+(100*i),
                                    ((40*j)*(hijo+1)+(40*dibuj)+20)+(lin1*40)+40, 20+(100*(i+1)));
              svg += lin.toSVG() + "\n";
            }
            svg += h.toSVG() + "\n";
            svg += tex1.toSVG() + "\n";
          } else {
            Circulo c = new Circulo((dibuj*40)+20, 40+(100*i), 20, "black", "white");
            Texto tex = new Texto(v.get().toString(),(dibuj*40)+20, 40+(100*i),"black");
            svg += c.toSVG() + "\n";
            svg += tex.toSVG() + "\n";
            if (v.hayIzquierdo()) {
              Linea lin = new Linea((dibuj*40)+20, 60+(100*i),
                                    ((dibuj+1)/2)*40-20, 20+(100*(i+1)));
              svg += lin.toSVG() + "\n";
            }
            if (v.hayDerecho()) {
              Linea lin = new Linea((dibuj*40)+20, 60+(100*i),
                                    (dibuj*40)+((dibuj+1)/2)*40+20, 20+(100*(i+1)));
              svg += lin.toSVG() + "\n";
            }
          }
          j += 1;
          if (j >= nivel) {
            j = 0;
            alt--;
          }
        });
        return svg;
    }

    /*
     * Método auxiliar graficaArbolOrdenado.
     * Que recibe una colección y regresa un String con el formato svg
     * de un árbol binario ordenado.
     */
    private String graficaArbolOrdenado(Lista<Integer> coleccion) {
        Ordenado m = new Ordenado();
        svg += m.graficadorOrdenado(coleccion);
        return svg;
    }

    /*
     * Método auxiliar graficaRojinegro.
     * Que recibe una colección y regresa un String con el formato svg
     * de un árbol rojinegro.
     */
    private String graficaRojinegro(Lista<Integer> coleccion) {
      Rojinegro r = new Rojinegro();
      svg += r.graficadorRojinegro(coleccion);
      return svg;
    }

    /*
     * Método auxiliar graficaArbolAVL.
     * Que recibe una colección y regresa un String con el formato svg
     * de un árbol AVL.
     */
    private String graficaArbolAVL(Lista<Integer> coleccion) {
      AvlSvg a = new AvlSvg();
      svg += a.graficadorAVL(coleccion);
      return svg;
    }

    /*
     * Método auxiliar graficaGrafo.
     * Que recibe una colección y regresa un String con el formato svg
     * de una gráfica.
     */
    private String graficaGrafo(Lista<Integer> coleccion) {
      int longitud = coleccion.getLongitud();
      int indice = 0;
      int j = 0;
      int l = 0;
      int k = 0;
      int abscisa = 0;
      int ordenada = 0;

      /* Construimos la gráfica. */
      Grafica<Integer> graph = new Grafica<Integer>();
      for (Integer elemento : coleccion) {
        if (!graph.contiene(elemento)) {
          graph.agrega(elemento);
        }
      }

      /* Conectamos. */
      for (int i = 0; i<longitud-1; i+=2) {
        j = i + 1;
        Integer izq = coleccion.get(i);
        Integer der = coleccion.get(j);
        try {
          if (!izq.equals(der)) {
            graph.conecta(izq,der);
          }
        } catch(IllegalArgumentException e) {
          System.err.println("La pareja de vértices xy es igual que yx, el programa"
                           + " no puede conectar dos vértices ya conectados.");
        }
      }

      /* Dibujamos la gráfica. */
      for (Integer a : graph) {
        if (indice%2 == 0) {
          abscisa = 100-(l*40)+(k*40);
          ordenada = 100+(40*indice);
          Circulo n = new Circulo(abscisa, ordenada, 20, "black", "black");
          Texto texV = new Texto(a.toString(), abscisa-30, ordenada-10, "blue");
          svg += n.toSVG() + "\n";
          svg += texV.toSVG() + "\n";
        } else {
          abscisa = 260-(l*40)+(k*40);
          ordenada = 20+40*indice;
          Circulo n1 = new Circulo(abscisa, ordenada, 20, "black", "black");
          Texto texV1 = new Texto(a.toString(), abscisa+25, ordenada-10, "blue");
          svg += n1.toSVG() + "\n";
          svg += texV1.toSVG() + "\n";
          if (l == 0) {
            l = 1;
            k = 0;
          } else {
            l = 0;
            k = 1;
          }
        }
        int p = 0;
        int aux = 0;
        int aux1 = 0;
        for (Integer b : graph) {
          if (graph.sonVecinos(a,b)) {
            int abscisaFin = 0;
            int ordenadaFin = 0;
            if (p%2 == 0) {
              abscisaFin = 100-(aux*40)+(aux1*40);
              ordenadaFin = 100+(40*p);
              Linea arista = new Linea(abscisa, ordenada, abscisaFin, ordenadaFin);
              svg += arista.toSVG() + "\n";
            } else {
              abscisaFin = 260-(aux*40)+(aux1*40);
              ordenadaFin = 20+(40*p);
              Linea arista = new Linea(abscisa, ordenada, abscisaFin, ordenadaFin);
              svg += arista.toSVG() + "\n";
            }
          }
          if (aux == 0 && p%2 == 1) {
            aux = 1;
            aux1 = 0;
          } else if (aux == 1 && p%2 == 1){
            aux = 0;
            aux1 = 1;
          }
          p++;
        }
        indice++;
      }
      return svg;
    }

    /*
     * Método auxiliar graficaMonticulo.
     * Que recibe una colección y regresa un String con el formato svg
     * de un montículo mínimo.
     */
    private String graficaMonticulo(Lista<Integer> coleccion) {
      /* Construimos el montículo. */
      Lista<ValorIndexable<Integer>> l = new Lista<ValorIndexable<Integer>>();
      for (Integer m : coleccion) {
        double p = 5.1;
        ValorIndexable<Integer> val = new ValorIndexable<Integer>(m, p);
        l.agrega(val);
      }
      MonticuloMinimo<ValorIndexable<Integer>> mont = new MonticuloMinimo<>(l);
      int elementos = mont.getElementos();
      double a = Math.log(elementos)/Math.log(2);
      int alt = (int)(Math.floor(a));
      int pos = (int)(Math.pow(2,alt));
      svg += gMont(mont,0,pos,0,pos);
      svg += "\n";
      return svg;
    }

    /*
     * Método auxiliar recursivo gMont.
     * Que se encarga de devolver en formato svg un montículo mínimo.
     * Recibe un montículo mínimo a graficar.
     */
    private String gMont(MonticuloMinimo<ValorIndexable<Integer>> monticulo, int indice, int posicion, int n, int p) {
        String svgMont = "";
        int pos = posicion*40;
        int aux = p/2;
        int vert = indice;
        int hijoIzq = 2*vert+1;
        int hijoDer = 2*vert+2;
        Circulo v = new Circulo(pos-20,40+(100*n),20,"black","white");
        Texto tex = new Texto(monticulo.get(vert).getElemento().toString(),pos-30,40+(100*n),"black");
        svgMont += v.toSVG() + "\n";
        svgMont += tex.toSVG() + "\n";
        /* Caso base. */
        if (hijoIzq >= monticulo.getElementos() ) {
          return svgMont;
        }
        Linea linI = new Linea(pos-20, 60+(100*n), pos-(aux*40)-20, 20+(100*(n+1)));
        svgMont += linI.toSVG() + "\n";
        svgMont += gMont(monticulo,hijoIzq,posicion-aux,n+1,aux);
        if (hijoDer >= monticulo.getElementos() ) {
          return svgMont;
        }
        Linea linD = new Linea(pos-20, 60+(100*n), pos+(aux*40)-20,20+(100*(n+1)));
        svgMont += linD.toSVG() + "\n";
        svgMont += gMont(monticulo,hijoDer,posicion+aux,n+1,aux);
        return svgMont;
    }
}
